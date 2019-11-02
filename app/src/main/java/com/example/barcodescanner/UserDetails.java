package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodescanner.retrofit.API;
import com.example.barcodescanner.retrofit.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetails extends AppCompatActivity {
    String minum;
    Button scan;
    TextView name, mi_num, email, mob, gender, yos, pcity, pcollege;
//    api2.moodi.org/userbymino/{}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        scan = findViewById(R.id.scan);
        name = findViewById(R.id.name);
        mi_num = findViewById(R.id.mi_number);
        email = findViewById(R.id.email);
        mob = findViewById(R.id.mobile_number);
        gender = findViewById(R.id.gender);
        yos = findViewById(R.id.year_of_study);
        pcity = findViewById(R.id.present_city);
        pcollege = findViewById(R.id.present_college);
        minum = getIntent().getStringExtra("MI number");
        setView(minum);
        Toast.makeText(getApplicationContext(), "ID: " + minum, Toast.LENGTH_LONG).show();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                finishActivity(0);
            }
        });
    }
    private void setView(String minum){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<User> call = api.getStudent(minum);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    User student = response.body();
                    name.setText(student.getName());
                    mi_num.setText(student.getMi_number());
                    email.setText(student.getEmail());
                    mob.setText(student.getMobile_number());
                    gender.setText(student.getGender());
                    yos.setText(student.getYear_of_study());
                    pcity.setText(student.getPresent_city());
                    pcollege.setText(student.getPresent_college());
                }
                else{
                    Toast.makeText(getApplicationContext(), "There was some error, try again motherfucker", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                Log.e("Retrofit", t.toString());
            }
        });
    }
}
