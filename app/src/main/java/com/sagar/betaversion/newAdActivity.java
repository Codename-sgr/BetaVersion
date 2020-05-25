package com.sagar.betaversion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.sagar.betaversion.AdCategory.electronics;
import com.sagar.betaversion.AdCategory.otherAds;
import com.sagar.betaversion.AdCategory.vehicle;

import java.util.ArrayList;

public class newAdActivity extends AppCompatActivity {
    ArrayList<String> categories= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Select Category");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        categories.add("Vehicle");
        categories.add("Electronic");
        categories.add("Books");
        categories.add("Furniture");
        categories.add("Sports");
        //categories.add("Misc");
        ListView listView=findViewById(R.id.Category);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,categories);
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
