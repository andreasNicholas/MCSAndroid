package project.aigo.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import project.aigo.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {


    public static void snackShort ( View view , String message ) {
        Snackbar.make(view , message , Snackbar.LENGTH_SHORT).show();
    }

    public static String route ( String uri ) {
        return "https://mobileapi.bslc.or.id/" + uri;
    }

    public static String toStringTrim ( EditText editText ) {
        return editText.getText().toString().trim();
    }

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final Context context = this;

        SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);
        final String name = sp.getString("name" , null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {

                if (name == null) {
                    startActivity(new Intent(context , LoginActivity.class));
                } else {
                    Toast.makeText(context , "Welcome, " + name , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context , HomeActivity.class));
                }
                finish();
            }
        } , 2000);
    }
}
