package com.example.bookself_main_deneme.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookself_main_deneme.model.Chat;
import com.example.bookself_main_deneme.ChatActivity;
import com.example.bookself_main_deneme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder>{

    Context context;
    List<Chat> chatUser;

    public ChatAdapter(Context context, List<Chat> chatUser) {
        this.context = context;
        this.chatUser = chatUser;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        String hisUid = chatUser.get(position).getUid();

        String chatImage = chatUser.get(position).getImage();
        String chatName = chatUser.get(position).getName();

        holder.chatName.setText(chatName);


        try{
            //Picasso
            Picasso.get().load(chatImage).placeholder(R.drawable.l1).into(holder.chatImage);
        }catch (Exception e){

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("name", hisUid);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return chatUser.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView chatImage;
        TextView chatName, chatMesseage;

        public MyHolder(@NonNull View itemView){
            super(itemView);

            chatImage = itemView.findViewById(R.id.item_chat_avatar);
            chatName = itemView.findViewById(R.id.item_chat_name);

        }
    }

}
