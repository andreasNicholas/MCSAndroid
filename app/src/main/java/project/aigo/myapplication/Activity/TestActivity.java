package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.BranchAdapter;
import project.aigo.myapplication.Object.Branch;
import project.aigo.myapplication.R;

public class TestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BranchAdapter branchAdapter;
    HashMap<String, String> paramsForAthleteBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        recyclerView = findViewById(R.id.rvBranch);
        mapParamsAthleteSportBranch();
        callApiGetBranchByGender();
        branchAdapter = new BranchAdapter(this.getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(branchAdapter);
    }

    private void mapParamsAthleteSportBranch(){
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAthleteBranch = new HashMap<>();

        paramsForAthleteBranch.put("userID" , id);
        paramsForAthleteBranch.put("remember_token" , remember_token);
    }

    private void callApiGetBranchByGender() {
        APIManager apiManagerBranchByGender = new APIManager();
        apiManagerBranchByGender.getBranchByGender(this, paramsForAthleteBranch, null, null, 0, null);
    }
}
