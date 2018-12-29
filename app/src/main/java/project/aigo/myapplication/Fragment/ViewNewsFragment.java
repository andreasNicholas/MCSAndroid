package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddNewsActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.MainActivity;
import project.aigo.myapplication.Activity.NewsActivity;
import project.aigo.myapplication.Adapter.NewsAdapter;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class ViewNewsFragment extends Fragment implements View.OnClickListener {

    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private Map<String, String> params;
    private View layoutView;
    private FloatingActionButton fabAddNews;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int layoutID;
    private String limit;
    GlobalActivity globalActivity;

    public ViewNewsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup parent ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        globalActivity = new GlobalActivity();
        final String role = globalActivity.getRole(getActivity());

        View view = inflater.inflate(R.layout.fragment_news_view , parent , false);
        RecyclerView recyclerView = view.findViewById(R.id.rcAdminNews);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                refresh();
            }
        });
        fabAddNews = view.findViewById(R.id.fabAddNews);
        fabAddNews.setOnClickListener(this);

        newsList = new ArrayList<>();
        if (getActivity() instanceof MainActivity) {
            layoutID = R.id.mainActivity;
            limit = "5";

        } else if (getActivity() instanceof NewsActivity) {
            layoutID = R.id.newsActivity;
            limit = "";
        }

        layoutView = getActivity().findViewById(layoutID);
        newsAdapter = new NewsAdapter(getActivity() , newsList , role , layoutView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                if (dy > 0 || dy < 0 && fabAddNews.isShown())
                    fabAddNews.hide();
            }

            @Override
            public void onScrollStateChanged ( RecyclerView recyclerView , int newState ) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((role.equals("athlete")) || getActivity() instanceof MainActivity)
                        fabAddNews.setVisibility(View.INVISIBLE);
                    else fabAddNews.show();

                }
                super.onScrollStateChanged(recyclerView , newState);
            }
        });
        refresh();

        if (role.equals("athlete") || getActivity() instanceof MainActivity)
            fabAddNews.setVisibility(View.INVISIBLE);

        return view;
    }

    private void refresh () {
        mapParams();
        callApi();
    }

    private void mapParams () {
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        params = new HashMap<>();

        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("limit" , limit);
    }

    private void callApi () {
        APIManager apiManager = new APIManager();
        apiManager.getNews(getActivity() , layoutView , params , newsAdapter , newsList , swipeRefreshLayout);
    }

    @Override
    public void onClick ( View view ) {
        if (view == fabAddNews) {
            //globalActivity.loadFragment(new AddNewsFragment() , R.id.mainActivity , getActivity() , null , "addFragment");
            Intent intent = new Intent(getActivity() , AddNewsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume () {
        super.onResume();
        refresh();
    }

}