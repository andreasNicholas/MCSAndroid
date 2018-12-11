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

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Adapter.SportAdapter;
import project.aigo.myapplication.Fragment.DatePickerFragment;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddBranchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etBranchName;
    private Button btnAddBranch;
    private BranchAdapter branchAdapter;
    private RecyclerView recyclerView;
    private Spinner spinSport2;
    private HashMap<String, String> paramsForAddBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);

        etBranchName = findViewById(R.id.etBranchName);
        btnAddBranch = findViewById(R.id.btnAddBranch);
        spinSport2 = findViewById(R.id.spinSport2);

        btnAddBranch.setOnClickListener(this);

        recyclerView = findViewById(R.id.rvBranch);
        branchAdapter = new BranchAdapter(this.getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(branchAdapter);

        ArrayList<String> sportSpinnerItem = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getBaseContext(), android.R.layout.simple_spinner_item, sportSpinnerItem);
        for(int i=0; i< Sport.sportList.size(); i++){
            sportSpinnerItem.add(Sport.sportList.get(i).getSportName());
        }
        spinSport2.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddBranch) {
            Branch branch1 = new Branch();
            branch1.setSportName(spinSport2.getSelectedItem().toString());
            branch1.setBranchName(etBranchName.getText().toString());
            Branch.branchList.add(branch1);
            branchAdapter.notifyDataSetChanged();
            mapParams();
            callApi();
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
}
