package com.sagar.betaversion.myAds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sagar.betaversion.R;

import java.util.ArrayList;

public class myAdActivity extends AppCompatActivity {
    ArrayList<String> categories= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad);
        categories.add("Vehicle");
        categories.add("Electronic");
        categories.add("Books");
        categories.add("Miscellaneous");

        //categories.add("Misc");
        ListView listView=findViewById(R.id.Category);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,categories);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    startActivity(new Intent(getApplicationContext(), myVehicle.class));
                }
                else if(i==1)
                {
                    startActivity(new Intent(getApplicationContext(), myElectronic.class));
                }
                else if(i==2)
                {
                    startActivity(new Intent(getApplicationContext(), myBooks.class));
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), myMisc.class));
                }

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);}
}
