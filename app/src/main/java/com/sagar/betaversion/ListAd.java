package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.AdCategory.BooksAd;
import com.sagar.betaversion.AdCategory.ElectronicsAd;
import com.sagar.betaversion.AdCategory.FurnitureAd;
import com.sagar.betaversion.AdCategory.SportsAd;
import com.sagar.betaversion.AdCategory.VehicleAd;

import com.squareup.picasso.Picasso;

public class ListAd extends AppCompatActivity {
     RecyclerView recyclerView;
     FirebaseRecyclerAdapter<VehicleAd, listAdRecViewAdapter> VehicleAdapter;
     FirebaseRecyclerAdapter<ElectronicsAd, listAdRecViewAdapter> ElectronicAdapter;
     FirebaseRecyclerAdapter<SportsAd, listAdRecViewAdapter> SportsAdapter;
     FirebaseRecyclerAdapter<BooksAd, listAdRecViewAdapter> BooksAdapter;
     FirebaseRecyclerAdapter<FurnitureAd, listAdRecViewAdapter> FurnitureAdapter;
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;

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

        databaseReference.keepSynced(true);

        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showList();

    }

    private void showList() {
        if(type.matches("Vehicle"))
        {
            FirebaseRecyclerOptions<VehicleAd> options=new FirebaseRecyclerOptions.Builder<VehicleAd>()
                    .setQuery(databaseReference,VehicleAd.class)
                    .build();

            VehicleAdapter=new FirebaseRecyclerAdapter<VehicleAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, final int i, @NonNull final VehicleAd vehicleAd) {
                    listAdRecViewAdapter.adProductBrand.setText(vehicleAd.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(vehicleAd.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(vehicleAd.getSellingPrice());
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
                            intent.putExtra("prodKmsDriven",vehicleAd.getKmsDriven());
                            intent.putExtra("prodMilege",vehicleAd.getMilege());
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
                    listAdRecViewAdapter.adProductPrice.setText(Ad.getSellingPrice());
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
                    listAdRecViewAdapter.adProductPrice.setText(Ad.getSellingPrice());
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
        else if(type.matches("Sports"))
        {
            FirebaseRecyclerOptions<SportsAd> options=new FirebaseRecyclerOptions.Builder<SportsAd>()
                    .setQuery(databaseReference,SportsAd.class)
                    .build();

            SportsAdapter=new FirebaseRecyclerAdapter<SportsAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, int i, @NonNull final SportsAd Ad) {
                    listAdRecViewAdapter.adProductBrand.setText(Ad.getBrand());
                    listAdRecViewAdapter.adProductModel.setText(Ad.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Ad.getSellingPrice());
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
        else if(type.matches("Furniture"))
        {
            FirebaseRecyclerOptions<FurnitureAd> options=new FirebaseRecyclerOptions.Builder<FurnitureAd>()
                    .setQuery(databaseReference,FurnitureAd.class)
                    .build();

            FurnitureAdapter=new FirebaseRecyclerAdapter<FurnitureAd, listAdRecViewAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final listAdRecViewAdapter listAdRecViewAdapter, int i, @NonNull final FurnitureAd Ad) {
                    listAdRecViewAdapter.adProductModel.setText(Ad.getModel());
                    listAdRecViewAdapter.adProductPrice.setText(Ad.getSellingPrice());
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
                            String mProdModel= listAdRecViewAdapter.adProductModel.getText().toString();
                            String mProductPrice= listAdRecViewAdapter.adProductPrice.getText().toString();
                            intent.putExtra("type",type);
                            intent.putExtra("img_count",Ad.getImg_count());
                            intent.putExtra("user_id",Ad.getUser_id());
                            intent.putExtra("ad_id",Ad.getId());
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
            FurnitureAdapter.startListening();
            FurnitureAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(FurnitureAdapter);
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
