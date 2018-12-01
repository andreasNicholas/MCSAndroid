package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import project.aigo.myapplication.Adapter.SportAdapter;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class AddSportActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etSportName;
    private Button btnAddSport;
    private SportAdapter sportAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sport);

        etSportName = findViewById(R.id.etSportName);
        btnAddSport = findViewById(R.id.btnAddSport);

        btnAddSport.setOnClickListener(this);

        Sport sport = new Sport();
        sport.setSportName("TEST");
        Sport.sportList.add(sport);

        recyclerView = findViewById(R.id.rvSport);
        sportAdapter = new SportAdapter(this.getBaseContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sportAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddSport) {
            Sport sport1 = new Sport();
            sport1.setSportName(etSportName.getText().toString());
            Sport.sportList.add(sport1);
            sportAdapter.notifyDataSetChanged();
        }
    }
}
