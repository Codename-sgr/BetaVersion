package com.manitkart.app;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.manitkart.app.auth.LoginActivity;
import com.manitkart.app.auth.myAccount;
import com.manitkart.app.auth.profile;
import com.manitkart.app.displayAds.ListAd;
import com.manitkart.app.displayAds.myAdsAll;
import com.manitkart.app.displayAds.verifyAd;
import com.manitkart.app.postAds.newAdActivity;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawer;
    ImageButton myAccount, newAd, MyAds, donateBtn;
    CircleImageView vehicleAds, electronicAds, bookAds, miscAds;
    LoadingDialog loadingDialog;
    String username, email, retrievedImage;
    TextView verifyBtn;
    Boolean dp; // display picture
    StorageReference storageReference;
    NavigationView navigationView;
    CircleImageView navUserImg;
    TextView navUserName, navUserEmail;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    String userKey;
    SharedPreferences pref;

    @Override
    protected void onResume() {
        reloadUserData();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingDialog = new LoadingDialog(this);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
//        verifyBtn=findViewById(R.id.verifyBtn);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("MANITkart");

        }
        if(!Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified())
        {
            Toast.makeText(this, "Email Not Verified", Toast.LENGTH_SHORT).show();
            logoutUser();
        }


        navigationView = findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        navUserImg = headerview.findViewById(R.id.nav_profile_img);
        navUserName = headerview.findViewById(R.id.nav_username);
        navUserEmail = headerview.findViewById(R.id.nav_email);
        reloadUserData();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userKey= user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference().child("Manit").child("UserImage");
        myAccount = findViewById(R.id.myAccountButton);
        newAd = findViewById(R.id.newAdButton);
        MyAds = findViewById(R.id.myAdButton);
        vehicleAds = findViewById(R.id.vehicleButton);
        miscAds = findViewById(R.id.miscButton);
        electronicAds = findViewById(R.id.electronicsButton);
        bookAds = findViewById(R.id.booksButton);
//        donateBtn = findViewById(R.id.donateImgBtn);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_myads, R.id.nav_myacc, R.id.nav_about, R.id.nav_contact)
                .build();
        selectCategory();


        //TO BE RELEASE IN NEXT UPDATE..........

        /*donateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Donation Coming Soon...", Toast.LENGTH_SHORT).show();
            }
        });*/
        /*if(userKey.matches("huUeaJADQKaPRr5j3No5xztAx1E2") || userKey.matches("K6dhzrpF2IZwsibOfllS5bP5pX83"))
        {
            Log.i("yeahhhhhhh","noooooooo");
            verifyBtn.setVisibility(View.VISIBLE);
        }*/



    }



    public void reloadUserData() {

        SharedPreferences preferences=getSharedPreferences("UserData",MODE_PRIVATE);;
        username = preferences.getString("user_name", "");
        email = preferences.getString("email", "");
        retrievedImage = preferences.getString("image", "");
        if (!retrievedImage.matches("") && retrievedImage != null) {
            Bitmap bitmap = profile.StringToBitMap(retrievedImage);
            navUserImg.setImageBitmap(bitmap);
        }
        if (username != null)
            navUserName.setText(username);

        if (email != null)
            navUserEmail.setText(email);

    }

    public void loadUserData() {

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference().child("Manit");

        pref = getSharedPreferences("UserData", MODE_PRIVATE);
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
                    if (dp!=null && dp == true) {
                        final long TEN_MEGABYTE = 10 * 1024 * 1024;
                        storageReference.child(userKey + ".jpg").getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                String img_str = Base64.encodeToString(bytes, 0);
                                editor.putString("image", img_str);
                                editor.apply();
                                /*Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes.length);
                                navUserImg.setImageBitmap(bitmap);
*/                              loadingDialog.dismissDialog();
                            }
                        });
                    } else {
                        loadingDialog.dismissDialog();
                        editor.apply();

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    loadingDialog.dismissDialog();
                }
            });
        }

        navUserName.setText(username);

    }

    // SELECT AD CATEGORY TO VIEW ADS

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


        //BOTTOM NAV MENU SELECTION
        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), com.manitkart.app.auth.myAccount.class));
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
                startActivity(new Intent(getApplicationContext(), myAdsAll.class));
            }
        });

        /*verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), verifyAd.class));
            }
        });*/


    }

    //SENDING EMAIL TO CONTACT
    protected void sendEmail() {
        Log.i("Send email", "");
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?subject=" + "MANITkart Feedback "+ "&body=" +
                "" + "&to=" + "asbtech.0420@gmail.com");
        mailIntent.setData(data);
        startActivity(Intent.createChooser(mailIntent, "Send Mail via.."));
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
        getMenuInflater().inflate(R.menu.options_menu, menu);
        if(userKey.matches("huUeaJADQKaPRr5j3No5xztAx1E2") || userKey.matches("K6dhzrpF2IZwsibOfllS5bP5pX83"))
        menu.getItem(2).setVisible(true);
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
        }
        else if (id == R.id.action_logout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Alert");
            alertDialogBuilder
                    .setMessage("You Want to LOGOUT ?")
                    .setCancelable(false)
                    .setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    logoutUser();
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
        else if(id==R.id.action_verify)
            startActivity(new Intent(getApplicationContext(), verifyAd.class));


        return super.onOptionsItemSelected(item);
    }


    //TO LOG USER OUT
    private void logoutUser() {

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_myads) {
            startActivity(new Intent(MainActivity.this, myAdsAll.class));
        } else if (id == R.id.nav_myacc) {
            startActivity(new Intent(MainActivity.this, myAccount.class));
        }else if (id == R.id.nav_donate) {
            Toast.makeText(this, "Donation Coming Soon...", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_contact) {
            sendEmail();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this,about.class));
        } else if (id==R.id.nav_privpol){
            startActivity(new Intent(MainActivity.this,privacy_policy.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
