package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.AdCategory.BooksAd;
import com.sagar.betaversion.AdCategory.ElectronicsAd;
import com.sagar.betaversion.AdCategory.MiscAd;
import com.sagar.betaversion.AdCategory.VehicleAd;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAd extends AppCompatActivity implements RecViewItemClickListener{
     RecyclerView recyclerView;
/*     FirebaseRecyclerAdapter<VehicleAd, listAdRecViewAdapter> VehicleAdapter;
     FirebaseRecyclerAdapter<ElectronicsAd, listAdRecViewAdapter> ElectronicAdapter;
     FirebaseRecyclerAdapter<MiscAd, listAdRecViewAdapter> SportsAdapter;
     FirebaseRecyclerAdapter<BooksAd, listAdRecViewAdapter> BooksAdapter;*/
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;
     String Uid;

    List<listAdModel> listAdModelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(intent.getStringExtra(type+" Ads"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        Uid=user.getUid();
//      databaseReference.keepSynced(true);


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
            databaseReference.addValueEventListener(new ValueEventListener() {
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

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,true);
                    recyclerView.setAdapter(listAdAdapter);
                    listAdAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if(type.matches("Electronic"))
        {
            final ArrayList<ElectronicsAd> arrayList= new ArrayList<>();
            databaseReference.addValueEventListener(new ValueEventListener() {
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
                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,true);
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
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final BooksAd Ad=vehicleSnapShot.getValue(BooksAd.class);
                        if(Ad.isStatus() && !Ad.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+Ad.getUser_id()+" my user "+Uid);
                            arrayList.add(Ad);
                        }
                    }
                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,true);
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
            final List<listAdModel> listAdModelList=new ArrayList<>();
            final ArrayList<MiscAd> arrayList= new ArrayList<>();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        final MiscAd Ad=vehicleSnapShot.getValue(MiscAd.class);
                        if(Ad.isStatus() && !Ad.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+Ad.getUser_id()+" my user "+Uid);
                            arrayList.add(Ad);
                        }
                    }
                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this,true);
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
        startActivity(intent);

    }

}
