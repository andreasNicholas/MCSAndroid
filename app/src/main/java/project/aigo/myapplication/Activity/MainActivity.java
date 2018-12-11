package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.TabAdapter;
import project.aigo.myapplication.Fragment.HomeFragment;
import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Fragment.ProfileHomeFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.Fragment.SearchAthleteFragment;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class MainActivity extends GlobalActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    FrameLayout flProfile;
    ImageView ivProfileHome, ivProfileCompStat, ivProfileChat, ivProfileSetting;
    private Map<String, String> paramsForSport, paramsForBranch, paramsForEvent;
    private int[] tabIcons = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_search_white_24dp,
            R.drawable.ic_forum_black_24dp,
            R.drawable.ic_settings_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new SearchAthleteFragment(), "Athletes");
        adapter.addFragment(new ProfileChatFragment(), "Events");
        adapter.addFragment(new ProfileSettingFragment(), "My Profile");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        //mapParams();
        //callApi();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void mapParams() {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForSport = new HashMap<>();
        paramsForBranch = new HashMap<>();
        paramsForEvent = new HashMap<>();

        paramsForSport.put("userID", id);
        paramsForSport.put("remember_token", remember_token);

        paramsForBranch.put("userID", id);
        paramsForBranch.put("remember_token", remember_token);

        paramsForEvent.put("userID", id);
        paramsForEvent.put("remember_token", remember_token);
    }

    private void callApi() {
        APIManager apiManagerEvent = new APIManager();
        apiManagerEvent.getAllEvent(this, paramsForEvent);
        APIManager apiManagerSport = new APIManager();
        apiManagerSport.getSport(this, paramsForSport);
        APIManager apiManagerBranch = new APIManager();
        apiManagerBranch.getBranch(this, paramsForBranch);
    }
}