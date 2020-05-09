package com.sagar.betaversion;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class BetaVersion extends Application {

    @Override
    public void onCreate() {

        super.onCreate();


        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
