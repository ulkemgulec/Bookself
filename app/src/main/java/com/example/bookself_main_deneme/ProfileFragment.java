
//Ülkem Güleç

package com.example.bookself_main_deneme;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class ProfileFragment extends Fragment {


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ImageView imageAvatar;
    TextView textName, textSurname, textBirth, textCity;

    Button button_share;


    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        button_share = view.findViewById(R.id.button_shareBook);
        imageAvatar = view.findViewById(R.id.imageView_avatar);
        textName = view.findViewById(R.id.textView_profileName);
        textSurname = view.findViewById(R.id.textView_profileSurname);
        textBirth = view.findViewById(R.id.textView_profileDate);
        textCity = view.findViewById(R.id.textView_profileCity);


        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AddBookActivity.class);
                startActivity(i);
            }
        });


        Query query =databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){

                    String name = ""+ ds.child("name").getValue();
                    String city = ""+ ds.child("city").getValue();
                    String surname = ""+ ds.child("surname").getValue();
                    String birth = ""+ ds.child("birth").getValue();
                    String image = ""+ ds.child("image").getValue();

                    textName.setText(name);
                    textBirth.setText(birth);
                    textCity.setText(city);
                    textSurname.setText(surname);

                    try{
                        Picasso.get().load(image).into(imageAvatar);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.avatar).into(imageAvatar);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}