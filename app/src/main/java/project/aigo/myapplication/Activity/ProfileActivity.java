package project.aigo.myapplication.Activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class ProfileActivity extends GlobalActivity {
    private TabLayout tabLayout;
    private String athleteId;
    public static Bundle athleteBundle;
    private TextView tvUserName, tvUserEmail;
    private ImageView ivProfilePicture;
    private Bitmap bitmapContainer;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private HashMap<String, String> paramsForGetProfile;

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
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserName = findViewById(R.id.tvUserName);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        final SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);

        try
        {
            if(getIntent().getExtras()!=null){
                athleteBundle = this.getIntent().getExtras();
                athleteId = athleteBundle.getString("athleteID", athleteId);
                getSupportActionBar().setTitle("Athlete Profile");
                mapParamsGetProfile();
                callApiGetProfile();

            }
            else {
                GlobalActivity globalActivity = new GlobalActivity();
                String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
                String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
                athleteId = id;
                getSupportActionBar().setTitle("My Profile");
                tvUserName.setText(sp.getString("name", null));
                tvUserEmail.setText(sp.getString("name", null));
                mapParamsGetProfile();
                callApiGetProfile();
            }
        }catch (Exception e){
            GlobalActivity globalActivity = new GlobalActivity();
            String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
            String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
            athleteId = id;
            getSupportActionBar().setTitle("My Profile");
            tvUserName.setText(sp.getString("name", null));
            tvUserEmail.setText(sp.getString("name", null));
            mapParamsGetProfile();
            callApiGetProfile();
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

    private void mapParamsGetProfile() {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForGetProfile = new HashMap<>();

        paramsForGetProfile.put("userID", athleteId);
        paramsForGetProfile.put("remember_token" , remember_token);
    }

    private void callApiGetProfile() {
        APIManager apiManagerGetProfile = new APIManager();
        apiManagerGetProfile.getProfile(this, this.findViewById(R.id.profileActivity) ,paramsForGetProfile);
    }
}
