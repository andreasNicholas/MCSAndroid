package project.aigo.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class ProfileHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HashMap<String, String> paramsForAthleteBranch;
    private BranchAdapter branchAdapter;
    private String tampAthleteID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_home, container, false);

        branchAdapter = new BranchAdapter(getActivity());
        recyclerView = view.findViewById(R.id.rvSportBranch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(branchAdapter);

        try
        {
            if(!getArguments().isEmpty() && !getArguments().get("athleteID").toString().equals(null)){
                tampAthleteID = getArguments().get("athleteID").toString();
            }
        }
        catch (Exception e){

        }

        mapParamsAthleteBranch();
        callApiGetBranch();

        return view;
    }

    private void mapParamsAthleteBranch(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this.getActivity().getBaseContext());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAthleteBranch = new HashMap<>();

        paramsForAthleteBranch.put("userID" , id);
        paramsForAthleteBranch.put("remember_token" , remember_token);
        paramsForAthleteBranch.put("athleteID", tampAthleteID);
    }

    private void callApiGetBranch() {
        APIManager apiManagerBranch = new APIManager();
        apiManagerBranch.getBranchByUserId(this.getActivity().getBaseContext(), paramsForAthleteBranch, branchAdapter, recyclerView);
    }
}
