package project.aigo.myapplication.Activity;

import android.os.Bundle;
import project.aigo.myapplication.Fragment.ViewNewsFragment;
import project.aigo.myapplication.R;

public class NewsActivity extends GlobalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setTitle("News");
        loadFragment(new ViewNewsFragment(),R.id.newsActivity,this,null,null);
    }
}
