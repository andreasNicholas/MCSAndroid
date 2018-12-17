package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import hirondelle.date4j.DateTime;
import project.aigo.myapplication.Adapter.ChatAdapter;
import project.aigo.myapplication.Object.Chat;
import project.aigo.myapplication.R;

public class ChatActivity extends GlobalActivity implements TextWatcher, View.OnClickListener {

    RecyclerView rvChat;
    EditText etMessage;
    ImageButton btnSend;
    ChatAdapter adapter;
    List<Chat> chatList;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String roomKey;
    String sender;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        Intent intent = getIntent();
        roomKey = intent.getStringExtra("roomKey");
        sender = intent.getStringExtra("sender");

        chatList = new ArrayList<>();

        adapter = new ChatAdapter(this , chatList);

        rvChat = findViewById(R.id.rvChat);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setNestedScrollingEnabled(false);
        rvChat.setAdapter(adapter);
        rvChat.setItemAnimator(new DefaultItemAnimator());
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        etMessage.addTextChangedListener(this);
        btnSend.setOnClickListener(this);
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
            Map<String, String> chat = new HashMap<>();
            chat.put("user_creator" , sender);
            chat.put("message" , message);
            String key = myRef.child("chats").child(roomKey).push().getKey();
            DatabaseReference chatRef = myRef.child("chats").child(roomKey);
            chatRef.child("lastChat").setValue(message);
            chatRef.child("lastTimeChat").setValue(millis);
            chatRef.child(Objects.requireNonNull(key)).setValue(chat);
            myRef.push();

            etMessage.setText("");


        }
    }
}
