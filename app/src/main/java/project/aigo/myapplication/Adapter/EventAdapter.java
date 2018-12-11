package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.EventCalendarActivity;
import project.aigo.myapplication.Activity.EventDetailActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Fragment.EventEditFragment;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private Context mContext;
    private List<Event> eventList;
    private String role;
    private View layoutView;
    private final GlobalActivity globalActivity = new GlobalActivity();

    public EventAdapter ( Context context , List<Event> eventList , String role , View layoutView ) {
        this.mContext = context;
        this.eventList = eventList;
        this.role = role;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.event_list , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull final EventAdapter.ViewHolder holder , int i ) {

        final int position = holder.getAdapterPosition();
        final Event events = eventList.get(position);
        /*String img = (events.getEvent_image_path().equals("null")) ? DEFAULT_IMAGE : events.getEvent_image_path();

        holder.ivEventImage.setVisibility(View.VISIBLE);
        Picasso.get().load(img).into(holder.ivEventImage);
        holder.ivEventImage.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.ivEventImage.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels) / 4);

        holder.tvTitle.setText(events.getEvent_name());
        holder.tvDescription.setText(events.getEvent_description());
        holder.tvCountView.setText(events.getViews_count());
        holder.tvCreator.setText(events.getCreated_by());
        holder.tvtvDateTimeStartEnd.setText(String.format("%s - %s" , events.getEvent_start_datetime() , events.getEvent_end_datetime()));

        if (role.equals("admin") && !(mContext instanceof EventCalendarActivity)) {
            holder.btnMenu.setVisibility(View.VISIBLE);
            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {

                    final String[] menu = {"Edit" , "Delete"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setItems(menu , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick ( DialogInterface dialogInterface , int i ) {
                            switch (i) {
                                case 0:
                                    String id = events.getId();
                                    String event_name = events.getEvent_name();
                                    String event_description = events.getEvent_description();
                                    String imageSrc = events.getEvent_image_path();
                                    String event_start_date = events.getEvent_start_datetime().substring(0 , 10);
                                    String event_start_time = events.getEvent_start_datetime().substring(11);
                                    String event_end_date = events.getEvent_end_datetime().substring(0 , 10);
                                    String event_end_time = events.getEvent_end_datetime().substring(11);
                                    String[] arrayEvent = {id , event_name , event_description , imageSrc , event_start_date , event_start_time , event_end_date , event_end_time};
                                    Bundle bundle = new Bundle();
                                    bundle.putStringArray("arrayEvent" , arrayEvent);
                                    globalActivity.loadFragment(new EventEditFragment() , R.id.eventActivity , mContext , bundle , "editFragment");
                                    break;
                                case 1:
                                    callApi(events.getId() , position);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    builder.show().create();
                }
            });
        }*/
    }


    @Override
    public int getItemCount () {
        return (eventList == null) ? 0 : eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivEventImage;
        TextView tvTitle, tvDescription, tvtvDateTimeStartEnd, tvCountView, tvCreator;
        SwipeRefreshLayout swipeRefreshLayout;
        ImageButton btnMenu;

        private ViewHolder ( View itemView ) {
            super(itemView);
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvtvDateTimeStartEnd = itemView.findViewById(R.id.tvDateTimeStartEnd);
            tvCountView = itemView.findViewById(R.id.tvCountView);
            tvCreator = itemView.findViewById(R.id.tvCreator);
            swipeRefreshLayout = itemView.findViewById(R.id.swipe);
            btnMenu = itemView.findViewById(R.id.btnMenu);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    final int position = getAdapterPosition();
                    final Event events = eventList.get(position);

                    String event_name = events.getEvent_name();
                    /*String event_description = events.getEvent_description();
                    String imageSrc = (events.getEvent_image_path().equals("null")) ? DEFAULT_IMAGE : events.getEvent_image_path();
                    String event_start_datetime = events.getEvent_start_datetime();
                    String event_end_datetime = events.getEvent_end_datetime();

                    intent.putExtra("event_name", event_name);
                    intent.putExtra("event_description", event_description);
                    intent.putExtra("imageSrc", imageSrc);
                    intent.putExtra("event_start_datetime", event_start_datetime);
                    intent.putExtra("event_end_datetime", event_end_datetime);*/
                    Intent intent = new Intent(mContext, EventDetailActivity.class);
                    mContext.startActivity(intent);

                }
            });
        }

    }

    private void callApi ( final String eventID , final int position ) {
        final APIManager apiManager = new APIManager();
        String titleAlert = "Delete Confirmation";
        String message = "Are you sure want to Delete?";
        AlertDialog.Builder builder = globalActivity.createGlobalAlertDialog(mContext , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(mContext);
                String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
                String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";

                Map<String, String> params = new HashMap<>();
                params.put("eventID" , eventID);
                params.put("userID" , id);
                params.put("remember_token" , remember_token);

                apiManager.deleteEvent(mContext , layoutView , params , EventAdapter.this , position , getItemCount() , eventList);


            }
        });
        builder.show().create();
    }

}
