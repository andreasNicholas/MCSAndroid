package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import project.aigo.myapplication.Activity.ChatActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Object.ChatList;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context mContext;
    private List<ChatList> chatLists;
    private String sender;

    public ChatListAdapter ( Context context , List<ChatList> chatLists , String sender ) {
        this.mContext = context;
        this.chatLists = chatLists;
        this.sender = sender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_history_list , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewHolder holder , int i ) {

        GlobalActivity globalActivity = new GlobalActivity();
        final int position = holder.getAdapterPosition();
        final ChatList chatList = chatLists.get(position);

       if (!chatList.getLastTimeChat().isEmpty()) {
            long lastTimeChat = Long.parseLong(chatList.getLastTimeChat());
            String date = globalActivity.getFriendlyTime(new Date(lastTimeChat));
            holder.tvLastTime.setText(date);
        }


        holder.tvName.setText(chatList.getName());
        holder.tvLastChat.setText(chatList.getLastChat());
        String img = (chatList.getPhoto().equals("null")) ? DEFAULT_IMAGE : chatList.getPhoto();
        Picasso.get().load(img).into(holder.ivPhoto);


    }

    @Override
    public int getItemCount () {
        return (chatLists == null) ? 0 : chatLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName, tvLastTime, tvLastChat;

        private ViewHolder ( View itemView ) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastTime = itemView.findViewById(R.id.tvLastTime);
            tvLastChat = itemView.findViewById(R.id.tvLastChat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    int position = getAdapterPosition();
                    ChatList chatList = chatLists.get(position);
                    Intent intent = new Intent(mContext , ChatActivity.class);
                    intent.putExtra("roomKey" , chatList.getId());
                    intent.putExtra("sender" , sender);
                    intent.putExtra("name" , chatList.getName());
                    intent.putExtra("notificationKey", chatList.getNotificationKey());
                    mContext.startActivity(intent);

                }
            });

        }
    }


}
