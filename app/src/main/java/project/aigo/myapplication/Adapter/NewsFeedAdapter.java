package project.aigo.myapplication.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.Fragment.AddNewsFragment;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;


public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {
    private Context mContext;
    private String[] mImageName;
    private String[] mImageTitles;
    private Bitmap[] mImageBitmap;
    LayoutInflater layoutInflater;
    PopupMenu popup;

    public NewsFeedAdapter(Context context) {
        this.mContext = context;
        if(News.newsList.isEmpty()){}
        else {
            mImageName = new String[News.newsList.size()];
            mImageTitles = new String[News.newsList.size()];
            mImageBitmap = new Bitmap[News.newsList.size()];
            for (int i = 0; i < mImageName.length; i++) {
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

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
              if (view == view.findViewById(R.id.btnMenu)) {
                  popup = new PopupMenu(view.getContext(), view);
                  popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                      @Override
                      public boolean onMenuItemClick(MenuItem menuItem) {
                          switch (menuItem.getItemId()){
                              case R.id.btnEditNews:
                                  Bundle arguments = new Bundle();
                                  arguments.putString("newsTitle", holder.imageTitles.getText().toString());

                                  FragmentManager fm = ((Activity) holder.getmContext()).getFragmentManager();
                                  android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                  AddNewsFragment AddNewsFragment = new AddNewsFragment();
                                  AddNewsFragment.setArguments(arguments);
                                  fragmentTransaction.replace(R.id.frameLayout, AddNewsFragment);
                                  fragmentTransaction.commit();
                                  return true;
                              case R.id.btnDeleteNews:
                                  News.newsList.removeElementAt(position);
                                  FragmentManager fm2 = ((Activity) holder.getmContext()).getFragmentManager();
                                  android.app.FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
                                  fragmentTransaction2.replace(R.id.frameLayout, new ViewNewsFragment());
                                  fragmentTransaction2.commit();
                                  return true;
                          }
                          return false;
                      }
                  });
                  popup.inflate(R.menu.layoutmenu);
                  popup.show();
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
        Button btnMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.ivNews);
            imageTitles = itemView.findViewById(R.id.tvNews);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }

        public Context getmContext() {
            return mContext;
        }
    }
}
