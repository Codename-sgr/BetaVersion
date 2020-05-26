package com.sagar.betaversion;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class listAdAdapter extends RecyclerView.Adapter<listAdAdapter.ViewHolder> {

    private List<listAdModel> listAdModelList;

    public listAdAdapter(List<listAdModel> listAdModelList) {
        this.listAdModelList = listAdModelList;
    }

    @NonNull
    @Override
    public listAdAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdAdapter.ViewHolder holder, int position) {
        String adProductBrand=listAdModelList.get(position).getProdBrand();
        String adProductModel=listAdModelList.get(position).getProdModel();
        int adProductPrice=listAdModelList.get(position).getProdPrice();
        String adProductImage=listAdModelList.get(position).getProdImg();
        holder.setAdProdct(adProductBrand,adProductModel,adProductPrice,adProductImage);

    }

    @Override
    public int getItemCount() {
        return listAdModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView adProductBrand,adProductModel,adProductPrice;
        private ImageView adProductImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            adProductImg=itemView.findViewById(R.id.adImageView);
            adProductBrand=itemView.findViewById(R.id.AdProdBrand);
            adProductModel=itemView.findViewById(R.id.AdProdModel);
            adProductPrice=itemView.findViewById(R.id.AdProdPrice);

        }
        public void setAdProdct(String adBrand, String adModel, int adPrice, String adImg){
            adProductBrand.setText(adBrand);
            adProductModel.setText(adModel);
            adProductPrice.setText(String.valueOf(adPrice));
            Picasso.get().load(adImg).into(adProductImg);


        }
    }
}
