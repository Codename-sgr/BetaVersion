package com.sagar.betaversion.myAds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagar.betaversion.AdCategory.VehicleAd;
import com.sagar.betaversion.R;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class myVehicle extends AppCompatActivity {


    String Uid;
    DatabaseReference userAd;
    ArrayList<VehicleAd> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_electronic);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Ads");
        }


        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Uid=firebaseUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userAd=firebaseDatabase.getReference().child("VehicleAd");
        userAd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                    final VehicleAd vehicleAd=vehicleSnapShot.getValue(VehicleAd.class);
                    if(vehicleAd.getUser_id().matches(Uid))
                    {
                        Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+vehicleAd.getId()+" my user "+Uid);
                        arrayList.add(vehicleAd);
                    }


                }
                Log.i("arrayList", String.valueOf(arrayList.size()));
                //HERE YOU HAVE YOUR ARRAYLIST

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
