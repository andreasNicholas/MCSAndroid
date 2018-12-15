package project.aigo.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.Object.User;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.ViewHolder> {
    private Context mContext;
    private List<User> userList;
    private String sender;

    public AthleteAdapter ( Context context , List<User> userList , String sender ) {
        this.mContext = context;
        this.userList = userList;
        this.sender = sender;
    }

    @NonNull
    @Override
    public AthleteAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.branch , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull AthleteAdapter.ViewHolder holder , int position ) {
        final User user = userList.get(position);

        String img = (user.getPhoto().equals("null")) ? DEFAULT_IMAGE : user.getPhoto();

        Picasso.get().load(img).into(holder.ivPhoto);

        holder.tvName.setText(user.getName());
        holder.tvGender.setText(user.getGender());
        holder.tvDetail.setText(user.getSport_branch());
    }

    @Override
    public int getItemCount () {
        return (userList == null) ? 0 : userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvGender, tvDetail;
        private ImageView ivPhoto;

        private ViewHolder ( View itemView ) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvDetail = itemView.findViewById(R.id.tvDetail);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick ( View view ) {

                    String[] menu = {"View" , "Chat"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setIcon(R.drawable.ic_search_black_24dp);
                    builder.setItems(menu , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick ( DialogInterface dialogInterface , int i ) {
                            int position = getAdapterPosition();
                            User user = userList.get(position);

                            switch (i) {
                                case 0:
                                    break;
                                case 1:

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference();
                                    String key = myRef.child("chats").push().getKey();

                                    String receiver = user.getUserID();
                                    String roomName = String.format("%s-%s" , sender , receiver);

                                    Map<String, Object> senderRoom = new HashMap<>();
                                    senderRoom.put("status" , true);
                                    senderRoom.put("with" , sender);

                                    Map<String, Object> receiverRoom = new HashMap<>();
                                    receiverRoom.put("status" , true);
                                    receiverRoom.put("with" , receiver);

                                    myRef.child("chats").child(Objects.requireNonNull(key)).setValue(roomName);
                                    myRef.child("users").child(sender).child("chat").child(key).setValue(senderRoom);
                                    myRef.child("users").child(receiver).child("chat").child(key).setValue(receiverRoom);
                                    myRef.push();

                                    break;
                            }
                        }
                    });
                    builder.show().create();

                    return true;
                }
            });
        }
    }
}
