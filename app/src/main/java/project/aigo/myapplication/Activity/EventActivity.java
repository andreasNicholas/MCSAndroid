package project.aigo.myapplication.Activity;

import android.app.FragmentManager;
import android.os.Bundle;

import project.aigo.myapplication.Fragment.EventListFragment;
import project.aigo.myapplication.R;

public class EventActivity extends GlobalActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        loadFragment(new EventListFragment(), R.id.eventActivity, this, null, null);
    }

    @Override
    public void onBackPressed(){
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
