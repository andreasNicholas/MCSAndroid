package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddUserBranchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Spinner spinSport3, spinBranch;
    private HashMap<String, String> paramsForAthleteBranchBySportGender, paramsForAthleteSport, paramsForAthleteBranch, paramsForAddBranchUser;
    private ArrayAdapter<String> adapterBranch, adapterSport;
    private Button btnAddUserBranch;
    private ArrayList<String> sportSpinnerItem = new ArrayList<String>(), branchSpinnerItem = new ArrayList<String>();
    private RecyclerView rvUserBranch;
    private BranchAdapter branchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_branch);
        //Branch.branchList.clear();

        spinSport3 = findViewById(R.id.spinSport3);
        spinBranch = findViewById(R.id.spinBranch);
        rvUserBranch = findViewById(R.id.rvUserBranch);

        btnAddUserBranch = findViewById(R.id.btnAddUserBranch);
        btnAddUserBranch.setOnClickListener(this);

        branchAdapter = new BranchAdapter(this.getBaseContext());
        mapParamsAthleteBranchAndSport();
        callApiGetBranchAndSport();

        //branchAdapter.notifyDataSetChanged();
        rvUserBranch.setLayoutManager(new LinearLayoutManager(this));
        rvUserBranch.setAdapter(branchAdapter);
        initSpinner();

        spinSport3.setOnItemSelectedListener(this);
    }

    private void initSpinner() {
        sportSpinnerItem.add("--Choose--");
        adapterSport = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, sportSpinnerItem);
        adapterSport.notifyDataSetChanged();
        adapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSport3.setAdapter(adapterSport);

        branchSpinnerItem.add("--Choose--");
        adapterBranch = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, branchSpinnerItem);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBranch.setAdapter(adapterBranch);
    }

    private void mapParamsAthleteBranchAndSport(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAthleteBranch = new HashMap<>();
        paramsForAthleteSport = new HashMap<>();

        paramsForAthleteBranch.put("userID" , id);
        paramsForAthleteBranch.put("remember_token" , remember_token);
        paramsForAthleteSport.put("userID" , id);
        paramsForAthleteSport.put("remember_token" , remember_token);
    }

    private void callApiGetBranchAndSport() {
        APIManager apiManagerBranch = new APIManager();
        APIManager apiManagerSport = new APIManager();
        apiManagerSport.getSport(this.getBaseContext(), paramsForAthleteSport, sportSpinnerItem, adapterSport, android.R.layout.simple_spinner_dropdown_item , spinSport3);
        apiManagerBranch.getBranchByUserId(this.getBaseContext(), paramsForAthleteBranch, branchAdapter, rvUserBranch);
    }

    private void mapParamsAthleteBranchBySportGender(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        String sportName = spinSport3.getSelectedItem().toString();
        paramsForAthleteBranchBySportGender = new HashMap<>();

        paramsForAthleteBranchBySportGender.put("userID" , id);
        paramsForAthleteBranchBySportGender.put("sportName" , sportName);
        paramsForAthleteBranchBySportGender.put("remember_token" , remember_token);
    }

    private void callApiGetBranchBySportGender() {
        APIManager apiManagerBranchByGender = new APIManager();
        apiManagerBranchByGender.getBranchBySportGender(this, paramsForAthleteBranchBySportGender, branchSpinnerItem, adapterBranch, android.R.layout.simple_spinner_dropdown_item, spinBranch);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddUserBranch&&(!spinSport3.getSelectedItem().toString().equals("--Choose--")&&!spinBranch.getSelectedItem().toString().equals("--Choose--"))){
            mapParamsAddBranchUser();
            callApiAddBranchUser();
            //REFRESH BUT FLICKER, FOR BETTER USE A FUNCTION
            Intent intent = getIntent();
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
        }
    }

    private void mapParamsAddBranchUser(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        String branchName = spinBranch.getSelectedItem().toString();
        String sportName = spinSport3.getSelectedItem().toString();
        paramsForAddBranchUser = new HashMap<>();

        paramsForAddBranchUser.put("userID" , id);
        paramsForAddBranchUser.put("branch_name" , branchName);
        paramsForAddBranchUser.put("sport_name" , sportName);
        paramsForAddBranchUser.put("remember_token" , remember_token);
    }

    private void callApiAddBranchUser() {
        APIManager apiManagerAddBranchUser = new APIManager();
        apiManagerAddBranchUser.addBranchUser(this.getBaseContext(), paramsForAddBranchUser, branchAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(spinSport3.getSelectedItem().toString().equals("--Choose--")){
            branchSpinnerItem.clear();
            branchSpinnerItem.add("--Choose--");
            spinBranch.setSelection(0);
        }
        else{
            mapParamsAthleteBranchBySportGender();
            callApiGetBranchBySportGender();

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
}
