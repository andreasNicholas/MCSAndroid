package project.aigo.myapplication.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragmentv
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        GlobalActivity globalActivity = new GlobalActivity();
        globalActivity.loadFragment(new ViewNewsFragment(),R.id.frameHome, getActivity(),null,null);

        return view;
    }
}
