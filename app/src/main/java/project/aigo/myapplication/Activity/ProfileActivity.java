package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import project.aigo.myapplication.Fragment.ProfileChatFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Fragment.ProfileHomeFragment;
import project.aigo.myapplication.Fragment.ProfileSettingFragment;
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
            Bundle bundle = new Bundle();
            bundle.putString("name","testing");
            Global.loadFragment(new ProfileHomeFragment(), flProfile.getId(), this, bundle);
        }
        else if(view == ivProfileCompStat){
            Global.loadFragment(new ProfileCompStatFragment(), flProfile.getId(), this, null);
        }
        else if(view == ivProfileChat){
            Global.loadFragment(new ProfileChatFragment(), flProfile.getId(), this,null);
        }
        else if(view == ivProfileSetting){
            Global.loadFragment(new ProfileSettingFragment(), flProfile.getId(), this,null);
        }
    }
}
