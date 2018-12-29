package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddBranchActivity;
import project.aigo.myapplication.Activity.AddSportActivity;
import project.aigo.myapplication.Activity.AddUserBranchActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Activity.TestActivity;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class ProfileSettingFragment extends Fragment implements View.OnClickListener {
    ImageView ivLogout, ivAddSport, ivAddBranch, ivMyProfile, ivAddMySport;
    TextView tvLogout, tvAddSport, tvAddBranch, tvMyProfile, tvAddMySport;
    private Map<String, String> paramsForSport, paramsForBranch, paramsForEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        ivLogout = view.findViewById(R.id.icLogout);
        tvLogout = view.findViewById(R.id.tvLogout);
        ivAddSport = view.findViewById(R.id.icAddSport);
        tvAddSport = view.findViewById(R.id.tvAddSport);
        ivAddBranch = view.findViewById(R.id.icAddBranch);
        tvAddBranch = view.findViewById(R.id.tvAddBranch);
        ivMyProfile = view.findViewById(R.id.icMyProfile);
        tvMyProfile = view.findViewById(R.id.tvMyProfile);
        tvAddMySport =  view.findViewById(R.id.tvAddMySport);
        ivAddMySport = view.findViewById(R.id.icAddMySport);

        ivLogout.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        ivAddSport.setOnClickListener(this);
        tvAddSport.setOnClickListener(this);
        ivAddBranch.setOnClickListener(this);
        tvAddBranch.setOnClickListener(this);
        ivMyProfile.setOnClickListener(this);
        tvMyProfile.setOnClickListener(this);
        ivAddMySport.setOnClickListener(this);
        tvAddMySport.setOnClickListener(this);

        mapParams();
        callApi();

        return view;
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForSport = new HashMap<>();
        paramsForBranch = new HashMap<>();
        paramsForEvent = new HashMap<>();

        paramsForSport.put("userID" , id);
        paramsForSport.put("remember_token" , remember_token);

        paramsForBranch.put("userID" , id);
        paramsForBranch.put("remember_token" , remember_token);

        paramsForEvent.put("userID" , id);
        paramsForEvent.put("remember_token" , remember_token);
    }

    private void callApi() {
        APIManager apiManagerEvent = new APIManager();
        apiManagerEvent.getAllEvent(getActivity(), paramsForEvent);
        //APIManager apiManagerSport = new APIManager();
        //apiManagerSport.getSport(getActivity(), paramsForSport);
        //APIManager apiManagerBranch = new APIManager();
        //apiManagerBranch.getBranch(getActivity(), paramsForBranch);
    }

    @Override
    public void onClick(View view) {
        if(view == ivLogout || view == tvLogout){
            Intent intent = new Intent(getActivity() , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Branch.branchList.clear();
            Sport.sportList.clear();
            startActivity(intent);
        }
        else if(view == ivAddBranch || view == tvAddBranch) {
            Intent intent = new Intent(getActivity() , AddBranchActivity.class);
            startActivity(intent);
        }
        else if(view == ivAddSport || view == tvAddSport) {
            Intent intent = new Intent(getActivity() , AddSportActivity.class);
            startActivity(intent);
        }
        else if(view == ivMyProfile || view == tvMyProfile) {
            Intent intent = new Intent(getActivity() , ProfileActivity.class);
            startActivity(intent);
        }
        else if(view == ivAddMySport || view == tvAddMySport) {
            Intent intent = new Intent(getActivity() , AddUserBranchActivity.class);
            startActivity(intent);
        }
    }
}
