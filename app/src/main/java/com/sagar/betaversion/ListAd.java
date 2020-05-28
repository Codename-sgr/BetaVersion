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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this);
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
            final List<listAdModel> listAdModelList=new ArrayList<>();
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
                    Log.i("QQQQ", String.valueOf(arrayList.size()));

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    Log.i("bbb", String.valueOf(listAdModelList.size()));
                    for (listAdModel s:listAdModelList){
                        Log.i("AAA",s.getProdBrand()+s.getProdModel()+s.getProdPrice()+s.getProdImg());
                    }

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this);
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
            final List<listAdModel> listAdModelList=new ArrayList<>();
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
                    Log.i("QQQQ", String.valueOf(arrayList.size()));

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    Log.i("bbb", String.valueOf(listAdModelList.size()));
                    for (listAdModel s:listAdModelList){
                        Log.i("AAA",s.getProdBrand()+s.getProdModel()+s.getProdPrice()+s.getProdImg());
                    }

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this);
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
                    Log.i("QQQQ", String.valueOf(arrayList.size()));

                    for (int i=0;i<arrayList.size();i++)
                        listAdModelList.add(new listAdModel(arrayList.get(i).getModel(),arrayList.get(i).getBrand(),arrayList.get(i).getSellingPrice(),arrayList.get(i).getImg1(),arrayList.get(i).getId()));

                    Log.i("bbb", String.valueOf(listAdModelList.size()));
                    for (listAdModel s:listAdModelList){
                        Log.i("AAA",s.getProdBrand()+s.getProdModel()+s.getProdPrice()+s.getProdImg());
                    }

                    listAdAdapter listAdAdapter=new listAdAdapter(listAdModelList,ListAd.this);
                    recyclerView.setAdapter(listAdAdapter);
                    listAdAdapter.notifyDataSetChanged();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        /*{

            //STARTS HERE CHECK THIS BRO
            final ArrayList<VehicleAd> arrayList= new ArrayList<>();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot vehicleSnapShot: dataSnapshot.getChildren()) {
                        VehicleAd vehicleAd=vehicleSnapShot.getValue(VehicleAd.class);
                        if(vehicleAd.isStatus() && !vehicleAd.getUser_id().matches(Uid))
                        {
                            Log.i("arrayList",dataSnapshot.getKey()+ " user Id "+vehicleAd.getUser_id()+" my user "+Uid);
                            arrayList.add(vehicleAd);
                        }

                        // TODO: handle the post
                    }
                    int n=arrayList.size();
                    Log.i("arrayList count",Integer.toString(n));

                    for(int i=0;i<n;i++)
                    {
                        Log.i("arrayList check",Integer.toString(arrayList.get(i).getSellingPrice()));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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
        }*/
        /*else if(type.matches("Electronic"))
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
        }*/


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

    @Override
    public void onItemClickListener(int position,String adId) {
        Intent intent=new Intent(getApplicationContext(),FinalProductView.class);
        intent.putExtra("type",type);
        intent.putExtra("adId",adId);
        startActivity(intent);

    }

}
