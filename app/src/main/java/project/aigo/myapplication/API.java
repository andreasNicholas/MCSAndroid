package project.aigo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static project.aigo.myapplication.Activity.SplashScreenActivity.route;
import static project.aigo.myapplication.Activity.SplashScreenActivity.snackShort;

public class API {

    public static void postRegister ( final Context context , final View view , final Map<String, String> params ) {

        String url = route("register");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                snackShort(view , response);
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                snackShort(view , error.getMessage());
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

    public static void postLogin ( final Context context , final View view , final Map<String, String> params, final SharedPreferences sharedPreferences ) {

        String url = route("login");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST , url , parameters , new Response.Listener<JSONObject>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONObject response ) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                snackShort(view , error.getMessage());
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

}
