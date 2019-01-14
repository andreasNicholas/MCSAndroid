package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.smooch.core.Settings;
import io.smooch.core.Smooch;
import io.smooch.core.SmoochCallback;
import io.smooch.ui.ConversationActivity;
import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddBranchActivity;
import project.aigo.myapplication.Activity.AddSportActivity;
import project.aigo.myapplication.Activity.AddUserBranchActivity;
import project.aigo.myapplication.Activity.ChangePasswordActivity;
import project.aigo.myapplication.Activity.EditProfileActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileSettingFragment extends Fragment implements View.OnClickListener {
    LinearLayout llLogout, llChangePassword, llAddSport, llAddBranch, llMyProfile, llAddMySport, llChatWithUs, llEditProfile;

    private Map<String, String> paramsForEvent;


    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        GlobalActivity globalActivity = new GlobalActivity();
        View view = inflater.inflate(R.layout.fragment_profile_setting , container , false);


        llEditProfile = view.findViewById(R.id.llEditProfile);
        llLogout = view.findViewById(R.id.llLogout);
        llChangePassword = view.findViewById(R.id.llChangePassword);
        llAddSport = view.findViewById(R.id.llAddSport);
        llAddBranch = view.findViewById(R.id.llAddBranch);
        llMyProfile = view.findViewById(R.id.llMyProfile);
        llAddMySport = view.findViewById(R.id.llAddMySport);
        llChatWithUs = view.findViewById(R.id.llChatWithUs);

        String role = globalActivity.getRole(getActivity());

        if (role.equals("admin")) {
            llAddMySport.setVisibility(View.GONE);
            llChatWithUs.setVisibility(View.GONE);
        } else {
            llAddSport.setVisibility(View.GONE);
            llAddBranch.setVisibility(View.GONE);
        }

        llEditProfile.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llChangePassword.setOnClickListener(this);
        llAddSport.setOnClickListener(this);
        llAddBranch.setOnClickListener(this);
        llMyProfile.setOnClickListener(this);
        llAddMySport.setOnClickListener(this);
        llChatWithUs.setOnClickListener(this);

        mapParams();
        callApi();

        return view;
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;

        paramsForEvent = new HashMap<>();
        paramsForEvent.put("userID" , id);
        paramsForEvent.put("remember_token" , remember_token);
    }

    private void callApi () {
        APIManager apiManagerEvent = new APIManager();
        apiManagerEvent.getAllEvent(getActivity() , paramsForEvent);

    }

    @Override
    public void onClick ( View view ) {
        if (view == llLogout) {

            OneSignal.setSubscription(false);
            SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("spLogin" , MODE_PRIVATE);
            sp.edit().clear().apply();
            Intent intent = new Intent(getActivity() , LoginActivity.class);

            Branch.branchList.clear();
            Sport.sportList.clear();
            startActivity(intent);
            Objects.requireNonNull(getActivity()).finish();
        } else if (view == llAddBranch) {
            Intent intent = new Intent(getActivity() , AddBranchActivity.class);
            startActivity(intent);
        } else if (view == llAddSport) {
            Intent intent = new Intent(getActivity() , AddSportActivity.class);
            startActivity(intent);
        } else if (view == llMyProfile) {
            Intent intent = new Intent(getActivity() , ProfileActivity.class);
            startActivity(intent);
        } else if (view == llAddMySport) {
            Intent intent = new Intent(getActivity() , AddUserBranchActivity.class);
            startActivity(intent);
        } else if(view == llChatWithUs){
            ConversationActivity.show(Objects.requireNonNull(getActivity()));
        } else if (view == llEditProfile) {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        } else if( view == llChangePassword){
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        }
    }
}
