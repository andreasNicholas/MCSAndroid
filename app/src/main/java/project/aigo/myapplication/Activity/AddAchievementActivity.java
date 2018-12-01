package project.aigo.myapplication.Activity;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddAchievementActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private EditText etEventName;
    private EditText etEventStartDate;
    private EditText etEventEndDate;
    private EditText etEventDescription;
    private Spinner spinBranch, spinSport;
    private RadioButton rbPos1, rbPos2, rbPos3;
    private Button btnAdd;
    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
        etEventName = findViewById(R.id.etEventName);
        etEventStartDate = findViewById(R.id.etEventStartDate);
        etEventEndDate = findViewById(R.id.etEventEndDate);
        etEventDescription = findViewById(R.id.etEventDescription);
        spinBranch = findViewById(R.id.spinBranch);
        spinSport = findViewById(R.id.spinSport);
        rbPos1 = findViewById(R.id.rbPos1);
        rbPos2 = findViewById(R.id.rbPos2);
        rbPos3 = findViewById(R.id.rbPos3);
        btnAdd = findViewById(R.id.btnAddAchievement);

        etEventStartDate.setOnClickListener(this);
        etEventEndDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        populateSpinner(spinSport);
        spinSport.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAdd){
            Achievement achievement = new Achievement();
                achievement.setEventName(etEventName.getText().toString());
                achievement.setEventStart(Date.valueOf(etEventStartDate.getText().toString()));
                achievement.setEventEnd(Date.valueOf(etEventEndDate.getText().toString()));
                achievement.setSport(spinSport.getSelectedItem().toString());
                achievement.setBranch(spinBranch.getSelectedItem().toString());
                achievement.setDesc(etEventDescription.getText().toString());
                if(rbPos1.isChecked()) achievement.setPosition(1);
                else if(rbPos2.isChecked()) achievement.setPosition(2);
                else if(rbPos3.isChecked()) achievement.setPosition(3);
            Achievement.achievementList.add(achievement);
            this.finish();
        }
        else if(view == etEventStartDate){
            flag = "Start";
            DatePickerFragment startDateFragment = new DatePickerFragment();
            FragmentManager fragmentStartDateManager = getSupportFragmentManager();
            startDateFragment.show(fragmentStartDateManager , "startDatePicker");
        }
        else if(view == etEventEndDate) {
            flag = "End";
            DatePickerFragment endDateFragment = new DatePickerFragment();
            FragmentManager fragmentEndDateManager = getSupportFragmentManager();
            endDateFragment.show(fragmentEndDateManager, "endDatePicker");
        }
    }

    public void populateSpinner(Spinner spinner){
        ArrayList<String> sportArrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, sportArrayList);
        for(int i=0; i< Sport.sportList.size(); i++){
            sportArrayList.add(Sport.sportList.get(i).getSportName());
        }
        spinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth);
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale.US);
        if(flag.equals("Start")){
            etEventStartDate.setText(sdf.format(calendar.getTime()));
        }
        else if(flag.equals("End")){
            etEventEndDate.setText(sdf.format(calendar.getTime()));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ArrayList<String> branchArrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, branchArrayList);
        for (int j = 0; j < Branch.branchList.size(); j++) {
            if (Branch.branchList.get(j).getSportName().equals(spinSport.getItemAtPosition(i).toString())) {
                branchArrayList.add(Branch.branchList.get(j).getBranchName());
            }
            spinBranch.setAdapter(adapter2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
