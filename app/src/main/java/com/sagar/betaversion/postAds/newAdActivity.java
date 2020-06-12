package com.sagar.betaversion.postAds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.sagar.betaversion.R;
import com.sagar.betaversion.postAds.electronics;
import com.sagar.betaversion.postAds.otherAds;
import com.sagar.betaversion.postAds.vehicle;

public class newAdActivity extends AppCompatActivity {

    CardView vehiclesCV,electronicsCV,booksCV,miscCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Select Category");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        vehiclesCV=findViewById(R.id.vehicleCV);
        electronicsCV=findViewById(R.id.electronicsCV);
        booksCV=findViewById(R.id.booksCV);
        miscCV=findViewById(R.id.miscCV);

        vehiclesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), vehicle.class));
            }
        });

        electronicsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), electronics.class));
            }
        });

        booksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), otherAds.class);
                intent.putExtra("type","Books");
                startActivity(intent);
            }
        });

        miscCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), otherAds.class);
                intent.putExtra("type","Miscellaneous");
                startActivity(intent);
            }
        });

        /*ListView listView=findViewById(R.id.Category);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,categories);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    startActivity(new Intent(getApplicationContext(), vehicle.class));
                }
                else if(i==1)
                {
                    startActivity(new Intent(getApplicationContext(), electronics.class));
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(), otherAds.class);
                    intent.putExtra("type",categories.get(i));
                    startActivity(intent);
                }

            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
