package project.aigo.myapplication.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class TabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<Bundle> mBundleList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, Bundle bundle, String title) {
        try {
            if (!bundle.isEmpty()) {
                fragment.setArguments(bundle);
            }
        }catch (Exception e){}
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mBundleList.add(bundle);
    }
    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
