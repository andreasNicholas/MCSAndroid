package project.aigo.myapplication.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddAchievementActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private EditText etEventDescription;
    private Spinner spinBranch, spinSport, spinEvent;
    private RadioButton rbPos1, rbPos2, rbPos3;
    private Button btnAdd;
    private ArrayList<String> sportSpinnerItem = new ArrayList<String>();
    private ArrayList<String> eventSpinnerItem = new ArrayList<String>();
    private ArrayList<String> branchSpinnerItem = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievement);
        etEventDescription = findViewById(R.id.etEventDescription);
        spinEvent = findViewById(R.id.spinEvent);
        spinBranch = findViewById(R.id.spinBranch);
        spinSport = findViewById(R.id.spinSport);
        rbPos1 = findViewById(R.id.rbPos1);
        rbPos2 = findViewById(R.id.rbPos2);
        rbPos3 = findViewById(R.id.rbPos3);
        btnAdd = findViewById(R.id.btnAddAchievement);

        btnAdd.setOnClickListener(this);

        populateSpinnerEvent(spinEvent);
        populateSpinnerSport(spinSport);
        spinSport.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAdd){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);


            /*Achievement achievement = new Achievement();
                achievement.setEventName(spinEvent.getSelectedItem().toString());
                //achievement.setEventStart(Date.valueOf(etEventStartDate.getText().toString()));
                //achievement.setEventEnd(Date.valueOf(etEventEndDate.getText().toString()));
                achievement.setSport(spinSport.getSelectedItem().toString());
                achievement.setBranch(spinBranch.getSelectedItem().toString());
                achievement.setDesc(etEventDescription.getText().toString());
                if(rbPos1.isChecked()) achievement.setPosition(1);
                else if(rbPos2.isChecked()) achievement.setPosition(2);
                else if(rbPos3.isChecked()) achievement.setPosition(3);
            Achievement.achievementList.add(achievement);*/
        }
    }

    private void populateSpinnerEvent(Spinner spinner) {
        for(int i = 0; i< Event.eventList.size(); i++){
            eventSpinnerItem.add(Event.eventList.get(i).getEvent_name());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, eventSpinnerItem);
        spinner.setAdapter(adapter);
    }

    public void populateSpinnerSport(Spinner spinner){
        for(int i=0; i< Sport.sportList.size(); i++){
            sportSpinnerItem.add(Sport.sportList.get(i).getSportName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, sportSpinnerItem);
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
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        branchSpinnerItem.clear();
        for(int k=0; k< Branch.branchList.size(); k++){
            if(Branch.branchList.get(k).getSportName().equals(sportSpinnerItem.get(i)))
            {
                branchSpinnerItem.add(Branch.branchList.get(k).getBranchName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, branchSpinnerItem);
        spinBranch.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
