package com.manitkart.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FinalImagePreview extends AppCompatActivity {

    //PREVIEW IMAGE IN FULL SCREEN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_image_preview);

        final ViewPager finalImgPreview = findViewById(R.id.finalImgPreview);
        TabLayout viewpagerIndicator = findViewById(R.id.finalImgTabLayout);
        Button closeBtn=findViewById(R.id.closeImgPrevBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent i=getIntent();
        ArrayList<String> imageURLs=i.getStringArrayListExtra("img");

        imagePreviewAdapter imagePreviewAdapter=new imagePreviewAdapter(FinalImagePreview.this,imageURLs);
        finalImgPreview.setAdapter(imagePreviewAdapter);
        viewpagerIndicator.setupWithViewPager(finalImgPreview,true);

    }
}
