package project.aigo.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import project.aigo.myapplication.Activity.EventCalendarActivity;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.Activity.HomeActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Adapter.EventAdapter;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.Object.News;

import static android.content.Context.MODE_PRIVATE;

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
                globalActivity.toastShort(context , response);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run () {
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

                    Intent intent = new Intent(context , HomeActivity.class);
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
        String limit = (params.get("limit").isEmpty()) ? "" : "/" + params.get("limit");

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

    public void updateOrCreateNews ( final Context context , final View view , final Map<String, String> params , final Fragment fragment ) {
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
                fragment.getFragmentManager().popBackStackImmediate();
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
                    ColorDrawable blue = new ColorDrawable(context.getResources().getColor(R.color.caldroid_sky_blue));
                    Calendar calendar = Calendar.getInstance();
                    Map<Date, Drawable> hm = new HashMap();
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

                            EventAdapter eventAdapter = new EventAdapter(context,eventArrayList,sp.getString("role",""), view);
                            RecyclerView rvEvent = ((EventCalendarActivity) context).findViewById(R.id.rvEventCalendar);
                            rvEvent.setLayoutManager(new LinearLayoutManager(context));
                            rvEvent.setAdapter(eventAdapter);
                            eventAdapter.notifyDataSetChanged();
                            Toast.makeText(context , sdf.format(date) , Toast.LENGTH_SHORT).show();
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

    public void updateOrCreateEvent ( final Context context , final View view , final Map<String, String> params , final Fragment fragment ) {
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
                fragment.getFragmentManager().popBackStackImmediate();
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

}
