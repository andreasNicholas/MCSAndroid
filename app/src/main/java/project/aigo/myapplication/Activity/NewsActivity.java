package project.aigo.myapplication.Activity;

import android.app.FragmentManager;
import android.os.Bundle;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class NewsActivity extends GlobalActivity {
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        loadFragment(new ViewNewsFragment(),R.id.newsActivity,this,null,null);
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
