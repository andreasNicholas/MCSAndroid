package project.aigo.myapplication.Fragment;


import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.SplashScreenActivity.snackShort;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileCompStatFragment extends Fragment {
    int tampung, getMonth;
    int[][] visibleX = {{0},{0}};
    int count =0;

    public ProfileCompStatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_comp_stat, container, false);
        generateChart(view);
        generateDetailFragment(view);
        return view;
    }

    private void generateDetailFragment(View view) {
    }

    private void generateChart(View view) {
        final LineChart chart = (LineChart) view.findViewById(R.id.chart);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 2));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(2, 3));
        entries.add(new Entry(3, 1));
        entries.add(new Entry(4, 1));
        entries.add(new Entry(5, 3));
        entries.add(new Entry(6, 2));
        entries.add(new Entry(7, 2));
        entries.add(new Entry(8, 3));
        entries.add(new Entry(9, 1));
        entries.add(new Entry(10, 3));
        entries.add(new Entry(11, 3));

        final LineDataSet dataSet = new LineDataSet(entries, "Customized values");
        dataSet.setColor(ContextCompat.getColor(this.getContext(), R.color.colorBg));
        dataSet.setValueTextColor(ContextCompat.getColor(this.getContext(), R.color.colorBlack));
        dataSet.setCircleColors( R.color.colorBlack );
        dataSet.setValueTextSize(14);
        LineData data = new LineData(dataSet);
        data.setDrawValues(false);

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "Mei", "Juni", "Juli", "Agustus", "September", "November", "Oktober", "Desember"};
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

        chart.setPinchZoom(true);
        chart.setData(data);
        chart.animateX(2500);
        chart.setVisibleXRangeMaximum(3); // allow 20 values to be displayed at once on the x-axis, not more
        chart.moveViewToX(3);

        //refresh
        chart.invalidate();


        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) { }
            @Override public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) { }
            @Override public void onChartLongPressed(MotionEvent me) { }
            @Override public void onChartDoubleTapped(MotionEvent me) { Toast.makeText(getActivity().getBaseContext(), "Double", Toast.LENGTH_SHORT).show(); }
            @Override public void onChartSingleTapped(MotionEvent me) { Toast.makeText(getActivity().getBaseContext(), "Single", Toast.LENGTH_SHORT).show(); }
            @Override public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) { }
            @Override public void onChartScale(MotionEvent me, float scaleX, float scaleY) { }
            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                onStop(chart);
            }
        });

        //USE THIS TO GET MONTH PARAMETER TO SHOW WINS BASED ON MONTH
    }

    public void onStop(LineChart chart) {
        super.onStop();

        if(true){
            ++count;
            int a = Math.round(chart.getLowestVisibleX());
            int b = Math.round(chart.getHighestVisibleX());
            visibleX[0][0] = a;
            visibleX[1][0] = b;
            if(visibleX[0][0] == 0) visibleX[1][0] = 3;
            if(visibleX[1][0] == 11) visibleX[0][0] = 8;
            if(visibleX[1][0] <= 3) snackShort(getActivity().findViewById(R.id.profileActivity) , String.valueOf(visibleX[0][0]) + " dan " +3 +" KALI " +count);
            else if(visibleX[0][0] >= 8) snackShort(getActivity().findViewById(R.id.profileActivity) , 8 + " dan " +String.valueOf(visibleX[1][0]) +" KALI " +count);
            else snackShort(getActivity().findViewById(R.id.profileActivity) , String.valueOf(visibleX[0][0] + " dan " +String.valueOf(visibleX[1][0])) +" KALI " +count);
        }
    }
}
