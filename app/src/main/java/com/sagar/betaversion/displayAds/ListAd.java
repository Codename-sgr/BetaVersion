package com.sagar.betaversion.displayAds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import com.sagar.betaversion.FinalProductView;
import com.sagar.betaversion.R;
import com.sagar.betaversion.RecViewItemClickListener;
import com.sagar.betaversion.listAdAdapter;
import com.sagar.betaversion.listAdModel;
import com.sagar.betaversion.models.BooksAd;
import com.sagar.betaversion.models.ElectronicsAd;
import com.sagar.betaversion.models.MiscAd;
import com.sagar.betaversion.models.VehicleAd;

import java.util.ArrayList;
import java.util.List;

public class ListAd extends AppCompatActivity implements RecViewItemClickListener {
     RecyclerView recyclerView;
     TextView noAds;
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;
     String Uid;
     List<listAdModel> listAdModelList=new ArrayList<>();
    public static com.sagar.betaversion.listAdAdapter listAdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Ads");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Manit").child(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference().child("Manit").child(type+"Image");
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        Uid=user.getUid();
//      databaseReference.keepSynced(true);

        noAds=findViewById(R.id.noAdsTV);
        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
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
                        if(vehicleAd.isStatus()  && vehicleAd.getVs()==1 && !vehicleAd.getUser_id().matches(Uid))
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
                        listAdModelList.add(
                                new listAdModel(
                                        arrayList.get(i).getModel(),
                                        arrayList.get(i).getBrand(),
                                        arrayList.get(i).getSellingPrice(),
                                        arrayList.get(i).getImg1(),
                                        arrayList.get(i).getId(),
                                        arrayList.get(i).isStatus(),
                                        type,
                                        arrayList.get(i).getVs(),null
                                )
                        );
                    arrayList.clear();


                    listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            listAdAdapter.shimmering=false;
                            listAdAdapter.notifyDataSetChanged();
                        }
                    },3000);

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
                        if(Ad.isStatus() && Ad.getVs()==1 && !Ad.getUser_id().matches(Uid))
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
                        listAdModelList.add(
                                new listAdModel(
                                        arrayList.get(i).getModel(),
                                        arrayList.get(i).getBrand(),
                                        arrayList.get(i).getSellingPrice(),
                                        arrayList.get(i).getImg1(),
                                        arrayList.get(i).getId(),
                                        arrayList.get(i).isStatus(),
                                        type,
                                        arrayList.get(i).getVs(),null
                                )
                        );

                    listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            listAdAdapter.shimmering=false;
                            listAdAdapter.notifyDataSetChanged();
                        }
                    },3000);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.matches("Books"))
        {
            Log.i("aaaaaaaaaaaaaaaaaaaa","I am here");
            final ArrayList<BooksAd> arrayList= new ArrayList<>();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final BooksAd Ad=vehicleSnapShot.getValue(BooksAd.class);
                        if(Ad.isStatus() && Ad.getVs()==1&& !Ad.getUser_id().matches(Uid))
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
                        listAdModelList.add(
                                new listAdModel(
                                        arrayList.get(i).getModel(),
                                        arrayList.get(i).getBrand(),
                                        arrayList.get(i).getSellingPrice(),
                                        arrayList.get(i).getImg1(),
                                        arrayList.get(i).getId(),
                                        arrayList.get(i).isStatus(),
                                        type,
                                        arrayList.get(i).getVs(),null
                                )
                        );

                    listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            listAdAdapter.shimmering=false;
                            listAdAdapter.notifyDataSetChanged();
                        }
                    },3000);
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
                        if(Ad.isStatus() && Ad.getVs()==1 && !Ad.getUser_id().matches(Uid))
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
                        listAdModelList.add(
                                new listAdModel(
                                        arrayList.get(i).getModel(),
                                        arrayList.get(i).getBrand(),
                                        arrayList.get(i).getSellingPrice(),
                                        arrayList.get(i).getImg1(),
                                        arrayList.get(i).getId(),
                                        arrayList.get(i).isStatus(),
                                        type,
                                        arrayList.get(i).getVs(),null
                                )
                        );

                    listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,Uid,getLocalClassName(),ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            listAdAdapter.shimmering=false;
                            listAdAdapter.notifyDataSetChanged();
                        }
                    },3000);
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
    public void onItemClickListener(int position,String adId,String type,String uadId) {
        Intent intent=new Intent(getApplicationContext(), FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        intent.putExtra("who",1);       //1---buyer   0---seller
        intent.putExtra("uadId",uadId);
        startActivity(intent);

    }

}
