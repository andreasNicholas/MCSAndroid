package project.aigo.myapplication.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.aigo.myapplication.Adapter.NewsFeedAdapter;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.NewsActivity.fragmentState;

public class ViewNewsFragment extends Fragment {

    public ViewNewsFragment() {
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
        getActivity().findViewById(R.id.addNewNews).setVisibility(View.VISIBLE);
        fragmentState = 1;
        return recyclerView;
    }
}