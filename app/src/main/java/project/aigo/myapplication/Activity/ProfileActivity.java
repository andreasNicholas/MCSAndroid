package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.TabAdapter;
import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Fragment.ProfileHomeFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.R;

public class ProfileActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Map<String, String> paramsForSport, paramsForBranch, paramsForEvent;
    private int[] tabIcons = {
            R.drawable.ic_dehaze_white_24dp,
            R.drawable.ic_assessment_black_24dp,
            R.drawable.ic_forum_black_24dp,
            R.drawable.ic_settings_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileHomeFragment(), "Overview");
        adapter.addFragment(new ProfileCompStatFragment(), "Statistics");
        adapter.addFragment(new ProfileChatFragment(), "Chat");
        adapter.addFragment(new ProfileSettingFragment(), "Settings");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        mapParams();
        callApi();
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
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
        apiManagerEvent.getAllEvent(this, paramsForEvent);
        APIManager apiManagerSport = new APIManager();
        apiManagerSport.getSport(this, paramsForSport);
        APIManager apiManagerBranch = new APIManager();
        apiManagerBranch.getBranch(this, paramsForBranch);
    }
/*
    @Override
    public void onClick(View view) {
        if(view == ivProfileHome){
            //Bundle bundle = new Bundle();
            //bundle.putString("name","testing");
            Global.loadFragment(new ProfileHomeFragment(), flProfile.getId(), this);
        }
        else if(view == ivProfileCompStat){
            Global.loadFragment(new ProfileCompStatFragment(), flProfile.getId(), this);
        }
        else if(view == ivProfileChat){
            Global.loadFragment(new ProfileChatFragment(), flProfile.getId(), this);
        }
        else if(view == ivProfileSetting){
            Global.loadFragment(new ProfileSettingFragment(), flProfile.getId(), this);
        }
    }*/
}
