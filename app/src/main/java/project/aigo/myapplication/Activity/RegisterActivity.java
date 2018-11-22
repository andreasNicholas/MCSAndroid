package project.aigo.myapplication.Activity;

import android.app.DatePickerDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import project.aigo.myapplication.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etEmail, etPassword, etRetypePassword, etPhone, etBirthDate, etAddress;
    RadioButton rdbtnMale, rdbtnFemale;
    CheckBox cbxTerm;
    Button btnRegister;
    ProgressBar pbRegister;
    int nameStat = 0, emailStat = 0, passwordStat = 0, retypePasswordStat = 0, phoneStat = 0, birthdateStat = 0, addressStat = 0, genderStat = 0, termCondStat = 0;
    String pickedGender;
    View view;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRetypePassword = findViewById(R.id.etRetypePassword);
        etPhone = findViewById(R.id.etPhone);
        etBirthDate = findViewById(R.id.etBirthDate);
        etAddress = findViewById(R.id.etAddress);
        rdbtnMale = findViewById(R.id.rdbtnMale);
        rdbtnFemale = findViewById(R.id.rdbtnFemale);
        cbxTerm = findViewById(R.id.cbxTerm);
        btnRegister = findViewById(R.id.btnRegister);
        pbRegister = findViewById(R.id.pbRegister);
        view = findViewById(R.id.registerActivity);

        rdbtnMale.setOnClickListener(this);
        rdbtnFemale.setOnClickListener(this);
        cbxTerm.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newCalendar.set(Calendar.YEAR, year);
                newCalendar.set(Calendar.MONTH, monthOfYear);
                newCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etBirthDate.setText(sdf.format(newCalendar.getTime()));
            }
        };

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, datePickerDialog,
                        newCalendar.get(Calendar.YEAR),
                        newCalendar.get(Calendar.MONTH),
                        newCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

    public String toStringTrim ( EditText editText ) {
        return editText.getText().toString().trim();
    }

    public void reRegisterProgressBar () {
        pbRegister.setProgress(nameStat + emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
    }

    @Override
    protected void onResume () {
        super.onResume();
        genderCondition();

        etName.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                //TERNARY Operator
                //if (toStringTrim(etName).length() <= 0) nameStat = 0; else nameStat = 1;
                nameStat = ((etName.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                emailStat = ((etEmail.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                passwordStat = ((etPassword.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etRetypePassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                retypePasswordStat = ((etRetypePassword.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etPhone.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                phoneStat = ((etPhone.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etBirthDate.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                birthdateStat = ((etBirthDate.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) { }
            @Override
            public void afterTextChanged ( Editable editable ) {
                addressStat = ((etAddress.length() <=0)? 0 : 1);
                reRegisterProgressBar();
            }
        });
    }

    @Override
    public void onClick ( View view ) {
        if (view == rdbtnMale) {
            if (rdbtnMale.isChecked()) {
                if (genderStat == 0) {
                    genderStat = 1;
                    pbRegister.setProgress(pbRegister.getProgress() + 1);
                }
                pickedGender = "Male";
            }
        }
        if (view == rdbtnFemale) {
            if (rdbtnFemale.isChecked()) {
                if (genderStat == 0) {
                    genderStat = 1;
                    pbRegister.setProgress(pbRegister.getProgress() + 1);
                }
                pickedGender = "Female";
            }
        }

        if (view == cbxTerm) {
            if (cbxTerm.isChecked()) {
                termCondStat = 1; pbRegister.setProgress(pbRegister.getProgress() + 1);
            } else {
                termCondStat = 0; pbRegister.setProgress(pbRegister.getProgress() - 1);
            }
        }

        if (view == btnRegister) {

            if (pbRegister.getProgress() == pbRegister.getMax()) {
                postRegister();
//                Intent intent = new Intent(this , LoginActivity.class);
//                startActivity(intent);
            } else {
                Snackbar.make(view , "Please fill all field and check the agreement first!" , Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void genderCondition () {
        if (rdbtnMale.isChecked()) {
            pickedGender = "Male";
            genderStat = 1;
        }
        if (rdbtnFemale.isChecked()) {
            pickedGender = "Female";
            genderStat = 1;
        }
        if (cbxTerm.isChecked())
            termCondStat = 1;
    }

    private void postRegister () {
        //String url = "https://middleware.bslc.or.id/oauth/token";
        String url1 = "https://mobileapi.bslc.or.id/register";

        //RequestQueue initialized
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        //new StringRequest(RequestMethod, url, ToastForResponse, ToastForError)
        StringRequest mStringRequest = new StringRequest(Request.Method.POST , url1 , new Response.Listener<String>() {
            @Override
            public void onResponse ( String response ) {
                Snackbar.make(view , response , Snackbar.LENGTH_SHORT).show();
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse ( VolleyError error ) {
                Snackbar.make(view , error.getMessage() , Snackbar.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams () {
                Map<String, String> params = new HashMap<>();
                params.put("name" , toStringTrim(etName));
                params.put("email" , toStringTrim(etEmail));
                params.put("password" , toStringTrim(etPassword));
                params.put("password_confirmation" , toStringTrim(etRetypePassword));
                params.put("gender" , pickedGender);
                params.put("phone" , toStringTrim(etPhone));
                params.put("birth_date" , toStringTrim(etBirthDate));
                params.put("address" , toStringTrim(etAddress));

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

}
