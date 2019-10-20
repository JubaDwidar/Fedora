package com.juba.fedora;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ImageView loginImage;
    private Button loginBtn, loginPhonebtn;
    private EditText loginEmail, loginPassword;
    private TextView needAccount;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        needAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movetToRegisteration();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
    }

    private void loginuser() {
        String email = loginEmail.getText().toString();
        final String password = loginPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    String error = task.getException().toString();

                    Toast.makeText(LoginActivity.this, "Error " + error + "has occurred", Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(LoginActivity.this, "Signed in Successfully", Toast.LENGTH_LONG).show();
                    moveToMainActivity();

                }
            }
        });


    }


    private void initialize() {
        loginBtn = findViewById(R.id.login_btn);
        loginPhonebtn = findViewById(R.id.login_phone_btn);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginImage = findViewById(R.id.login_image);
        needAccount = findViewById(R.id.new_account_btn);
        mAuth = FirebaseAuth.getInstance();


    }

    private void movetToRegisteration() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void moveToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


}
