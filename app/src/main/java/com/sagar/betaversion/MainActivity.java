package com.sagar.betaversion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    ImageButton myAccount,newAd,vehicleAds,electronicAds,bookAds,sportAds,furnitureAds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

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
