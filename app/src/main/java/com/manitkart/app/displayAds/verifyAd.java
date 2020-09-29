package com.manitkart.app.displayAds;

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
import com.manitkart.app.FinalProductView;
import com.manitkart.app.R;
import com.manitkart.app.RecViewItemClickListener;
import com.manitkart.app.listAdAdapter;
import com.manitkart.app.listAdModel;
import com.manitkart.app.models.Ad;

import java.util.ArrayList;
import java.util.List;

public class verifyAd extends AppCompatActivity implements RecViewItemClickListener {
    RecyclerView recyclerView;
    String Uid;
    DatabaseReference verifyAd;
    ArrayList<Ad> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    List<listAdModel> listAdModelList=new ArrayList<>();
    public static com.manitkart.app.listAdAdapter listAdAdapter;
    public static TextView noAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_ad);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Verify Ads");
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
        verifyAd=firebaseDatabase.getReference().child("Manit").child("Verification");
        verifyAd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                    arrayList.get(i).getUadId(),
                                    arrayList.get(i).getUser_id()

                            )

                    );
                    //Log.i("AAAAA",arrayList.get(i).getModel());
                }

                listAdAdapter=new listAdAdapter(listAdModelList,verifyAd.this,Uid,verifyAd.this.getClass().getSimpleName(),verifyAd.this);
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
    public void onItemClickListener(int position, String adId, String type, String uadId, String owner_id) {
        Intent intent=new Intent(getApplicationContext(), FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        intent.putExtra("who",2);//1---buyer   0---seller  2----verifier
        intent.putExtra("uadId",uadId);
        intent.putExtra("owner_id",owner_id);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
