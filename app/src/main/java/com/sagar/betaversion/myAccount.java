package com.sagar.betaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class myAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageView userImage;
    EditText userName,mobileNo,email,address;


    public void logoutUser(View view){
        firebaseAuth.signOut();
        Intent intent=new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        firebaseAuth=FirebaseAuth.getInstance();

        userName=findViewById(R.id.accUserName);
        mobileNo=findViewById(R.id.accMobile);
        email=findViewById(R.id.accEmail);
        address=findViewById(R.id.accAddress);

    }

}
