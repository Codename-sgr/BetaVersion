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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
    StorageReference storageReference;
    private finalProdDescRecViewAdapter finalProdDescRecViewAdapter;
    int image_count=2;
    /*ImageView prodImg;*/
    TextView prodBrand,prodModel,prodPrice,prodMillege,prodDop,prodDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product_view);

        prodImageViewPager=findViewById(R.id.viewPagerImage);
        viewpagerIndicator=findViewById(R.id.finalImgTabLayout);

        final Intent intent=getIntent();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(intent.getStringExtra("prodName"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //prodImg=findViewById(R.id.prodImageView);
        prodBrand=findViewById(R.id.prodBrand);
        prodModel=findViewById(R.id.productModel);
        prodPrice=findViewById(R.id.priceTextView);
        /*prodMillege=findViewById(R.id.prodMillege);
        prodDop=findViewById(R.id.prodDop);
        prodDesc=findViewById(R.id.prodDesc);*/


        String type=intent.getStringExtra("type");
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");
        image_count=intent.getIntExtra("img_count",2);
        final String[] imageUrls= new String[image_count];
        String user_id=intent.getStringExtra("user_id");
        String ad_id=intent.getStringExtra("ad_id");

        Log.i("work",Integer.toString(image_count));

        for(int i = 0; i<image_count; i++){
            final int finalI = i;
            storageReference.child(user_id+"/"+ad_id+"/"+i+".jpg")
                    .getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrls[finalI]=uri.toString();
                            Log.i("work shivam",uri.toString());
                            if(finalI==image_count-1)
                            {
                                finalProductImageAdapter finalProductImageAdapter=new finalProductImageAdapter(FinalProductView.this,imageUrls);
                                prodImageViewPager.setAdapter(finalProductImageAdapter);

                                viewpagerIndicator.setupWithViewPager(prodImageViewPager,true);
                            }

                        }
                    });
        }

        /*Uri uri=intent.getData();
        //Picasso.get().load(uri).into(prodImg);*/


        recyclerView=findViewById(R.id.finalProdDescRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*Map<String, Object> mp = new HashMap<>();
        mp.put("spec1",intent.getStringExtra("prodMilege"));
        mp.put("spec2",intent.getStringExtra("prodDOP"));
        mp.put("spec3",intent.getStringExtra("prodDesc"));*/


        List<finalProdSpecificationModel> finalProdSpecificationModelList=new ArrayList<>();

        if(intent.getStringExtra("prodBrand")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Brand: ",intent.getStringExtra("prodBrand")));
        if(intent.getStringExtra("prodModel")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Model: ",intent.getStringExtra("prodModel")));
        if(intent.getStringExtra("prodDOP")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Date of Purchase: ",intent.getStringExtra("prodDOP")));
        if(intent.getStringExtra("prodMilege")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Mileage: ",intent.getStringExtra("prodMilege")));
        if(intent.getStringExtra("prodKmsDriven")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Kms Driven: ",intent.getStringExtra("prodKmsDriven")));
        if(intent.getStringExtra("prodDesc")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("Note: ",intent.getStringExtra("prodDesc")));
        if(intent.getStringExtra("ad_id")!=null)
            finalProdSpecificationModelList.add(new finalProdSpecificationModel("AD ID: ",intent.getStringExtra("ad_id")));



        finalProdDescRecViewAdapter finalProdDescRecViewAdapter=new finalProdDescRecViewAdapter(finalProdSpecificationModelList);
        recyclerView.setAdapter(finalProdDescRecViewAdapter);
        finalProdDescRecViewAdapter.notifyDataSetChanged();


        prodModel.setText(intent.getStringExtra("prodModel"));
        prodBrand.setText(intent.getStringExtra("prodBrand"));
        prodPrice.setText(intent.getStringExtra("prodPrice"));

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
