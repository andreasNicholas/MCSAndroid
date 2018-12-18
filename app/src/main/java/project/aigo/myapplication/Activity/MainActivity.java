package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.util.Objects;

import project.aigo.myapplication.Adapter.TabAdapter;
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
            R.drawable.ic_settings_black_24dp
    };
    private String id;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        String[] getDataforAuthenticate = getDataforAuthenticate(this);
        id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";

        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable ( String userId , String registrationId ) {

                myRef.child("users").child(id).child("notificationKey").setValue(userId);
            }
        });

        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);

        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment() , "Home");
        adapter.addFragment(new SearchAthleteFragment() , "Athletes");
        adapter.addFragment(new ProfileChatFragment() , "Chat");
        adapter.addFragment(new ProfileSettingFragment() , "My Profile");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons () {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(tabIcons[3]);
    }


}