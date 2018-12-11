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

import project.aigo.myapplication.Object.ShownAchievement;
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
            if(!ShownAchievement.shownAchievementList.isEmpty()) {

                Date endDate = ShownAchievement.shownAchievementList.get(position).getEventStart();
                SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MM yyyy");//formating according to my need

                holder.tvDate.setText(formatter.format(endDate));
                holder.tvAchievement.setText(ShownAchievement.shownAchievementList.get(position).getEventName()+" Position "+ShownAchievement.shownAchievementList.get(position).getPosition());
            }
    }

    @Override
    public int getItemCount() {
        return ShownAchievement.shownAchievementList.size();
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
