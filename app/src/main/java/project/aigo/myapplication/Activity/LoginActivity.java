package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;
import project.aigo.myapplication.API;
import project.aigo.myapplication.R;
import static project.aigo.myapplication.Activity.SplashScreenActivity.toStringTrim;

public class LoginActivity extends Global implements View.OnClickListener {
    View layoutView;
    Button btnLogin;
    TextView tvRegister;
    EditText etEmail, etPassword;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("spLogin" , MODE_PRIVATE);

        layoutView = findViewById(R.id.loginActivity);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick ( View view ) {
        if (view == btnLogin) {

            Map<String, String> params = new HashMap<>();
            params.put("email" , toStringTrim(etEmail));
            params.put("password" , toStringTrim(etPassword));

            API.postLogin(this , layoutView , params, sharedPreferences);
        } else if (view == tvRegister) {
            Intent intent = new Intent(this , RegisterActivity.class);
            startActivity(intent);
        }
    }



}
