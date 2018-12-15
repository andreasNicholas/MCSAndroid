package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        chatList = new ArrayList<>();

        adapter = new ChatAdapter(this, chatList);

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
        if (view == btnSend){
            String message = toStringTrim(etMessage);

            Map<String, String> chat = new HashMap<>();
            chat.put("user_creator","1");
            chat.put("message",message);

            String key = myRef.child("chats").child("-LTlauomz62w2fysKFzk").push().getKey();
            myRef.child("chats").child("-LTlauomz62w2fysKFzk").child(key).setValue(chat);
            myRef.push();

            etMessage.setText("");
        }
    }
}
