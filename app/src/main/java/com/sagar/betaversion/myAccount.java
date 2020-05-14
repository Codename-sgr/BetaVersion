package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class myAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageView userImage;
    TextView userNameView,mobileNoView,emailView,addressView;
    String username,mobile,email,address;
    SharedPreferences preferences;


    public void UpdateProfile(View view)
    {
        Intent intent= new Intent(getApplicationContext(),profile.class);
        startActivity(intent);
        finish();
    }




    public void logoutUser(View view){
        firebaseAuth.signOut();
        preferences.edit().clear();
        preferences.edit().apply();
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
        preferences=getSharedPreferences("UserData",MODE_PRIVATE);
        username=preferences.getString("user_name","Enter Your User Name.");
        mobile=preferences.getString("mobile","Enter your contact number.");
        email=preferences.getString("email","Enter your email");
        address=preferences.getString("address","Enter your address");
        userNameView=findViewById(R.id.accUserName);
        mobileNoView=findViewById(R.id.accMobile);
        emailView=findViewById(R.id.accEmail);
        addressView=findViewById(R.id.accAddress);
        userNameView.setText(username);
        mobileNoView.setText(mobile);
        emailView.setText(email);
        addressView.setText(address);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}


