package com.sagar.betaversion;


import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.sagar.betaversion.displayAds.ListAd;
import com.sagar.betaversion.displayAds.myAdsAll;
import com.squareup.picasso.Picasso;

import java.util.List;

public class listAdAdapter extends RecyclerView.Adapter<listAdAdapter.ViewHolder> {

    private List<listAdModel> listAdModelList;
    private RecViewItemClickListener recViewItemClickListener;
//    private Boolean listAd;
    private  String Uid,activity;
    private Context context;

    public listAdAdapter(List<listAdModel> listAdModelList,RecViewItemClickListener recViewItemClickListener,String Uid ,String activity,Context context) {
        this.listAdModelList = listAdModelList;
        this.recViewItemClickListener=recViewItemClickListener;
//        this.listAd=listAd;

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
        String type=listAdModelList.get(position).getType();
        int vs=listAdModelList.get(position).getVs();
        String uadId=listAdModelList.get(position).getUadId();
        holder.setAdProdct(adProductBrand,adProductModel,adProductPrice,adProductImage,adProductAdId,uadId,status,type,vs,position);

    }

    @Override
    public int getItemCount() {
        return listAdModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView adProductBrand,adProductModel,adProductPrice,adProductId,soldOut,verified,uadType,adType;
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
            verified=itemView.findViewById(R.id.verified);
            adType=itemView.findViewById(R.id.adType);
            uadType=itemView.findViewById(R.id.uadType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recViewItemClickListener.onItemClickListener(getAdapterPosition(),adProductId.getText().toString(),adType.getText().toString(),uadType.getText().toString());
                }
            });


        }
        void setAdProdct(final String adBrand, String adModel, int adPrice, String adImg, final String adId, final String uadId,  final Boolean status, final String type,final int vs, final int position){
            adProductBrand.setText(adBrand);
            adProductModel.setText(adModel);
            adProductPrice.setText(String.valueOf(adPrice));
            Picasso.get().load(adImg).error(R.drawable.androidlogo).placeholder(R.drawable.loadinga).into(adProductImg);
            adProductId.setText(adId);
            adType.setText(type);
            uadType.setText(uadId);
            if(!status){
                soldOut.setVisibility(View.VISIBLE);
            }
            if(vs==0)
            {
                verified.setVisibility(View.VISIBLE);
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

                    androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle("This ad will be deleted permanently, are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Task t1=FirebaseDatabase.getInstance().getReference().child("Manit").child("UserAd")
                                    .child(Uid).child(uadId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Task t2=FirebaseDatabase.getInstance().getReference().child("Manit").child(type+"Ad").child(adId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    switch (activity) {
                                                        case "ListAd":
                                                            listAdModelList.remove(position);
                                                            ListAd.listAdAdapter.notifyDataSetChanged();
                                                            break;
                                                        case "myAdsAll":
                                                            listAdModelList.remove(position);
                                                            myAdsAll.listAdAdapter.notifyDataSetChanged();
                                                            break;

                                                    }
                                                }
                                            });
                                        }
                                    });
                            //FirebaseStorage.getInstance().getReference().child("Manit").child(type+"Image"+"/"+Uid+"/"+adId).delete();

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

            adSoldBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Manit");
                    final Intent i=new Intent(context,com.sagar.betaversion.MainActivity.class);
                    androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(context);
                    builder.setTitle("Warning");
                    if(status){
                        builder.setMessage("Do you want to mark this Product as SOLD ?")
                                .setCancelable(false);
                        builder.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        databaseReference.child(type+"Ad").child(adId).child("status").setValue(false);
                                        databaseReference.child("UserAd").child(Uid).child(uadId).child("status").setValue(false);
                                        soldOut.setVisibility(View.VISIBLE);
                                        Toast.makeText(context,"Marked as SOLD",Toast.LENGTH_SHORT).show();
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(i);

                                    }
                                });
                    }
                    else {
                        builder.setMessage("Do you want to mark this Product as UNSOLD ?")
                                .setCancelable(false);
                        builder.setPositiveButton("YES",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        databaseReference.child(type+"Ad").child(adId).child("status").setValue(true);
                                        databaseReference.child("UserAd").child(Uid).child(uadId).child("status").setValue(true);
                                        soldOut.setVisibility(View.INVISIBLE);
                                        Toast.makeText(context,"Marked as UNSOLD",Toast.LENGTH_SHORT).show();
                                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(i);

                                    }
                                });
                    }

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
