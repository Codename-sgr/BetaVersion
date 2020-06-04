package com.sagar.betaversion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class imagePreviewAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> productImageUrls;

    imagePreviewAdapter(Context context, ArrayList<String> productImageUrls) {
        this.context=context;
        this.productImageUrls=productImageUrls;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        ImageView finalProdImage=new ImageView(context);
        Picasso.get()
                .load(productImageUrls.get(position))
                .into(finalProdImage);
        container.addView(finalProdImage);


        return finalProdImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    @Override
    public int getCount() {
        return productImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
