package project.aigo.myapplication.Adapter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Fragment.AddNewsFragment;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.R;

public class ProfileCompetitionAdapter extends RecyclerView.Adapter<ProfileCompetitionAdapter.ViewHolder> {
    private Context mContext;
    private View layoutView;

    public ProfileCompetitionAdapter (Context context, View layoutView) {
        this.mContext = context;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public ProfileCompetitionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.achievement , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCompetitionAdapter.ViewHolder holder, int position) {
        final Achievement achievement = Achievement.achievementList.get(position);
            if(!Achievement.achievementList.isEmpty()) {
                holder.tvDate.setText(Achievement.achievementList.get(position).getEventEnd().toString());
                holder.tvAchievement.setText(Achievement.achievementList.get(position).getEventName()+Achievement.achievementList.get(position).getPosition());
            }
    }

    @Override
    public int getItemCount() {
        return Achievement.achievementList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvAchievement;

        private ViewHolder ( View itemView ) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAchievement = itemView.findViewById(R.id.tvAchievement);
        }

    }
}
