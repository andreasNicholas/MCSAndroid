package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.NewsActivity.fragmentState;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileSettingFragment extends Fragment implements View.OnClickListener {
    ImageView ivLogout;
    TextView tvLogout;

    public ProfileSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        ivLogout = view.findViewById(R.id.icLogout);
        tvLogout = view.findViewById(R.id.tvLogout);

        ivLogout.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        fragmentState = 3;
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
    }
}
