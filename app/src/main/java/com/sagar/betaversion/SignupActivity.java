package com.sagar.betaversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText name,email,password,conpass;;
    LoadingDialog loadingDialog;
    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;

    public void signUp(View view){

        String uname=name.getText().toString().trim();
        String uemail=email.getText().toString().trim();
        String upassword=password.getText().toString().trim();



        if (uname.matches(""))
        {
            name.setError("Enter Your Name");
            name.setFocusable(true);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(uemail).matches())
        {
            email.setError("Invalid Email");
            email.setFocusable(true);
        }
        else if (upassword.length()<6)
        {
            password.setError("Password length should be at least 6 characters");
            password.setFocusable(true);
        }
        else if (!upassword.matches(conpass.getText().toString()))
            conpass.setError("Password Doesn't Match");
        else
            signupuser(uname,uemail,upassword);
    }

    private void signupuser(final String uname, final String uemail, final String upassword) {
        loadingDialog.startLoadingDialog();
            mAuth.createUserWithEmailAndPassword(uemail,upassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            loadingDialog.dismissDialog();
                            if(task.isSuccessful()){
                                FirebaseUser user=mAuth.getCurrentUser();

                                String user_id=user.getUid();
                                User user1=new User(user_id,uname,uemail);
                                databaseUsers.child(user_id).setValue(user1);

                                Toast.makeText(SignupActivity.this, "Registered: "+user.getEmail(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                finish();

                            }
                            else
                            {
                                Log.w("info", "User With Email:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    loadingDialog.dismissDialog();
                    Toast.makeText(SignupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        name=findViewById(R.id.editTextName);

        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.password);
        conpass=findViewById(R.id.editTextConPass);

        databaseUsers= FirebaseDatabase.getInstance().getReference("Users");

        loadingDialog=new LoadingDialog(this);

        mAuth=FirebaseAuth.getInstance();

    }

}
