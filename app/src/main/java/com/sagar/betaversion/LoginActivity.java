package com.sagar.betaversion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth=FirebaseAuth.getInstance();


    LoadingDialog loadingDialog;

    SignInButton googleLoginBtn;
    FirebaseUser user;

    TextInputEditText email;
    TextInputEditText password;
    Button LogIn;
    TextView signUp,forgetPwd;
    Boolean isValid=false;

    private int RC_SIGN_IN=100;
    DatabaseReference databaseUsers;//shivam
    GoogleSignInClient mGoogleSignInClient;

    public void logIn(View view){
        String uemail=email.getText().toString().trim();
        String upassword=password.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(uemail).matches()){
            email.setError("Invalid Email");
            email.setFocusable(true);
        }
        else if(upassword.isEmpty()){
            password.setError("Enter Your Password");
            password.setFocusable(true);
        }
        else{
            logUserIn(uemail,upassword);

            /*if(user.isEmailVerified()){

            }
            else
            {
                Toast.makeText(this, "Email Not Verified !!!", Toast.LENGTH_SHORT).show();
            }*/

        }

    }

    private void logUserIn(String email, String password) {
        loadingDialog.startLoadingDialog();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                                user=mAuth.getCurrentUser();
                                try {
                                    if(user.isEmailVerified()){
                                        isValid=true;
                                        loadingDialog.dismissDialog();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        loadingDialog.dismissDialog();
                                        final androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(LoginActivity.this);
                                        builder.setTitle("Error");
                                        builder
                                                .setMessage("Your Email ID is not Verified !")
                                                .setCancelable(false);
                                        builder.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,
                                                                        int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        androidx.appcompat.app.AlertDialog dialog=builder.create();
                                        dialog.show();
                                    }
                                }catch (NullPointerException e){
                                    loadingDialog.dismissDialog();
                                    Log.i("Info",""+e.getMessage());

                                }




                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismissDialog();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resendVerificationMail() {
        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(LoginActivity.this, "Verification Email Has Been Sent to your Email ID", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG","Failed"+e.getMessage());
            }
        });
    }

    private void showPwdRecoveryDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Recovery");

        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailET=new EditText(this);
        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(16);

        linearLayout.addView(emailET);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email=emailET.getText().toString().trim();
                beginRecovery(email);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {

        loadingDialog.startLoadingDialog();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingDialog.dismissDialog();
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(LoginActivity.this, "Failed..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.dismissDialog();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserStatus(){
        if (user!=null && user.isEmailVerified())
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
    }



    @Override
    public void onStart(){
        super.onStart();
        user=mAuth.getCurrentUser();
        // Check if user is signed in (non-null) and update UI accordingly.
        checkUserStatus();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar()!=null)
        getSupportActionBar().hide();

        databaseUsers= FirebaseDatabase.getInstance().getReference("Users");
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.password);
        LogIn=findViewById(R.id.LogIn);
        signUp=findViewById(R.id.signUp);
        forgetPwd=findViewById(R.id.textViewForgetPass);
        googleLoginBtn=findViewById(R.id.googleLoginBtn);



        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPwdRecoveryDialog();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && isValid)
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return false;
            }
        });

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });



        loadingDialog = new LoadingDialog(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        loadingDialog.startLoadingDialog();
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            loadingDialog.dismissDialog();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {

        loadingDialog.startLoadingDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingDialog.dismissDialog();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            isValid=true;
                            FirebaseUser user = mAuth.getCurrentUser();//shivam
                            Toast.makeText(LoginActivity.this, ""+user.getDisplayName()+"\n"+user.getEmail() , Toast.LENGTH_SHORT).show();
                            final String Uid=user.getUid();
                            databaseUsers.child(Uid).child("email").setValue(user.getEmail());
                            databaseUsers.child(Uid).child("user_id").setValue(user.getUid());
                            databaseUsers.child(Uid).child("user_name").setValue(user.getDisplayName());//shivam
                            databaseUsers.child(Uid).child("dp").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(!dataSnapshot.exists())
                                    {
                                        databaseUsers.child(Uid).child("dp").setValue(false);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Intent i=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(i);

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
