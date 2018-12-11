package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Adapter.AthleteAdapter;
import project.aigo.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchAthleteFragment extends Fragment implements View.OnClickListener {
    Button btn6;

    public SearchAthleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_athlete , container , false);
        RecyclerView recyclerView = view.findViewById(R.id.rvAthlete);

        AthleteAdapter athleteAdapter = new AthleteAdapter(getActivity().getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(athleteAdapter);

        btn6 = view.findViewById(R.id.button6);
        btn6.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.getActivity(), ProfileActivity.class);
        startActivity(intent);
    }
}
