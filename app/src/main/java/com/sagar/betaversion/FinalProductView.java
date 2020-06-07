package com.sagar.betaversion;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalProductView extends AppCompatActivity {

    private ViewPager prodImageViewPager;
    private TabLayout viewpagerIndicator;

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    TextView prodBrand,prodModel,prodPrice,descNote;

    String pBrand,pModel,pPurchaseDate,pDesc,pImg1,pImg2,pImg3,pUserID,email;
    int pPrice,pKmsDriven,pMileage,pImgCount;
    Button contactBtn;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    String smsNumber;
    int who;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_product_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prodImageViewPager=findViewById(R.id.viewPagerImage);
        viewpagerIndicator=findViewById(R.id.finalImgTabLayout);
        contactBtn=findViewById(R.id.contactBtn);
        final Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        final String adId=intent.getStringExtra("adId");
        who=intent.getIntExtra("who",1);

        if(who==0)
            contactBtn.setText("EDIT PRICE");

        prodBrand=findViewById(R.id.prodBrand);
        prodModel=findViewById(R.id.productModel);
        prodPrice=findViewById(R.id.priceTextView);
        descNote=findViewById(R.id.descNote);
//        updateBtn=findViewById(R.id.UpdateBtn);
        Log.i("TYPE",""+type);
        Log.i("ADID",""+adId);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference(type+"Ad").child(adId);
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
                final ArrayList<String> imageURLs=new ArrayList<>();
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
                    descNote.setText("Note : "+pDesc);
//                    finalProdSpecificationModelList.add(new finalProdSpecificationModel("Note: ",pDesc));
//                finalProdSpecificationModelList.add(new finalProdSpecificationModel("AD ID: ",adId));

                finalProdDescRecViewAdapter finalProdDescRecViewAdapter=new finalProdDescRecViewAdapter(finalProdSpecificationModelList);
                recyclerView.setAdapter(finalProdDescRecViewAdapter);
                finalProdDescRecViewAdapter.notifyDataSetChanged();

                if(getSupportActionBar()!=null){
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle(pModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (who==1)
                    checkForSmsPermission();
                else
                    updatePrice();



            }
        });

//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updatePrice();
//            }
//        });

    }

    public  void message(){
        Log.i("User",pUserID);
        DatabaseReference reference=database.getReference().child("Users").child(pUserID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    smsNumber=dataSnapshot.getValue(User.class).getMobile();
                    email=dataSnapshot.getValue(User.class).getEmail();
                    sendEmail(email);

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
        startActivity(Intent.createChooser(mailIntent, "Send Mail via.."));
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

    public void updatePrice(){
        if (who==0){
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            final EditText newPrice=new EditText(this);
            builder.setView(newPrice);
            builder.setTitle("Update Price");
            builder
                    .setMessage("Enter New Price:")
                    .setCancelable(false)
                    .setPositiveButton("UPDATE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // what to do if YES is tapped
                                    String val=newPrice.getText().toString();
                                    if(val.matches(""))
                                    {
                                        Toast.makeText(FinalProductView.this, "Enter new price", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        int np=Integer.parseInt(val);
                                        databaseReference.child("sellingPrice").setValue(np);
                                        Toast.makeText(FinalProductView.this,"Price Updated",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),com.sagar.betaversion.myAds.myAdActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });
            builder.setNegativeButton("CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            // code to do on NO tapped
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog=builder.create();
            dialog.show();
            /*update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String val=newPrice.getText().toString();
                    if(val.matches(""))
                    {
                        Toast.makeText(FinalProductView.this, "Enter new price", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int np=Integer.valueOf(val);
                        databaseReference.child("sellingPrice").setValue(np);
                        Toast.makeText(FinalProductView.this,"Price Updated",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            });*/

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
