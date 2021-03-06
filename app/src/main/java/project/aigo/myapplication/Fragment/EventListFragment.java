package project.aigo.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import java.util.Objects;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddEventActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.MainActivity;
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
    private GlobalActivity globalActivity;
    private View view;
    private String role;

    public EventListFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup parent ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        globalActivity = new GlobalActivity();
        role = globalActivity.getRole(getActivity());

        view = inflater.inflate(R.layout.fragment_event_list , parent , false);

        initSwipeRefreshLayout();
        initFab();
        initRecyclerView();
        refresh();

        return view;
    }

    private void initRecyclerView () {
        RecyclerView recyclerView = view.findViewById(R.id.rcEventList);
        eventsList = new ArrayList<>();
        if (getActivity() instanceof MainActivity) {
            layoutID = R.id.mainActivity;
            limit = "";

        } else {
            limit = "";
        }
        layoutView = Objects.requireNonNull(getActivity()).findViewById(layoutID);

        eventAdapter = new EventAdapter(getActivity() , eventsList , role , layoutView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                if (dy > 0)
                    fabAddEvent.hide();
            }

            @Override
            public void onScrollStateChanged ( RecyclerView recyclerView , int newState ) {
                super.onScrollStateChanged(recyclerView , newState);
                fabAddEvent.show();

            }

        });
    }

    private void initFab () {
        fabAddEvent = view.findViewById(R.id.fabAddEvent);
        fabAddEvent.setOnClickListener(this);
        if ((role.equals("athlete")))
            fabAddEvent.setVisibility(View.INVISIBLE);
        else fabAddEvent.setVisibility(View.VISIBLE);

    }

    private void initSwipeRefreshLayout () {
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                refresh();
            }
        });
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
        apiManager.getEvents(getActivity() , layoutView , params , eventAdapter , eventsList , swipeRefreshLayout);
    }

    @Override
    public void onClick ( View view ) {
        if (view == fabAddEvent) {
            Intent intent = new Intent(getActivity() , AddEventActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onResume () {
        super.onResume();
        refresh();
    }
}