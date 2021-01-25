
//Ülkem Güleç

package com.example.bookself_main_deneme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookself_main_deneme.model.Book;
import com.example.bookself_main_deneme.recylerview.BookAdapter;
import com.example.bookself_main_deneme.recylerview.CustomItemAnimator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{

     RecyclerView rvBooks;
     BookAdapter bookAdapter;
     List<Book> bookList;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvBooks = view.findViewById(R.id.rv_book);

        rvBooks.setLayoutManager(new LinearLayoutManager(getActivity()));

        bookList = new ArrayList<>();

        loadPost();

        return view;
    }

    private void loadPost() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Book");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Book bookClass = ds.getValue(Book.class);

                    bookList.add(bookClass);
                    rvBooks.setItemAnimator(new CustomItemAnimator());
                    bookAdapter = new BookAdapter(getActivity(), bookList);
                    rvBooks.setAdapter(bookAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}