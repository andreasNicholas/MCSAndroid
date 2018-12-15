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
import project.aigo.myapplication.Object.Chat;
import project.aigo.myapplication.Object.ChatList;
import project.aigo.myapplication.R;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<Chat> chatLists;

    public ChatAdapter ( Context context , List<Chat> chatLists ) {
        this.mContext = context;
        this.chatLists = chatLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_list , parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewHolder holder , int i ) {

        final int position = holder.getAdapterPosition();
        final Chat chatList = chatLists.get(position);

        holder.tvMessage.setText(chatList.getMessage());

    }

    @Override
    public int getItemCount () {
        return (chatLists == null) ? 0 : chatLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvMessage, tvTimeSent, tvName;

        private ViewHolder ( View itemView ) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTimeSent = itemView.findViewById(R.id.tvTimeSent);

        }
    }


}
