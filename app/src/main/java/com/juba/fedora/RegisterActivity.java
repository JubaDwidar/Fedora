package com.juba.fedora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ImageView registerImage;
    private Button registerBtn;
    private EditText registerEmail, registerPassword;
    private TextView haveAccount;
    private FirebaseAuth mAuth;
    private ProgressDialog progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToLoginActivity();

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setTitle("Sign up");
                progressbar.setMessage("Please, Wait till Signing up");
                progressbar.show();
                Register();
            }
        });


    }


    private void initialize() {
        registerImage = findViewById(R.id.register_image);
        registerBtn = findViewById(R.id.register_btn);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        haveAccount = findViewById(R.id.have_account_btn);
        mAuth = FirebaseAuth.getInstance();
        progressbar = new ProgressDialog(this);
    }

    private void Register() {

        String email = registerEmail.getText().toString();
        final String password = registerPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Please,Try Again Later", Toast.LENGTH_LONG).show();
                    progressbar.dismiss();

                } else {
                    Toast.makeText(RegisterActivity.this, "Signed up Successfully", Toast.LENGTH_LONG).show();
                    progressbar.dismiss();
                    moveToMainActivity();

                }

            }
        });


    }

    private void moveToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void moveToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }


}
