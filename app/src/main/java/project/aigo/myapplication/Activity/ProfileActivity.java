package project.aigo.myapplication.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import project.aigo.myapplication.Adapter.ImageSliderAdapter;
import project.aigo.myapplication.Adapter.ProfileCompetitionAdapter;
import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Fragment.ProfileHomeFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FrameLayout flProfile;
    ImageView ivProfileHome, ivProfileCompStat,ivProfileChat, ivProfileSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        flProfile = findViewById(R.id.flProfile);
        ivProfileHome = findViewById(R.id.ivProfileHome);
        ivProfileCompStat = findViewById(R.id.ivProfileCompStat);
        ivProfileChat = findViewById(R.id.ivProfileChat);
        ivProfileSetting = findViewById(R.id.ivProfileSetting);
        ivProfileHome.setOnClickListener(this);
        ivProfileCompStat.setOnClickListener(this);
        ivProfileChat.setOnClickListener(this);
        ivProfileSetting.setOnClickListener(this);

    }

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

            Branch branch = new Branch();
            branch.setSportName("TEST");
            branch.setBranchName("TEST2");
            Branch.branchList.add(branch);
            Global.loadFragment(new ProfileSettingFragment(), flProfile.getId(), this);
        }
    }
}
