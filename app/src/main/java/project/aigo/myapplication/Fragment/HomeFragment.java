package project.aigo.myapplication.Fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Adapter.ImageSliderAdapter;
import project.aigo.myapplication.R;

public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentv
        View view = (View) inflater.inflate(R.layout.fragment_home,
                container, false);
        //ViewNewsFragment viewNewsFragment = new ViewNewsFragment();
        //android.support.v4.app.FragmentTransaction transaction = new F
        //transaction.add(viewNewsFragment, "view news fragment").commit();


        GlobalActivity globalActivity = new GlobalActivity();

        ViewPager viewPager = view.findViewById(R.id.slider);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(view.getContext());
        viewPager.setAdapter(imageSliderAdapter);

        globalActivity.loadFragment(this.getActivity(), new ViewNewsFragment(),R.id.frameHome, getActivity().getBaseContext(),null,null);

        return view;
    }
}
