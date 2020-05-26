package com.sagar.betaversion.AdCategory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.sagar.betaversion.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.sagar.betaversion.R;

public class otherAds extends AppCompatActivity {
    String type;
    EditText brand,model,date_of_purchase,sellinPrice,description;
    FirebaseAuth mAuth;
    ImageView img1,img2,img3;
    ProgressDialog progressDialog;
    ArrayList<Uri> ImageUri= new ArrayList<>();
    DatabaseReference databaseAd,userAd;
    String user_id, ad_id;
    ArrayList<byte[]> ImageArray=new ArrayList<>();
    private static final int IMAGE_REQUEST=1;
    StorageReference imageStorageRef;
    public void post(View view)
    {
        progressDialog.setMessage("Uploading Ad, Please wait!");
        progressDialog.show();

        ad_id=databaseAd.push().getKey();
        final int[] flag = {0};
        if(type.matches("Books"))
        {
            final BooksAd booksAd= new BooksAd(ad_id,
                                                user_id,
                                                brand.getText().toString(),
                                                model.getText().toString(),
                                                date_of_purchase.getText().toString(),
                                                description.getText().toString(),
                                                Integer.parseInt(sellinPrice.getText().toString()));
            final int count=ImageUri.size();
            booksAd.setImg_count(count);
            for(int i=0;i<count;i++)
            {
                final StorageReference ref=imageStorageRef.child(user_id+"/"+ad_id+"/"+ (i) +".jpg");

                UploadTask uploadTask = ref.putBytes(ImageArray.get(i));
                final int finalI = i;
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(finalI==0)
                                    booksAd.setImg1(uri.toString());
                                if(finalI==1)
                                    booksAd.setImg2(uri.toString());
                                if(finalI==2)
                                    booksAd.setImg3(uri.toString());
                                if(finalI ==count-1)
                                {
                                    progressDialog.dismiss();
                                    userAd.child(ad_id).setValue(true);
                                    databaseAd.child(ad_id).setValue(booksAd);
                                    Toast.makeText(otherAds.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0] =1;
                    }
                });


            }


            if(flag[0]==1) {

                Toast.makeText(otherAds.this,"Retry!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

        }

        else if(type.matches("Miscellaneous"))
        {
            final MiscAd Ad= new MiscAd(ad_id,
                    user_id,
                    brand.getText().toString(),
                    model.getText().toString(),
                    date_of_purchase.getText().toString(),
                    description.getText().toString(),
                    Integer.parseInt(sellinPrice.getText().toString()));
            final int count=ImageUri.size();
            Ad.setImg_count(count);

            for(int i=0;i<count;i++)
            {
                final StorageReference ref=imageStorageRef.child(user_id+"/"+ad_id+"/"+ (i) +".jpg");

                UploadTask uploadTask = ref.putBytes(ImageArray.get(i));
                final int finalI = i;
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if(finalI==0)
                                    Ad.setImg1(uri.toString());
                                if(finalI==1)
                                    Ad.setImg2(uri.toString());
                                if(finalI==2)
                                    Ad.setImg3(uri.toString());
                                if(finalI ==count-1)
                                {
                                    progressDialog.dismiss();
                                    userAd.child(ad_id).setValue(true);
                                    databaseAd.child(ad_id).setValue(Ad);
                                    Toast.makeText(otherAds.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        flag[0] =1;
                    }
                });


            }


            if(flag[0]==1) {

                Toast.makeText(this,"Retry!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
        else{
            Toast.makeText(otherAds.this,type,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        /*if(flag[0]==0)
        {
            Toast.makeText(others.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
        }*/

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
                    Toast.makeText(otherAds.this,"You can select maximum 3 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else if(count<=1)
                {
                    Toast.makeText(otherAds.this,"You have to select minimum 2 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (int i=0; i<count; i++){
                        try {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.WEBP, 40, stream);
                            byte[] byteArray = stream.toByteArray();
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
        setContentView(R.layout.activity_others);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");


        brand=findViewById(R.id.brand);
        model=findViewById(R.id.model);
        date_of_purchase=findViewById(R.id.purchaseDate);
        progressDialog= new ProgressDialog(this);
        sellinPrice=findViewById(R.id.sellingPrice);
        description=findViewById(R.id.description);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        imageStorageRef= FirebaseStorage.getInstance().getReference(type+"Image");
        mAuth=FirebaseAuth.getInstance();
        databaseAd= FirebaseDatabase.getInstance().getReference(type+"Ad");
        FirebaseUser user =mAuth.getCurrentUser();
        user_id=user.getUid();
        userAd=FirebaseDatabase.getInstance().getReference("UserAd").child(user_id).child(type+"Id");

        if(type.matches("Books"))
        {
            brand.setHint("Author");
            model.setHint("Title");
        }

        if(type.matches("Miscellaneous"))
        {
            brand.setHint("Item");
            model.setHint("Brand");
        }

    }

}
