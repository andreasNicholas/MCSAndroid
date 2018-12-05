package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Fragment.AddNewsFragment;
import project.aigo.myapplication.Fragment.DetailNewsFragment;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<News> newsList;
    private String role;
    private View layoutView;
    private final GlobalActivity globalActivity = new GlobalActivity();

    public NewsAdapter ( Context context , List<News> newsList , String role , View layoutView  ) {
        this.mContext = context;
        this.newsList = newsList;
        this.role = role;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.news , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull final NewsAdapter.ViewHolder holder , int i ) {

        final int position = holder.getAdapterPosition();
        final News news = newsList.get(position);
        String img = (news.getImageSrc().equals("null")) ? DEFAULT_IMAGE : news.getImageSrc();

        Picasso.get().load(img).into(holder.imageNews);

        holder.imageNews.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageNews.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels) / 4);
        holder.imageTitles.setText(news.getTitle());

        if (role.equals("admin")) {
            holder.btnMenu.setVisibility(View.VISIBLE);
            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {

                    final String[] menu = {"Edit" , "Delete"};

                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setItems(menu , new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick ( DialogInterface dialogInterface , int i ) {
                            switch (i) {
                                case 0:
                                    String id = news.getId();
                                    String title = news.getTitle();
                                    String description = news.getDescription();
                                    String imageSrc = news.getImageSrc();
                                    String[] arrayNews = {id , title , description , imageSrc};
                                    Bundle bundle = new Bundle();
                                    bundle.putStringArray("arrayNews" , arrayNews);
                                    globalActivity.loadFragment(new AddNewsFragment() , R.id.newsActivity , mContext , bundle , "editFragment");
                                    break;
                                case 1:
                                    callApi(news.getId() , position);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    builder.show().create();
                }
            });
        }
    }

    @Override
    public int getItemCount () {
        return (newsList == null) ? 0 : newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNews;
        TextView imageTitles;
        Button btnMenu;
        SwipeRefreshLayout swipeRefreshLayout;

        private ViewHolder ( View itemView ) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.ivNews);
            imageTitles = itemView.findViewById(R.id.tvNews);
            btnMenu = itemView.findViewById(R.id.btnMenu);
            swipeRefreshLayout = itemView.findViewById(R.id.swipe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick ( View view ) {
                    final int position = getAdapterPosition();
                    final News news = newsList.get(position);

                    String title = news.getTitle();
                    String description = news.getDescription();
                    String imageSrc = news.getImageSrc();

                    String[] arrayEvent = {title , description , imageSrc };
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("arrayNews" , arrayEvent);
                    globalActivity.loadFragment(new DetailNewsFragment() , R.id.newsActivity , mContext , bundle , "detailFragment");
                }
            });
        }

    }

    private void callApi ( final String newsID , final int position ) {
        final APIManager apiManager = new APIManager();
        String titleAlert = "Delete Confirmation";
        String message = "Are you sure want to Delete?";
        AlertDialog.Builder builder = globalActivity.createGlobalAlertDialog(mContext , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(mContext);
                String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
                String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";

                Map<String, String> params = new HashMap<>();
                params.put("newsID" , newsID);
                params.put("userID" , id);
                params.put("remember_token" , remember_token);

                apiManager.deleteNews(mContext , layoutView , params , NewsAdapter.this , position , getItemCount() , newsList);
            }
        });
        builder.show().create();
    }

}
