package com.example.bookself_main_deneme;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddBookActivity extends AppCompatActivity {


    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;

    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;


    String[] cameraPermissions;
    String[] storagePermissions;


    ActionBar actionBar;

    FirebaseAuth firebaseAuth;
    DatabaseReference userDbRef;

    Button button_share;
    EditText add_bookName, add_bookGenre, add_bookAuthor, add_bookPubliser, add_bookRequest;
    ImageView add_bookPhotos;

    Uri image_uri = null;

    String name, email, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Share New Book");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();


        userDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = userDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    name = ""+ ds.child("name").getValue();
                    email = ""+ ds.child("email").getValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        button_share = findViewById(R.id.button_share);
        add_bookName = findViewById(R.id.add_bookName);
        add_bookPubliser = findViewById(R.id.add_publisher);
        add_bookGenre = findViewById(R.id.add_bookGenre);
        add_bookAuthor = findViewById(R.id.add_bookAuthor);
        add_bookRequest = findViewById(R.id.add_bookRequest);

        add_bookPhotos = findViewById(R.id.add_bookPhotos);


        add_bookPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });


        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bookName = add_bookName.getText().toString().trim();
                String bookAuthor = add_bookAuthor.getText().toString().trim();
                String bookGenre = add_bookGenre.getText().toString().trim();
                String bookPublisher = add_bookPubliser.getText().toString().trim();
                String bookRequest = add_bookRequest.getText().toString().trim();

                if(TextUtils.isEmpty(bookName)){
                    Toast.makeText(AddBookActivity.this, "Enter Book Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookAuthor)){
                    Toast.makeText(AddBookActivity.this, "Enter Book Author", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookGenre)){
                    Toast.makeText(AddBookActivity.this, "Enter Book Genre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookPublisher)){
                    Toast.makeText(AddBookActivity.this, "Enter Book Publisher", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(bookRequest)){
                    Toast.makeText(AddBookActivity.this, "Enter Your Requests", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (image_uri == null){
                    uploadData(bookName, bookAuthor, bookGenre, bookPublisher, bookRequest, "noImage");
                }
                else{
                    uploadData(bookName, bookAuthor, bookGenre, bookPublisher, bookRequest, String.valueOf(image_uri));

                }
            }
        });


    }

    private void uploadData(String bookName, String bookAuthor, String bookGenre, String bookPublisher, String bookRequest, String uri) {

        String filePathAndName = "Posts/" + "post/";

        if(!uri.equals("noImage")){

            StorageReference ref = FirebaseStorage.getInstance().getReference().child(filePathAndName);
            ref.putFile(Uri.parse(uri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());

                    String downloadUri = uriTask.getResult().toString();

                    if(uriTask.isSuccessful()){

                        HashMap<Object, String> hashMap = new HashMap<>();

                        hashMap.put("uid", uid);
                        hashMap.put("name", name);
                        hashMap.put("bookName", bookName);
                        hashMap.put("bookAuthor", bookAuthor);
                        hashMap.put("bookGenre", bookGenre);
                        hashMap.put("bookPublisher", bookPublisher);
                        hashMap.put("bookRequest", bookRequest);
                        hashMap.put("bookPhoto", downloadUri);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Book");
                        ref.child(uid).setValue(hashMap);

                        add_bookName.setText("");
                        add_bookAuthor.setText("");
                        add_bookGenre.setText("");
                        add_bookPubliser.setText("");
                        add_bookRequest.setText("");
                        add_bookName.setText("");
                        image_uri = null;
                    }
                }
            });
        }else{

            HashMap<Object, String> hashMap = new HashMap<>();

            hashMap.put("uid", uid);
            hashMap.put("name", name);
            hashMap.put("email", email);
            hashMap.put("bookName", bookName);
            hashMap.put("bookAuthor", bookAuthor);
            hashMap.put("bookGenre", bookGenre);
            hashMap.put("bookPublisher", bookPublisher);
            hashMap.put("bookRequest", bookRequest);
            hashMap.put("bookPhoto", "noImage");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Book");
            ref.child(uid).setValue(hashMap);

            add_bookName.setText("");
            add_bookAuthor.setText("");
            add_bookGenre.setText("");
            add_bookPubliser.setText("");
            add_bookRequest.setText("");
            add_bookName.setText("");
            image_uri = null;

        }
    }
    private void showImagePickDialog() {

        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Image from");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromCamera();
                    }
                }if(i==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues cv = new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION, "Temp Descr");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);

    }

    private boolean checkStoragePermission(){

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){

        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUserStatus();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkUserStatus();
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            email = user.getEmail();
            uid = user.getUid();
        }else {
            startActivity(new Intent(this, MainLoginActivity.class));
            finish();
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Camera and Storage permissions are necessary", Toast.LENGTH_LONG).show();
                    }
                }else{
                }
            }break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(storageAccepted && storageAccepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Storage permission necessary", Toast.LENGTH_LONG).show();
                    }

                }else{

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                add_bookPhotos.setImageURI(image_uri);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                add_bookPhotos.setImageURI(image_uri);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);

    }
}

