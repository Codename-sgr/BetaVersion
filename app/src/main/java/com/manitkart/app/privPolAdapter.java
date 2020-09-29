package com.manitkart.app;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class privPolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<privPolModel> mStoreMyModelArrayList;


    public privPolAdapter(Activity activity, ArrayList<privPolModel> storeMyModelArrayList) {
        this.mContext = activity;
        this.mStoreMyModelArrayList = storeMyModelArrayList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.privpol,
                parent, false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final privPolModel item = getValueAt(position);
        privPolAdapter.MyCartViewHolder myCartViewHolder = (privPolAdapter.MyCartViewHolder) holder;
        if (item != null) {
            setupValuesInWidgets(myCartViewHolder, item);
        }
    }


    private privPolModel getValueAt(int position) {
        return mStoreMyModelArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return mStoreMyModelArrayList.size();
    }

    private void setupValuesInWidgets(privPolAdapter.MyCartViewHolder itemHolder, privPolModel
            cartMyModel) {
        if (cartMyModel != null) {
            itemHolder.title.setText(Html.fromHtml(cartMyModel.getTitle()));
        }
    }


    public class MyCartViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;

        public MyCartViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

        }
    }
}
