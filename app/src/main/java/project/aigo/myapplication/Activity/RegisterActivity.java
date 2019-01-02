package project.aigo.myapplication.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class RegisterActivity extends GlobalActivity implements View.OnClickListener, TextWatcher, DatePickerDialog.OnDateSetListener {
    EditText etName, etEmail, etPassword, etRetypePassword, etPhone, etBirthDate, etAddress;
    RadioButton rdbtnMale, rdbtnFemale;
    CheckBox cbxTerm;
    Button btnRegister;
    ProgressBar pbRegister;
    int nameStat = 0, emailStat = 0, passwordStat = 0, retypePasswordStat = 0, phoneStat = 0, birthdateStat = 0, addressStat = 0, genderStat = 0, termCondStat = 0;
    String pickedGender;
    View layoutView;

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
        layoutView = findViewById(R.id.registerActivity);
        rdbtnMale.setOnClickListener(this);
        rdbtnFemale.setOnClickListener(this);
        cbxTerm.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        etBirthDate.setOnClickListener(this);

        clickeableComponentCondition();

        etName.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        etRetypePassword.addTextChangedListener(this);
        etPhone.addTextChangedListener(this);
        etBirthDate.addTextChangedListener(this);
        etAddress.addTextChangedListener(this);
    }

    public void showDatePickerDialog ( View v ) {
        DatePickerFragment newFragment = new DatePickerFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment.show(fragmentManager , "datePicker");
    }

    @Override
    public void onDateSet ( DatePicker datePicker , int year , int monthOfYear , int dayOfMonth ) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale.US);
        etBirthDate.setText(sdf.format(calendar.getTime()));
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
        } else if (view == rdbtnFemale) {
            if (rdbtnFemale.isChecked()) {
                if (genderStat == 0) {
                    genderStat = 1;
                    pbRegister.setProgress(pbRegister.getProgress() + 1);
                }
                pickedGender = "Female";
            }
        } else if (view == cbxTerm) {
            if (cbxTerm.isChecked()) {
                termCondStat = 1;
                pbRegister.setProgress(pbRegister.getProgress() + 1);
            } else {
                termCondStat = 0;
                pbRegister.setProgress(pbRegister.getProgress() - 1);
            }
        } else if (view == etBirthDate) {
            showDatePickerDialog(view);
        } else if (view == btnRegister) {

            if (pbRegister.getProgress() == pbRegister.getMax()) {

                callApi();
            } else {
                String message = "Please fill all field and check the agreement first!";
                snackShort(layoutView , message);
            }
        }
    }

    private void clickeableComponentCondition () {
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

    @Override
    public void beforeTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {
    }

    @Override
    public void onTextChanged ( CharSequence charSequence , int i , int i1 , int i2 ) {
    }

    @Override
    public void afterTextChanged ( Editable editable ) {

        nameStat = (toStringTrim(etName).length() <= 0) ? 0 : 1;
        emailStat = (toStringTrim(etEmail).length() <= 0) ? 0 : 1;
        passwordStat = (toStringTrim(etPassword).length() <= 0) ? 0 : 1;
        retypePasswordStat = (toStringTrim(etRetypePassword).length() <= 0) ? 0 : 1;
        phoneStat = (toStringTrim(etPhone).length() <= 0) ? 0 : 1;
        birthdateStat = (toStringTrim(etBirthDate).length() <= 0) ? 0 : 1;
        addressStat = (toStringTrim(etAddress).length() <= 0) ? 0 : 1;

        pbRegister.setProgress(nameStat + emailStat + passwordStat + retypePasswordStat
                + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);

    }

    private void callApi () {

        final Map<String, String> params = new HashMap<>();
        params.put("name" , toStringTrim(etName));
        params.put("email" , toStringTrim(etEmail));
        params.put("password" , toStringTrim(etPassword));
        params.put("password_confirmation" , toStringTrim(etRetypePassword));
        params.put("photo", DEFAULT_IMAGE);
        params.put("gender" , pickedGender);
        params.put("phone" , toStringTrim(etPhone));
        params.put("birth_date" , toStringTrim(etBirthDate));
        params.put("address" , toStringTrim(etAddress));
        String titleAlert = "Message Confirmation";
        String message = "Are you sure want to register?";
        final APIManager apiManager = new APIManager();
        AlertDialog.Builder builder = createGlobalAlertDialog(this , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                apiManager.postRegister(RegisterActivity.this , layoutView , params);
            }
        });
        builder.show().create();
    }
}
