package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.AdCategory.VehicleAd;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FinalProductView extends AppCompatActivity {

    private ViewPager prodImageViewPager;
    private TabLayout viewpagerIndicator;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    /*ImageView prodImg;*/
    TextView prodName,prodPrice,prodMillege,prodDop,prodDesc;

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
        prodName=findViewById(R.id.productNameTextView);
        prodPrice=findViewById(R.id.priceTextView);
        prodMillege=findViewById(R.id.prodMillege);
        prodDop=findViewById(R.id.prodDop);
        prodDesc=findViewById(R.id.prodDesc);
        VehicleAd vehicleAd = new VehicleAd();

        String type=intent.getStringExtra("type");
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad");
        storageReference= FirebaseStorage.getInstance().getReference(type+"Image");
        final String[] imageUrls= new String[]{

                ///INSERT IMAGE URLS in this place.
                /*"https://firebasestorage.googleapis.com/v0/b/manitcart.appspot.com/o/"+type+"Image"+vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/0.jpg",
                "https://firebasestorage.googleapis.com/v0/b/manitcart.appspot.com/o/"+type+"Image"+vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/1.jpg",
                "https://firebasestorage.googleapis.com/v0/b/manitcart.appspot.com/o/"+type+"Image"+vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/2.jpg",*/

                "https://i.pinimg.com/originals/76/47/9d/76479dd91dc55c2768ddccfc30a4fbf5.png",
                "https://w7.pngwing.com/pngs/8/546/png-transparent-pikachu-illustration-pikachu-ash-ketchum-pokemon-pichu-raichu-pikachu-mammal-vertebrate-cartoon.png",
                "https://i1.pngguru.com/preview/38/605/204/cartoons-pikachu-of-pokemon-illustration-png-clipart.jpg"
        };

        /*for(int i = 0; i<vehicleAd.getImg_count(); i++){
            storageReference.child(vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/"+i+".jpg")
                    .getDownloadUrl()
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Log.i("URL", String.valueOf(task.getResult()));
                        }
                    });
        }*/

        finalProductImageAdapter finalProductImageAdapter=new finalProductImageAdapter(this,imageUrls);
        prodImageViewPager.setAdapter(finalProductImageAdapter);

        viewpagerIndicator.setupWithViewPager(prodImageViewPager,true);

        /*Uri uri=intent.getData();
        //Picasso.get().load(uri).into(prodImg);*/


        prodName.setText(intent.getStringExtra("prodName"));
        prodPrice.setText(intent.getStringExtra("prodPrice"));
        prodMillege.setText("Mileage: "+(intent.getStringExtra("prodMilege")));
        prodDop.setText("Date of Purchase: "+intent.getStringExtra("prodDOP"));
        prodDesc.setText("Note: "+intent.getStringExtra("prodDesc"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
