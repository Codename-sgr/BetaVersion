package com.sagar.betaversion;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sagar.betaversion.myAds.myBooks;
import com.sagar.betaversion.myAds.myElectronic;
import com.sagar.betaversion.myAds.myMisc;
import com.sagar.betaversion.myAds.myVehicle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class listAdAdapter extends RecyclerView.Adapter<listAdAdapter.ViewHolder> {

    private List<listAdModel> listAdModelList;
    private RecViewItemClickListener recViewItemClickListener;
//    private Boolean listAd;
    private  String type,Uid,activity;
    private Context context;

    public listAdAdapter(List<listAdModel> listAdModelList,RecViewItemClickListener recViewItemClickListener/*,Boolean listAd*/,String type,String Uid ,String activity,Context context) {
        this.listAdModelList = listAdModelList;
        this.recViewItemClickListener=recViewItemClickListener;
//        this.listAd=listAd;
        this.type=type;
        this.Uid=Uid;
        this.activity=activity;
        this.context=context;
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
        Boolean status=listAdModelList.get(position).isStatus();
        holder.setAdProdct(adProductBrand,adProductModel,adProductPrice,adProductImage,adProductAdId,status,position);

    }

    @Override
    public int getItemCount() {
        return listAdModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView adProductBrand,adProductModel,adProductPrice,adProductId,soldOut;
        private ImageView adProductImg;
        private ImageButton adDelete,adSoldBtn;


        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            adProductImg=itemView.findViewById(R.id.adImageView);
            adProductBrand=itemView.findViewById(R.id.AdProdBrand);
            adProductModel=itemView.findViewById(R.id.AdProdModel);
            adProductPrice=itemView.findViewById(R.id.AdProdPrice);
            adProductId=itemView.findViewById(R.id.AdProdId);
            adDelete=itemView.findViewById(R.id.adDeleteBtn);
            adSoldBtn=itemView.findViewById(R.id.adSoldBtn);
            soldOut=itemView.findViewById(R.id.soldOut);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recViewItemClickListener.onItemClickListener(getAdapterPosition(),adProductId.getText().toString());
                }
            });


        }
        void setAdProdct(final String adBrand, String adModel, int adPrice, String adImg, final String adId, final Boolean status, final int position){
            adProductBrand.setText(adBrand);
            adProductModel.setText(adModel);
            adProductPrice.setText(String.valueOf(adPrice));
            Picasso.get().load(adImg).error(R.drawable.androidlogo).placeholder(R.drawable.loadinga).into(adProductImg);
            adProductId.setText(adId);

            if(!status){
                soldOut.setVisibility(View.VISIBLE);
            }



            if(context instanceof ListAd){
                adSoldBtn.setVisibility(View.INVISIBLE);
                adDelete.setVisibility(View.INVISIBLE);
            }
            else{
                adSoldBtn.setVisibility(View.VISIBLE);
                adDelete.setVisibility(View.VISIBLE);
            }

            adDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Task t1=FirebaseDatabase.getInstance().getReference().child("UserAd")
                            .child(Uid).child(type+"Id").child(adId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Task t2=FirebaseDatabase.getInstance().getReference().child(type+"Ad").child(adId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            switch (activity) {
                                                case "ListAd":
                                                    listAdModelList.remove(position);
                                                    ListAd.listAdAdapter.notifyDataSetChanged();
                                                    break;
                                                case "myElectronic":
                                                    listAdModelList.remove(position);
                                                    myElectronic.listAdAdapter.notifyDataSetChanged();
                                                    break;
                                                case "myMisc":
                                                    listAdModelList.remove(position);
                                                    myMisc.listAdAdapter.notifyDataSetChanged();
                                                    break;
                                                case "myVehicle":
                                                    listAdModelList.remove(position);
                                                    myVehicle.listAdAdapter.notifyDataSetChanged();
                                                    break;
                                                case "myBooks":
                                                    listAdModelList.remove(position);
                                                    myBooks.listAdAdapter.notifyDataSetChanged();
                                                    break;

                                            }
                                        }
                                    });
                                }
                            });
                }
            });

            adSoldBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle("Warning");
                    builder
                            .setMessage("You want to mark this Ad as SOLD ?\n This Action cannot be Undone !!!")
                            .setCancelable(false);
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                        FirebaseDatabase.getInstance().getReference().child(type+"Ad").child(adId).child("status").setValue(false);
                                        soldOut.setVisibility(View.VISIBLE);
                                        adSoldBtn.setVisibility(View.INVISIBLE);
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    androidx.appcompat.app.AlertDialog dialog=builder.create();
                    dialog.show();


                }
            });

        }

    }

}
