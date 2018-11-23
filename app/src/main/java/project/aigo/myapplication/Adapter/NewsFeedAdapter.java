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
        mImageIds = new int[News.newsList.size()];
        for(int i=0; i<mImageIds.length; i++){
            mImageIds[i] = News.newsList.get(i).getImage_id();
        }
        mImageTitles = new String[News.newsList.size()];
        for(int i=0; i<mImageTitles.length; i++){
            mImageTitles[i] = News.newsList.get(i).getNews_title();
        }
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mContext);
        View v = layoutInflater.inflate(R.layout.news, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedAdapter.ViewHolder holder, int position) {
        holder.imageNews.setImageResource(mImageIds[position]);
        holder.imageNews.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageNews.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels)/4);
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

    public Context getmContext() {
        return mContext;
    }
}
