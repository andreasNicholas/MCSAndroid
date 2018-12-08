package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import project.aigo.myapplication.Activity.AddAchievementActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Adapter.NewsAdapter;
import project.aigo.myapplication.Adapter.ProfileCompetitionAdapter;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCompStatFragment extends Fragment {
    int tampung, getMonth;
    int[][] visibleX = {{0},{0}};
    int count =0;
    int tampBA = 0, tampBB = 0, batasAtas, batasBawah;
    LineChart chart;
    Vector <Achievement> visibleAchievement = new Vector<>();
    private FloatingActionButton fabAchievement;
    private ProfileCompetitionAdapter profileCompetitionAdapter;
    private ArrayList<Entry> entries;

    public ProfileCompStatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_comp_stat, container, false);
        fabAchievement = view.findViewById(R.id.fabAddAchievement);
        visibleAchievement = Achievement.achievementList;
        View layoutView = getActivity().findViewById(R.id.profileActivity);
        profileCompetitionAdapter = new ProfileCompetitionAdapter(getActivity(), layoutView);

        generateChart(view);
        generateDetail(view);
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


        /*for(int i=0; i<20 ; i++){
            Achievement achievement = new Achievement();
            achievement.setEventName("Event "+i);
            achievement.setPosition(i);
            Achievement.achievementList.add(achievement);
        }*/
            /*RecyclerView recyclerView = view.findViewById(R.id.rvAchievement);
            ProfileCompetitionAdapter profileCompetitionAdapter= new ProfileCompetitionAdapter(getActivity().getBaseContext());
            //recyclerView.setAdapter(profileCompetitionAdapter);
            recyclerView.setAdapter(profileCompetitionAdapter);*/
        return view;
    }

    private void generateChart(View view) {
        chart = (LineChart) view.findViewById(R.id.chart);
        entries = new ArrayList<>();

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Des"};
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value];
            }
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        xAxis.setTextSize(14);

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);
        yAxisRight.setTextSize(14);
        yAxisLeft.setTextSize(14);

        Legend legend = chart.getLegend();
        legend.setTextSize(14);
        legend.setTextColor(R.color.colorBg);
        legend.setEnabled(true);
        ArrayList<LegendEntry> legendEntry = new ArrayList<>();
        legendEntry.add(new LegendEntry("Position", Legend.LegendForm.CIRCLE,15f,10f,null, Color.RED));
        legend.setCustom(legendEntry);

        Description description = chart.getDescription();
        description.setEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        chart.setDragDecelerationEnabled(false);
        chart.setPinchZoom(true);
        chart.animateX(2500);
        chart.setVisibleXRangeMaximum(3); // allow 20 values to be displayed at once on the x-axis, not more
        chart.moveViewToX(3);

        //refresh
        chart.invalidate();


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
//                onStop(chart);
            }
        });

        tampBA = batasAtas;
        tampBB = batasBawah;
        updateDetailRV(tampBA, tampBB);
    }

    @Override
    public void onResume() {
        super.onResume();

        LineDataSet dataSet = new LineDataSet(entries, "Customized values");
        LineData data = new LineData(dataSet);

        //dataSet.clear();
        //data.clearValues();
        entries.clear();

        //Logic ini jangan diubah dulu
        if(entries.isEmpty()) {
            if (Achievement.achievementList.isEmpty()) {
                entries.add(new Entry(0, 0));
                entries.add(new Entry(1, 0));
                entries.add(new Entry(1, 0));
                entries.add(new Entry(2, 0));
                entries.add(new Entry(3, 0));
                entries.add(new Entry(4, 0));
                entries.add(new Entry(5, 0));
                entries.add(new Entry(6, 0));
                entries.add(new Entry(7, 0));
                entries.add(new Entry(8, 0));
                entries.add(new Entry(9, 0));
                entries.add(new Entry(10, 0));
                entries.add(new Entry(11, 0));
            } else {
                entries.clear();
                if(Achievement.achievementList.size()<11) {
                    for (int i = 0; i < Achievement.achievementList.size(); i++) {
                        if(Achievement.achievementList.size() == 1) {
                            if(Integer.parseInt(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)) == 1) {
                                entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)), Achievement.achievementList.get(i).getPosition()));
                                entries.add(new Entry(2, 0));
                            }
                            else
                            {
                                entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7))-1, 0));
                                entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)), Achievement.achievementList.get(i).getPosition()));
                            }
                        }
                    }
                }
            }
        }
        else{
            entries.clear();
            if(Achievement.achievementList.size()<11){
                for (int i = 0; i < Achievement.achievementList.size(); i++) {
                    if(Achievement.achievementList.size() == 1) {
                        if(Integer.parseInt(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)) == 1) {
                            entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)), Achievement.achievementList.get(i).getPosition()));
                            entries.add(new Entry(2, 0));
                        }
                        else
                        {
                            entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7))-1, 0));
                            entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)), Achievement.achievementList.get(i).getPosition()));
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < 11; i++) {
                    entries.add(new Entry(Float.parseFloat(Achievement.achievementList.get(i).getEventEnd().toString().substring(5, 7)), Achievement.achievementList.get(i).getPosition()));
                    }
                }
            }

        dataSet.notifyDataSetChanged();
        data.notifyDataChanged();

        dataSet.setColor(ContextCompat.getColor(this.getContext(), R.color.colorBg));
        dataSet.setValueTextColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));
        dataSet.setCircleColors( R.color.colorBlack );
        dataSet.setValueTextSize(14);
        data.setDrawValues(false);
        chart.setData(data);
    }

    public void onStop(LineChart chart) {
        super.onStop();
        ++count;
        batasBawah = Math.round(chart.getLowestVisibleX());
        batasAtas = Math.round(chart.getHighestVisibleX());

        visibleX[0][0] = batasBawah;
        visibleX[1][0] = batasAtas;

        if(batasBawah == 0) batasAtas = 3;
        if(batasAtas == 11) batasBawah = 8;

        if(tampBA != batasAtas){
            tampBA = batasAtas;
            tampBB = batasBawah;
            if(visibleX[1][0] < 4) Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , String.valueOf(visibleX[0][0]) + " dan " +3 +" KALI " +count , Toast.LENGTH_SHORT).show();
            else if(visibleX[0][0] > 7) Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , 8 + " dan " +String.valueOf(visibleX[1][0]) +" KALI " +count , Toast.LENGTH_SHORT).show();
            else Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , String.valueOf(visibleX[0][0] + " dan " +String.valueOf(visibleX[1][0])) +" KALI " +count , Toast.LENGTH_SHORT).show();
        }

        if(tampBB != batasBawah){
            tampBA = batasAtas;
            tampBB = batasBawah;
            if(visibleX[1][0] < 4) Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , String.valueOf(visibleX[0][0]) + " dan " +3 +" KALI " +count , Toast.LENGTH_SHORT).show();
            else if(visibleX[0][0] > 7) Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , 8 + " dan " +String.valueOf(visibleX[1][0]) +" KALI " +count , Toast.LENGTH_SHORT).show();
            else Toast.makeText(getActivity().findViewById(R.id.profileActivity).getContext() , String.valueOf(visibleX[0][0] + " dan " +String.valueOf(visibleX[1][0])) +" KALI " +count , Toast.LENGTH_SHORT).show();
        }
    }

    private void generateDetail(View view) {

    }

    private void updateDetailRV(int tampBA, int  tampBB) {
        //visibleAchievement = spGetDataByRange();
    }

    private void generateFilter(View view) {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1900; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, years);

        Spinner spinYear = view.findViewById(R.id.spinYear);
        spinYear.setAdapter(adapter);
    }
}
