package project.aigo.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddAchievementActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Adapter.AthleteAdapter;
import project.aigo.myapplication.Adapter.ProfileCompetitionAdapter;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.R;

public class ProfileCompStatFragment extends Fragment {
    LineChart chart;
    List<Achievement> achievementList = new ArrayList<>();
    List<Achievement> detailAchievementList = new ArrayList<>();
    private FloatingActionButton fabAchievement;
    private ProfileCompetitionAdapter profileCompetitionAdapter;
    private Map<String, String> params;
    private Spinner spinYear;
    private View layoutView;

    @Override
    public View onCreateView ( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_profile_comp_stat , container , false);
        fabAchievement = view.findViewById(R.id.fabAddAchievement);
        layoutView = view.findViewById(R.id.profileActivity);
        profileCompetitionAdapter = new ProfileCompetitionAdapter(getActivity()  , detailAchievementList);
        chart = view.findViewById(R.id.chart);
        spinYear = view.findViewById(R.id.spinYear);

        generateFilter(view);

        RecyclerView recyclerView = view.findViewById(R.id.rvAchievement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(profileCompetitionAdapter);
        fabAchievement.show();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                if(dy >0)
                    fabAchievement.hide();
            }

            @Override
            public void onScrollStateChanged ( RecyclerView recyclerView , int newState ) {
                super.onScrollStateChanged(recyclerView , newState);
                fabAchievement.show();

            }

        });

        fabAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View view ) {
                Intent intent = new Intent(view.getContext() , AddAchievementActivity.class);
                startActivity(intent);
            }
        });

        spinYear.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected ( AdapterView<?> adapterView , View view , int i , long l ) {
                detailAchievementList.clear();
                //JIKA SEARCH ATHLETE
                try {
                    if (!ProfileActivity.athleteBundle.isEmpty()) {
                        mapParamsForAthlete();
                        callApi();
                    }
                    //JIKA MY PROFILE
                    else {
                        mapParams();
                        callApi();
                    }
                }
                catch (Exception e){
                    mapParams();
                    callApi();
                }
            }

            @Override
            public void onNothingSelected ( AdapterView<?> adapterView ) {
            }
        });

        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart ( MotionEvent me , ChartTouchListener.ChartGesture lastPerformedGesture ) {
            }

            @Override
            public void onChartGestureEnd ( MotionEvent me , ChartTouchListener.ChartGesture lastPerformedGesture ) {
            }

            @Override
            public void onChartLongPressed ( MotionEvent me ) {
            }

            @Override
            public void onChartDoubleTapped ( MotionEvent me ) {
            }

            @Override
            public void onChartSingleTapped ( MotionEvent me ) {
            }

            @Override
            public void onChartFling ( MotionEvent me1 , MotionEvent me2 , float velocityX , float velocityY ) {
            }

            @Override
            public void onChartScale ( MotionEvent me , float scaleX , float scaleY ) {
            }

            @Override
            public void onChartTranslate ( MotionEvent me , float dX , float dY ) {

                loadAchievementDetail(chart);
            }
        });
        return view;
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        params = new HashMap<>();

        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("year" , spinYear.getSelectedItem().toString());
    }

    private void callApi () {
        APIManager apiManager = new APIManager();
        apiManager.getAchievementNoMonth(getContext() , layoutView , params , chart);
        apiManager.getAchievement(getActivity() , layoutView , params , profileCompetitionAdapter , achievementList, detailAchievementList);
    }

    private void mapParamsForAthlete () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        params = new HashMap<>();

        params.put("userID" , ProfileActivity.athleteBundle.getString("athleteID"));
        params.put("remember_token" , remember_token);
        params.put("year" , spinYear.getSelectedItem().toString());
    }

    private void callApiForAthlete () {
        APIManager apiManager = new APIManager();
        apiManager.getAchievementNoMonth(getContext() , layoutView , params , chart);
        apiManager.getAchievement(getActivity() , layoutView , params , profileCompetitionAdapter , achievementList, detailAchievementList);
    }

    public void loadAchievementDetail ( LineChart chart ) {

        detailAchievementList.clear();
        int min = (int) chart.getLowestVisibleX() + 1;
        int max = (int) chart.getHighestVisibleX() + 1;

        for (Achievement achievement : achievementList) {
            Date startDate = achievement.getEventStart();
            SimpleDateFormat formatter = new SimpleDateFormat("MM", Locale.US);//formating according to my need
            int tempMonth = Integer.parseInt(formatter.format(startDate));
            if (tempMonth >= min && tempMonth <= max) {
                detailAchievementList.add(achievement);
            }
        }
        profileCompetitionAdapter.notifyDataSetChanged();

//        batasBawah = Math.round(chart.getLowestVisibleX());
//        batasAtas = Math.round(chart.getHighestVisibleX());
//
//        if (batasBawah == 0) batasAtas = 3;
//        if (batasAtas == 12) batasBawah = 9;
//
//        visibleX[0] = batasBawah;
//        visibleX[1] = batasAtas;
//
//        if (visibleX[1] < 4) setShownMonthDetail(0 , 4);
//        else if (visibleX[0] > 9) setShownMonthDetail(9 , 12);
//        else {
//            if (visibleX[1] < 12)
//                setShownMonthDetail(visibleX[0] + 1 , visibleX[1]);
//            else if (visibleX[0] > 1)
//                setShownMonthDetail(visibleX[0] , visibleX[1] - 1);
//            else
//                setShownMonthDetail(visibleX[0] , visibleX[1]);
//        }
    }

    private void generateFilter ( View view ) {
        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 2000; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext() , android.R.layout.select_dialog_item , years);

        spinYear.setAdapter(adapter);
    }
}
