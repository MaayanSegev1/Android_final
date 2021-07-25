package com.example.finalprojectandroid1.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectandroid1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmail, mPassword, mConfirmPassword;
    private Button mRegisterBtn;
    private TextView mLoginBtn;
    private FirebaseAuth fAuth;
    private ProgressBar progressBar;

    //vars
    private FirebaseFirestore mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.emailReg);
        mPassword = findViewById(R.id.passText);
        mConfirmPassword = findViewById(R.id.confiemPassText);
        mRegisterBtn = findViewById(R.id.registerBth);
        mLoginBtn = findViewById(R.id.moveToLogin);

        findViewById(R.id.registerBth).setOnClickListener((View.OnClickListener) this);

        mDb = FirebaseFirestore.getInstance();


        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar3);

/*        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

       // mRegisterBtn.setOnClickListener(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                //insert some default data
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setUser_id(FirebaseAuth.getInstance().getUid());

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mEmail.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    mEmail.setError("Confirm Password is Required.");
                    return;
                }

                if (password.length() < 6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }


                if (!password.equals(confirmPassword)){
                    mConfirmPassword.setError("The Passwords is not same");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

                DocumentReference newUserRef = mDb
                        .collection(getString(R.string.collection_users))
                        .document(FirebaseAuth.getInstance().getUid());

                newUserRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //hideDialog();

                        if(task.isSuccessful()){
                            //redirectLoginScreen();
                        }else{
                            View parentLayout = findViewById(android.R.id.content);
                           // Snackbar.make(parentLayout, "Something went wrong.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        // move to login activity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}