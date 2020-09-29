package com.manitkart.app;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class BetaVersion extends Application {
    //enabling offline caching
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
