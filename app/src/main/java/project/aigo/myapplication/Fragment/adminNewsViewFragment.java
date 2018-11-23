package project.aigo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import project.aigo.myapplication.Activity.Admin.adminNewsActivity;
import project.aigo.myapplication.Adapter.NewsFeedAdapter;
import project.aigo.myapplication.Object.Donation;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class adminNewsViewFragment extends Fragment {

    public adminNewsViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        News.newsList.clear();
        News news = new News("this is news 1", R.drawable.ic_launcher_background, 0, "content for news 1");
        news.newsList.add(news);
        News news2 = new News("this is news 2", R.drawable.ic_launcher_background, 0, "content for news 2");
        news.newsList.add(news2);
        News news3 = new News("this is news 3", R.drawable.ic_launcher_foreground, 0, "content for news 3");
        news.newsList.add(news3);


        View view = inflater.inflate(R.layout.fragment_admin_news_view, parent, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcAdminNews);
        ((ViewGroup)recyclerView.getParent()).removeView(recyclerView);
        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsFeedAdapter);

        return recyclerView;
    }

}
