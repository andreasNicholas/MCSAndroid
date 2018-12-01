package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.aigo.myapplication.Activity.AddBranchActivity;
import project.aigo.myapplication.Activity.AddSportActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSettingFragment extends Fragment implements View.OnClickListener {
    ImageView ivLogout, ivAddSport, ivAddBranch;
    TextView tvLogout, tvAddSport, tvAddBranch;

    public ProfileSettingFragment() {
        // Required empty public constructor
    }


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

        ivLogout.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        ivAddSport.setOnClickListener(this);
        tvAddSport.setOnClickListener(this);
        ivAddBranch.setOnClickListener(this);
        tvAddBranch.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view == ivLogout || view == tvLogout){
            Intent intent = new Intent(getActivity() , LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
    }
}
