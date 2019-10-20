package com.juba.fedora;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
    private Button sendCodeBtn, verifyBtn;
    private EditText phonenumber, codeVerification;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mresendCode;
    private PhoneAuthCredential credential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        sendCodeBtn = findViewById(R.id.send_code_btn);
        verifyBtn = findViewById(R.id.veridy_btn);
        phonenumber = findViewById(R.id.phone_number);
        codeVerification = findViewById(R.id.veri_code);


        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = codeVerification.getText().toString();
                credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);


            }
        });

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verification();
                sendCodeBtn.setVisibility(View.INVISIBLE);
                verifyBtn.setVisibility(View.VISIBLE);
                phonenumber.setVisibility(View.INVISIBLE);
                codeVerification.setVisibility(View.VISIBLE);


            }
        });

    }

    private void verification() {
        String Number = phonenumber.getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber
                (
                        Number, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                moveToMainActivity();
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(PhoneLoginActivity.this, "Try Again Later", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                mVerificationId = s;
                                mresendCode = forceResendingToken;

                            }
                        }
                );

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PhoneLoginActivity.this, "Logged Successfully", Toast.LENGTH_LONG).show();
                            moveToMainActivity();
                            FirebaseUser user = task.getResult().getUser();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(PhoneLoginActivity.this, "Logged Failed", Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                });
    }

    private void moveToMainActivity() {
        Intent phoneIntent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(phoneIntent);
    }
}







