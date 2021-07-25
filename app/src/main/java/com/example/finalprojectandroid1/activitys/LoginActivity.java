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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalprojectandroid1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.view.View;
import android.view.View.OnClickListener;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;
    Switch typeAccount;
    boolean typeAccSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailLogin);
        mPassword = findViewById(R.id.passTextLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.signInBut);
        mCreateBtn = findViewById(R.id.moveToLogin);
        typeAccount = findViewById(R.id.swUserType);

        startService(new Intent( this, UserService.class ) );

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (typeAccount.isChecked())
                {
                    typeAccSignIn=true;
                }
                else typeAccSignIn=false;

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mEmail.setError("Password is Required.");
                    return;
                }


                if (password.length() < 6){
                    mPassword.setError("Password must be more than 6 characters");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);

                if (typeAccSignIn) {
                    // authenticate the user
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "sign in Successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ChildActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }
                else {
                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "sign in Successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }

            }
        });

        // move to register activity
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

}