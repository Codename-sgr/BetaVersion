package com.sagar.betaversion.postAds;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.sagar.betaversion.LoadingDialog;
import com.sagar.betaversion.MainActivity;
import com.sagar.betaversion.models.BooksAd;
import com.sagar.betaversion.models.MiscAd;
import com.sagar.betaversion.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class otherAds extends AppCompatActivity {

    LoadingDialog loadingDialog;
    String type;
    EditText brand,model,date_of_purchase,sellinPrice,description;
    String vbrand,vmodel,vdescription,vpurchaseDate;
    int vsellinPrice;
    FirebaseAuth mAuth;
    ImageView img1,img2,img3;
    ArrayList<Uri> ImageUri= new ArrayList<>();
    DatabaseReference databaseAd,userAd,verRef;
    String user_id, ad_id,uad_id;
    ArrayList<byte[]> ImageArray=new ArrayList<>();
    private static final int IMAGE_REQUEST=1;
    StorageReference imageStorageRef;
    public void uploadAd(final int i, final BooksAd electronicsAd, final int count) {
        if(i==count)
        {
            loadingDialog.dismissDialog();
            databaseAd.child(ad_id).setValue(electronicsAd);
            userAd.child(uad_id).setValue(electronicsAd);
            verRef.child(uad_id).setValue(electronicsAd);
            userAd.child(uad_id).child("uadId").setValue(uad_id);
            userAd.child(uad_id).child("type").setValue("Books");
            verRef.child(uad_id).child("uadId").setValue(uad_id);
            verRef.child(uad_id).child("type").setValue("Books");;
            Toast.makeText(otherAds.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(otherAds.this,"Retry!",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    public void uploadAdMisc(final int i, final MiscAd electronicsAd, final int count) {
        if(i==count)
        {
            loadingDialog.dismissDialog();
            databaseAd.child(ad_id).setValue(electronicsAd);
            userAd.child(uad_id).setValue(electronicsAd);
            verRef.child(uad_id).setValue(electronicsAd);
            userAd.child(uad_id).child("uadId").setValue(uad_id);
            userAd.child(uad_id).child("type").setValue("Miscellaneous");
            verRef.child(uad_id).child("uadId").setValue(uad_id);
            verRef.child(uad_id).child("type").setValue("Miscellaneous");;
            Toast.makeText(otherAds.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        uploadAdMisc(i+1,electronicsAd,count);
                        return;
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(otherAds.this,"Retry!",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    public void post(View view)
    {
        vbrand=brand.getText().toString();
        if(vbrand.isEmpty()){
            brand.setError("Required.");
            brand.setFocusable(true);
        }
        vmodel=model.getText().toString();
        if(vmodel.isEmpty()){
            model.setError("Required.");
            model.setFocusable(true);
        }
        vdescription=description.getText().toString();
        if(vdescription.isEmpty()){
            description.setError("Required.");
            description.setFocusable(true);
        }
        vpurchaseDate=date_of_purchase.getText().toString();
        if(vpurchaseDate.isEmpty()){
            date_of_purchase.setError("Required.");
            date_of_purchase.setFocusable(true);
        }

        try {
            vsellinPrice=Integer.parseInt(sellinPrice.getText().toString());
        }catch (NumberFormatException nfe){
            if(vsellinPrice<1) {
                sellinPrice.setError("Fill Proper Data.");
                model.setFocusable(true);
            }
        }

        final int count=ImageUri.size();
        if(count<2)
            Toast.makeText(this, "Select atleast 2 Images", Toast.LENGTH_SHORT).show();
        else
        {
            loadingDialog.startLoadingDialog();
            ad_id=databaseAd.push().getKey();
            uad_id=userAd.push().getKey();
            final int[] flag = {0};
            if(type.matches("Books"))
            {
                final BooksAd Ad= new BooksAd(ad_id,
                        user_id,
                        vbrand,
                        vmodel,
                        vpurchaseDate,
                        vdescription,
                        vsellinPrice);

                Ad.setImg_count(count);
                uploadAd(0,Ad,count);
            }

            else if(type.matches("Miscellaneous")) {
                final MiscAd Ad = new MiscAd(ad_id,
                        user_id,
                        vbrand,
                        vmodel,
                        vpurchaseDate,
                        vdescription,
                        vsellinPrice);

                Ad.setImg_count(count);
                uploadAdMisc(0,Ad,count);

            }
        }


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
                            int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            scaled.compress(Bitmap.CompressFormat.JPEG,50, stream);
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("NEW AD - "+type);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadingDialog=new LoadingDialog(this);

        brand=findViewById(R.id.brand);
        model=findViewById(R.id.model);
        date_of_purchase=findViewById(R.id.purchaseDate);

        sellinPrice=findViewById(R.id.sellingPrice);
        description=findViewById(R.id.description);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        imageStorageRef= FirebaseStorage.getInstance().getReference().child("Manit").child(type+"Image");
        verRef=FirebaseDatabase.getInstance().getReference().child("Manit").child("Verification");
        mAuth=FirebaseAuth.getInstance();
        databaseAd= FirebaseDatabase.getInstance().getReference().child("Manit").child(type+"Ad");
        FirebaseUser user =mAuth.getCurrentUser();
        user_id=user.getUid();
        userAd=FirebaseDatabase.getInstance().getReference().child("Manit").child("UserAd").child(user_id);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
