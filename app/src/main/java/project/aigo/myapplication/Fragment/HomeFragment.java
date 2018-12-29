package project.aigo.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import project.aigo.myapplication.Activity.EventCalendarActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.NewsActivity;
import project.aigo.myapplication.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    public HomeFragment() {
        // Required empty public constructor
    }

    Button btnEventCalendar;
    TextView tvNewsList;
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragmentv
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        btnEventCalendar = view.findViewById(R.id.btnEventCalendar);
        btnEventCalendar.setOnClickListener(this);
        tvNewsList = view.findViewById(R.id.tvNewsList);
        tvNewsList.setOnClickListener(this);

        GlobalActivity globalActivity = new GlobalActivity();
        String role = globalActivity.getRole(getActivity());

        if (role.equals("admin")) btnEventCalendar.setVisibility(View.GONE);

        globalActivity.loadFragment(new ViewNewsFragment(),R.id.frameHome, getActivity(),null,null);

        return view;
    }

    @Override
    public void onClick ( View v ) {
        if (v == btnEventCalendar){
            Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), EventCalendarActivity.class));
        }else if(v == tvNewsList){
            Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(), NewsActivity.class));
        }
    }
}
