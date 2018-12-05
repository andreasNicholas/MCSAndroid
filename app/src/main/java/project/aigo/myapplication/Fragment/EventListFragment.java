package project.aigo.myapplication.Fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
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
import project.aigo.myapplication.Activity.EventActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.HomeActivity;
import project.aigo.myapplication.Adapter.EventAdapter;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.R;

public class EventListFragment extends Fragment implements View.OnClickListener {

    private List<Event> eventsList;
    private EventAdapter eventAdapter;
    private Map<String, String> params;
    private View layoutView;
    private FloatingActionButton fabAddEvent;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int layoutID;
    private String limit;
    GlobalActivity globalActivity;

    public EventListFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup parent ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        globalActivity = new GlobalActivity();
        final String role = globalActivity.getRole(getActivity());

        View view = inflater.inflate(R.layout.fragment_event_list , parent , false);
        RecyclerView recyclerView = view.findViewById(R.id.rcEventList);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                refresh();
            }
        });
        fabAddEvent = view.findViewById(R.id.fabAddEvent);
        fabAddEvent.setOnClickListener(this);

        eventsList = new ArrayList<>();
        if (getActivity() instanceof HomeActivity) {
            layoutID = R.id.homeActivity;
            limit = "5";

        } else if (getActivity() instanceof EventActivity) {
            layoutID = R.id.eventActivity;
            limit = "";
        }
        layoutView = getActivity().findViewById(layoutID);

        eventAdapter = new EventAdapter(getActivity() , eventsList , role , layoutView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                if (dy > 0 || dy < 0 && fabAddEvent.isShown())
                    fabAddEvent.hide();
            }

            @Override
            public void onScrollStateChanged ( RecyclerView recyclerView , int newState ) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if ((role.equals("athlete")) || getActivity() instanceof HomeActivity)
                        fabAddEvent.setVisibility(View.INVISIBLE);
                    else fabAddEvent.show();

                }
                super.onScrollStateChanged(recyclerView , newState);
            }

        });
        refresh();

        if (role.equals("athlete") || getActivity() instanceof HomeActivity)
            fabAddEvent.setVisibility(View.INVISIBLE);

        return view;
    }

    private void refresh () {
        mapParams();
        callApi();
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
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
        apiManager.getEvents(getActivity() , layoutView , params , eventAdapter , eventsList , swipeRefreshLayout);
    }

    @Override
    public void onClick ( View view ) {
        if (view == fabAddEvent) {
            globalActivity.loadFragment(new EventEditFragment() , R.id.eventActivity , getActivity() , null , "addFragment");
        }

    }

}