
//Emre Ertürk

package com.example.bookself_main_deneme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookself_main_deneme.adapters.ChatAdapter;
import com.example.bookself_main_deneme.model.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    ChatAdapter chatAdapter;
    List<Chat> chat;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chat_recylerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        chat = new ArrayList<>();
        getAllUsers();

        return view;

    }

    private void getAllUsers() {

        final FirebaseUser fChat = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chat.clear();;
                for (DataSnapshot ds: snapshot.getChildren()){

                    Chat chatClass = ds.getValue(Chat.class);

                    if(!chatClass.getUid().equals(fChat.getUid())){
                            chat.add(chatClass);
                        }

                    chatAdapter = new ChatAdapter(getActivity(), chat);
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}