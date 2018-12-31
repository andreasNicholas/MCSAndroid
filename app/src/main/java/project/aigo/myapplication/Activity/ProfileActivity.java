package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Adapter.TabAdapter;
import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Fragment.ProfileHomeFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.R;

public class ProfileActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private String athleteId;
    public static Bundle athleteBundle;

    private int[] tabIcons = {
            R.drawable.ic_dehaze_white_24dp,
            R.drawable.ic_assessment_black_24dp
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (!athleteBundle.isEmpty()) {
                athleteBundle.clear();
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        try
        {
            if(getIntent().getExtras()!=null){
                athleteBundle = this.getIntent().getExtras();
                athleteId = athleteBundle.getString("athleteID", athleteId);
                getSupportActionBar().setTitle("Athlete Profile");
            }
            else
                getSupportActionBar().setTitle("My Profile");
        }catch (Exception e){
            getSupportActionBar().setTitle("My Profile");
        }

        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("athleteID", athleteId);
        adapter.addFragment(new ProfileHomeFragment(), bundle, "Overview");
        adapter.addFragment(new ProfileCompStatFragment(), null,"Statistics");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
    }
}
