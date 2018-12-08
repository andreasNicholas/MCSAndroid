package project.aigo.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Object.Event;
import project.aigo.myapplication.R;

public class EventCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        Map<String,String> params = new HashMap<>();
        List<Event> eventList = new ArrayList<>();
        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("limit" , "");
        APIManager apiManager = new APIManager();
        View view = findViewById(R.id.eventCalendarActivity);
        apiManager.getEvents(this , view , params,null,eventList,null);

    }
}
