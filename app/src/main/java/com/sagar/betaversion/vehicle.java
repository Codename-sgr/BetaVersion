package com.sagar.betaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class vehicle extends AppCompatActivity {
    EditText  model,purchaseDate,insuranceDate,milege,description;
    FirebaseAuth mAuth;
    ImageView img1,img2,img3;
    DatabaseReference databaseAd;
    String user_id;
    public void post(View view)
    {
        FirebaseUser user =mAuth.getCurrentUser();
        user_id=user.getUid();
        String ad_id=databaseAd.push().getKey();
        VehicleAd vehicleAd= new VehicleAd(ad_id,user_id,model.getText().toString(),purchaseDate.getText().toString(),insuranceDate.getText().toString(),Integer.parseInt(milege.getText().toString()),description.toString());
        databaseAd.child(ad_id).setValue(vehicleAd);
    }
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        model=findViewById(R.id.model);
        purchaseDate=findViewById(R.id.purchaseDate);
        insuranceDate=findViewById(R.id.insuranceDate);
        milege=findViewById(R.id.milege);
        description=findViewById(R.id.description);
        mAuth=FirebaseAuth.getInstance();
        databaseAd= FirebaseDatabase.getInstance().getReference("VehicleAd");

    }
}
