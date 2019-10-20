package com.juba.fedora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    private CircleImageView settingimage;
    private EditText username, userstatus;
    private Button update;
    private DatabaseReference rootRef;
    private String currentUserId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savantUpdateUserInfo();
            }
        });

    }

    private void initialize() {
        settingimage = findViewById(R.id.setting_photo);
        username = findViewById(R.id.user_name);
        userstatus = findViewById(R.id.user_status);
        update = findViewById(R.id.update_btn);
        rootRef = FirebaseDatabase.getInstance().getReference().child("Info");
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


    }

    private void savantUpdateUserInfo()

    {

        String name = username.getText().toString();
        String status = userstatus.getText().toString();
        HashMap<String, Object> info = new HashMap();
        info.put("username", name);
        info.put("userStatus", status);
        rootRef.child(currentUserId).updateChildren(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SettingsActivity.this, "Saved Successfully ", Toast.LENGTH_SHORT).show();
                } else {
                    String error = task.getException().toString();
                    Toast.makeText(SettingsActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


}
