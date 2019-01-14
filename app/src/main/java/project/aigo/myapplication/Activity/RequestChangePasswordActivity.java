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

public class RequestChangePasswordActivity extends GlobalActivity implements View.OnClickListener {
    EditText etEmail;
    Button btnRequest;
    View layoutView;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_change_password);

        layoutView = findViewById(R.id.changePasswordLayout);
        etEmail = findViewById(R.id.etEmail);
        btnRequest = findViewById(R.id.btnRequest);

        btnRequest.setOnClickListener(this);
    }

    @Override
    public void onClick ( View v ) {
        if (v == btnRequest) {
            if (toStringTrim(etEmail).isEmpty()) {
                snackShort(layoutView , "Email must  be filled!");
            } else {
                APIManager apiManager = new APIManager();

                Map<String, String> params = new HashMap<>();
                params.put("email" , toStringTrim(etEmail));

                apiManager.requestChangePassword(this , layoutView , params);
            }
        }
    }
}
