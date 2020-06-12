package com.sagar.betaversion.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.sagar.betaversion.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class myAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

//    ImageView userImage;
    CircleImageView userImage;
    TextView userNameView,mobileNoView,emailView,addressView;
    String username,mobile,email,address,retrievedImage;
    SharedPreferences preferences;


    public void UpdateProfile(View view)
    {
        Intent intent= new Intent(getApplicationContext(), profile.class);
        startActivity(intent);
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
        else{
            userNameView.setText("Update User Name.");
        }

        if(mobile!=null )
            mobileNoView.setText(mobile);
        else{
            mobileNoView.setText("Update Mobile Number.");
            mobileNoView.setTextSize(12);
        }

        if(email!=null)
            emailView.setText(email);

        if(address!=null)
            addressView.setText(address);
        else{
            addressView.setText("Update address");
            addressView.setTextSize(12);
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


