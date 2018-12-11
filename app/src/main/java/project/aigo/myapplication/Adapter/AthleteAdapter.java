package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.aigo.myapplication.Object.User;
import project.aigo.myapplication.R;

public class AthleteAdapter extends RecyclerView.Adapter<AthleteAdapter.ViewHolder> {
    private Context mContext;
    private List<User> userList;
    private View layoutView;

    public AthleteAdapter(Context context) {
        this.mContext = context;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public AthleteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.branch , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AthleteAdapter.ViewHolder holder, int position) {
        final User user = User.userList.get(position);
        holder.tvAthleteName.setText(user.getName());
        holder.tvAthleteGender.setText(user.getGender());
    }

    @Override
    public int getItemCount() {
        return User.userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAthleteName, tvAthleteGender;
        private ImageView ivAthletePP;

        private ViewHolder ( View itemView ) {
            super(itemView);
            tvAthleteName = itemView.findViewById(R.id.tvAthleteName);
            tvAthleteGender = itemView.findViewById(R.id.tvAthleteGender);
            ivAthletePP = itemView.findViewById(R.id.ivAthletePP);
        }
    }
}
