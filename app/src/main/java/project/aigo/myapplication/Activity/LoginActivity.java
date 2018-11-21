package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.aigo.myapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login = findViewById(R.id.loginButton);
        login.setOnClickListener(this);
    }

    public void tryLogin(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("appPref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        editor.putString("email", "");
        editor.putString("password", "");

        editor.commit();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == login){
            tryLogin();
        }
    }
}
