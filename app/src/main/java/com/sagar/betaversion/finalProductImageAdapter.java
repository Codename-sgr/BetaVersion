package com.sagar.betaversion;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class finalProductImageAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> productImageUrls;

    finalProductImageAdapter(Context context, ArrayList<String> productImageUrls) {
        this.context=context;
        this.productImageUrls=productImageUrls;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,int position){
        ImageView finalProdImage=new ImageView(context);
        Picasso.get()
                .load(productImageUrls.get(position))
                .into(finalProdImage);
        container.addView(finalProdImage);

        finalProdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,FinalImagePreview.class);
                i.putStringArrayListExtra("img",productImageUrls);
                context.startActivity(i);
            }
        });


        return finalProdImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }

    public int getCount() {
        return productImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


}
