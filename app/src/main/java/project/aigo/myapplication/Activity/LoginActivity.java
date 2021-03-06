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

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.R;

public class LoginActivity extends GlobalActivity implements View.OnClickListener {
    View layoutView;
    Button btnLogin;
    TextView tvRegister, tvForgotPassword;
    EditText etEmail, etPassword;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);

        sharedPreferences = getSharedPreferences("spLogin" , MODE_PRIVATE);

        layoutView = findViewById(R.id.loginActivity);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick ( View view ) {

        if (view == btnLogin) {

            Map<String, String> params = new HashMap<>();
            params.put("email" , toStringTrim(etEmail));
            params.put("password" , toStringTrim(etPassword));

            APIManager api = new APIManager();

            api.postLogin(this , layoutView , params, sharedPreferences);
            //Intent intent = new Intent(this , EventActivity.class);
            //startActivity(intent);
        } else if (view == tvRegister) {
            Intent intent = new Intent(this , RegisterActivity.class);
            startActivity(intent);
        } else if (view == tvForgotPassword){
            Intent intent = new Intent(this, RequestChangePasswordActivity.class);
            startActivity(intent);
        }
    }



}
