package com.sagar.betaversion.myAds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sagar.betaversion.AdCategory.ElectronicsAd;
import com.sagar.betaversion.AdCategory.VehicleAd;
import com.sagar.betaversion.R;
import com.sagar.betaversion.listAdModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class myElectronic extends AppCompatActivity {
    String Uid;
    DatabaseReference userAd;
    ArrayList<ElectronicsAd> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_electronic);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Uid=firebaseUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final List<listAdModel> listAdModelList=new ArrayList<>();
        final long[] c = {0};
        final long[] count = {-1};
        userAd=firebaseDatabase.getReference().child("UserAd").child(Uid).child("ElectronicId");
        userAd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean temp=true;
                Iterator iterator=dataSnapshot.getChildren().iterator();
                count[0] =dataSnapshot.getChildrenCount();
                Log.i("Array", Long.toString(count[0]));

                while(iterator.hasNext())
                {
                     String key=((DataSnapshot)iterator.next()).getKey();
                     Log.i("ArrayList","This is key "+key);
                     Query reference=FirebaseDatabase.getInstance().getReference().child("ElectronicAd").child(key);
                     reference.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             Log.i("Array", dataSnapshot.getValue(ElectronicsAd.class).getImg1());
                             String model=dataSnapshot.getValue(ElectronicsAd.class).getModel();
                             String brand=dataSnapshot.getValue(ElectronicsAd.class).getBrand();
                             int price=dataSnapshot.getValue(ElectronicsAd.class).getSellingPrice();
                             String img1=dataSnapshot.getValue(ElectronicsAd.class).getImg1();
                             String ad_id=dataSnapshot.getValue(ElectronicsAd.class).getId();
                             listAdModel adModel=new listAdModel(model,brand,price,img1,ad_id);

                             listAdModelList.add(adModel);
                             c[0]++;
                             Log.i("Array", Long.toString(c[0]));
                         }
                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
