package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static java.security.AccessController.getContext;

public class ListAd extends AppCompatActivity {
     RecyclerView recyclerView;
     FirebaseRecyclerAdapter<VehicleAd,MyAdapter> adapter;
     FirebaseDatabase database;
     DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_ad);

        database= FirebaseDatabase.getInstance();
        databaseReference=database.getReference("VehicleAd");

        recyclerView=findViewById(R.id.AdListRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        showList();

    }

    private void showList() {
        FirebaseRecyclerOptions<VehicleAd> options=new FirebaseRecyclerOptions.Builder<VehicleAd>()
                .setQuery(databaseReference,VehicleAd.class)
                .build();

        adapter=new FirebaseRecyclerAdapter<VehicleAd, MyAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyAdapter myAdapter, int i, @NonNull VehicleAd vehicleAd) {
                myAdapter.adProductName.setText(vehicleAd.getModel());
                myAdapter.adProductPrice.setText(Integer.toString(vehicleAd.getMilege()));

                myAdapter.adProductImage.setImageResource(R.drawable.androidlogo);
            }

            @NonNull
            @Override
            public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_data,parent,false);
                return new MyAdapter(view);
            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


}
