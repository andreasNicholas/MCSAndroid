package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import java.util.Date;

import project.aigo.myapplication.Object.User;
import project.aigo.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password, retypePassword, phone, birthdate, address;
    RadioButton genderMale, genderFemale;
    CheckBox termcond;
    Button register;
    ProgressBar progressBar;
    int emailStat = 0, passwordStat = 0, retypePasswordStat = 0, phoneStat = 0, birthdateStat = 0, addressStat = 0, genderStat = 0, termCondStat=0;
    String pickedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passET);
        retypePassword = findViewById(R.id.repassET);
        phone = findViewById(R.id.phoneET);
        birthdate = findViewById(R.id.birthET);
        address = findViewById(R.id.addressET);
        genderMale = findViewById(R.id.maleRB);
        genderFemale = findViewById(R.id.femaleRB);
        termcond = findViewById(R.id.termCB);
        register = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);

        genderMale.setOnClickListener(this);
        genderFemale.setOnClickListener(this);
        termcond.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(email.getText().toString().length()<=0) emailStat=0;
                else emailStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(password.getText().length()<=0) passwordStat=0;
                else passwordStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

        retypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(retypePassword.getText().length()<=0) retypePasswordStat=0;
                else retypePasswordStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(phone.getText().length()<=0) phoneStat=0;
                else phoneStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

        birthdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(birthdate.getText().length()<=0) birthdateStat=0;
                else birthdateStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(address.getText().length()<=0) addressStat=0;
                else addressStat = 1;

                progressBar.setProgress(emailStat + passwordStat + retypePasswordStat + addressStat + birthdateStat + phoneStat + genderStat + termCondStat);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == genderMale){
            if(genderMale.isChecked()) {
                if(genderStat == 0) {
                    genderStat = 1;
                    pickedGender = "Male";
                    progressBar.setProgress(progressBar.getProgress() + 1);
                }
            }
        }
        if(view == genderFemale){
            if(genderFemale.isChecked()) {
                if(genderStat == 0) {
                    genderStat = 1;
                    pickedGender = "Female";
                    progressBar.setProgress(progressBar.getProgress() + 1);
                }
            }
        }

        if(view == termcond){
            if(termcond.isChecked()) {
                termCondStat = 1;
                progressBar.setProgress(progressBar.getProgress()+1);
            }
            if(termcond.isChecked() == false) {
                termCondStat = 0;
                progressBar.setProgress(progressBar.getProgress()-1);
            }
        }

        if(view == register){
            //if(progressBar.getProgress() == 8){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                //tryRegister();
           // }
        }
    }

    public void tryRegister(){
        User user = new User();
        user.setUserEmail(email.getText().toString());
        user.setUserPassword(password.getText().toString());
        user.setUserAddress(address.getText().toString());
        user.setUserBirthDate((Date) birthdate.getText());
        user.setUserPhone(phone.getText().toString());
        user.setUserGender(pickedGender);

        User.objUserList.add(user);
    }
}
