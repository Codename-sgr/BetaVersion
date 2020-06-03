package com.sagar.betaversion.myAds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sagar.betaversion.R;

public class myAdActivity extends AppCompatActivity {

    CardView vehiclesCV,electronicsCV,booksCV,miscCV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("My Ads");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        vehiclesCV=findViewById(R.id.vehicleCV);
        electronicsCV=findViewById(R.id.electronicsCV);
        booksCV=findViewById(R.id.booksCV);
        miscCV=findViewById(R.id.miscCV);

        vehiclesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myVehicle.class).putExtra("type","Vehicle"));
            }
        });

        electronicsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myElectronic.class).putExtra("type","Electronic"));

            }
        });

        booksCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myBooks.class).putExtra("type","Books"));

            }
        });

        miscCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myMisc.class).putExtra("type","Miscellaneous"));

            }
        });

        /*ListView listView=findViewById(R.id.Category);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,categories);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    startActivity(new Intent(getApplicationContext(), myVehicle.class).putExtra("type","Vehicle"));

                }
                else if(i==1)
                {
                    startActivity(new Intent(getApplicationContext(), myElectronic.class).putExtra("type","Electronic"));

                }
                else if(i==2)
                {
                    startActivity(new Intent(getApplicationContext(), myBooks.class).putExtra("type","Books"));

                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), myMisc.class).putExtra("type","Miscellaneous"));

                }


            }
        });*/
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
