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

    private List<ChatList> chatHistoryList;
    private DatabaseReference myRef;
    private ChatListAdapter adapter;
    private String id;

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        View view = inflater.inflate(R.layout.fragment_profile_chat , container , false);
        chatHistoryList = new ArrayList<>();
        adapter = new ChatListAdapter(getActivity() , chatHistoryList , id);
        RecyclerView recyclerView = view.findViewById(R.id.rvChatHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()) , DividerItemDecoration.VERTICAL));

        getChatList();

        return view;
    }

    private void getChatList () {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                chatHistoryList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childDataSnapShot : dataSnapshot.child("users").child(id).child("chat").getChildren()) {
                        String keyRoom = childDataSnapShot.getKey();
                        String with = (String) childDataSnapShot.child("with").getValue();
                        DataSnapshot room = dataSnapshot.child("chats").child(Objects.requireNonNull(keyRoom));
                        DataSnapshot user = dataSnapshot.child("users").child(Objects.requireNonNull(with));
                        String name = (String) user.child("name").getValue();
                        String photo = (String) user.child("photo").getValue();
                        String notificationKey = (String) user.child("notificationKey").getValue();
                        String lastTimeChat = (String) room.child("lastTimeChat").getValue();
                        String lastChat = (String) room.child("lastChat").getValue();
                        ChatList chatList = new ChatList(keyRoom , name , lastTimeChat , lastChat , photo, notificationKey);
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
