package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class others extends AppCompatActivity {
    String type;
    EditText model,purchaseDate,sellinPrice,description;
    FirebaseAuth mAuth;
    ImageView img1,img2,img3;
    ArrayList<Uri> ImageUri= new ArrayList<>();
    DatabaseReference databaseAd;
    String user_id, ad_id;
    Uri uri;
    private static final int IMAGE_REQUEST=1;
    StorageReference imageStorageRef;
    public void post(View view)
    {
        // progressDialog.setMessage("Uploading Ad, Please wait!");
        //progressDialog.show();
        FirebaseUser user =mAuth.getCurrentUser();
        user_id=user.getUid();
        ad_id=databaseAd.push().getKey();
        final int[] flag = {0};
        if(type.matches("Books"))
        {
            final BooksAd booksAd= new BooksAd(ad_id,user_id,model.getText().toString(),purchaseDate.getText().toString(),description.getText().toString(),sellinPrice.getText().toString());
            int count=ImageUri.size();
            booksAd.setImg_count(count);
            booksAd.setFileExtension(getFileExtension(ImageUri.get(0)));
            databaseAd.child(ad_id).setValue(booksAd);
            for(int i=0;i<count;i++) {
                final StorageReference ref = imageStorageRef.child(user_id + "/" + ad_id + "/" + Integer.toString(i) + '.' + getFileExtension(ImageUri.get(i)));

                UploadTask uploadTask = ref.putFile(ImageUri.get(i));

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                        } else {
                            Toast.makeText(others.this, "Failed", Toast.LENGTH_SHORT).show();
                            flag[0] = 1;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(others.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        flag[0] = 1;
                    }
                });
            }
        }
        else if(type.matches("Furniture"))
        {
            final FurnitureAd furnitureAd= new FurnitureAd(ad_id,user_id,model.getText().toString(),purchaseDate.getText().toString(),description.getText().toString(),sellinPrice.getText().toString());
            int count=ImageUri.size();
            furnitureAd.setImg_count(count);
            furnitureAd.setFileExtension(getFileExtension(ImageUri.get(0)));
            databaseAd.child(ad_id).setValue(furnitureAd);
            for(int i=0;i<count;i++)
            {
                final StorageReference ref=imageStorageRef.child(user_id+"/"+ad_id+"/"+ Integer.toString(i) +'.'+getFileExtension(ImageUri.get(i)));

                UploadTask uploadTask = ref.putFile(ImageUri.get(i));

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                        } else {
                            Toast.makeText(others.this,"Failed",Toast.LENGTH_SHORT).show();
                            flag[0] =1;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(others.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        flag[0]=1;
                    }
                });
            }

        }
        else if(type.matches("Sports"))
        {
            final SportsAd sportsAd= new SportsAd(ad_id,user_id,model.getText().toString(),purchaseDate.getText().toString(),description.getText().toString(),sellinPrice.getText().toString());
            int count=ImageUri.size();
            sportsAd.setImg_count(count);
            sportsAd.setFileExtension(getFileExtension(ImageUri.get(0)));
            databaseAd.child(ad_id).setValue(sportsAd);
            for(int i=0;i<count;i++)
            {
                final StorageReference ref=imageStorageRef.child(user_id+"/"+ad_id+"/"+ Integer.toString(i) +'.'+getFileExtension(ImageUri.get(i)));

                UploadTask uploadTask = ref.putFile(ImageUri.get(i));

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                        } else {
                            Toast.makeText(others.this,"Failed",Toast.LENGTH_SHORT).show();
                            flag[0] =1;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(others.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        flag[0]=1;
                    }
                });
            }

        }
        else{
            Toast.makeText(others.this,type,Toast.LENGTH_SHORT).show();
        }
        if(flag[0]==0)
        {
            Toast.makeText(others.this,"Ad Posted Successfully",Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
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
                    Toast.makeText(others.this,"You can select maximum 3 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else if(count<2)
                {
                    Toast.makeText(others.this,"You have to select minimum 2 photos, Try Again!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (int i=0; i<count; i++){

                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        if(i==0)
                        {
                            Picasso.get().load(imageUri).into(img1);
                            ImageUri.add(imageUri);
                            img1.setVisibility(View.VISIBLE);
                        }
                        else if(i==1)
                        {
                            Picasso.get().load(imageUri).into(img2);
                            ImageUri.add(imageUri);
                            img2.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Picasso.get().load(imageUri).into(img3);
                            ImageUri.add(imageUri);
                            img3.setVisibility(View.VISIBLE);
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
        model=findViewById(R.id.model);
        purchaseDate=findViewById(R.id.purchaseDate);
        sellinPrice=findViewById(R.id.sellingPrice);
        description=findViewById(R.id.description);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        imageStorageRef= FirebaseStorage.getInstance().getReference(type+"Image");
        mAuth=FirebaseAuth.getInstance();
        databaseAd= FirebaseDatabase.getInstance().getReference(type+"Ad");
    }
    public String getFileExtension(Uri uri)
    {
        ContentResolver CR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }
}
