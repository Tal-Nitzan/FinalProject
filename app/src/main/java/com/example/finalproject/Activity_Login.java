package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Login extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        btn = findViewById(R.id.temp_button);

        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // Check for credentials TODO
                Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}