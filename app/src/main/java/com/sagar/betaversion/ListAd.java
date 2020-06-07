package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListAd extends AppCompatActivity implements RecViewItemClickListener{
     RecyclerView recyclerView;
     TextView noAds;
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;
     String Uid;
     List<listAdModel> listAdModelList=new ArrayList<>();
    public static listAdAdapter listAdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Ads");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        Uid=user.getUid();
//      databaseReference.keepSynced(true);

        noAds=findViewById(R.id.noAdsTV);
        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        /*layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);*/
        recyclerView.setLayoutManager(layoutManager);

        showList();

    }

    private void showList() {

        if(type.matches("Vehicle"))
        {
            final ArrayList<VehicleAd> arrayList= new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final VehicleAd vehicleAd=vehicleSnapShot.getValue(VehicleAd.class);
                        if(vehicleAd.isStatus() && !vehicleAd.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+vehicleAd.getUser_id()+" my user "+Uid);
                            arrayList.add(vehicleAd);
                        }
                    }

                    if(arrayList.size()==0){
                        recyclerView.setVisibility(View.INVISIBLE);
                        noAds.setVisibility(View.VISIBLE);
                    }

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId(),arrayList.get(i).isStatus()));
                    arrayList.clear();


                    listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,type,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if(type.matches("Electronic"))
        {
            final ArrayList<ElectronicsAd> arrayList= new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final ElectronicsAd Ad=vehicleSnapShot.getValue(ElectronicsAd.class);
                        if(Ad.isStatus() && !Ad.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+Ad.getUser_id()+" my user "+Uid);
                            arrayList.add(Ad);
                        }
                    }

                    if(arrayList.size()==0){
                        recyclerView.setVisibility(View.INVISIBLE);
                        noAds.setVisibility(View.VISIBLE);
                    }

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId(),arrayList.get(i).isStatus()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,type,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    listAdAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.matches("Books"))
        {
            final ArrayList<BooksAd> arrayList= new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final BooksAd Ad=vehicleSnapShot.getValue(BooksAd.class);
                        if(Ad.isStatus() && !Ad.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+Ad.getUser_id()+" my user "+Uid);
                            arrayList.add(Ad);
                        }

                        if(arrayList.size()==0){
                            recyclerView.setVisibility(View.INVISIBLE);
                            noAds.setVisibility(View.VISIBLE);
                        }
                    }
                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId(),arrayList.get(i).isStatus()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,type,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    listAdAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.matches("Miscellaneous"))
        {
            final ArrayList<MiscAd> arrayList= new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final MiscAd Ad=vehicleSnapShot.getValue(MiscAd.class);
                        if(Ad.isStatus() && !Ad.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+Ad.getUser_id()+" my user "+Uid);
                            arrayList.add(Ad);
                        }

                        if(arrayList.size()==0){
                            recyclerView.setVisibility(View.INVISIBLE);
                            noAds.setVisibility(View.VISIBLE);
                        }
                    }
                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId(),arrayList.get(i).isStatus()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,type,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    listAdAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
            {
            Toast.makeText(ListAd.this,"Tapped "+type,Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
    return super.onOptionsItemSelected(item);}

    @Override
    public void onItemClickListener(int position,String adId) {
        Intent intent=new Intent(getApplicationContext(),FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        intent.putExtra("who",1);       //1---buyer   0---seller
        startActivity(intent);

    }

}
