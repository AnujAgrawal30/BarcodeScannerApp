package com.example.barcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.barcodescanner.retrofit.API;
import com.example.barcodescanner.retrofit.User;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    Boolean scanned = false;
    BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
    }



    private void initialiseDetectorsAndSources() {
//        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
//                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {

                        @Override
                        public void run() {
//                            if (scanned){
//                                return;
//                            }
//                            scanned = true;
                            barcodeDetector.release();
                            intentData = barcodes.valueAt(0).displayValue;
                            txtBarcodeValue.setText(intentData);

                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(API.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            API api = retrofit.create(API.class);
                            Call<User> call = api.getStudent(intentData);

                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                                    if (response.body() != null) {
                                        User student = response.body();
                                        if (student.getBarcode_number() == null){
                                            next();
                                        }
                                        else {
//                                            Toast.makeText(getApplicationContext(), "This MI number is already assigned a barcode", Toast.LENGTH_LONG).show();
                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                                            builder1.setMessage("This MI number is already assigned a barcode");
                                            Intent intent = new Intent(getApplicationContext(), confirmation.class);
                                            intent.putExtra("MI number", intentData);
                                            startActivity(intent);
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "There was some error, try again", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                                    Log.e("Retrofit", t.toString());
                                }
                            });
                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
        scanned = false;
    }

    private void next(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String url = "http://192.168.0.11:8000/polls/lk/" + intentData;


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError){
                    barcodeDetector.release();
                }
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        if(!scanned) {
            Intent intent = new Intent(getApplicationContext(), UserDetails.class);
            intent.putExtra("MI number", intentData);
            startActivity(intent);
            scanned = true;
        }
// Add the request to the RequestQueue.
        queue.cancelAll(queue);
        queue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
        txtBarcodeValue.setText("No Barcode Detected");
    }
}
