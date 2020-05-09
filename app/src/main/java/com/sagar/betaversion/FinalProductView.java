package com.sagar.betaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FinalProductView extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    ImageView prodImg;
    TextView prodName,prodPrice,prodDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product_view);
        Intent intent=getIntent();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(intent.getStringExtra("prodName"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        prodImg=findViewById(R.id.prodImageView);
        prodName=findViewById(R.id.productNameTextView);
        prodPrice=findViewById(R.id.priceTextView);
        prodDescription=findViewById(R.id.DescriptionTextView);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference();

        //storageReference.child(vehicleAd.getUser_id()+"/"+vehicleAd.getId()+"/0.jpg");


        Uri uri=intent.getData();
        Picasso.get().load(uri).into(prodImg);


        prodName.setText(intent.getStringExtra("prodName"));
        prodPrice.setText(intent.getStringExtra("prodPrice"));
        prodDescription.setText(intent.getStringExtra("prodMilege")
                +"\n"+intent.getStringExtra("prodDOP")
                +"\n"+intent.getStringExtra("prodDesc"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
