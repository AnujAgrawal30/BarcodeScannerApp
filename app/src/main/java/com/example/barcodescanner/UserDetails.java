package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserDetails extends AppCompatActivity {
    String minum;
    Button scan;
//    api2.moodi.org/userbymino/{}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        scan = findViewById(R.id.scan);
        minum = getIntent().getStringExtra("MI number");
        Toast.makeText(getApplicationContext(), minum, Toast.LENGTH_LONG).show();
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
