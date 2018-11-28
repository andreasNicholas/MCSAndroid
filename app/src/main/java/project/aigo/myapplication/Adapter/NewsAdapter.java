package project.aigo.myapplication.Adapter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context mContext;
    private List<News> newsList;
    private View layoutView;

    public NewsAdapter ( Context context , List<News> newsList , View layoutView ) {
        this.mContext = context;
        this.newsList = newsList;
        this.layoutView = layoutView;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder ( @NonNull ViewGroup parent , int viewType ) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.news , parent , false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder ( @NonNull final NewsAdapter.ViewHolder holder , final int position ) {

        final News news = newsList.get(position);
        Picasso.get().load(news.getImageSrc()).into(holder.imageNews);
        holder.imageNews.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageNews.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels) / 4);
        holder.imageTitles.setText(news.getTitle());

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {

                final String[] menu = {"Edit" , "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setItems(menu , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick ( DialogInterface dialogInterface , int i ) {
                        APIManager apiManager = new APIManager();
                        String id;
                        switch (i) {
                            case 0:
                                id = news.getId();
                                String title = news.getTitle();
                                String description = news.getDescription();
                                String imageSrc = news.getImageSrc();
                                String[] arrayNews = {id , title , description , imageSrc};
                                Bundle bundle = new Bundle();
                                bundle.putStringArray("arrayNews" , arrayNews);
                                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getFragmentManager();
                                FragmentTransaction fragmentTransaction;
                                AddNewsFragment fragment = new AddNewsFragment();
                                fragment.setArguments(bundle);
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.newsActivity, fragment, String.valueOf(fragment.getId()));
                                fragmentTransaction.addToBackStack("editFragment");
                                fragmentTransaction.commit();
                                break;
                            case 1:

                                GlobalActivity globalActivity = new GlobalActivity();
                                String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(mContext);
                                id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
                                String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";

                                Map<String, String> params = new HashMap<>();
                                params.put("newsID" , news.getId());
                                params.put("userID" , id);
                                params.put("remember_token" , remember_token);

                                apiManager.deleteNews(mContext , layoutView , params , NewsAdapter.this , position , getItemCount(), newsList);
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount () {
        return (newsList == null) ? 0 : newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNews;
        TextView imageTitles;
        Button btnMenu;

        private ViewHolder ( View itemView ) {
            super(itemView);
            imageNews = itemView.findViewById(R.id.ivNews);
            imageTitles = itemView.findViewById(R.id.tvNews);
            btnMenu = itemView.findViewById(R.id.btnMenu);
        }

    }


}
