package com.sagar.betaversion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    Uri uri;
    byte[] image;
    DatabaseReference databaseRef;
    StorageReference imageRef;
    FirebaseAuth firebaseAuth;
    SharedPreferences preferences;
    ImageView userImage;
    EditText userNameView,mobileNoView,addressView;
    TextView emailView;
    String Uid;
    String username,mobile,email,address;
    SharedPreferences.Editor editor;
    Map<String,Object> mp= new HashMap<>();
    private static final int IMAGE_REQUEST=1;
    public void update(View view)
    {
        username=userNameView.getText().toString();
        mobile=mobileNoView.getText().toString();
        email=emailView.getText().toString();
        address=addressView.getText().toString();
        mp.put("user_name",username);
        mp.put("email",email);
        mp.put("mobile",mobile);
        mp.put("address",address);
        databaseRef.child(Uid).updateChildren(mp);
        editor.putString("user_name",username);
        editor.putString("email",email);
        editor.putString("mobile",mobile);
        editor.putString("address",address);
        editor.apply();

    }
    public void ChooseImage()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && requestCode==RESULT_OK && data!=null)
        {
            uri=data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.WEBP, 40, stream);
            image = stream.toByteArray();
            Picasso.get().load(uri).into(userImage);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseRef= FirebaseDatabase.getInstance().getReference("Users");
        imageRef= FirebaseStorage.getInstance().getReference("UserImage");
        firebaseAuth=FirebaseAuth.getInstance();
        Uid=firebaseAuth.getUid();
        preferences=getSharedPreferences("UserData",MODE_PRIVATE);
        editor=preferences.edit();
        username=preferences.getString("user_name","");
        mobile=preferences.getString("mobile","");
        email=preferences.getString("email","");
        address=preferences.getString("address","");

        userNameView=findViewById(R.id.accUserName);
        mobileNoView=findViewById(R.id.accMobile);
        emailView=findViewById(R.id.accEmail);
        addressView=findViewById(R.id.accAddress);

        userNameView.setHint("Enter Username");
        mobileNoView.setHint("Enter mobile number");
        addressView.setHint("Enter address");

        userNameView.setText(username);
        mobileNoView.setText(mobile);
        emailView.setText(email);
        addressView.setText(address);
    }
}
