package project.aigo.myapplication.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import project.aigo.myapplication.Adapter.ImageSliderAdapter;
import project.aigo.myapplication.Adapter.NewsFeedAdapter;
import project.aigo.myapplication.Object.Donation;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class HomeActivity extends AppCompatActivity {
    public static TextView tvDonationTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_menu:
                    return true;
                case R.id.news_menu:
                    return true;
                case R.id.search_menu:
                    return true;
                case R.id.donation_menu:
                    return true;
                case R.id.events_menu:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //chnage later get from DB

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPager viewPager = findViewById(R.id.slider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this);
        viewPager.setAdapter(imageSliderAdapter);

        RecyclerView recyclerView = findViewById(R.id.rcNews);
        NewsFeedAdapter newsFeedAdapter = new NewsFeedAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsFeedAdapter);
    }

}
