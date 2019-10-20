package com.juba.fedora;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class GroupsFragment extends Fragment {

    private ListView groupList;
    private DatabaseReference RootRef;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list_groups = new ArrayList<String>();


    public GroupsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        RootRef = FirebaseDatabase.getInstance().getReference().child("Groups");
        groupList = container.findViewById(R.id.group_list);
        new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_groups);
        groupList.setAdapter(adapter);


        retriveGroups();
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String currentgroupname = adapterView.getItemAtPosition(i).toString();

                Intent groupName = new Intent(getContext(), GroupChatActivity.class);
                groupName.putExtra("groupName", currentgroupname);
                startActivity(groupName);
            }
        });

        return view;
    }

    private void retriveGroups()

    {
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Set<String> set = new HashSet<String>();
                    Iterator iterator = dataSnapshot.getChildren().iterator();

                    while (iterator.hasNext())

                    {
                        set.add(((DataSnapshot) iterator.next()).getKey());
                        list_groups.clear();
                        list_groups.addAll(set);
                        adapter.notifyDataSetChanged();


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
