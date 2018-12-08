package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import project.aigo.myapplication.Adapter.ImageSliderAdapter;
import project.aigo.myapplication.Adapter.NewsAdapter;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class HomeActivity extends GlobalActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected ( @NonNull MenuItem item ) {
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.home_menu:
                    return true;
                case R.id.news_menu:
                    intent.setClass(HomeActivity.this , NewsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.search_menu:
                    return true;
                case R.id.donation_menu:
                    return true;
                case R.id.events_menu:
                    intent.setClass(HomeActivity.this , EventActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return true;
            }

        }
    };

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GlobalActivity globalActivity = new GlobalActivity();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPager viewPager = findViewById(R.id.slider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(imageSliderAdapter);

        globalActivity.loadFragment(new ViewNewsFragment(),R.id.frameHome,this,null,null);

    }


}
