package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {
    private Context mContext;

    public SportAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public SportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.sport , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SportAdapter.ViewHolder holder, int position) {
        final Sport sport = Sport.sportList.get(position);
        holder.tvSportName.setText(sport.getSportName());
    }

    @Override
    public int getItemCount() {
        return Sport.sportList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSportName;

        private ViewHolder ( View itemView ) {
            super(itemView);
            tvSportName = itemView.findViewById(R.id.tvSportName);
        }
    }
}
