package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.Objects;

import io.smooch.core.Settings;
import io.smooch.core.Smooch;
import io.smooch.core.SmoochCallback;
import project.aigo.myapplication.Adapter.TabAdapter;
import project.aigo.myapplication.Fragment.EventListFragment;
import project.aigo.myapplication.Fragment.HomeFragment;
import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.Fragment.SearchAthleteFragment;
import project.aigo.myapplication.R;

public class MainActivity extends GlobalActivity {
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_home_black_24dp ,
            R.drawable.ic_search_white_24dp ,
            R.drawable.ic_forum_black_24dp ,
            R.drawable.ic_insert_invitation_black_24dp,
            R.drawable.ic_settings_black_24dp
    };
    private String id;
    private DatabaseReference myRef;
    private TabAdapter adapter;
    private String role;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);

        getSupportActionBar().setTitle("Home");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        String[] getDataforAuthenticate = getDataforAuthenticate(this);
        id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";

        Smooch.init(Objects.requireNonNull(this).getApplication(), new Settings(GlobalActivity.SMOOCH_APP_TOKEN), new SmoochCallback() {
            @Override
            public void run(Response response) {
                // Your code after init is complete
            }

        });

        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable ( String userId , String registrationId ) {

                myRef.child("users").child(id).child("notificationKey").setValue(userId);
            }
        });

        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);


        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());

        role = getRole(this);
        menuSetting(role);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(role);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                if(!role.equals("admin")){
                    if(tab.getPosition() == 0) getSupportActionBar().setTitle("Home");
                    else if(tab.getPosition() == 1) getSupportActionBar().setTitle("Search Athlete");
                    else if(tab.getPosition() == 2) getSupportActionBar().setTitle("Chat");
                    else if(tab.getPosition() == 3) getSupportActionBar().setTitle("Settings");
                }
                else if(role.equals("admin")){
                    if(tab.getPosition() == 0) getSupportActionBar().setTitle("Home");
                    else if(tab.getPosition() == 1) getSupportActionBar().setTitle("Search Athlete");
                    else if(tab.getPosition() == 2) getSupportActionBar().setTitle("Events");
                    else if(tab.getPosition() == 3) getSupportActionBar().setTitle("Settings");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void menuSetting ( String role ) {
        adapter.addFragment(new HomeFragment(), null, "Home");
        adapter.addFragment(new SearchAthleteFragment(), null, "Athletes");
        if (role.equals("admin")) adapter.addFragment(new EventListFragment(), null, "Events");
        else adapter.addFragment(new ProfileChatFragment(), null, "Chat");
        adapter.addFragment(new ProfileSettingFragment(), null, "My Profile");

    }

    private void setupTabIcons (String role) {

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        if (role.equals("admin")) Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[3]);
        else Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(tabIcons[4]);

    }

}