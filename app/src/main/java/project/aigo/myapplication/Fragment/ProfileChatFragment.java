package project.aigo.myapplication.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.aigo.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileChatFragment extends Fragment {


    public ProfileChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_chat, container, false);
    }

}
