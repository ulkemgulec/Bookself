
//Ülkem Güleç


package com.example.bookself_main_deneme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class OtherProfile extends AppCompatActivity {


    Button button_sendMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Other Profile");

        button_sendMessage = findViewById(R.id.button_sendMessage);


        button_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            Intent i = new Intent(OtherProfile.this, ChatActivity.class);
            startActivity(i);


            }
        });


    }
}