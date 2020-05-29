package com.sagar.betaversion;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class listAdAdapter extends RecyclerView.Adapter<listAdAdapter.ViewHolder> {

    private List<listAdModel> listAdModelList;
    private RecViewItemClickListener recViewItemClickListener;
    private Boolean listAd;
    private  String type;
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();

    public listAdAdapter(List<listAdModel> listAdModelList,RecViewItemClickListener recViewItemClickListener,Boolean listAd,String type) {
        this.listAdModelList = listAdModelList;
        this.recViewItemClickListener=recViewItemClickListener;
        this.listAd=listAd;
        this.type=type;
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
        String adProductAdId=listAdModelList.get(position).getAdId();

        holder.setAdProdct(adProductBrand,adProductModel,adProductPrice,adProductImage,adProductAdId);

    }

    @Override
    public int getItemCount() {
        return listAdModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView adProductBrand,adProductModel,adProductPrice,adProductId;
        private ImageView adProductImg;
        private ImageButton adDelete,adWishlist;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            adProductImg=itemView.findViewById(R.id.adImageView);
            adProductBrand=itemView.findViewById(R.id.AdProdBrand);
            adProductModel=itemView.findViewById(R.id.AdProdModel);
            adProductPrice=itemView.findViewById(R.id.AdProdPrice);
            adProductId=itemView.findViewById(R.id.AdProdId);
            adDelete=itemView.findViewById(R.id.adDeleteBtn);
            adWishlist=itemView.findViewById(R.id.adWishListBtn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recViewItemClickListener.onItemClickListener(getAdapterPosition(),adProductId.getText().toString());
                }
            });


        }
        public void setAdProdct(String adBrand, String adModel, int adPrice, String adImg,String adId){
            adProductBrand.setText(adBrand);
            adProductModel.setText(adModel);
            adProductPrice.setText(String.valueOf(adPrice));
            Picasso.get().load(adImg).into(adProductImg);
            adProductId.setText(adId);

            if(listAd){
                adWishlist.setVisibility(View.VISIBLE);
                adDelete.setVisibility(View.INVISIBLE);
            }
            else{
                adWishlist.setVisibility(View.INVISIBLE);
                adDelete.setVisibility(View.VISIBLE);
            }

            adDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //CODE HERE *********************************************************************************
                    Toast.makeText(itemView.getContext(), "Delete "+type+"  "+ firebaseAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                }
            });

            adWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Wishlist", Toast.LENGTH_SHORT).show();
                }
            });



        }

    }

}
