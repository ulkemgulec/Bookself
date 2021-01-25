
//Emre Ertürk

package com.example.bookself_main_deneme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_email, edit_password, edit_name, edit_surname, edit_birth, edit_country, edit_city;
    Button button_register;
    TextView have_account;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        edit_name = findViewById(R.id.edit_name);
        edit_surname = findViewById(R.id.edit_surname);
        edit_birth = findViewById(R.id.edit_birth);
        edit_country = findViewById(R.id.edit_country);
        edit_city = findViewById(R.id.edit_city);
        button_register = findViewById(R.id.button_register);
        have_account = findViewById(R.id.have_account);


        mAuth = FirebaseAuth.getInstance();


        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edit_email.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                String name = edit_name.getText().toString().trim();
                String surname = edit_surname.getText().toString().trim();
                String birth = edit_birth.getText().toString().trim();
                String country = edit_country.getText().toString().trim();
                String city = edit_city.getText().toString().trim();


                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    edit_email.setError("Invalid Email");
                    edit_email.setFocusable(true);

                }else if (password.length()<6) {

                    edit_password.setError("Invalid password");
                    edit_password.setFocusable(true);


                }else{
                    registerUser(email, password, name, surname, birth, country, city);
                }

            }
        });

        have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser(String email, String password, String name, String surname, String birth, String country, String city) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();

                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", name);
                            hashMap.put("surname", surname);
                            hashMap.put("birth", birth);
                            hashMap.put("country", country);
                            hashMap.put("city", city);
                            hashMap.put("image", "");

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("Users");

                            reference.child(uid).setValue(hashMap);


                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}



