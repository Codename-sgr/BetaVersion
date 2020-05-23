package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class myAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageView userImage;
    TextView userNameView,mobileNoView,emailView,addressView;
    String username,mobile,email,address,retrievedImage;
    SharedPreferences preferences;


    public void UpdateProfile(View view)
    {
        Intent intent= new Intent(getApplicationContext(),profile.class);
        startActivity(intent);
        finish();
    }




    public void logoutUser(View view){
        firebaseAuth.signOut();
        SharedPreferences pref=getSharedPreferences("UserData",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void reloadUserData()
    {
        username=preferences.getString("user_name","Enter Your User Name.");
        mobile=preferences.getString("mobile",null);
        email=preferences.getString("email","Enter your email");
        address=preferences.getString("address",null);
        retrievedImage=preferences.getString("image","");
        userImage=findViewById(R.id.userImage);
        if(!retrievedImage.matches("") && retrievedImage!=null) {
            Bitmap bitmap = StringToBitMap(retrievedImage);
            userImage.setImageBitmap(bitmap);
        }
        if(username!=null)
            userNameView.setText(username);
        else
            userNameView.setText("Enter User Name.");
        if(mobile!=null )
            mobileNoView.setText(mobile);
        else
            mobileNoView.setText("Enter Mobile Number.");
        if(email!=null)
            emailView.setText(email);
        if(address!=null)
            addressView.setText(address);
        else
             addressView.setText("Enter address");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("My Account");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        firebaseAuth=FirebaseAuth.getInstance();
        userNameView=findViewById(R.id.accUserName);
        mobileNoView=findViewById(R.id.accMobile);
        emailView=findViewById(R.id.accEmail);
        addressView=findViewById(R.id.accAddress);
        preferences=getSharedPreferences("UserData",MODE_PRIVATE);
        reloadUserData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);

            InputStream inputStream = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}


