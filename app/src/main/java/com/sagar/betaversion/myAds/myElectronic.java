package com.sagar.betaversion.myAds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import com.sagar.betaversion.FinalProductView;
import com.sagar.betaversion.ListAd;
import com.sagar.betaversion.R;
import com.sagar.betaversion.RecViewItemClickListener;
import com.sagar.betaversion.listAdAdapter;
import com.sagar.betaversion.listAdModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class myElectronic extends AppCompatActivity implements RecViewItemClickListener {

    public static RecyclerView recyclerView;
    String Uid,type;
    DatabaseReference userAd;
    FirebaseDatabase firebaseDatabase;
    public static listAdAdapter listAdAdapter;
    public static TextView noAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_electronic);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Ads");
        }
        noAds=findViewById(R.id.noAdsTV);
        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Uid=firebaseUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final List<listAdModel> listAdModelList=new ArrayList<>();
        final long[] c = {0};
        final long[] count = {-1};
        userAd=firebaseDatabase.getReference().child("UserAd").child(Uid).child("ElectronicId");
        userAd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean temp=true;
                Iterator iterator=dataSnapshot.getChildren().iterator();
                count[0] =dataSnapshot.getChildrenCount();
                Log.i("Array","yessssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

                while(iterator.hasNext())
                {
                     String key=((DataSnapshot)iterator.next()).getKey();
                     Log.i("ArrayList","This is key "+key);
                     Query reference=FirebaseDatabase.getInstance().getReference().child("ElectronicAd").child(key);
                     reference.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             Log.i("Array","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                             String model=dataSnapshot.getValue(ElectronicsAd.class).getModel();
                             String brand=dataSnapshot.getValue(ElectronicsAd.class).getBrand();
                             int price=dataSnapshot.getValue(ElectronicsAd.class).getSellingPrice();
                             String img1=dataSnapshot.getValue(ElectronicsAd.class).getImg1();
                             String ad_id=dataSnapshot.getValue(ElectronicsAd.class).getId();
                             listAdModel adModel=new listAdModel(model,brand,price,img1,ad_id);
                             Log.i("INFO",model+brand+(price)+img1+ad_id);
                             Log.i("Array","bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                             listAdModelList.add(adModel);
                             Log.i("Array","cccccccccccccccccccccccccccccccccccccccccccccccc");
                             c[0]++;
                             Log.i("Array", Long.toString(c[0]));
                             if(c[0]==count[0])
                             {
                                 //CODE HERE
                                 Log.i("qwertyuiop",model+brand+(price)+img1+ad_id);
                                 if(listAdModelList.size()==0){
                                     noAds.setVisibility(View.VISIBLE);
                                     recyclerView.setVisibility(View.INVISIBLE);

                                 }
                                 Log.i("Array","dddddddddddddddddddddddddddddddddddddddddd");
                                 listAdAdapter=new listAdAdapter(listAdModelList, myElectronic.this,false,type,Uid,myElectronic.this.getClass().getSimpleName());
                                 recyclerView.setAdapter(listAdAdapter);
                                 listAdAdapter.notifyDataSetChanged();

                             }
                             Log.i("Array","eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

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

    @Override
    public void onItemClickListener(int position, String adId) {
        Intent intent=new Intent(getApplicationContext(), FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
