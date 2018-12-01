package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {
    private Context mContext;
    private List<Branch> branchList;
    private View layoutView;

    public BranchAdapter(Context context) {
        this.mContext = context;
        this.branchList = branchList;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public BranchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = LayoutInflater.from(mContext).inflate(R.layout.branch , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchAdapter.ViewHolder holder, int position) {
        final Branch branch = Branch.branchList.get(position);
        holder.tvSport.setText(branch.getSportName());
        holder.tvBranch.setText(branch.getBranchName());
    }

    @Override
    public int getItemCount() {
        return Branch.branchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSport, tvBranch;

        private ViewHolder ( View itemView ) {
            super(itemView);
            tvSport = itemView.findViewById(R.id.tvSport);
            tvBranch = itemView.findViewById(R.id.tvbranch);
        }
    }
}
