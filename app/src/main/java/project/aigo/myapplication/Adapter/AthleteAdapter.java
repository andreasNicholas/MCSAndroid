package project.aigo.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.Activity.ChatActivity;
import project.aigo.myapplication.Object.User;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.ViewHolder> {
    private Context mContext;
    private List<User> userList;
    private String sender;

    private String notificationKey;

    private String getNotificationKey () {
        return notificationKey;
    }

    private void setNotificationKey ( String notificationKey ) {
        this.notificationKey = notificationKey;
    }



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
                        public void onClick ( DialogInterface dialogInterface , final int i ) {
                            int position = getAdapterPosition();
                            User user = userList.get(position);

                            switch (i) {
                                case 0:
                                    break;
                                case 1:

                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef = database.getReference();
                                    final String key = myRef.child("chats").push().getKey();
                                    final String receiver = user.getUserID();

                                    final Map<String, String> newRoom = new HashMap<>();
                                    newRoom.put("lastTimeChat" , "");
                                    newRoom.put("lastChat" , "");

                                    final Map<String, Object> senderRoom = new HashMap<>();
                                    senderRoom.put("status" , true);
                                    senderRoom.put("with" , receiver);

                                    final Map<String, Object> receiverRoom = new HashMap<>();
                                    receiverRoom.put("status" , true);
                                    receiverRoom.put("with" , sender);

                                    myRef.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange ( @NonNull DataSnapshot dataSnapshot ) {

                                            String notifKey = (String) dataSnapshot.child("users").child(receiver).child("notificationKey").getValue();

                                            if (getNotificationKey() == null) setNotificationKey(notifKey);

                                            boolean exists = false;
                                            for (DataSnapshot snapshot : dataSnapshot.child("users").child(sender).child("chat").getChildren()) {
                                                if (Objects.requireNonNull(snapshot.child("with").getValue()).toString().equals(receiver)) {
                                                    exists = true;
                                                    break;
                                                }
                                            }

                                            if (!exists) {
                                                myRef.child("chats").child(Objects.requireNonNull(key)).setValue(newRoom);
                                                myRef.child("users").child(sender).child("chat").child(key).setValue(senderRoom);
                                                myRef.child("users").child(receiver).child("chat").child(key).setValue(receiverRoom);
                                                myRef.push();
                                            }
                                        }

                                        @Override
                                        public void onCancelled ( @NonNull DatabaseError databaseError ) {

                                        }
                                    });


                                    Intent intent = new Intent(mContext , ChatActivity.class);
                                    intent.putExtra("roomKey" , key);
                                    intent.putExtra("sender" , sender);
                                    intent.putExtra("name" , user.getName());
                                    intent.putExtra("notificationKey", notificationKey);
                                    mContext.startActivity(intent);


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
