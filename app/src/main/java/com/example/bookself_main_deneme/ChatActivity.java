
//Ülkem Güleç

package com.example.bookself_main_deneme;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView chat_recyler;
    ImageView chatImage;
    TextView chatName;
    EditText messageEt;
    ImageButton sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        chat_recyler = findViewById(R.id.chat_recyler);
        chatImage = findViewById(R.id.chatImage);
        chatName = findViewById(R.id.chatName);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);


    }




}