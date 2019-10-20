package com.juba.fedora;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText messageText;
    private ImageButton sendMsgBtn;
    private RecyclerView chatList;
    private FirebaseAuth mauth;
    private DatabaseReference RootRef;
    private String sender_user_id;
    private String receiver_user_id;
    private MessagesAdapter adapter;
    private final List<Messages> messagesList=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageText = findViewById(R.id.message_text);
        chatList = findViewById(R.id.chat_list);
        sendMsgBtn = findViewById(R.id.send_message_btn);
        adapter=new MessagesAdapter(messagesList);
        mauth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference().child("Messages").child(sender_user_id).child(receiver_user_id);
        sender_user_id = mauth.getCurrentUser().getUid();
        receiver_user_id = getIntent().getExtras().get("receiver_user_id").toString();
        chatList.setAdapter(adapter);




        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                sendMessage();
            }


        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Messages messages=dataSnapshot.getValue(Messages.class);
                messagesList.add(messages);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        String message = messageText.getText().toString();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Message", message);
        map.put("to", receiver_user_id);
        map.put("from", sender_user_id);


        RootRef.child(sender_user_id).child(receiver_user_id).updateChildren(map);

    }
}
