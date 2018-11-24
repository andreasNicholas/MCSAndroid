package project.aigo.myapplication.Fragment;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        View view = inflater.inflate(R.layout.fragment_admin_news_view, parent, false);
        final RecyclerView recyclerView = view.findViewById(R.id.rcAdminNews);
        ((ViewGroup)recyclerView.getParent()).removeView(recyclerView);
        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(newsFeedAdapter);
        return recyclerView;
    }
}