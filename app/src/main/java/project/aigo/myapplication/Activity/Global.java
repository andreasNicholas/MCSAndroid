package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import static project.aigo.myapplication.Activity.SplashScreenActivity.toastShort;

public class Global extends AppCompatActivity {

    protected static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    protected long mBackPressed;

    @Override
    public void onBackPressed () {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            toastShort(getBaseContext() , "Tap back button in order to exit");
        }

        mBackPressed = System.currentTimeMillis();
    }


}
