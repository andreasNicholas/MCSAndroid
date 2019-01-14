package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.R;

public class ChangePasswordActivity extends GlobalActivity {

    EditText etPassword, etPasswordConf, etNewPassword;
    Button btnChange;
    View view;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etPassword = findViewById(R.id.etPassword);
        etPasswordConf = findViewById(R.id.etPasswordConf);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnChange = findViewById(R.id.btnChange);
        view = findViewById(R.id.changePasswLayout);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick ( View v ) {
                if (toStringTrim(etPassword).isEmpty() || toStringTrim(etPasswordConf).isEmpty() || toStringTrim(etNewPassword).isEmpty())
                    snackShort(view , "All field must be filled");
                else if (!toStringTrim(etPassword).equals(toStringTrim(etPasswordConf)))
                    snackShort(view , "Confirmation password doesn't match");
                else {
                    String[] getDataforAuthenticate = getDataforAuthenticate(ChangePasswordActivity.this);
                    String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
                    String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";

                    Map<String, String> params = new HashMap<>();
                    params.put("id" , id);
                    params.put("remember_token" , remember_token);
                    params.put("password", toStringTrim(etPassword));
                    params.put("passwordConf", toStringTrim(etPasswordConf));
                    params.put("newPassword", toStringTrim(etNewPassword));
                    APIManager apiManager = new APIManager();
                    apiManager.changePassword(ChangePasswordActivity.this , view , params);
                }


            }
        });
    }
}
