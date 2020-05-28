package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.AdCategory.ElectronicsAd;
import com.sagar.betaversion.AdCategory.VehicleAd;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FinalProductView extends AppCompatActivity {

    private ViewPager prodImageViewPager;
    private TabLayout viewpagerIndicator;

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    int image_count=2;
    /*ImageView prodImg;*/
    TextView prodBrand,prodModel,prodPrice;

    String pBrand,pModel,pPurchaseDate,pDesc,pImg1,pImg2,pImg3;
    int pPrice,pKmsDriven,pMileage,pImgCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product_view);

        prodImageViewPager=findViewById(R.id.viewPagerImage);
        viewpagerIndicator=findViewById(R.id.finalImgTabLayout);

        final Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        final String adId=intent.getStringExtra("adId");
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        prodBrand=findViewById(R.id.prodBrand);
        prodModel=findViewById(R.id.productModel);
        prodPrice=findViewById(R.id.priceTextView);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad").child(adId);

//        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                VehicleAd itemDetails=dataSnapshot.getValue(VehicleAd.class);
                pBrand=itemDetails.getBrand();
                pModel=itemDetails.getModel();
                pPrice=itemDetails.getSellingPrice();
                pDesc=itemDetails.getDescription();
                pKmsDriven=itemDetails.getKmsDriven();
                pMileage=itemDetails.getMilege();
                pPurchaseDate=itemDetails.getDate_of_purchase();
                pImgCount=itemDetails.getImg_count();
                pImg1=itemDetails.getImg1();
                pImg2=itemDetails.getImg2();
                pImg3=itemDetails.getImg3();

//FOR IMAGE IN VIEW PAGER
                List<String> imageURLs=new ArrayList<>();
                if(pImg1!=null)
                    imageURLs.add(pImg1);
                if(pImg2!=null)
                    imageURLs.add(pImg2);
                if(pImg3!=null)
                    imageURLs.add(pImg3);

                finalProductImageAdapter finalProductImageAdapter=new finalProductImageAdapter(FinalProductView.this,imageURLs);
                prodImageViewPager.setAdapter(finalProductImageAdapter);
                viewpagerIndicator.setupWithViewPager(prodImageViewPager,true);

//FOR DETAILS
                 prodBrand.setText(pBrand);
                 prodModel.setText(pModel);
                 prodPrice.setText(Integer.toString(pPrice));

//FOR SPECIFICATIONS
                recyclerView=findViewById(R.id.finalProdDescRV);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(FinalProductView.this));
                List<finalProdSpecificationModel> finalProdSpecificationModelList=new ArrayList<>();
                if(pBrand!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Brand: ",pBrand));
                if(pModel!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Model: ",pModel));
                if(pPurchaseDate!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Date of Purchase: ",pPurchaseDate));
                if(String.valueOf(pMileage)!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Mileage: ",Integer.toString(pMileage)));
                if(Integer.toString(pKmsDriven)!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Kms Driven: ",Integer.toString(pKmsDriven)));
                if(pDesc!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Note: ",pDesc));
                if(adId!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("AD ID: ",adId));

                Log.i("Info",Integer.toString(finalProdSpecificationModelList.size()));
                finalProdDescRecViewAdapter finalProdDescRecViewAdapter=new finalProdDescRecViewAdapter(finalProdSpecificationModelList);
                recyclerView.setAdapter(finalProdDescRecViewAdapter);
                finalProdDescRecViewAdapter.notifyDataSetChanged();

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
}
