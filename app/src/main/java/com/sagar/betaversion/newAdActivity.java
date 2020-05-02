package com.sagar.betaversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class newAdActivity extends AppCompatActivity {
    ArrayList<String> categories= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ad);

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
                    startActivity(new Intent(getApplicationContext(),vehicle.class));
                }
                else if(i==1)
                {
                    startActivity(new Intent(getApplicationContext(),electronics.class));
                }
                else
                {
                    Intent intent=new Intent(getApplicationContext(),others.class);
                    intent.putExtra("type",categories.get(i));
                    startActivity(intent);
                }

            }
        });

    }
}