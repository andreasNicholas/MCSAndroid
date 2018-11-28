package project.aigo.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class NewsActivity extends GlobalActivity {
    private Bundle bundle;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        loadFragment(new ViewNewsFragment());
    }

    public Bundle createPrimaryBundle(){
        String[] getDataforAuthenticate = getDataforAuthenticate(this);
        bundle = new Bundle();
        bundle.putString("limit", "");
        bundle.putStringArray("getDataforAuthenticate",getDataforAuthenticate);

        return bundle;
    }


    private  void loadFragment(Fragment fragment){
        fragmentManager = getFragmentManager();
        bundle = createPrimaryBundle();
        bundle.putString("limit", "");
        fragment.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.newsActivity, fragment, String.valueOf(fragment.getId()));
//        fragmentTransaction.addToBackStack("loadFragment");
        fragmentTransaction.commit();
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
