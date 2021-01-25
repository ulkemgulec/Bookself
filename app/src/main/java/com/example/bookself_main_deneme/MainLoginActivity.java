//Authors
//Emre Ertürk 170709007
//Ülkem Güleç 170709035



package com.example.bookself_main_deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainLoginActivity extends AppCompatActivity {



    Button mRegisterBtn, mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);


        mRegisterBtn = findViewById(R.id.btn_register);
        mLoginBtn = findViewById(R.id.btn_login);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginActivity.this,
                        RegisterActivity.class));
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainLoginActivity.this,
                        LoginActivity.class));
            }
        });




    }





}