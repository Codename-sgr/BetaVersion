package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageButton myAccount,newAd,vehicleAds,electronicAds,bookAds,sportAds,furnitureAds;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    String username;
    @Override
    protected void onResume()
    {
        loadUserData();

        super.onResume();
    }
    public void loadUserData()
    {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait!");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();
        SharedPreferences pref=getSharedPreferences("UserData",MODE_PRIVATE);
        username=pref.getString("user_name","");
        if(username.matches(""))
        {
            progressDialog.show();
            final SharedPreferences.Editor editor= pref.edit();
            mDb.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    username = String.valueOf(dataSnapshot.child("user_name").getValue());
                    Log.i("Name: ",username);
                    getSupportActionBar().setTitle("Welcome "+username);
                    String email=String.valueOf(dataSnapshot.child("email").getValue());
                    String mobile=String.valueOf(dataSnapshot.child("mobile").getValue());
                    String address=String.valueOf(dataSnapshot.child("address").getValue());
                    String image=String.valueOf(dataSnapshot.child("image").getValue());
                    editor.putString("user_name",username);
                    editor.putString("email",email);
                    editor.putString("mobile",mobile);
                    editor.putString("address",address);
                    editor.putString("image",image);
                    editor.apply();
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }
        else
            getSupportActionBar().setTitle("Welcome "+username);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUserData();
        myAccount=findViewById(R.id.myAccountButton);
        newAd= findViewById(R.id.newAdButton);
        vehicleAds=findViewById(R.id.vehicleButton);
        electronicAds=findViewById(R.id.electronicsButton);
        bookAds=findViewById(R.id.booksButton);
        sportAds=findViewById(R.id.sportsButton);
        furnitureAds=findViewById(R.id.furnitureButton);
        final Intent intent=new Intent(getApplicationContext(),ListAd.class);
        vehicleAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Vehicle");
                startActivity(intent);
            }
        });
        electronicAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Electronic");
                startActivity(intent);
            }
        });
        bookAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Books");
                startActivity(intent);
            }
        });
        sportAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Sports");
                startActivity(intent);
            }
        });
        furnitureAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type","Furniture");
                startActivity(intent);
            }
        });
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),myAccount.class));
            }
        });

       newAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),newAdActivity.class));
            }
        });


    }


}
