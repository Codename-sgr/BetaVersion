package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class electronics extends AppCompatActivity {
    EditText brand,model,purchaseDate,sellingPrice,description;
    FirebaseAuth mAuth;
    ImageView img1,img2,img3;
    ArrayList<Uri> ImageUri= new ArrayList<>();
    ArrayList<byte[]> ImageArray= new ArrayList<>();
    DatabaseReference databaseAd,userAd;
    LoadingDialog loadingDialog;
    String user_id, ad_id;
    private static final int IMAGE_REQUEST=1;
    StorageReference imageStorageRef;
    public void uploadAd(final int i, final ElectronicsAd electronicsAd, final int count) {
        if(i==count)
        {
            loadingDialog.dismissDialog();
            databaseAd.child(ad_id).setValue(electronicsAd);
            userAd.child(ad_id).setValue(true);
            Toast.makeText(electronics.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        final int[] flag = {0};
        final StorageReference ref = imageStorageRef.child(user_id + "/" + ad_id + "/"+i+".jpg");
        UploadTask uploadTask = ref.putBytes(ImageArray.get(i));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if(i==0)
                            electronicsAd.setImg1(uri.toString());
                        if(i==1)
                            electronicsAd.setImg2(uri.toString());
                        if(i==2)
                            electronicsAd.setImg3(uri.toString());
                        uploadAd(i+1,electronicsAd,count);
                        return;
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(electronics.this,"Retry!",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    public void post(View view)
    {
        loadingDialog.startLoadingDialog();

        ad_id=databaseAd.push().getKey();
        final ElectronicsAd electronicsAd= new ElectronicsAd(ad_id
                                                            ,user_id
                                                            ,brand.getText().toString()
                                                            ,model.getText().toString()
                                                            ,purchaseDate.getText().toString()
                                                            ,description.getText().toString()
                                                            ,Integer.parseInt(sellingPrice.getText().toString()));
        final int count=ImageUri.size();
        electronicsAd.setImg_count(count);
        uploadAd(0,electronicsAd,count);

    }
    public void ChooseImage(View view)
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK )
        {
            if(data.getClipData()!=null)
            {
                int count=data.getClipData().getItemCount();
                if(count>3)
                {
                    Toast.makeText(electronics.this,"You can select maximum 3 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else if(count<=1)
                {
                    Toast.makeText(electronics.this,"You have to select minimum 2 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (int i=0; i<count; i++){


                        try {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            scaled.compress(Bitmap.CompressFormat.JPEG,50, stream);
                            byte[] byteArray = stream.toByteArray();

//                            Bitmap bitmapImage = BitmapFactory.decodeFile(imageUri.toString());
//                            int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//                            Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//                            your_imageview.setImageBitmap(scaled);

                            if(i==0)
                            {
                                Picasso.get().load(imageUri).into(img1);
                                ImageUri.add(imageUri);
                                ImageArray.add(byteArray);
                                img1.setVisibility(View.VISIBLE);
                            }
                            else if(i==1)
                            {
                                Picasso.get().load(imageUri).into(img2);
                                ImageUri.add(imageUri);
                                ImageArray.add(byteArray);
                                img2.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Picasso.get().load(imageUri).into(img3);
                                ImageUri.add(imageUri);
                                ImageArray.add(byteArray);
                                img3.setVisibility(View.VISIBLE);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("NEW AD - Electronic");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        loadingDialog=new LoadingDialog(this);

        brand=findViewById(R.id.brand);
        model=findViewById(R.id.model);
        purchaseDate=findViewById(R.id.purchaseDate);
        sellingPrice=findViewById(R.id.sellingPrice);
        description=findViewById(R.id.description);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        imageStorageRef= FirebaseStorage.getInstance().getReference("ElectronicImage");
        mAuth=FirebaseAuth.getInstance();
        databaseAd= FirebaseDatabase.getInstance().getReference("ElectronicAd");
        FirebaseUser user =mAuth.getCurrentUser();
        user_id=user.getUid();
        userAd=FirebaseDatabase.getInstance().getReference("UserAd").child(user_id).child("ElectronicId");

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
