
//Ülkem Güleç

package com.example.bookself_main_deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GatheringInfoActivity extends AppCompatActivity {


    TextInputLayout name_text_input, surname_text_input, birth_text_input, country_text_input, city_text_input;

    Button next;

    FirebaseDatabase roodNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_info);

        name_text_input = findViewById(R.id.name_text_input);
        surname_text_input = findViewById(R.id.surname_text_input);
        birth_text_input = findViewById(R.id.birth_text_input);
        country_text_input = findViewById(R.id.country_text_input);
        city_text_input = findViewById(R.id.city_text_input);
        next = findViewById(R.id.button_next);




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roodNode = FirebaseDatabase.getInstance();

                reference = roodNode.getReference("users");

                reference.setValue("first database");

                Intent i = new Intent(GatheringInfoActivity.this, MainActivity.class);
                startActivity(i);

            }
        });


    }
}