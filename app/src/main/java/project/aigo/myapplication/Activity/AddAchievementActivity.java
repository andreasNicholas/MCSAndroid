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
import project.aigo.myapplication.Adapter.EventAdapter;
import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.Fragment.ProfileCompStatFragment;
import project.aigo.myapplication.Object.Achievement;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddAchievementActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private EditText etAchievementDesc;
    private Spinner spinBranch, spinSport, spinEvent;
    private RadioButton rbPos1, rbPos2, rbPos3;
    private Button btnAdd;
    private ArrayList<String> sportSpinnerItem = new ArrayList<String>();
    private ArrayList<String> eventSpinnerItem = new ArrayList<String>();
    private ArrayList<String> branchSpinnerItem = new ArrayList<String>();
    private ArrayAdapter<String> adapterBranch, adapterSport, adapter;
    private Map<String, String> params;
    private HashMap<String, String> paramsForAthleteSport, paramsForAthleteBranch, paramsForAddAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Add Achievement");
        setContentView(R.layout.activity_add_achievement);
        etAchievementDesc = findViewById(R.id.etAchievementDesc);
        spinEvent = findViewById(R.id.spinEvent);
        spinBranch = findViewById(R.id.spinBranch);
        spinSport = findViewById(R.id.spinSport);
        rbPos1 = findViewById(R.id.rbPos1);
        rbPos2 = findViewById(R.id.rbPos2);
        rbPos3 = findViewById(R.id.rbPos3);
        btnAdd = findViewById(R.id.btnAddAchievement);

        mapParams();
        callApi();

        mapParamsAthleteSport();
        callApiGetSport();

        btnAdd.setOnClickListener(this);

        initSpinner();
        spinSport.setOnItemSelectedListener(this);
    }

    private void initSpinner() {
        eventSpinnerItem.add("--Choose--");
        adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, eventSpinnerItem);
        adapter.notifyDataSetChanged();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEvent.setAdapter(adapter);

        sportSpinnerItem.add("--Choose--");
        adapterSport = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, sportSpinnerItem);
        adapterSport.notifyDataSetChanged();
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSport.setAdapter(adapterSport);

        branchSpinnerItem.add("--Choose--");
        spinBranch.setSelection(0);
        adapterBranch = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, branchSpinnerItem);
        adapterBranch.notifyDataSetChanged();
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBranch.setAdapter(adapterBranch);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAdd){
            mapParamsAddAchievement();
            callApiAddAchievement();

            etAchievementDesc.setText(null);
            spinEvent.setSelection(0);
            spinSport.setSelection(0);

            branchSpinnerItem.clear();
            branchSpinnerItem.add("--Choose--");
            spinBranch.setSelection(0);

            rbPos1.setChecked(false);
            rbPos2.setChecked(false);
            rbPos3.setChecked(false);
        }
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        params = new HashMap<>();

        params.put("userID" , id);
        params.put("remember_token" , remember_token);
    }

    private void callApi () {
        APIManager apiManager = new APIManager();
        apiManager.getEventsForSpinner(this.getBaseContext(), params , eventSpinnerItem, adapter , android.R.layout.simple_spinner_item , spinEvent);
    }

    private void mapParamsAthleteSport(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAthleteSport = new HashMap<>();

        paramsForAthleteSport.put("userID" , id);
        paramsForAthleteSport.put("remember_token" , remember_token);
    }

    private void callApiGetSport() {
        APIManager apiManagerSport = new APIManager();
        apiManagerSport.getSport(this.getBaseContext(), paramsForAthleteSport, sportSpinnerItem, adapterSport, android.R.layout.simple_spinner_dropdown_item , spinSport);
    }

    private void mapParamsAthleteBranch(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        String sportName = spinSport.getSelectedItem().toString();
        paramsForAthleteBranch = new HashMap<>();

        paramsForAthleteBranch.put("userID" , id);
        paramsForAthleteBranch.put("sportName" , sportName);
        paramsForAthleteBranch.put("remember_token" , remember_token);
    }

    private void callApiGetBranch() {
        APIManager apiManagerBranch = new APIManager();
        apiManagerBranch.getBranchForSpinner(this.getBaseContext(), paramsForAthleteBranch, branchSpinnerItem, adapterBranch, android.R.layout.simple_spinner_dropdown_item , spinBranch);
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
        if(!spinSport.getSelectedItem().equals("--Choose--")){
            mapParamsAthleteBranch();
            callApiGetBranch();

            branchSpinnerItem.add("--Choose--");
            adapterBranch = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, branchSpinnerItem);
            adapterBranch.notifyDataSetChanged();
            adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinBranch.setAdapter(adapterBranch);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void mapParamsAddAchievement() {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        String sportName = spinSport.getSelectedItem().toString();
        String eventName = spinEvent.getSelectedItem().toString();
        String branchName = spinBranch.getSelectedItem().toString();
        String desc = etAchievementDesc.getText().toString();
        String position = null;
        if(rbPos1.isChecked()) position = "1";
        else if(rbPos2.isChecked()) position = "2";
        else if(rbPos3.isChecked()) position = "3";

        paramsForAddAchievement = new HashMap<>();

        paramsForAddAchievement.put("userID" , id);
        paramsForAddAchievement.put("sportName" , sportName);
        paramsForAddAchievement.put("eventName" , eventName);
        paramsForAddAchievement.put("branchName" , branchName);
        paramsForAddAchievement.put("desc" , desc);
        paramsForAddAchievement.put("position" , position);
        paramsForAddAchievement.put("remember_token" , remember_token);
    }

    private void callApiAddAchievement() {
        APIManager apiManagerAddAchievement = new APIManager();
        apiManagerAddAchievement.addAchievement(this.getBaseContext(), paramsForAddAchievement);
    }
}
