package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FinalImagePreview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_image_preview);

        ViewPager finalImgPreview = findViewById(R.id.finalImgPreview);
        TabLayout viewpagerIndicator = findViewById(R.id.finalImgTabLayout);


        Intent i=getIntent();
        ArrayList<String> imageURLs=i.getStringArrayListExtra("img");

        imagePreviewAdapter imagePreviewAdapter=new imagePreviewAdapter(FinalImagePreview.this,imageURLs);
        finalImgPreview.setAdapter(imagePreviewAdapter);
        viewpagerIndicator.setupWithViewPager(finalImgPreview,true);

    }
}
