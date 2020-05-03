package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class ListAd extends AppCompatActivity {
     RecyclerView recyclerView;
     FirebaseRecyclerAdapter<VehicleAd,MyAdapter> VehicleAdapter;
     FirebaseRecyclerAdapter<ElectronicsAd,MyAdapter> ElectronicAdapter;
     FirebaseRecyclerAdapter<SportsAd,MyAdapter> SportsAdapter;
     FirebaseRecyclerAdapter<BooksAd,MyAdapter> BooksAdapter;
     FirebaseRecyclerAdapter<FurnitureAd,MyAdapter> FurnitureAdapter;
     FirebaseDatabase database;
     DatabaseReference databaseReference;
     StorageReference storageReference;
     String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");


        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");

        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        showList();

    }

    private void showList() {
        if(type.matches("Vehicle"))
        {
            FirebaseRecyclerOptions<VehicleAd> options=new FirebaseRecyclerOptions.Builder<VehicleAd>()
                    .setQuery(databaseReference,VehicleAd.class)
                    .build();

            VehicleAdapter=new FirebaseRecyclerAdapter<VehicleAd, MyAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MyAdapter myAdapter, int i, @NonNull VehicleAd vehicleAd) {
                    myAdapter.adProductName.setText(vehicleAd.getModel());
                    myAdapter.adProductPrice.setText(vehicleAd.getSellingPrice());
                    storageReference.child(vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(myAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                }
                @NonNull
                @Override
                public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new MyAdapter(view);
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

            ElectronicAdapter=new FirebaseRecyclerAdapter<ElectronicsAd, MyAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MyAdapter myAdapter, int i, @NonNull ElectronicsAd electronicsAd) {
                    myAdapter.adProductName.setText(electronicsAd.getModel());
                    myAdapter.adProductPrice.setText(electronicsAd.getSellingPrice());
                    storageReference.child(electronicsAd.getUser_id()+"/"+electronicsAd.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(myAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                }
                @NonNull
                @Override
                public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new MyAdapter(view);
                }
            };
            ElectronicAdapter.startListening();
            ElectronicAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(ElectronicAdapter);
        }
        else if(type.matches("Books"))
        {
            FirebaseRecyclerOptions<BooksAd> options=new FirebaseRecyclerOptions.Builder<BooksAd>()
                    .setQuery(databaseReference,BooksAd.class)
                    .build();

            BooksAdapter=new FirebaseRecyclerAdapter<BooksAd, MyAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MyAdapter myAdapter, int i, @NonNull BooksAd booksAd) {
                    myAdapter.adProductName.setText(booksAd.getItem());
                    myAdapter.adProductPrice.setText(booksAd.getSellingPrice());
                    storageReference.child(booksAd.getUser_id()+"/"+booksAd.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(myAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                }
                @NonNull
                @Override
                public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new MyAdapter(view);
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

            SportsAdapter=new FirebaseRecyclerAdapter<SportsAd, MyAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MyAdapter myAdapter, int i, @NonNull SportsAd Ad) {
                    myAdapter.adProductName.setText(Ad.getItem());
                    myAdapter.adProductPrice.setText(Ad.getSellingPrice());
                    storageReference.child(Ad.getUser_id()+"/"+Ad.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(myAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                }
                @NonNull
                @Override
                public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new MyAdapter(view);
                }
            };
            SportsAdapter.startListening();
            SportsAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(SportsAdapter);
        }else if(type.matches("Furniture"))
        {
            FirebaseRecyclerOptions<FurnitureAd> options=new FirebaseRecyclerOptions.Builder<FurnitureAd>()
                    .setQuery(databaseReference,FurnitureAd.class)
                    .build();

            FurnitureAdapter=new FirebaseRecyclerAdapter<FurnitureAd, MyAdapter>(options) {
                @Override
                protected void onBindViewHolder(@NonNull final MyAdapter myAdapter, int i, @NonNull FurnitureAd Ad) {
                    myAdapter.adProductName.setText(Ad.getItem());
                    myAdapter.adProductPrice.setText(Ad.getSellingPrice());
                    storageReference.child(Ad.getUser_id()+"/"+Ad.getId()+"/0.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).into(myAdapter.adProductImage);
                            // Got the download URL for 'users/me/profile.png'
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
                        }
                    });

                }
                @NonNull
                @Override
                public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_data,parent,false);
                    return new MyAdapter(view);
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


}
