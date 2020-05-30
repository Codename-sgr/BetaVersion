package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    String pBrand,pModel,pPurchaseDate,pDesc,pImg1,pImg2,pImg3,pUserID,email;
    int pPrice,pKmsDriven,pMileage,pImgCount;
    Button contactBtn;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    String smsNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product_view);

        prodImageViewPager=findViewById(R.id.viewPagerImage);
        viewpagerIndicator=findViewById(R.id.finalImgTabLayout);
        contactBtn=findViewById(R.id.contactBtn);
        final Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        final String adId=intent.getStringExtra("adId");

        prodBrand=findViewById(R.id.prodBrand);
        prodModel=findViewById(R.id.productModel);
        prodPrice=findViewById(R.id.priceTextView);

        Log.i("TYPE",""+type);
        Log.i("ADID",""+adId);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad").child(adId);

//      storageReference= FirebaseStorage.getInstance().getReference(type+"Image");

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
                pUserID=itemDetails.getUser_id();

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
                if(pMileage!=0)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Mileage: ",Integer.toString(pMileage)));
                if(pKmsDriven!=0)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Kms Driven: ",Integer.toString(pKmsDriven)));
                if(pDesc!=null)
                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Note: ",pDesc));
                finalProdSpecificationModelList.add(new finalProdSpecificationModel("AD ID: ",adId));

                Log.i("Info",Integer.toString(finalProdSpecificationModelList.size()));
                finalProdDescRecViewAdapter finalProdDescRecViewAdapter=new finalProdDescRecViewAdapter(finalProdSpecificationModelList);
                recyclerView.setAdapter(finalProdDescRecViewAdapter);
                finalProdDescRecViewAdapter.notifyDataSetChanged();
                contactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkForSmsPermission();


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(pModel);
        }

    }
    public  void message()
    {
        Log.i("User",pUserID);
        DatabaseReference reference=database.getReference().child("Users").child(pUserID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    smsNumber=dataSnapshot.getValue(User.class).getMobile();
                    email=dataSnapshot.getValue(User.class).getEmail();
                    if(smsNumber!=null)
                    {
                        String newNumber = String.format("smsto: %s",smsNumber);
                        Log.i("User",smsNumber);
                        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                        smsIntent.setData(Uri.parse(newNumber));
                        smsIntent.putExtra("sms_body", "I want to bye your product.");
                        startActivity(smsIntent);

                    }
                    else
                    {
                        sendEmail(email);
                        //Toast.makeText(FinalProductView.this,"Using Email Instead",Toast.LENGTH_SHORT).show();
                    }

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    protected void sendEmail(String to) {
        Log.i("Send email", "");

        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "Manit Cart"+ "&body=" +
                "I want to buy your product" + "&to=" + to);
        mailIntent.setData(data);
        startActivity(Intent.createChooser(mailIntent, "Send mail..."));
    }
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("User","Permisiion nOt granted");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }else
        {
            message();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    message();

                    // Permission was granted. Enable sms button.

                } else {
                    // Permission denied.
                    Log.d("User", "Failed");
                    Toast.makeText(this, "Failed",
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
