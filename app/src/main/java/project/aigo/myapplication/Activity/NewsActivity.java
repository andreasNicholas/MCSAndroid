package project.aigo.myapplication.Activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import project.aigo.myapplication.Activity.Global;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.Fragment.AddNewsFragment;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.SplashScreenActivity.toastShort;

public class NewsActivity extends Global implements View.OnClickListener {
    LinearLayout LLNews;
    Button btnMenu;
    FloatingActionButton addNewNews;
    public static int fragmentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news);
        addNewNews = findViewById(R.id.addNewNews);

        addNewNews.setOnClickListener(this);
        loadFragment(new ViewNewsFragment());
        fragmentState = 1;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, String.valueOf(fragment.getId()));
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view == addNewNews){ loadFragment(new AddNewsFragment()); fragmentState = 2;}
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            MenuItem Edit = menu.add( 0, R.id.btnEditNews, 0,R.string.Edit);
            MenuItem Delete = menu.add( 0, R.id.btnDeleteNews, 0,R.string.Delete);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed () {
            FragmentManager fm = getFragmentManager();

            if(fragmentState == 2) {
                android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                ViewNewsFragment ViewNewsFragment = new ViewNewsFragment();
                fragmentTransaction.replace(R.id.frameLayout, ViewNewsFragment);
                fragmentTransaction.commit();
            }
            else if(fragmentState == 1){
                if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                    super.onBackPressed();
                    return;
                } else {
                    toastShort(getBaseContext() , "Tap back button in order to exit");
                }

                mBackPressed = System.currentTimeMillis();
            }
    }
}
