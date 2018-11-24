package project.aigo.myapplication.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.aigo.myapplication.Fragment.adminAddNewsFragment;
import project.aigo.myapplication.Fragment.adminNewsViewFragment;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context mContext;
    private String[] mImageName;
    private String[] mImageTitles;
    private Bitmap[] mImageBitmap;
    LayoutInflater layoutInflater;

    public NewsFeedAdapter(Context context) {
        this.mContext = context;
        if(News.newsList.isEmpty()){}
        else {
            mImageName = new String[News.newsList.size()];
            mImageTitles = new String[News.newsList.size()];
            mImageBitmap = new Bitmap[News.newsList.size()];
            for (int i = 0; i < mImageName.length; i++) {
                mImageName[i] = News.newsList.get(i).getImage_name();
                mImageTitles[i] = News.newsList.get(i).getNews_title();
                mImageBitmap[i] = News.newsList.get(i).getImage_bitmap();
            }
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
    public void onBindViewHolder(@NonNull final NewsFeedAdapter.ViewHolder holder, final int position) {
        holder.imageNews.setImageBitmap(mImageBitmap[position]);
        holder.imageNews.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageNews.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels) / 4);
        holder.imageTitles.setText(mImageTitles[position]);

        holder.btnEditNews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(v == holder.btnEditNews) {
                    Bundle arguments = new Bundle();
                    arguments.putString("newsTitle", holder.imageTitles.getText().toString());

                    FragmentManager fm = ((Activity) holder.getmContext()).getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    adminAddNewsFragment adminAddNewsFragment = new adminAddNewsFragment();
                    adminAddNewsFragment.setArguments(arguments);
                    fragmentTransaction.replace(R.id.frameLayout, adminAddNewsFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        holder.btnDeleteNews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(v == holder.btnDeleteNews) {
                    News.newsList.removeElementAt(position);
                    FragmentManager fm = ((Activity) holder.getmContext()).getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new adminNewsViewFragment());
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(News.newsList.isEmpty())
            return 0;
        else
            return mImageTitles.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNews;
        TextView imageTitles;
        Button btnEditNews, btnDeleteNews;

        public ViewHolder(View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.ivNews);
            imageTitles = itemView.findViewById(R.id.tvNews);
            btnEditNews = itemView.findViewById(R.id.btnEditNews);
            btnDeleteNews = itemView.findViewById(R.id.btnDeleteNews);
        }

        public Context getmContext() {
            return mContext;
        }
    }
}
