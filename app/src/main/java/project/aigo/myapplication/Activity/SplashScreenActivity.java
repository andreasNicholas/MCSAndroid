package project.aigo.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import project.aigo.myapplication.R;

public class SplashScreenActivity extends GlobalActivity {

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
                    toastShort(context, "Welcome, " + name);
                    startActivity(new Intent(context , MainActivity.class));
                }
                finish();
            }
        } , 2000);
    }
}
