package com.sagar.betaversion;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.sagar.betaversion.myAds.myBooks;
import com.sagar.betaversion.myAds.myElectronic;
import com.sagar.betaversion.myAds.myMisc;
import com.sagar.betaversion.myAds.myVehicle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class listAdAdapter extends RecyclerView.Adapter<listAdAdapter.ViewHolder> {

    private List<listAdModel> listAdModelList;
    private RecViewItemClickListener recViewItemClickListener;
    private Boolean listAd;
    private  String type,Uid,activity;

    public listAdAdapter(List<listAdModel> listAdModelList,RecViewItemClickListener recViewItemClickListener,Boolean listAd,String type,String Uid ,String activity) {
        this.listAdModelList = listAdModelList;
        this.recViewItemClickListener=recViewItemClickListener;
        this.listAd=listAd;
        this.type=type;
        this.Uid=Uid;
        this.activity=activity;
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
        holder.setAdProdct(adProductBrand,adProductModel,adProductPrice,adProductImage,adProductAdId,position);

    }

    @Override
    public int getItemCount() {
        return listAdModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView adProductBrand,adProductModel,adProductPrice,adProductId;
        private ImageView adProductImg;
        private ImageButton adDelete,adWishlist;


        ViewHolder(@NonNull final View itemView) {
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
        void setAdProdct(String adBrand, String adModel, int adPrice, String adImg, final String adId, final int position){
            adProductBrand.setText(adBrand);
            adProductModel.setText(adModel);
            adProductPrice.setText(String.valueOf(adPrice));
            Picasso.get().load(adImg).into(adProductImg);
            adProductId.setText(Integer.toString(position));

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
                    Log.i("BEFORE",""+(position));
                    Log.i("BEFORE",""+listAdModelList.size());
//                    Log.i("BEFORE",""+listAdModelList.get(position));
                    Task t1=FirebaseDatabase.getInstance().getReference().child("UserAd")
                            .child(Uid).child(type+"Id").child(adId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Task t2=FirebaseDatabase.getInstance().getReference().child(type+"Ad").child(adId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            switch (activity) {
                                                case "ListAd":
                                                    ListAd.listAdAdapter.notifyItemRemoved(position);
                                                    listAdModelList.remove(position);
                                                    break;
                                                case "myElectronic":
                                                    myElectronic.listAdAdapter.notifyItemRemoved(position);
                                                    listAdModelList.remove(position);
                                                    break;
                                                case "myMisc":
                                                    myMisc.listAdAdapter.notifyItemRemoved(position);
                                                    listAdModelList.remove(position);
                                                    break;
                                                case "myVehicle":
                                                    myVehicle.listAdAdapter.notifyItemRemoved(position);
                                                    listAdModelList.remove(position);
                                                    break;
                                                case "myBooks":
                                                    myBooks.listAdAdapter.notifyItemRemoved(position);
                                                    listAdModelList.remove(position);
                                                    break;

                                            }
                                        }
                                    });
                                }
                            });
                    //CODE HERE *****************************************************************************
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
