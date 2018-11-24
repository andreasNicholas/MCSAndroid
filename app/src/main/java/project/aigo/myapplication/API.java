package project.aigo.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
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

import project.aigo.myapplication.Activity.HomeActivity;
import project.aigo.myapplication.Activity.LoginActivity;

import static project.aigo.myapplication.Activity.SplashScreenActivity.route;
import static project.aigo.myapplication.Activity.SplashScreenActivity.snackShort;
import static project.aigo.myapplication.Activity.SplashScreenActivity.toastShort;

public class API {

    public static void postRegister ( final Context context , final View view , final Map<String, String> params ) {

        String url = route("register");
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                snackShort(view , response);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run () {
                        Intent intent = new Intent(context , LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                } , 2000);


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

    public static void postLogin ( final Context context , final View view , final Map<String, String> params , final SharedPreferences sharedPreferences ) {

        String url = route("login");

        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST , url , parameters , new Response.Listener<JSONObject>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse ( JSONObject response ) {

                try {
                    int id = response.getInt("id");
                    String name = response.getString("name");
                    String email = response.getString("email");
                    String gender = response.getString("gender");
                    String role = response.getString("role");
                    String address = response.getString("address");
                    String phone = response.getString("phone");
                    String birthdate = response.getString("birthdate");
                    String remember_token = response.getString("remember_token");

                    String message = "Welcome, " + name;
                    toastShort(context , message);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id" , id);
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
                }

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
