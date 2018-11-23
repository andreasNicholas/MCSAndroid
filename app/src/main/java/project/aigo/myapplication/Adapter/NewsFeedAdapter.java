package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context mContext;
    private int[] mImageIds;
    private String[] mImageTitles;
    LayoutInflater layoutInflater;

    public NewsFeedAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.news, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.ViewHolder holder, int position) {
        holder.imageNews.setImageResource(mImageIds[position]);
        holder.imageTitles.setText(mImageTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mImageTitles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageNews;
        TextView imageTitles;
        public ViewHolder(View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.ivNews);
            imageTitles = itemView.findViewById(R.id.tvNews);
        }
    }
}
