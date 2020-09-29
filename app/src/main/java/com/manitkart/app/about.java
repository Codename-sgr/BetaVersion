package com.manitkart.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

//ABOUT THE DEVELOPERS
public class about extends AppCompatActivity {

    ImageView sagarFB,sagarLI,bansalFB,bansalLI,bansalIG;
    ImageView namrataFB,namrataLI,namrataIG,abhashIG,abhashTT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("About Us");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        sagarFB=findViewById(R.id.sagarFB);
        sagarLI=findViewById(R.id.sagarLI);
        bansalFB=findViewById(R.id.bansalFB);
        bansalIG=findViewById(R.id.bansalIG);
        bansalLI=findViewById(R.id.bansalLI);
        namrataFB=findViewById(R.id.namrataFB);
        namrataLI=findViewById(R.id.namrataLI);
        namrataIG=findViewById(R.id.namrataIG);
        abhashTT=findViewById(R.id.abhashTT);
        abhashIG=findViewById(R.id.abhashIG);


        sagarLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/in/sagar-singh-817976179/"));
                startActivity(intent);
            }
        });

        sagarFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/codename.sgr/"));
                startActivity(intent);

            }
        });

        bansalLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/in/shivam-bansal-manit/"));
                startActivity(intent);

            }
        });

        bansalIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/shivam_bansal_4/"));
                startActivity(intent);

            }
        });

        bansalFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/shivamb0407"));
                startActivity(intent);

            }
        });



        namrataFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/namrata.niaj"));
                startActivity(intent);
            }
        });
        namrataIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/namrata.niaj/"));
                startActivity(intent);
            }
        });
        namrataLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/in/namrata-jain-382000177"));
                startActivity(intent);
            }
        });



        abhashIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.instagram.com/abhash_r_r/"));
                startActivity(intent);
            }
        });
        abhashTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/AbhashRahangda1?s=08"));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
