package com.sagar.betaversion;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.myAds.myAdActivity;


public class MainActivity extends AppCompatActivity {


    ImageButton myAccount, newAd, vehicleAds, electronicAds, bookAds, miscAds,MyAds;
    ProgressDialog progressDialog;
    String username;
    Boolean dp;
    StorageReference storageReference;

    @Override
    protected void onResume() {
        loadUserData();
        super.onResume();
    }

    public void loadUserData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait!");

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String userKey = user.getUid();
        SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
        username = pref.getString("user_name", "");
        if (username.matches("")) {
            progressDialog.show();
            Log.i("work", "preference clear works fine");
            final SharedPreferences.Editor editor = pref.edit();
            mDb.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("work", userKey);
                    username = String.valueOf(dataSnapshot.child("user_name").getValue());
                    Log.i("Name: ", username);
                    getSupportActionBar().setTitle("Welcome " + username);
                    String email = String.valueOf(dataSnapshot.child("email").getValue());
                    String mobile = String.valueOf(dataSnapshot.child("mobile").getValue());
                    String address = String.valueOf(dataSnapshot.child("address").getValue());
                    dp=(Boolean)dataSnapshot.child("dp").getValue();
                    editor.putString("user_name", username);
                    if(!email.matches("null"))
                        editor.putString("email", email);
                    if(!mobile.matches("null"))
                        editor.putString("mobile", mobile);
                    if(!address.matches("null"))
                        editor.putString("address", address);
                    if(dp==true)
                    {
                        final long TEN_MEGABYTE=10*1024*1024;
                        storageReference.child(userKey+".jpg").getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                String img_str = Base64.encodeToString(bytes, 0);
                                editor.putString("image",img_str);
                                editor.apply();
                                progressDialog.dismiss();


                            }
                        });
                    }
                    else
                    {
                        editor.apply();
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        } else
            getSupportActionBar().setTitle("Welcome " + username);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storageReference = FirebaseStorage.getInstance().getReference().child("UserImage");
        myAccount = findViewById(R.id.myAccountButton);
        newAd = findViewById(R.id.newAdButton);
        MyAds=findViewById(R.id.myAdButton);
        vehicleAds = findViewById(R.id.vehicleButton);
        miscAds=findViewById(R.id.miscButton);
        electronicAds = findViewById(R.id.electronicsButton);
        bookAds = findViewById(R.id.booksButton);


        final Intent intent = new Intent(getApplicationContext(), ListAd.class);
        vehicleAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Vehicle");
                startActivity(intent);
            }
        });
        electronicAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Electronic");
                startActivity(intent);
            }
        });
        bookAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Books");
                startActivity(intent);
            }
        });
        miscAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Miscellaneous");
                startActivity(intent);
            }
        });


        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myAccount.class));
            }
        });

        newAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), newAdActivity.class));
            }
        });
        MyAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myAdActivity.class));
            }
        });


    }


}
