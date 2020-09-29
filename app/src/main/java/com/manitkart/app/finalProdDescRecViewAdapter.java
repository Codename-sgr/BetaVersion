package com.manitkart.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class finalProdDescRecViewAdapter extends RecyclerView.Adapter<finalProdDescRecViewAdapter.ViewHolder> {

    private List<finalProdSpecificationModel> finalProdSpecificationModelList;

    public finalProdDescRecViewAdapter(List<finalProdSpecificationModel> finalProdSpecificationModelList) {
        this.finalProdSpecificationModelList = finalProdSpecificationModelList;
    }

    @NonNull
    @Override
    public finalProdDescRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.finalprod_desc_row_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull finalProdDescRecViewAdapter.ViewHolder holder, int position) {
        String featureName = finalProdSpecificationModelList.get(position).getFeatureName();
        String featureValue = finalProdSpecificationModelList.get(position).getFeatureValue();
        holder.setFeatures(featureName, featureValue);
    }

    @Override
    public int getItemCount() {
        return finalProdSpecificationModelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView featureName;
        private TextView featureValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            featureName=itemView.findViewById(R.id.prodFeatureName);
            featureValue=itemView.findViewById(R.id.prodFeatureValue);
        }
        private void setFeatures(String featureTitle,String featureDetail){
            featureName.setText(featureTitle);
            featureValue.setText(featureDetail);
        }

    }
}
