package project.aigo.myapplication.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Adapter.ChatListAdapter;
import project.aigo.myapplication.Object.ChatList;
import project.aigo.myapplication.R;

public class ProfileChatFragment extends Fragment {

    List<ChatList> chatHistoryList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ChatListAdapter adapter;
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        View view = inflater.inflate(R.layout.fragment_profile_chat, container, false);
        chatHistoryList = new ArrayList<>();
        adapter = new ChatListAdapter(getActivity(), chatHistoryList);
        RecyclerView recyclerView = view.findViewById(R.id.rvChatHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()) ,DividerItemDecoration.VERTICAL));

        getChatList();
        
        return view;


    }

    private void getChatList () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        DatabaseReference ref = myRef.child("users").child(id).child("chat");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                chatHistoryList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()){
                        ChatList chatList = new ChatList(childDataSnapshot.getKey());
                        chatHistoryList.add(chatList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }
        });
    }
}
