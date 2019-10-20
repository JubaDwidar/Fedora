package com.juba.fedora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {

    private String groupname;
    private FirebaseAuth mauth;
    private DatabaseReference userRef,groupRef;
    private String currentUser;
    private ImageButton sendmessagebtn;
    private ScrollView mScrollView;
    private TextView displayText;
    private EditText sendGroupText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        groupname=getIntent().getExtras().get("groupname").toString();
        mauth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        groupRef= FirebaseDatabase.getInstance().getReference().child("Groups").child(groupname);
       currentUser =mauth.getCurrentUser().getUid();
       sendmessagebtn=findViewById(R.id.send_message_btn);
        displayText=findViewById(R.id.group_chat_text_display);
        mScrollView=findViewById(R.id.my_scroll_view);
        sendGroupText=findViewById(R.id.send_group_text);



        sendmessagebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {

               saveMessageInfoDatabase();
               mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

           }
       });


    }

    private void saveMessageInfoDatabase()

    {
        String MessgeText=displayText.getText().toString();
        if (MessgeText.isEmpty())
        {
            Toast.makeText(GroupChatActivity.this,"Enter Message",Toast.LENGTH_LONG).show();

        }

        HashMap<String,Object> hashMap=new HashMap<String,Object>();
        hashMap.put("MessagesText",MessgeText);
        hashMap.put("CurrentUser",currentUser);

        groupRef.updateChildren(hashMap);


    }

    @Override
    protected void onStart() {
        super.onStart();

        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    displayMessages(dataSnapshot);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayMessages(DataSnapshot dataSnapshot)
    {

        Iterator iterator=dataSnapshot.getChildren().iterator();

        while (iterator.hasNext())
        {
            String chatMessages=(String) ((DataSnapshot)iterator.next()).getValue();
            String fromMessages=(String) ((DataSnapshot)iterator.next()).getValue();


            displayText.append(chatMessages+": /n" +fromMessages );

            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);

        }
    }
}
