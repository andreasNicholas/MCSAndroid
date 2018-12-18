package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.Adapter.ChatAdapter;
import project.aigo.myapplication.Object.Chat;
import project.aigo.myapplication.R;

public class ChatActivity extends GlobalActivity implements TextWatcher, View.OnClickListener {

    private RecyclerView rvChat;
    private EditText etMessage;
    private ImageButton btnSend;
    private ChatAdapter adapter;
    private List<Chat> chatList;
    private DatabaseReference myRef;
    private String roomKey;
    private String sender;
    private String name;
    private String notificationKey;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        roomKey = intent.getStringExtra("roomKey");
        sender = intent.getStringExtra("sender");
        name = intent.getStringExtra("name");
        notificationKey = intent.getStringExtra("notificationKey");
        chatList = new ArrayList<>();

        adapter = new ChatAdapter(this , chatList , sender);
        floatingActionButton = findViewById(R.id.fabScroll);
        rvChat = findViewById(R.id.rvChat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setNestedScrollingEnabled(false);
        rvChat.setAdapter(adapter);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        rvChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                if (dy > 0) floatingActionButton.setVisibility(View.INVISIBLE);
                else floatingActionButton.setVisibility(View.VISIBLE);

            }
        });
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        etMessage.addTextChangedListener(this);
        btnSend.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        getChatMessage();
    }

    private void getChatMessage () {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {
                chatList.clear();

                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.child("chats").child(roomKey).getChildren()) {
                        if (Objects.requireNonNull(snapshot.getKey()).equals("lastChat")) break;
                        String message = Objects.requireNonNull(snapshot.child("message").getValue()).toString();
                        String creatorID = Objects.requireNonNull(snapshot.child("user_creator").getValue()).toString();
                        String time = Objects.requireNonNull(snapshot.child("time").getValue()).toString();
                        String name = Objects.requireNonNull(dataSnapshot.child("users").child(creatorID).child("name").getValue()).toString();
                        String photo = Objects.requireNonNull(dataSnapshot.child("users").child(creatorID).child("photo").getValue()).toString();
                        Chat chat = new Chat(creatorID , message , time , name , photo);
                        chatList.add(chat);
                        adapter.notifyDataSetChanged();
                        rvChat.smoothScrollToPosition(adapter.getItemCount() - 1);
                    }
                }
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError databaseError ) {

            }
        });
    }

    @Override
    public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {

    }

    @Override
    public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {

    }

    @Override
    public void afterTextChanged ( Editable editable ) {
        if (toStringTrim(etMessage).isEmpty()) btnSend.setVisibility(View.GONE);
        else btnSend.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick ( View view ) {
        if (view == btnSend) {
            String message = toStringTrim(etMessage);
            String millis = String.valueOf(System.currentTimeMillis());
            DateFormat dateFormat = new SimpleDateFormat("HH:mm" , Locale.US);
            Date date = new Date();
            String time = dateFormat.format(date);

            Map<String, String> chat = new HashMap<>();
            chat.put("user_creator" , sender);
            chat.put("message" , message);
            chat.put("time" , time);
            String key = myRef.child("chats").child(roomKey).push().getKey();

            DatabaseReference chatRef = myRef.child("chats").child(roomKey);
            chatRef.child("lastChat").setValue(message);
            chatRef.child("lastTimeChat").setValue(millis);
            chatRef.child(Objects.requireNonNull(key)).setValue(chat);
            myRef.push();

            sendNotification(message , name , notificationKey);

            etMessage.setText("");
        } else if (view == floatingActionButton) {
            rvChat.smoothScrollToPosition(adapter.getItemCount() - 1);
        }
    }
}
