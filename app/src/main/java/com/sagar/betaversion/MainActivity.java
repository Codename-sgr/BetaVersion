package com.sagar.betaversion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sagar.betaversion.myAds.myAdActivity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ImageButton myAccount, newAd, vehicleAds, electronicAds, bookAds, miscAds, MyAds,donateBtn;
    LoadingDialog loadingDialog;
    String username;
    Boolean dp;
    StorageReference storageReference;

    @Override
    protected void onResume() {
        loadUserData();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(this);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("MANIT-KART");

        }

        NavigationView navigationView = findViewById(R.id.nav_view);

        storageReference = FirebaseStorage.getInstance().getReference().child("UserImage");
        myAccount = findViewById(R.id.myAccountButton);
        newAd = findViewById(R.id.newAdButton);
        MyAds = findViewById(R.id.myAdButton);
        vehicleAds = findViewById(R.id.vehicleButton);
        miscAds = findViewById(R.id.miscButton);
        electronicAds = findViewById(R.id.electronicsButton);
        bookAds = findViewById(R.id.booksButton);
        donateBtn=findViewById(R.id.donateImgBtn);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myads, R.id.nav_myacc, R.id.nav_about, R.id.nav_contact)
                .setDrawerLayout(drawer)
                .build();

        selectCategory();

        donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Donation Coming Soon...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadUserData() {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        final String userKey = user.getUid();
        SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
        username = pref.getString("user_name", "");
        if (username.matches("")) {
            loadingDialog.startLoadingDialog();
            Log.i("work", "preference clear works fine");
            final SharedPreferences.Editor editor = pref.edit();
            mDb.child("Users").child(userKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("work", userKey);
                    username = String.valueOf(dataSnapshot.child("user_name").getValue());
                    Log.i("Name: ", username);
//                    getSupportActionBar().setTitle("Welcome " + username);
                    String email = String.valueOf(dataSnapshot.child("email").getValue());
                    String mobile = String.valueOf(dataSnapshot.child("mobile").getValue());
                    String address = String.valueOf(dataSnapshot.child("address").getValue());
                    dp = (Boolean) dataSnapshot.child("dp").getValue();
                    editor.putString("user_name", username);
                    if (!email.matches("null"))
                        editor.putString("email", email);
                    if (!mobile.matches("null"))
                        editor.putString("mobile", mobile);
                    if (!address.matches("null"))
                        editor.putString("address", address);
                    if (dp == true) {
                        final long TEN_MEGABYTE = 10 * 1024 * 1024;
                        storageReference.child(userKey + ".jpg").getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                String img_str = Base64.encodeToString(bytes, 0);
                                editor.putString("image", img_str);
                                editor.apply();
                                loadingDialog.dismissDialog();


                            }
                        });
                    } else {
                        editor.apply();
                        loadingDialog.dismissDialog();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    loadingDialog.dismissDialog();
                }
            });
        } /*else
            getSupportActionBar().setTitle("Welcome " + username);*/
    }

    private void selectCategory() {

        final Intent intent = new Intent(getApplicationContext(), ListAd.class);
        vehicleAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Vehicle");
                startActivity(intent);
            }
        });
        electronicAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Electronic");
                startActivity(intent);
            }
        });
        bookAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Books");
                startActivity(intent);
            }
        });
        miscAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("type", "Miscellaneous");
                startActivity(intent);
            }
        });


        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myAccount.class));
            }
        });

        newAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), newAdActivity.class));
            }
        });
        MyAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), myAdActivity.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit");
            alertDialogBuilder
                    .setMessage("You Want to Exit ?")
                    .setCancelable(false)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // what to do if YES is tapped
                                    finishAffinity();
                                }
                            });
            alertDialogBuilder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            // code to do on NO tapped
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        } else if (id == R.id.action_logout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Alert");
            alertDialogBuilder
                    .setMessage("You Want to LOGOUT ?")
                    .setCancelable(false)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    FirebaseAuth.getInstance().signOut();
                                    SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.clear();
                                    editor.apply();
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
            alertDialogBuilder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_myads) {
//            Toast.makeText(MainActivity.this, "ADS", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, myAdActivity.class));

        } else if (id == R.id.nav_myacc) {
//            Toast.makeText(MainActivity.this, "ACC", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, myAccount.class));
        } else if (id == R.id.nav_contact) {
            Toast.makeText(MainActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
            //
        } else if (id == R.id.nav_about) {
            Toast.makeText(MainActivity.this, "About Us", Toast.LENGTH_SHORT).show();
            //
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
