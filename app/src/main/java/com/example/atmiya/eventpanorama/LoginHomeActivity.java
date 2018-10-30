package com.example.atmiya.eventpanorama;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LoginHomeActivity extends AppCompatActivity {

    private EditText mLoginEmailEditText;
    private EditText mLoginPasswordEditText;

    private Button mLoginSubmit;
    private Button mloginSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mLoginEmailEditText = findViewById(R.id.et_login_email);
        mLoginPasswordEditText = findViewById(R.id.et_login_password);

        mLoginSubmit = findViewById(R.id.btn_login_submit);
        mloginSignUp = findViewById(R.id.btn_login_signup);

        mLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmailEditText.getText().toString();
                String password = mLoginPasswordEditText.getText().toString();
                loginUser(email, password);
            }
        });

        mloginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginHomeActivity.this, SignupTabbed.class));
            }
        });
    }

    void loginUser(String email, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(LoginHomeActivity.this, "Welcome !!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginHomeActivity.this, MainActivity.class));
                } else {
                    // If sign in fails, display a message to the user.
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(LoginHomeActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                // ...
            }
        });
    }

}
