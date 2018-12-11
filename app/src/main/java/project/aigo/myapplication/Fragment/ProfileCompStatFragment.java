package project.aigo.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

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
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.AddAchievementActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Adapter.ProfileCompetitionAdapter;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.ShownAchievement;
import project.aigo.myapplication.R;

public class ProfileCompStatFragment extends Fragment {
    LineChart chart;
    Vector <Achievement> visibleAchievement = new Vector<>();
    private FloatingActionButton fabAchievement;
    private ProfileCompetitionAdapter profileCompetitionAdapter;
    private View view;
    private Map<String, String> paramsForDetail;
    private Map<String, String> paramsForGraph;
    private Spinner spinYear;
    private View layoutView;
    private GlobalActivity globalActivity = new GlobalActivity();
    private ArrayList<Entry> entriesChart = new ArrayList<>();
    private int batasBawah, batasAtas;
    private int visibleX[] = new int[2] ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_comp_stat, container, false);
        fabAchievement = view.findViewById(R.id.fabAddAchievement);
        visibleAchievement = Achievement.achievementList;
        profileCompetitionAdapter = new ProfileCompetitionAdapter(getActivity(), layoutView);
        chart = view.findViewById(R.id.chart);
        spinYear = view.findViewById(R.id.spinYear);

        generateFilter(view);

        RecyclerView recyclerView = view.findViewById(R.id.rvAchievement);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(profileCompetitionAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled ( RecyclerView recyclerView , int dx , int dy ) {
                super.onScrolled(recyclerView , dx , dy);
                if (dy > 0 && fabAchievement.getVisibility() == View.VISIBLE) {
                    fabAchievement.hide();
                } else if (dy < 0 && fabAchievement.getVisibility() != View.VISIBLE) {
                    fabAchievement.show();
                }
            }
        });

        fabAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddAchievementActivity.class);
                startActivity(intent);
            }
        });

        spinYear.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Achievement.achievementList.clear();
                ShownAchievement.shownAchievementList.clear();
                entriesChart.clear();
                mapParams();
                callApi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }
            @Override public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }
            @Override public void onChartLongPressed(MotionEvent me) {
            }
            @Override public void onChartDoubleTapped(MotionEvent me) {
                Toast.makeText(getActivity().getBaseContext(), "Double", Toast.LENGTH_SHORT).show();
            }
            @Override public void onChartSingleTapped(MotionEvent me) {
                Toast.makeText(getActivity().getBaseContext(), "Single", Toast.LENGTH_SHORT).show();
            }
            @Override public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }
            @Override public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            }
            @Override public void onChartTranslate(MotionEvent me, float dX, float dY) {
                visibleX[0] = 1;
                visibleX[1] = 1;

                onStop(chart);
            }
        });
        return view;
    }

    //BUGGY, SEARCH FOR OTHER ALTERNATIVE WHY JSON ARRAY OBJECT IS NULL WHEN ON CREATE
    @Override
    public void onStart() {
        super.onStart();
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForDetail = new HashMap<>();
        paramsForGraph = new HashMap<>();

        paramsForDetail.put("userID" , id);
        paramsForDetail.put("remember_token" , remember_token);
        paramsForDetail.put("year" , spinYear.getSelectedItem().toString());

        paramsForGraph.put("userID", id);
        paramsForGraph.put("remember_token" , remember_token);
        paramsForGraph.put("year" , spinYear.getSelectedItem().toString());
    }

    private void callApi() {
        APIManager apiManagerGraph = new APIManager();
        apiManagerGraph.getAchievementNoMonth(getContext() , view , paramsForGraph, chart , entriesChart ,1);
        APIManager apiManagerDetail = new APIManager();
        apiManagerDetail.getAchievement(getActivity() , view , paramsForDetail , profileCompetitionAdapter);
    }


    public void onStop(LineChart chart) {
        super.onStop();
        batasBawah = Math.round(chart.getLowestVisibleX());
        batasAtas = Math.round(chart.getHighestVisibleX());

        if(batasBawah == 0) batasAtas = 3;
        if(batasAtas == 12) batasBawah = 9;

        visibleX[0] = batasBawah;
        visibleX[1] = batasAtas;

        if(visibleX[1] < 4) setShownMonthDetail(0,4);
        else if(visibleX[0] > 9) setShownMonthDetail(9,12);
        else {
            if(visibleX[1]<12)
                setShownMonthDetail(visibleX[0] + 1, visibleX[1]);
            else if(visibleX[0]>1)
                setShownMonthDetail(visibleX[0], visibleX[1] - 1);
            else
                setShownMonthDetail(visibleX[0], visibleX[1]);
        }
    }

    private void setShownMonthDetail(int start, int end){
        ShownAchievement.shownAchievementList.clear();
        for(int i=0; i<Achievement.achievementList.size();i++){
            Date startDate = Achievement.achievementList.get(i).getEventStart();
            SimpleDateFormat formatter = new SimpleDateFormat("MM");//formating according to my need

            int tampMonth = Integer.parseInt(formatter.format(startDate));

            if(tampMonth >= start && tampMonth <= end) {
                ShownAchievement shownAchievement = new ShownAchievement();
                shownAchievement.setAchievementId(Achievement.achievementList.get(i).getAchievementId());
                shownAchievement.setEventName(Achievement.achievementList.get(i).getEventName());
                shownAchievement.setEventStart(Achievement.achievementList.get(i).getEventStart());
                shownAchievement.setEventEnd(Achievement.achievementList.get(i).getEventEnd());
                shownAchievement.setPosition(Achievement.achievementList.get(i).getPosition());
                shownAchievement.setDesc(Achievement.achievementList.get(i).getDesc());
                shownAchievement.setBranch(Achievement.achievementList.get(i).getBranch());
                shownAchievement.setSport(Achievement.achievementList.get(i).getSport());
                ShownAchievement.shownAchievementList.add(shownAchievement);

                profileCompetitionAdapter.notifyDataSetChanged();
            }
        }
    }

    private void generateFilter(View view) {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1900; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, years);

        spinYear.setAdapter(adapter);
    }
}
