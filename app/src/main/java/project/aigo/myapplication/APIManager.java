package project.aigo.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import project.aigo.myapplication.Activity.AddUserBranchActivity;
import project.aigo.myapplication.Activity.EventCalendarActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Activity.MainActivity;
import project.aigo.myapplication.Activity.ProfileActivity;
import project.aigo.myapplication.Adapter.AthleteAdapter;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Adapter.EventAdapter;
import project.aigo.myapplication.Adapter.SportAdapter;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.Object.User;

import static android.content.Context.MODE_PRIVATE;
import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class APIManager {

    private final GlobalActivity globalActivity = new GlobalActivity();

    public void postRegister ( final Context context , final View view , final Map<String, String> params ) {

        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("register");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                final String[] split = response.split(";");

                globalActivity.toastShort(context , split[1]);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run () {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();
                        myRef.child("users").child(split[0]).setValue(params);
                        myRef.push();


                        Intent intent = new Intent(context , LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                } , 2000);
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);

    }

    public void postLogin ( final Context context , final View view , final Map<String, String> params , final SharedPreferences sharedPreferences ) {

        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("login");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST , url , parameters , new Response.Listener<JSONObject>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONObject response ) {

                try {
                    String id = response.getString("id");
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String gender = response.getString("gender");
                    String role = response.getString("role");
                    String address = response.getString("address");
                    String phone = response.getString("phone");
                    String birthdate = response.getString("birthdate");
                    String remember_token = response.getString("remember_token");

                    String message = "Welcome, " + name;
                    globalActivity.toastShort(context , message);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id" , id);
                    editor.putString("name" , name);
                    editor.putString("email" , email);
                    editor.putString("gender" , gender);
                    editor.putString("role" , role);
                    editor.putString("address" , address);
                    editor.putString("phone" , phone);
                    editor.putString("birthdate" , birthdate);
                    editor.putString("remember_token" , remember_token);
                    editor.apply();

                    Intent intent = new Intent(context , MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getNews ( final Context context , final View view , final Map<String, String> params ,
                          final RecyclerView.Adapter adapter , final List<News> newsList , final SwipeRefreshLayout swipeRefreshLayout ) {


        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String limit = (params.get("limit") == null) ? "" : "/" + params.get("limit");

        String url = globalActivity.route("getNews/" + id + "/" + remember_token + limit);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                newsList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        News news = new News();
                        news.setId(jsonObject.getString("id"));
                        news.setTitle(jsonObject.getString("title"));
                        news.setDescription(jsonObject.getString("description"));
                        news.setImageSrc(jsonObject.getString("image_path"));
                        news.setViewsCount(jsonObject.getString("views_count"));
                        newsList.add(news);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                swipeRefreshLayout.setRefreshing(false);

            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(jsonArrayRequest);
    }

    public void deleteNews ( final Context context , final View view , final Map<String, String> params , final RecyclerView.Adapter adapter , final int position , final int count , final List<News> newsList ) {
        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("deleteNews");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {

                newsList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position , count);
                globalActivity.toastShort(context , "Delete Success");
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);

    }

    public void updateOrCreateNews ( final Context context , final View view , final Map<String, String> params ) {
        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("updateOrCreateNews");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                String message = (params.get("newsID").isEmpty()) ? "Add" : "Update";
                globalActivity.toastShort(context , message + " Success");
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);

    }

    public void getEvents ( final Context context , final View view , final Map<String, String> params ,
                            final RecyclerView.Adapter adapter , final List<Event> eventsList , final SwipeRefreshLayout swipeRefreshLayout ) {


        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String limit = (params.get("limit").isEmpty()) ? "" : "/" + params.get("limit");

        String url = globalActivity.route("getEvents/" + id + "/" + remember_token + limit);

        final RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                eventsList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Event event = new Event();
                        event.setId(jsonObject.getString("id"));
                        event.setEvent_name(jsonObject.getString("event_name"));
                        event.setEvent_start_datetime(jsonObject.getString("event_start_datetime"));
                        event.setEvent_end_datetime(jsonObject.getString("event_end_datetime"));
                        event.setEvent_description(jsonObject.getString("event_description"));
                        event.setEvent_image_path(jsonObject.getString("event_image_path"));
                        event.setViews_count(jsonObject.getString("views_count"));
                        event.setCreated_by(jsonObject.getString("created_by"));
                        eventsList.add(event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (context instanceof EventCalendarActivity) {
                    ColorDrawable blue = new ColorDrawable(context.getResources().getColor(R.color.caldroid_sky_blue , null));
                    Calendar calendar = Calendar.getInstance();
                    Map<Date, Drawable> hm = new HashMap<>();
                    for (int i = 0; i < eventsList.size(); i++) {
                        int year = Integer.parseInt(eventsList.get(i).getEvent_start_datetime().substring(0 , 4));
                        int month = Integer.parseInt(eventsList.get(i).getEvent_start_datetime().substring(5 , 7)) - 1;
                        int day = Integer.parseInt(eventsList.get(i).getEvent_start_datetime().substring(8 , 10));
                        calendar.set(Calendar.YEAR , year);
                        calendar.set(Calendar.MONTH , month);
                        calendar.set(Calendar.DAY_OF_MONTH , day);
                        Date date = calendar.getTime();
                        hm.put(date , blue);
                    }

                    CalendarView eventCalendar = ((EventCalendarActivity) context).findViewById(R.id.eventCalendar);

                    CaldroidFragment caldroidFragment = new CaldroidFragment();
                    Bundle args = new Bundle();
                    args.putInt(CaldroidFragment.MONTH , calendar.get(Calendar.MONTH) + 1);
                    args.putInt(CaldroidFragment.YEAR , calendar.get(Calendar.YEAR));
                    args.putBoolean(CaldroidFragment.ENABLE_SWIPE , true);
                    args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR , true);
                    caldroidFragment.setArguments(args);

                    caldroidFragment.setBackgroundDrawableForDates(hm);

                    final CaldroidListener caldroidListener = new CaldroidListener() {
                        @Override
                        public void onSelectDate ( Date date , View view ) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd" , Locale.US);
                            sdf.format(date);

                            ArrayList<Event> eventArrayList = new ArrayList<>();

                            for (Event event : eventsList) {
                                if (event.getEvent_start_datetime().startsWith(sdf.format(date)))
                                    eventArrayList.add(event);
                            }

                            SharedPreferences sp = context.getSharedPreferences("spLogin" , MODE_PRIVATE);

                            EventAdapter eventAdapter = new EventAdapter(context , eventArrayList , sp.getString("role" , "") , view);
                            RecyclerView rvEvent = ((EventCalendarActivity) context).findViewById(R.id.rvEventCalendar);
                            rvEvent.setLayoutManager(new LinearLayoutManager(context));
                            rvEvent.setAdapter(eventAdapter);
                            eventAdapter.notifyDataSetChanged();
                        }
                    };

                    caldroidFragment.setCaldroidListener(caldroidListener);

                    FragmentTransaction t = ((EventCalendarActivity) context).getSupportFragmentManager().beginTransaction();
                    t.replace(R.id.eventCalendar , caldroidFragment);
                    t.commit();

                    eventCalendar.setVisibility(View.VISIBLE);

                } else {
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                if(swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);

            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(jsonArrayRequest);
    }

    public void deleteEvent ( final Context context , final View view , final Map<String, String> params , final RecyclerView.Adapter adapter , final int position , final int count , final List<Event> eventsList ) {
        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("deleteEvent");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {

                eventsList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position , count);
                globalActivity.toastShort(context , "Delete Success");
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void updateOrCreateEvent ( final Context context , final View view , final Map<String, String> params  ) {
        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("updateOrCreateEvent");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                String message = (params.get("eventID").isEmpty()) ? "Add" : "Update";
                globalActivity.toastShort(context , message + " Success");
                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);

    }

    public void getAchievement ( final Context context , final View view , final Map<String, String> params , final RecyclerView.Adapter adapter , final List<Achievement> achievementList , final List<Achievement> detailAchievementList ) {
        achievementList.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String year = params.get("year");

        String url = globalActivity.route("getAchievementbyParam/" + id + "/" + remember_token + "/" + year + "/*");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);

                        Achievement achievement = new Achievement();
                        achievement.setAchievementId(jsonObject.getInt("id"));
                        achievement.setEventName(jsonObject.getString("event_name"));
                        String startDate = jsonObject.getString("event_start_datetime");
                        String endDate = jsonObject.getString("event_end_datetime");

                        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd" , Locale.US);
                        java.util.Date startDateObj = null;
                        java.util.Date endDateObj = null;
                        try {
                            startDateObj = curFormater.parse(startDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            endDateObj = curFormater.parse(endDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        achievement.setEventName(jsonObject.getString("event_name"));
                        achievement.setEventStart(startDateObj);
                        achievement.setEventEnd(endDateObj);
                        achievement.setPosition(jsonObject.getInt("position"));
                        achievement.setDesc(jsonObject.getString("description"));
                        achievement.setBranch(jsonObject.getString("branch_name"));
                        achievement.setSport(jsonObject.getString("sport_name"));
                        achievementList.add(achievement);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                detailAchievementList.clear();
                int min = 1;
                int max = 4;

                for (Achievement achievement : achievementList) {
                    Date startDate = achievement.getEventStart();
                    SimpleDateFormat formatter = new SimpleDateFormat("MM" , Locale.US);//formating according to my need
                    int tempMonth = Integer.parseInt(formatter.format(startDate));
                    if (tempMonth >= min && tempMonth <= max) {
                        detailAchievementList.add(achievement);
                    }
                }
                adapter.notifyDataSetChanged();
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getAchievementNoMonth ( final Context context , final View view , final Map<String, String> params , final LineChart chart ) {
        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String year = params.get("year");

        String url = globalActivity.route("getAchievementbyParam/" + id + "/" + remember_token + "/" + year);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                final String[] months = new String[12];
                List<Entry> entryList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        entryList.add(new Entry((jsonObject.getInt("id")) - 1 , jsonObject.getInt("y")));
                        months[i] = jsonObject.getString("month_name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LineDataSet dataSet = new LineDataSet(entryList , "Customized values");
                LineData data = new LineData(dataSet);

                IAxisValueFormatter formatter = new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue ( float value , AxisBase axis ) {
                        return months[(int) value];
                    }
                };

                dataSet.setColor(ContextCompat.getColor(context , R.color.colorBg));
                dataSet.setValueTextColor(ContextCompat.getColor(context , R.color.colorBlack));
                dataSet.setCircleColors(R.color.colorBlack);
                dataSet.setValueTextSize(14);
                data.setDrawValues(false);

                XAxis xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                xAxis.setValueFormatter(formatter);
                xAxis.setTextSize(14);

                Legend legend = chart.getLegend();
                legend.setTextSize(14);
                legend.setTextColor(R.color.colorBg);
                legend.setEnabled(true);
                ArrayList<LegendEntry> legendEntry = new ArrayList<>();
                legendEntry.add(new LegendEntry("Position" , Legend.LegendForm.CIRCLE , 15f , 10f , null , Color.RED));
                legend.setCustom(legendEntry);

                Description description = chart.getDescription();
                description.setEnabled(false);

                chart.invalidate();
                chart.setData(data);

                YAxis yAxisRight = chart.getAxisRight();
                yAxisRight.setEnabled(false);
                YAxis yAxisLeft = chart.getAxisLeft();
                yAxisLeft.setGranularity(1f);
                yAxisRight.setTextSize(14);
                yAxisLeft.setTextSize(14);
                chart.setVisibleXRangeMaximum(3); // allow 20 values to be displayed at once on the x-axis, not more
                chart.moveViewToX(3);
                chart.setPinchZoom(false);
                chart.animateX(2500);
                chart.moveViewToX(0);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getSport ( final Context context , final Map<String, String> params, final ArrayList<String> spinnerItem, ArrayAdapter<String> arrayAdapter, final int layoutId, final Spinner spinner) {
        Sport.sportList.clear();
        if(spinnerItem!=null) spinnerItem.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getSports/" + id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Sport sport = new Sport();
                        sport.setSportId(jsonObject.getInt("id"));
                        sport.setSportName(jsonObject.getString("sport_name"));

                        Sport.sportList.add(sport);
                        if(spinnerItem!=null)spinnerItem.add(Sport.sportList.get(j).getSportName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
        if(layoutId!=0 || spinnerItem!=null || arrayAdapter!=null){
            arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerItem);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    public void getBranch ( final Context context , final Map<String, String> params ) {
        Branch.branchList.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getBranches/" + id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Branch branch = new Branch();
                        branch.setBranchId(jsonObject.getInt("id"));
                        branch.setBranchName(jsonObject.getString("branch_name"));
                        branch.setSportId(jsonObject.getInt("sport_id"));
                        branch.setSportName(jsonObject.getString("sport_name"));

                        Branch.branchList.add(branch);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getBranchByUserId ( final Context context , final Map<String, String> params, final BranchAdapter branchAdapter, RecyclerView rvUserBranch ) {
        Branch.branchList.clear();

        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        final String athlete_id = params.get("athleteID");

        String url = "";
        if(athlete_id == null)url = globalActivity.route("getBranchesByUserId/" + id + "/" + remember_token);
        else url = globalActivity.route("getBranchesByUserId/" + athlete_id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Branch branch = new Branch();
                        branch.setBranchId(jsonObject.getInt("id"));
                        branch.setBranchName(jsonObject.getString("branch_name"));
                        branch.setSportId(jsonObject.getInt("sport_id"));
                        branch.setSportName(jsonObject.getString("sport_name"));

                        Branch.branchList.add(branch);
                        branchAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getBranchByGender (final Context context , final Map<String, String> params, final ArrayList<String> spinnerItem, ArrayAdapter<String> arrayAdapter, final int layoutId, final Spinner spinner ) {
        Branch.branchList.clear();
        if(spinnerItem!=null) spinnerItem.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getBranchesByGender/" + id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Branch branch = new Branch();
                        branch.setBranchId(jsonObject.getInt("id"));
                        branch.setBranchName(jsonObject.getString("branch_name"));
                        branch.setSportId(jsonObject.getInt("sport_id"));
                        branch.setSportName(jsonObject.getString("sport_name"));

                        Branch.branchList.add(branch);
                        if(spinnerItem==null)spinnerItem.add(Branch.branchList.get(j).getBranchName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
        if(layoutId!=0 || spinnerItem!=null || arrayAdapter!=null){
            arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerItem);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    public void getAllEvent ( final Context context , final Map<String, String> params ) {
        Event.eventList.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getEvents/" + id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Event event = new Event();
                        event.setId(String.valueOf(jsonObject.getInt("id")));
                        event.setEvent_name(jsonObject.getString("event_name"));

                        Event.eventList.add(event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void addSport ( final Context context , final HashMap<String, String> params , final SportAdapter adapter ) {
        String url = globalActivity.route("addSport");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                adapter.notifyDataSetChanged();
                globalActivity.toastShort(context , response);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void addBranch ( final Context context , final HashMap<String, String> params , final BranchAdapter adapter ) {
        String url = globalActivity.route("addBranch");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                adapter.notifyDataSetChanged();
                globalActivity.toastShort(context , response);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void getAthlete ( final Context context , final View view , final Map<String, String> params , final AthleteAdapter adapter , final List<User> userList , final SwipeRefreshLayout swipeRefreshLayout , final EditText editText ) {
        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String athleteID = params.get("athleteID");

        String url = globalActivity.route("getAthlete/" + id + "/" + remember_token + athleteID);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                final List<User> tempList = new ArrayList<>();
                userList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String photo = jsonObject.getString("photo");
                        String phone = jsonObject.getString("phone");
                        String birthDate = jsonObject.getString("birthdate");
                        String address = jsonObject.getString("address");
                        String gender = jsonObject.getString("gender");
                        StringBuilder builder = new StringBuilder();
                        JSONArray jsonArray = jsonObject.getJSONArray("branches");

                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject object = jsonArray.getJSONObject(j);
                            String sport_name = object.getJSONObject("sports").getString("sport_name");
                            String branch_name = object.getString("branch_name");
                            String sport_branch = String.format("%s - %s \n" , sport_name , branch_name);
                            builder.append(sport_branch);
                        }

                        User user = new User(id , name , email , photo , phone , birthDate , address , gender , builder.toString());
                        tempList.add(user);

                    } catch (JSONException e) {
                        globalActivity.toastShort(context , e.toString());
                    }
                }

                if (!globalActivity.toStringTrim(editText).isEmpty()) {

                    String query = globalActivity.toStringTrim(editText);

                    for (User user : tempList) {
                        if (user.getName().contains(query)) {
                            userList.add(user);
                        }
                    }
                } else {
                    userList.addAll(tempList);
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {

                    }

                    @Override
                    public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {

                    }

                    @Override
                    public void afterTextChanged ( Editable editable ) {
                        userList.clear();

                        if (!globalActivity.toStringTrim(editText).isEmpty()) {

                            String query = globalActivity.toStringTrim(editText);

                            for (User user : tempList) {
                                if (user.getName().contains(query)) {
                                    userList.add(user);
                                }
                            }
                        } else {
                            userList.addAll(tempList);
                        }
                        adapter.notifyDataSetChanged();

                    }

                });

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                swipeRefreshLayout.setRefreshing(false);

            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }

    public void getBranchBySportGender(final Context context , final Map<String, String> params, final ArrayList<String> spinnerItem, ArrayAdapter<String> arrayAdapter, final int layoutId, final Spinner spinner) {
        Branch.branchList.clear();
        if(spinnerItem!=null) spinnerItem.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String sportName = params.get("sportName");

        String url = globalActivity.route("getBranchesBySportGender/" + id + "/" +sportName +"/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url , new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONArray response ) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Branch branch = new Branch();
                        branch.setBranchId(jsonObject.getInt("id"));
                        branch.setBranchName(jsonObject.getString("branch_name"));
                        branch.setSportId(jsonObject.getInt("sport_id"));
                        branch.setSportName(jsonObject.getString("sport_name"));

                        Branch.branchList.add(branch);
                        if(spinnerItem!=null)spinnerItem.add(Branch.branchList.get(j).getBranchName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
        if(layoutId!=0 || spinnerItem!=null || arrayAdapter!=null){
            arrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_item, spinnerItem);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    public void addBranchUser ( final Context context , final HashMap<String, String> params , final BranchAdapter adapter ) {
        String url = globalActivity.route("addBranchUser");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                adapter.notifyDataSetChanged();
                globalActivity.toastShort(context , response);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return null;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void getEventsForSpinner(Context baseContext, Map<String,String> params, final ArrayList<String> eventSpinnerItem, ArrayAdapter arrayAdapter, int layoutId, Spinner spinner) {
        Event.eventList.clear();
        if (eventSpinnerItem != null) eventSpinnerItem.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getEvents/" + id + "/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(baseContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONArray response) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Event event = new Event();
                        event.setId(jsonObject.getString("id"));
                        event.setEvent_name(jsonObject.getString("event_name"));

                        Event.eventList.add(event);
                        if (eventSpinnerItem != null)
                            eventSpinnerItem.add(Event.eventList.get(j).getEvent_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
        if (layoutId != 0 || eventSpinnerItem != null || arrayAdapter != null) {
            arrayAdapter = new ArrayAdapter<String>(baseContext, R.layout.spinner_item, eventSpinnerItem);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    public void getBranchForSpinner(Context baseContext, HashMap<String,String> params, final ArrayList<String> branchSpinnerItem, ArrayAdapter<String> arrayAdapter, int layoutId, Spinner spinner) {
        Branch.branchList.clear();
        if (branchSpinnerItem != null) branchSpinnerItem.clear();
        String id = params.get("userID");
        String remember_token = params.get("remember_token");
        String sportName = params.get("sportName");

        String url = globalActivity.route("getBranchesBySportGender/" + id + "/" +sportName +"/" + remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(baseContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONArray response) {
                for (int j = 0; j < response.length(); j++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(j);
                        Branch branch = new Branch();
                        branch.setBranchName(jsonObject.getString("branch_name"));

                        Branch.branchList.add(branch);
                        if (branchSpinnerItem != null)
                            branchSpinnerItem.add(Branch.branchList.get(j).getBranchName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
        if (layoutId != 0 || branchSpinnerItem != null || arrayAdapter != null) {
            arrayAdapter = new ArrayAdapter<String>(baseContext, R.layout.spinner_item, branchSpinnerItem);
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }
    }

    public void addAchievement(final Context context , final Map<String, String> params){
        String url = globalActivity.route("addAchievement");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                globalActivity.toastShort(context , response);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.toastShort(context , error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams () {

                return params;
            }

            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(mStringRequest);
    }

    public void editProfile ( final Context context , final View view , final Map<String, String> params ) {

        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
        progressDialog.show();

        String url = globalActivity.route("editProfile");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST , url , parameters , new Response.Listener<JSONObject>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONObject response ) {

                try {
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String password = response.getString("password");
                    String photo = response.getString("photo");
                    String address = response.getString("address");
                    String phone = response.getString("phone");
                    String birthdate = response.getString("birthdate");
                    String userID = response.getString("id");
                    String message = response.getString("message");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference().child("users").child(userID);
                    myRef.child("name").setValue(name);
                    myRef.child("email").setValue(email);
                    myRef.child("password").setValue(password);
                    myRef.child("photo").setValue(photo);
                    myRef.child("address").setValue(address);
                    myRef.child("phone").setValue(phone);
                    myRef.child("birth_date").setValue(birthdate);
                    myRef.push();
                    globalActivity.snackShort(view , message);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }

                progressDialog.dismiss();

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                globalActivity.snackShort(view , error.getMessage());
                progressDialog.dismiss();
            }

        }) {
            @Override
            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }

                return volleyError;
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }


//    public void editProfile ( final Context context , final View view , final Map<String, String> params ) {
//        final ProgressDialog progressDialog = globalActivity.showProgressDialog(context);
//        progressDialog.show();
//
//        String url = globalActivity.route("editProfile");
//        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
//
//        //String Request initialized
//        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
//            @Override
//            public void onResponse ( String response ) {
//                //final String[] split = response.split(";");
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference();
//                globalActivity.snackShort(view , "Success");
//                progressDialog.dismiss();
//            }
//        } , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse ( VolleyError error ) {
//                globalActivity.snackShort(view , error.getMessage());
//                progressDialog.dismiss();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams () {
//
//                return params;
//            }
//
//            @Override
//            protected VolleyError parseNetworkError ( VolleyError volleyError ) {
//                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
//
//                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
//                }
//
//                return volleyError;
//            }
//        };
//
//        mRequestQueue.add(mStringRequest);
//    }

    public void getProfile(final Context context, final View view, final Map<String, String> params){
        String id = params.get("userID");
        String remember_token = params.get("remember_token");

        String url = globalActivity.route("getProfile/" + id + "/"+ remember_token);

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i<response.length();i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        TextView tvUserName = view.findViewById(R.id.tvUserName);
                        TextView tvUserEmail = view.findViewById(R.id.tvUserEmail);
                        ImageView ivProfilePicture;
                        if(view.getId() == R.id.profileActivity) {
                            ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
                            tvUserName.setText(jsonObject.getString("name"));
                            tvUserEmail.setText(jsonObject.getString("email"));
                        }
                        else{
                            ivProfilePicture = view.findViewById(R.id.ivEditPP);
                        }

                        String img = jsonObject.getString("photo");
                        ivProfilePicture.setVisibility(View.VISIBLE);
                        Picasso.get().load(img).into(ivProfilePicture);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    volleyError = new VolleyError(new String(volleyError.networkResponse.data));
                }
                return volleyError;
            }
        };
        mRequestQueue.add(jsonArrayRequest);
    }
}