package com.sagar.betaversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.ViewHolder{

    public TextView adProductName;
    public TextView adProductPrice;
    public ImageView adProductImage;



    public MyAdapter(@NonNull View itemView) {
        super(itemView);
        adProductName=itemView.findViewById(R.id.AdProdName);
        adProductPrice=itemView.findViewById(R.id.AdProdPrice);
        adProductImage=itemView.findViewById(R.id.adImageView);
    }
}
