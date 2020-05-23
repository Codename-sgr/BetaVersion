package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    Uri uri;
    byte[] image;
    DatabaseReference databaseRef;
    StorageReference imageRef;
    FirebaseAuth firebaseAuth;
    SharedPreferences preferences;
    ImageView userImage;
    EditText userNameView, mobileNoView, addressView;
    TextView emailView;
    String Uid;
    String username, mobile, email, address,retrievedImage,uploadedImage;
    SharedPreferences.Editor editor;
    Bitmap bitmap = null;
    Map<String, Object> mp = new HashMap<>();
    ProgressDialog progressDialog;
    private static final int IMAGE_REQUEST = 1;

    public void UploadImage(View view) {
        ChooseImage();
    }
    public void moveBackToMyAccount()
    {
        Intent intent = new Intent(getApplicationContext(), myAccount.class);
        startActivity(intent);
        finish();
    }
    public void update(View view) {
        progressDialog.setMessage("Updating Profile!!");
        progressDialog.show();
        username = userNameView.getText().toString();
        mobile = mobileNoView.getText().toString();
        email = emailView.getText().toString();
        address = addressView.getText().toString();
        mp.put("user_name", username);
        mp.put("email", email);
        mp.put("mobile", mobile);
        mp.put("address", address);
        if(uploadedImage!=null || retrievedImage!=null)
            mp.put("dp",true);
        databaseRef.child(Uid).updateChildren(mp);
        editor.putString("user_name", username);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("address", address);
        if(uploadedImage!=null)
        {
            editor.putString("image",uploadedImage);
            editor.apply();
            StorageReference ref=imageRef.child(Uid+".jpg");
            UploadTask uploadTask=ref.putBytes(image);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(profile.this,"Profile Updated!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    moveBackToMyAccount();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profile.this,"Try Again!!",Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            editor.putString("image",retrievedImage);
            editor.apply();
            progressDialog.dismiss();
            moveBackToMyAccount();
        }



    }

    public void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            image = stream.toByteArray();
            String img_str = Base64.encodeToString(image, 0);
            uploadedImage = img_str;
            Picasso.get().load(uri).into(userImage);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        progressDialog=new ProgressDialog(this);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
        imageRef = FirebaseStorage.getInstance().getReference("UserImage");
        userNameView = findViewById(R.id.accUserName);
        mobileNoView = findViewById(R.id.accMobile);
        emailView = findViewById(R.id.accEmail);
        addressView = findViewById(R.id.accAddress);
        userImage = findViewById(R.id.userImage);
        firebaseAuth = FirebaseAuth.getInstance();
        Uid = firebaseAuth.getUid();
        preferences = getSharedPreferences("UserData", MODE_PRIVATE);
        editor = preferences.edit();
        username = preferences.getString("user_name", "");
        mobile = preferences.getString("mobile", "");
        email = preferences.getString("email", "");
        address = preferences.getString("address", "");
        retrievedImage = preferences.getString("image", "");
        if(!retrievedImage.matches("") && retrievedImage!=null)
        {
            bitmap = StringToBitMap(retrievedImage);
            userImage.setImageBitmap(bitmap);
        }

        userNameView.setHint("Enter Username");
        mobileNoView.setHint("Enter mobile number");
        addressView.setHint("Enter address");
        userNameView.setText(username);
        mobileNoView.setText(mobile);
        emailView.setText(email);
        addressView.setText(address);
    }

    public Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);

            InputStream inputStream = new ByteArrayInputStream(encodeByte);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;

        }
    }

}
