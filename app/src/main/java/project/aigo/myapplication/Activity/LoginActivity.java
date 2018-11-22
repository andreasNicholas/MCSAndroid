package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import project.aigo.myapplication.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogin){

        }
    }

    private void postLogin(){

    }
}
