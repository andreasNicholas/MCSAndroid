package project.aigo.myapplication.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import project.aigo.myapplication.Fragment.ProfileSettingFragment;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class Global extends AppCompatActivity {

    protected static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    protected long mBackPressed;

    @Override
    public void onBackPressed () {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
        }

        mBackPressed = System.currentTimeMillis();
    }

    public static void loadFragment(Fragment fragment, int frameLayoutId, Activity activity, Bundle bundle){
        if(!bundle.isEmpty()) fragment.setArguments(bundle);

        FragmentManager fm = activity.getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(frameLayoutId, fragment, String.valueOf(fragment.getId()));
        fragmentTransaction.commit();
    }
}