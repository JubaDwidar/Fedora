package com.juba.fedora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private Button sendMsgBtn;
    private TextView profileName, profileStatus;
    private DatabaseReference profileRef;
    private String receiver_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        receiver_id = getIntent().getExtras().get("receiver_user_id").toString();

        intialize();
        vaildateProfileinfo();
        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    private void intialize() {
        sendMsgBtn = findViewById(R.id.send_msg_btn);
        profileName = findViewById(R.id.profile_username);
        profileStatus = findViewById(R.id.profile_userstatus);
        profileImage = findViewById(R.id.profile_image);
        profileRef = FirebaseDatabase.getInstance().getReference().child("Users").child(receiver_id);


    }

    private void vaildateProfileinfo()
    {
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!dataSnapshot.exists())
            {
                Toast.makeText(ProfileActivity.this,"This User Dose not Exist",Toast.LENGTH_LONG).show();

            }
            else
                {
                    profileName.setText(dataSnapshot.child("name").getValue().toString());
                    profileStatus.setText(dataSnapshot.child("status").getValue().toString());
                    Picasso.get().load(dataSnapshot.child("status").getValue().toString()).into(profileImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
