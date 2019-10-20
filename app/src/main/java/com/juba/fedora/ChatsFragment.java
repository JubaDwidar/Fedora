package com.juba.fedora;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatsFragment extends Fragment {
    private RecyclerView friendsView;
    private DatabaseReference userRef;

    public ChatsFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_chats, container, false);

        friendsView = new RecyclerView(getContext());
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(userRef,Model.class)
                .build();


        FirebaseRecyclerAdapter<Model,ChatsFragment.FriendsViewHolder> adapter =
                new FirebaseRecyclerAdapter<Model,ChatsFragment.FriendsViewHolder>(options)
                {
                    @Override
                    protected void onBindViewHolder(@NonNull ChatsFragment.FriendsViewHolder holder, final int position, @NonNull Model model)
                    {
                        holder.fName.setText(model.getName());
                        holder.fStatus.setText(model.getStatus());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.profile_image).into(holder.friendImage);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String reciver_user_id=getRef(position).getKey();
                                Intent profileIntent=new Intent(getContext(),ProfileActivity.class);
                                profileIntent.putExtra("receiver_user_id",reciver_user_id);
                                startActivity(profileIntent);
                            }
                        });


                    }



                    @NonNull
                    @Override
                    public ChatsFragment.FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
                        ChatsFragment.FriendsViewHolder fHolder=new ChatsFragment.FriendsViewHolder(view);
                        return fHolder;
                    }
                };

        friendsView.setAdapter(adapter);
        adapter.startListening();
    }







    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        CircleImageView friendImage;
        TextView fName,fStatus;
        public FriendsViewHolder(View itemView)
        {
            super(itemView);
            friendImage=itemView.findViewById(R.id.friend_image);
            fName=itemView.findViewById(R.id.friend_username);
            fStatus=itemView.findViewById(R.id.friend_userstatus);



        }
    }
}



