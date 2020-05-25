package com.sagar.betaversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listAdRecViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView adProductBrand;
    public TextView adProductModel;
    public TextView adProductPrice;
    public ImageView adProductImage;
    ItemClickListener itemClickListener;


    listAdRecViewAdapter(@NonNull View itemView) {
        super(itemView);
        adProductBrand=itemView.findViewById(R.id.AdProdBrand);
        adProductModel=itemView.findViewById(R.id.AdProdModel);
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
