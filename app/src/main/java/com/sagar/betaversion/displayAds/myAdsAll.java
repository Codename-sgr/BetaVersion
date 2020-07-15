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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagar.betaversion.FinalProductView;
import com.sagar.betaversion.R;
import com.sagar.betaversion.RecViewItemClickListener;
import com.sagar.betaversion.listAdAdapter;
import com.sagar.betaversion.listAdModel;
import com.sagar.betaversion.models.Ad;

import java.util.ArrayList;
import java.util.List;

public class myAdsAll extends AppCompatActivity implements RecViewItemClickListener {
    RecyclerView recyclerView;
    String Uid;
    DatabaseReference userAd;
    ArrayList<Ad> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    List<listAdModel> listAdModelList=new ArrayList<>();
    public static com.sagar.betaversion.listAdAdapter listAdAdapter;
    public static TextView noAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Start","Start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_electronic);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Uid=firebaseUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userAd=firebaseDatabase.getReference().child("Manit").child("UserAd").child(Uid);
        Log.i("Start",Uid);
        userAd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("Start","Start1");
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Ad ad=snapshot.getValue(Ad.class);
                    Log.i("yeaah",ad.toString());
                    if(ad.getBrand()!=null)
                        Log.i("yeaahhh",ad.getBrand());
                    arrayList.add(snapshot.getValue(Ad.class));

                }
                if(arrayList.size()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    noAds.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i<arrayList.size(); i++)
                {
                    listAdModelList.add(
                            new listAdModel(
                                    arrayList.get(i).getModel(),
                                    arrayList.get(i).getBrand(),
                                    arrayList.get(i).getSellingPrice(),
                                    arrayList.get(i).getImg1(),
                                    arrayList.get(i).getId(),
                                    arrayList.get(i).isStatus(),
                                    arrayList.get(i).getType(),
                                    arrayList.get(i).getVs(),
                                    arrayList.get(i).getUadId()

                            )

                    );
                    //Log.i("AAAAA",arrayList.get(i).getModel());
                }

                listAdAdapter=new listAdAdapter(listAdModelList,myAdsAll.this,Uid,myAdsAll.this.getClass().getSimpleName(),myAdsAll.this);
                recyclerView.setAdapter(listAdAdapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listAdAdapter.shimmering=false;
                        listAdAdapter.notifyDataSetChanged();
                    }
                },2500);

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

    @Override
    public void onItemClickListener(int position, String adId,String type,String uadId) {
        Intent intent=new Intent(getApplicationContext(), FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        intent.putExtra("who",0);//1---buyer   0---seller
        intent.putExtra("uadId",uadId);
        startActivity(intent);
    }
}
