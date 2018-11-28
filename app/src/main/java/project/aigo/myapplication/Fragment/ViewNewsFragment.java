package project.aigo.myapplication.Fragment;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import project.aigo.myapplication.Adapter.NewsAdapter;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class ViewNewsFragment extends Fragment implements View.OnClickListener {

    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private Map<String, String> params;
    private View layoutView;
    private FloatingActionButton fabAddNews;

    public ViewNewsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup parent ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        layoutView = getActivity().findViewById(R.id.newsActivity);
        View view = inflater.inflate(R.layout.fragment_news_view , parent , false);
        RecyclerView recyclerView = view.findViewById(R.id.rcAdminNews);
        fabAddNews = view.findViewById(R.id.fabAddNews);
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity() , newsList , layoutView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                super.onScrolled(recyclerView , dx , dy);
                if (dy > 0 && fabAddNews.getVisibility() == View.VISIBLE) {
                    fabAddNews.hide();
                } else if (dy < 0 && fabAddNews.getVisibility() != View.VISIBLE) {
                    fabAddNews.show();
                }
            }
        });
        fabAddNews.setOnClickListener(this);
        mapParams();
        callApi();
        return view;
    }

    private void mapParams () {
        params = new HashMap<>();
        String[] getDataforAuthenticate = getArguments().getStringArray("getDataforAuthenticate");
        String limit = getArguments().getString("limit");
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("limit" , limit);
    }

    private void callApi () {
        APIManager apiManager = new APIManager();
        apiManager.getNews(getActivity() , layoutView , params , newsAdapter , newsList);
    }

    @Override
    public void onClick ( View view ) {
        if (view == fabAddNews)
            addFragment(new AddNewsFragment());

    }

    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.newsActivity, fragment, String.valueOf(fragment.getId()));
        fragmentTransaction.addToBackStack("addFragment");
        fragmentTransaction.commit();
    }
}