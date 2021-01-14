package com.example.bookself_main_deneme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailsActivity extends AppCompatActivity {

    TextView item_book_title, item_book_author, item_book_genre, item_book_req;
    ImageView item_book_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Book Details");

        String bookName = getIntent().getStringExtra("bookName");
        String bookGenre = getIntent().getStringExtra("bookGenre");
        String bookAuthor = getIntent().getStringExtra("bookAuthor");

        item_book_title = findViewById(R.id.item_book_title);
        item_book_author = findViewById(R.id.item_book_author);
        item_book_genre = findViewById(R.id.item_book_genre);
        item_book_img = findViewById(R.id.item_book_img);

        item_book_title.setText(bookName);
        item_book_author.setText(bookAuthor);
        item_book_genre.setText(bookGenre);

        item_book_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookDetailsActivity.this, OtherProfile.class);
                startActivity(i);
            }
        });


    }

}