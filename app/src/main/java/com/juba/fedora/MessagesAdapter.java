package com.juba.fedora;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder>{

    private TextView receiverMsgTxt,senderMsgText;
    private DatabaseReference rootref;
    private List<Messages> messagesList;
    public MessagesAdapter(List<Messages> messagesList) {
        this.messagesList=messagesList;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position)

    {
Messages messages=messagesList.get(position);
String fromUser=messages.getFrom();

rootref= FirebaseDatabase.getInstance().getReference().child("Users").child(fromUser);
rootref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
   // can retrive any thing about receiverUser
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
     public MessageHolder(View itemView) {
         super(itemView);

         receiverMsgTxt=itemView.findViewById(R.id.receiver_text);
         senderMsgText=itemView.findViewById(R.id.sender_text);

     }
 }
}
