package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.R;

public class ProfileCompetitionAdapter extends RecyclerView.Adapter<ProfileCompetitionAdapter.ViewHolder> {
    private Context mContext;
    private List<Achievement> achievementList;
    public ProfileCompetitionAdapter (Context context, List<Achievement> achievementList) {
        this.mContext = context;
        this.achievementList = achievementList;
    }

    @NonNull
    @Override
    public ProfileCompetitionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = Objects.requireNonNull(inflater).inflate(R.layout.achievement , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCompetitionAdapter.ViewHolder holder, int position) {
            if(!achievementList.isEmpty()) {

                Date endDate = achievementList.get(position).getEventStart();
                SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MM yyyy", Locale.US);

                holder.tvDate.setText(formatter.format(endDate));
                String achievement = String.format("%s Position %s",achievementList.get(position).getEventName(),achievementList.get(position).getPosition());
                holder.tvAchievement.setText(achievement);
            }
    }

    @Override
    public int getItemCount() {
        return (achievementList == null) ? 0 : achievementList.size();
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
