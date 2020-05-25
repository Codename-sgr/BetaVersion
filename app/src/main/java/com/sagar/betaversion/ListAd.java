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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.AdCategory.BooksAd;
import com.sagar.betaversion.AdCategory.ElectronicsAd;
import com.sagar.betaversion.AdCategory.MiscAd;
import com.sagar.betaversion.AdCategory.VehicleAd;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAd extends AppCompatActivity {
     RecyclerView recyclerView;
     FirebaseRecyclerAdapter<VehicleAd, listAdRecViewAdapter> VehicleAdapter;
     FirebaseRecyclerAdapter<ElectronicsAd, listAdRecViewAdapter> ElectronicAdapter;
     FirebaseRecyclerAdapter<MiscAd, listAdRecViewAdapter> SportsAdapter;
     FirebaseRecyclerAdapter<BooksAd, listAdRecViewAdapter> BooksAdapter;

     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;
     String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        Intent intent=getIntent();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle(intent.getStringExtra("type")+" Ads");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        type=intent.getStringExtra("type");


        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        Uid=user.getUid();
        databaseReference.keepSynced(true);

        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showList();

    }

    private void showList() {

        if(type.matches("Vehicle"))
        {

            //STARTS HERE CHECK THIS BRO
            final ArrayList<VehicleAd> arrayList= new ArrayList<>();
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    VehicleAd vehicleAd=dataSnapshot.getValue(VehicleAd.class);
                    if(vehicleAd.isStatus() && vehicleAd.getUser_id()!=Uid)
                    {
                        Log.i("arrayList",dataSnapshot.getKey());
                        arrayList.add(vehicleAd);
                    }

                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    VehicleAd vehicleAd=dataSnapshot.getValue(VehicleAd.class);
                    arrayList.remove(vehicleAd);
                    if(vehicleAd.isStatus() && vehicleAd.getUser_id()!=Uid)
                    {
                        Log.i("arrayList",dataSnapshot.getKey());
                        arrayList.add(vehicleAd);
                    }

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    VehicleAd vehicleAd=dataSnapshot.getValue(VehicleAd.class);
                    arrayList.remove(vehicleAd);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ListAd.this,"Database Error try again!!",Toast.LENGTH_SHORT).show();
                }
            };
            //ENDS HERE BAAKI CODE ME KCH CHANGES NAHI HAI RECYCLERVIEW ME YE ARRAYLIST DAAL DENA
            databaseReference.orderByKey();
            databaseReference.addChildEventListener(childEventListener);
            FirebaseRecyclerOptions<VehicleAd> options=new FirebaseRecyclerOptions.Builder<VehicleAd>()
                    .setQuery(databaseReference,VehicleAd.class)
                    .build();

            VehicleAdapter=new FirebaseRecyclerAdapter<VehicleAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, final int i, @NonNull final VehicleAd vehicleAd) {
                    listAdRecViewAdapter.adProductBrand.setText(vehicleAd.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(vehicleAd.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Integer.toString(vehicleAd.getSellingPrice()));
                    final Intent intent = new Intent(getApplicationContext(), FinalProductView.class);
                            intent.putExtra("type",type);
                    storageReference.child(vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/0.jpg")
                            .getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(listAdRecViewAdapter.adProductImage);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listAdRecViewAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                    listAdRecViewAdapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            String mProdBrand= listAdRecViewAdapter.adProductBrand.getText().toString();
                            String mProdModel= listAdRecViewAdapter.adProductModel.getText().toString();
                            String mProductPrice= listAdRecViewAdapter.adProductPrice.getText().toString();
                            intent.putExtra("img_count",vehicleAd.getImg_count());
                            intent.putExtra("user_id",vehicleAd.getUser_id());
                            intent.putExtra("ad_id",vehicleAd.getId());
                            intent.putExtra("prodBrand",mProdBrand);
                            intent.putExtra("prodModel",mProdModel);
                            intent.putExtra("prodPrice",mProductPrice);
                            intent.putExtra("prodKmsDriven",Integer.toString(vehicleAd.getKmsDriven()));
                            intent.putExtra("prodMilege",Integer.toString(vehicleAd.getMilege()));
                            intent.putExtra("prodDOP",vehicleAd.getDate_of_purchase());
                            intent.putExtra("prodDesc",vehicleAd.getDescription());
                            startActivity(intent);
                        }
                    });

                }
                @NonNull
                @Override
                public listAdRecViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new listAdRecViewAdapter(view);
                }
            };
            VehicleAdapter.startListening();
            VehicleAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(VehicleAdapter);
        }
        else if(type.matches("Electronic"))
        {
            FirebaseRecyclerOptions<ElectronicsAd> options=new FirebaseRecyclerOptions.Builder<ElectronicsAd>()
                    .setQuery(databaseReference,ElectronicsAd.class)
                    .build();

            ElectronicAdapter=new FirebaseRecyclerAdapter<ElectronicsAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, int i, @NonNull final ElectronicsAd Ad) {
                    listAdRecViewAdapter.adProductBrand.setText(Ad.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(Ad.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Integer.toString(Ad.getSellingPrice()));
                    final Intent intent=new Intent(getApplicationContext(),FinalProductView.class);

                    storageReference.child(Ad.getUser_id()+"/"+Ad.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {


                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(listAdRecViewAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                            intent.setData(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listAdRecViewAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                    listAdRecViewAdapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            String mProdBrand= listAdRecViewAdapter.adProductBrand.getText().toString();
                            String mProdModel= listAdRecViewAdapter.adProductModel.getText().toString();
                            String mProductPrice= listAdRecViewAdapter.adProductPrice.getText().toString();
                            intent.putExtra("type",type);
                            intent.putExtra("img_count",Ad.getImg_count());
                            intent.putExtra("user_id",Ad.getUser_id());
                            intent.putExtra("ad_id",Ad.getId());
                            intent.putExtra("prodBrand",mProdBrand);
                            intent.putExtra("prodModel",mProdModel);
                            intent.putExtra("prodPrice",mProductPrice);
                            intent.putExtra("prodDOP",Ad.getDate_of_purchase());
                            intent.putExtra("prodDesc",Ad.getDescription());
                            startActivity(intent);
                        }
                    });

                }
                @NonNull
                @Override
                public listAdRecViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new listAdRecViewAdapter(view);
                }
            };
            ElectronicAdapter.startListening();
            ElectronicAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(ElectronicAdapter);
        }
        else if(type.matches("Books"))   //BRAND--AUTHOR_______MODEL=TITLE
        {

            FirebaseRecyclerOptions<BooksAd> options=new FirebaseRecyclerOptions.Builder<BooksAd>()
                    .setQuery(databaseReference,BooksAd.class)
                    .build();

            BooksAdapter=new FirebaseRecyclerAdapter<BooksAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, int i, @NonNull final BooksAd Ad) {
                    listAdRecViewAdapter.adProductBrand.setText(Ad.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(Ad.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Integer.toString(Ad.getSellingPrice()));
                    final Intent intent=new Intent(getApplicationContext(),FinalProductView.class);
                    storageReference.child(Ad.getUser_id()+"/"+Ad.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(listAdRecViewAdapter.adProductImage);
                            intent.setData(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            listAdRecViewAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                    listAdRecViewAdapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            String mProdBrand= listAdRecViewAdapter.adProductBrand.getText().toString();
                            String mProdModel= listAdRecViewAdapter.adProductModel.getText().toString();
                            String mProductPrice= listAdRecViewAdapter.adProductPrice.getText().toString();
                            intent.putExtra("type",type);
                            intent.putExtra("img_count",Ad.getImg_count());
                            intent.putExtra("user_id",Ad.getUser_id());
                            intent.putExtra("ad_id",Ad.getId());
                            intent.putExtra("prodBrand",mProdBrand);
                            intent.putExtra("prodModel",mProdModel);
                            intent.putExtra("prodPrice",mProductPrice);
                            intent.putExtra("prodDesc",Ad.getDescription());
                            startActivity(intent);
                        }
                    });

                }
                @NonNull
                @Override
                public listAdRecViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new listAdRecViewAdapter(view);
                }
            };
            BooksAdapter.startListening();
            BooksAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(BooksAdapter);
        }
        else if(type.matches("Miscellaneous"))
        {
            FirebaseRecyclerOptions<MiscAd> options=new FirebaseRecyclerOptions.Builder<MiscAd>()
                    .setQuery(databaseReference, MiscAd.class)
                    .build();

            SportsAdapter=new FirebaseRecyclerAdapter<MiscAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, int i, @NonNull final MiscAd Ad) {
                    listAdRecViewAdapter.adProductBrand.setText(Ad.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(Ad.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Integer.toString(Ad.getSellingPrice()));
                    final Intent intent=new Intent(getApplicationContext(),FinalProductView.class);
                    storageReference.child(Ad.getUser_id()+"/"+Ad.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(listAdRecViewAdapter.adProductImage);
                            intent.setData(uri);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            listAdRecViewAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                    listAdRecViewAdapter.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemClickListener(View v, int position) {
                            String mProdBrand= listAdRecViewAdapter.adProductBrand.getText().toString();
                            String mProdModel= listAdRecViewAdapter.adProductModel.getText().toString();
                            String mProductPrice= listAdRecViewAdapter.adProductPrice.getText().toString();
                            intent.putExtra("type",type);
                            intent.putExtra("img_count",Ad.getImg_count());
                            intent.putExtra("user_id",Ad.getUser_id());
                            intent.putExtra("ad_id",Ad.getId());
                            intent.putExtra("prodBrand",mProdBrand);
                            intent.putExtra("prodModel",mProdModel);
                            intent.putExtra("prodPrice",mProductPrice);
                            intent.putExtra("prodDesc",Ad.getDescription());
                            startActivity(intent);
                        }
                    });

                }
                @NonNull
                @Override
                public listAdRecViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new listAdRecViewAdapter(view);
                }
            };
            SportsAdapter.startListening();
            SportsAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(SportsAdapter);
        }

        else {
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

}
