package com.sagar.betaversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView adProductName;
    public TextView adProductPrice;
    public ImageView adProductImage;
    ItemClickListener itemClickListener;


    MyAdapter(@NonNull View itemView) {
        super(itemView);
        adProductName=itemView.findViewById(R.id.AdProdName);
        adProductPrice=itemView.findViewById(R.id.AdProdPrice);
        adProductImage=itemView.findViewById(R.id.adImageView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClickListener(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener=ic;
    }
}
