package project.aigo.myapplication.Activity.Admin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import project.aigo.myapplication.Adapter.NewsFeedAdapter;
import project.aigo.myapplication.Fragment.adminAddNewsFragment;
import project.aigo.myapplication.Fragment.adminNewsViewFragment;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class adminNewsActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout LLNews;
    Button newsList, addNewNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_news);
        newsList = findViewById(R.id.newsList);
        addNewNews = findViewById(R.id.addNewNews);

        newsList.setOnClickListener(this);
        addNewNews.setOnClickListener(this);

        loadFragment(new adminNewsViewFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view == newsList){ loadFragment(new adminNewsViewFragment()); }
        else if(view == addNewNews){ loadFragment(new adminAddNewsFragment()); }
    }
}
