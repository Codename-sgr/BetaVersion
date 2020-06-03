package com.sagar.betaversion.myAds;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagar.betaversion.ElectronicsAd;
import com.sagar.betaversion.FinalProductView;
import com.sagar.betaversion.R;
import com.sagar.betaversion.RecViewItemClickListener;
import com.sagar.betaversion.listAdAdapter;
import com.sagar.betaversion.listAdModel;

import java.util.ArrayList;
import java.util.List;

public class myElectronic extends AppCompatActivity implements RecViewItemClickListener {

    RecyclerView recyclerView;
    String Uid,type;
    DatabaseReference userAd;
    ArrayList<ElectronicsAd> arrayList=new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    List<listAdModel> listAdModelList=new ArrayList<>();
    public static com.sagar.betaversion.listAdAdapter listAdAdapter;
    TextView noAds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        Intent intent=getIntent();
        type=intent.getStringExtra("type");

        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        Uid=firebaseUser.getUid();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userAd=firebaseDatabase.getReference().child("ElectronicAd");
        userAd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {

                    final ElectronicsAd booksAd=vehicleSnapShot.getValue(ElectronicsAd.class);

                    if(booksAd.getUser_id().matches(Uid))
                    {
                        Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+booksAd.getId()+" my user "+Uid);
                        arrayList.add(booksAd);
                    }

                }
                if(arrayList.size()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    noAds.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i<arrayList.size(); i++)
                    listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                listAdAdapter=new listAdAdapter(listAdModelList,myElectronic.this,false,type,Uid,myElectronic.this.getClass().getSimpleName());
                recyclerView.setAdapter(listAdAdapter);
                listAdAdapter.notifyDataSetChanged();

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
    public void onItemClickListener(int position, String adId) {
        Intent intent=new Intent(getApplicationContext(), FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        startActivity(intent);
    }
}
