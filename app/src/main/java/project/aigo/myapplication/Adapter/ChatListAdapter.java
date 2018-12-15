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

import java.util.List;

import project.aigo.myapplication.Activity.ChatActivity;
import project.aigo.myapplication.Object.ChatList;
import project.aigo.myapplication.R;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context mContext;
    private List<ChatList> chatLists;

    public ChatListAdapter ( Context context , List<ChatList> chatLists ) {
        this.mContext = context;
        this.chatLists = chatLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_history_list , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewHolder holder , int i ) {

        final int position = holder.getAdapterPosition();
        final ChatList chatList = chatLists.get(position);

        holder.tvName.setText(chatList.getId());

    }

    @Override
    public int getItemCount () {
        return (chatLists == null) ? 0 : chatLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName, tvLastDate, tvLastChat;

        private ViewHolder ( View itemView ) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvLastDate = itemView.findViewById(R.id.tvLastDate);
            tvLastChat = itemView.findViewById(R.id.tvLastChat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    mContext.startActivity(new Intent(mContext, ChatActivity.class));
                }
            });

        }
    }


}
