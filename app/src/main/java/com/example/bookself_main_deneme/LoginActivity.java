
//Ülkem Güleç

package com.example.bookself_main_deneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    //login
    EditText edit_email, edit_password;

    TextView havenot_account;
    Button button_login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login Screen");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        button_login = findViewById(R.id.button_login);
        havenot_account = findViewById(R.id.havenot_account);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edit_email.getText().toString();
                String passw = edit_password.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    edit_email.setError("Invalid Email");
                    edit_email.setFocusable(true);

                }else{
                    loginUser(email, passw);
                }

            }
        });

        havenot_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    private void loginUser(String email, String passw) {

        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (task.getResult().getAdditionalUserInfo().isNewUser()){

                                String email = user.getEmail();
                                String uid = user.getUid();

                                HashMap<Object, String> hashMap = new HashMap<>();

                                hashMap.put("email", email);
                                hashMap.put("uid", uid);
                                hashMap.put("name", "");
                                hashMap.put("surname", "");
                                hashMap.put("birth", "");
                                hashMap.put("country", "");
                                hashMap.put("city", "");
                                hashMap.put("image", "");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                            }

                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return super.onSupportNavigateUp();
    }

}