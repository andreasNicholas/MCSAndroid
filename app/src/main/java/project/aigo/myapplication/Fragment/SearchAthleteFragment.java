package project.aigo.myapplication.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Adapter.AthleteAdapter;
import project.aigo.myapplication.Object.User;
import project.aigo.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchAthleteFragment extends Fragment{

    List<User> userList;
    Map<String, String> params;
    View layoutView;
    AthleteAdapter athleteAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    EditText etSearch;
    GlobalActivity globalActivity;
    public SearchAthleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        globalActivity = new GlobalActivity();
        View view = inflater.inflate(R.layout.fragment_search_athlete , container , false);

        mapParams();

        layoutView = Objects.requireNonNull(getActivity()).findViewById(R.id.mainActivity);
        RecyclerView recyclerView = view.findViewById(R.id.rvAthlete);

        etSearch = view.findViewById(R.id.etSearch);
        userList = new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                refresh();
            }
        });
        String role = globalActivity.getRole(getActivity());
        athleteAdapter = new AthleteAdapter(getActivity() , userList, params.get("userID"), role);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()) ,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(athleteAdapter);

        callApi();

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
        params.put("athleteID" , "");
    }

    private void callApi () {
        APIManager apiManager = new APIManager();
        apiManager.getAthlete(getActivity() , layoutView , params , athleteAdapter , userList, swipeRefreshLayout , etSearch);
    }

}
