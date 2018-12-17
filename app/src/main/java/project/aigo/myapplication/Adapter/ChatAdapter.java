package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import project.aigo.myapplication.Object.Chat;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<Chat> chatLists;
    private String sender;
    private static final int MESSAGE_SENDER = 0;
    private static final int MESSAGE_RECEIVER = 1;

    public ChatAdapter ( Context context , List<Chat> chatLists , String sender ) {
        this.mContext = context;
        this.chatLists = chatLists;
        this.sender = sender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v;
        if (viewType == 1) {
            v = LayoutInflater.from(mContext).inflate(R.layout.chat_list , parent , false);
        } else {
            v = LayoutInflater.from(mContext).inflate(R.layout.chat_list_me , parent , false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull ViewHolder holder , int i ) {

        final int position = holder.getAdapterPosition();
        final Chat chatList = chatLists.get(position);
        String img = (chatList.getPhoto().equals("null")) ? DEFAULT_IMAGE : chatList.getPhoto();
        Picasso.get().load(img).into(holder.ivPhoto);
        holder.tvName.setText(chatList.getName());
        holder.tvMessage.setText(chatList.getMessage());
        holder.tvTimeSent.setText(chatList.getTimeSent());

    }

    @Override
    public int getItemCount () {
        return (chatLists == null) ? 0 : chatLists.size();
    }

    @Override
    public int getItemViewType ( int position ) {
        return (chatLists.get(position).getUserID().equals(sender)) ? MESSAGE_SENDER : MESSAGE_RECEIVER;
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
