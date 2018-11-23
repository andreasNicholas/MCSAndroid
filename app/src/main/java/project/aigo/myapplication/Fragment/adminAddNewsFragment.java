package project.aigo.myapplication.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import project.aigo.myapplication.Adapter.NewsFeedAdapter;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class adminAddNewsFragment extends Fragment {
    EditText etNewsTitle, etNewsImage, etNewsVideo, etNewsContent;
    Button btnAddNews;

    public adminAddNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_add_news, parent, false);
        etNewsTitle = view.findViewById(R.id.etNewsTitle);
        etNewsImage = view.findViewById(R.id.etNewsImage);
        etNewsVideo = view.findViewById(R.id.etNewsVideo);
        etNewsContent = view.findViewById(R.id.etNewsContent);
        btnAddNews = view.findViewById(R.id.btnAddNews);
        btnAddNews.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(v == btnAddNews) {
                    //laravel validation for TITLE, IMAGE, AND CONTENT, video is optional
                    //if(true){
                    News news = new News(etNewsTitle.getText().toString(), 0, 0, etNewsContent.getText().toString());
                    News.newsList.add(news);
                    //sementara data tidak muncul karena di clear dan input ulang di adminNewsViewFragment.java

                    etNewsTitle.setText("clicked");
                    FragmentManager fm = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new adminNewsViewFragment());
                    fragmentTransaction.commit();
                    //else snackbar
                }
            }
        });

        return view;
    }

}