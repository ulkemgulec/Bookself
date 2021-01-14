package com.example.bookself_main_deneme.recylerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookself_main_deneme.BookDetailsActivity;
import com.example.bookself_main_deneme.R;
import com.example.bookself_main_deneme.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends  RecyclerView.Adapter<BookAdapter.MyHolder>{

    Context context;
    List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        String uid = bookList.get(position).getUid();
        String bookName = bookList.get(position).getBookName();
        String bookGenre = bookList.get(position).getBookGenre();
        String bookAuthor = bookList.get(position).getBookAuthor();
        String bookImage = bookList.get(position).getBookImg();

        holder.pbookName.setText(bookName);
        holder.pbookGenre.setText(bookGenre);
        holder.pbookAuthor.setText(bookAuthor);

        try{
            Picasso.get().load(bookImage).placeholder(R.drawable.l1).into(holder.pbookImage);
        }catch (Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, BookDetailsActivity.class);
                intent.putExtra("bookName", bookName);
                intent.putExtra("bookAuthor", bookAuthor);
                intent.putExtra("bookGenre", bookGenre);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{


        ImageView pbookImage;
        TextView pbookName, pbookAuthor, pbookGenre;

        public MyHolder (@NonNull View itemView){
            super(itemView);

            pbookImage = itemView.findViewById(R.id.item_book_img);
            pbookName = itemView.findViewById(R.id.item_book_title);
            pbookAuthor = itemView.findViewById(R.id.item_book_author);
            pbookGenre = itemView.findViewById(R.id.item_book_genre);

        }
    }
}
