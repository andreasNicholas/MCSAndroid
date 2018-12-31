package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Adapter.SportAdapter;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddSportActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSportName;
    private Button btnAddSport;
    private SportAdapter sportAdapter;
    private RecyclerView recyclerView;
    private HashMap<String, String> paramsForAddSport;
    private HashMap<String, String> paramsForAthleteSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport);

        getSupportActionBar().setTitle("Add Sport");
        mapParamsAthleteSport();
        callApiGetSport();

        etSportName = findViewById(R.id.etSportName);
        btnAddSport = findViewById(R.id.btnAddSport);

        btnAddSport.setOnClickListener(this);

        recyclerView = findViewById(R.id.rvSport);
        sportAdapter = new SportAdapter(this.getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sportAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Sport.sportList.clear();
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddSport) {
            Sport newSport = new Sport();
            newSport.setSportName(etSportName.getText().toString());
            Sport.sportList.add(newSport);
            sportAdapter.notifyDataSetChanged();
            mapParams();
            callApi();
        }
    }

    private void mapParams () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String sportName = etSportName.getText().toString();
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForAddSport = new HashMap<>();

        paramsForAddSport.put("userID", id);
        paramsForAddSport.put("remember_token" , remember_token);
        paramsForAddSport.put("sport_name" , sportName);
    }

    private void callApi() {
        APIManager apiManagerAddSport = new APIManager();
        apiManagerAddSport.addSport(this, paramsForAddSport, sportAdapter);
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
        apiManagerSport.getSport(this, paramsForAthleteSport, null, null, 0, null);
    }
}
