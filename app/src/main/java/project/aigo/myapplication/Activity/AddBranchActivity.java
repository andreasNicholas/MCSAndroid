package project.aigo.myapplication.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Adapter.SportAdapter;
import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddBranchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText etBranchName;
    private Button btnAddBranch;
    private BranchAdapter branchAdapter;
    private RecyclerView recyclerView;
    private Spinner spinSport2;
    private HashMap<String, String> paramsForAddBranch, paramsForAthleteBranch, paramsForAthleteSport;
    private ArrayList<String> sportSpinnerItem = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        etBranchName = findViewById(R.id.etBranchName);
        btnAddBranch = findViewById(R.id.btnAddBranch);
        spinSport2 = findViewById(R.id.spinSport2);

        mapParamsAthleteSport();
        callApiGetSport();

        mapParamsAthleteBranch();
        callApiGetBranch();

        btnAddBranch.setOnClickListener(this);

        recyclerView = findViewById(R.id.rvBranch);
        branchAdapter = new BranchAdapter(this.getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(branchAdapter);

        spinSport2.setOnItemSelectedListener(this);
        sportSpinnerItem.add("--Choose--");
        adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_dropdown_item, sportSpinnerItem);
        adapter.notifyDataSetChanged();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSport2.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddBranch) {
            if(spinSport2.getSelectedItemPosition() == 0){}
            else {
                Branch branch1 = new Branch();
                branch1.setSportName(spinSport2.getSelectedItem().toString());
                branch1.setBranchName(etBranchName.getText().toString());
                Branch.branchList.add(branch1);
                branchAdapter.notifyDataSetChanged();
                mapParams();
                callApi();
                spinSport2.setSelection(0);
                etBranchName.setText("");
            }
        }
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String sportName = spinSport2.getSelectedItem().toString();
        int sportId=0;
        for(int i=0; i<Sport.sportList.size();i++){
            if(Sport.sportList.get(i).getSportName().equals(sportName)) {
                sportId = Sport.sportList.get(i).getSportId();
                break;
            }
            else continue;
        }
        String branchName = etBranchName.getText().toString();
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAddBranch = new HashMap<>();

        paramsForAddBranch.put("userID", id);
        paramsForAddBranch.put("remember_token" , remember_token);
        paramsForAddBranch.put("sport_id" , String.valueOf(sportId));
        paramsForAddBranch.put("branch_name" , branchName);
    }

    private void callApi() {
        APIManager apiManageraddBranch = new APIManager();
        apiManageraddBranch.addBranch(this, paramsForAddBranch, branchAdapter);
    }

    private void mapParamsAthleteBranch(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAthleteBranch = new HashMap<>();

        paramsForAthleteBranch.put("userID" , id);
        paramsForAthleteBranch.put("remember_token" , remember_token);
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

    private void callApiGetBranch() {
        APIManager apiManagerBranch = new APIManager();
        apiManagerBranch.getBranch(this, paramsForAthleteBranch);
    }

    private void callApiGetSport() {
        APIManager apiManagerSport = new APIManager();
        apiManagerSport.getSport(this.getBaseContext(), paramsForAthleteSport, sportSpinnerItem, adapter, android.R.layout.simple_spinner_item, spinSport2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
