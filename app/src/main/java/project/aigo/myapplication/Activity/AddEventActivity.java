package project.aigo.myapplication.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.R;


public class AddEventActivity extends GlobalActivity implements View.OnClickListener {
    private EditText etEventName, etEventDescription, etEventStartDate, etEventStartTime, etEventEndDate, etEventEndTime;
    private ImageView ivEventImage;
    private Button btnEditEvent;
    private String imageExtension = "";
    private Bitmap bitmapContainer;
    private String currentEventID = "";
    private View layoutView;
    private String addorEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        layoutView = findViewById(R.id.AddEventActivity);
        etEventName = findViewById(R.id.etEventName);
        etEventDescription = findViewById(R.id.etEventDescription);
        etEventStartDate = findViewById(R.id.etEventStartDate);
        etEventStartTime = findViewById(R.id.etEventStartTime);
        etEventEndDate = findViewById(R.id.etEventEndDate);
        etEventEndTime = findViewById(R.id.etEventEndTime);
        btnEditEvent = findViewById(R.id.btnEditEvent);
        ivEventImage = findViewById(R.id.ivEventImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String[] arrayEvent = bundle.getStringArray("arrayEvent");
            currentEventID = (arrayEvent != null) ? arrayEvent[0] : "id";
            String currentEventName = (arrayEvent != null) ? arrayEvent[1] : "event_name";
            String currentEventDescription = (arrayEvent != null) ? arrayEvent[2] : "event_description";
            String currentEventImage = (arrayEvent != null) ? arrayEvent[3] : DEFAULT_IMAGE;
            String currentEventStartDate = (arrayEvent != null) ? arrayEvent[4] : "event_start_date";
            String currentEventStartTime = (arrayEvent != null) ? arrayEvent[5] : "event_start_time";
            String currentEventEndDate = (arrayEvent != null) ? arrayEvent[6] : "event_end_date";
            String currentEventEndTime = (arrayEvent != null) ? arrayEvent[7] : "event_end_time";
            currentEventImage = (currentEventImage.equals("null")) ? DEFAULT_IMAGE : currentEventImage;
            Picasso.get().load(currentEventImage).into(ivEventImage);
            ivEventImage.setScaleType(ImageView.ScaleType.FIT_XY);
            ivEventImage.getLayoutParams().height = ((this.getResources().getDisplayMetrics().heightPixels) / 4);
            etEventName.setText(currentEventName);
            etEventDescription.setText(currentEventDescription);
            etEventStartDate.setText(currentEventStartDate);
            etEventStartTime.setText(currentEventStartTime);
            etEventEndDate.setText(currentEventEndDate);
            etEventEndTime.setText(currentEventEndTime);
        } else {
            Picasso.get().load(DEFAULT_IMAGE).into(ivEventImage);
        }

        addorEdit = (bundle == null) ? "Add" : "Edit";
        String btnEvent = addorEdit.toUpperCase() + " EVENT";
        btnEditEvent.setText(btnEvent);
        getSupportActionBar().setTitle(addorEdit+" Event");

        etEventStartDate.setOnClickListener(this);
        etEventStartTime.setOnClickListener(this);
        etEventEndDate.setOnClickListener(this);
        etEventEndTime.setOnClickListener(this);
        btnEditEvent.setOnClickListener(this);
        ivEventImage.setOnClickListener(this);
    }

    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri uri = result.getUri();
            imageExtension = getImageName(uri);
            try {
                bitmapContainer = MediaStore.Images.Media.getBitmap(this.getContentResolver() , result.getUri());

            } catch (IOException e) {
                e.printStackTrace();
            }

            ivEventImage.setImageBitmap(bitmapContainer);
        }
    }

    @Override
    public void onClick ( View view ) {
        if (view == btnEditEvent) {
            callApi();
        } else if (view == ivEventImage) {
            CropImage.activity().setAspectRatio(3 , 1).start(this);
        } else if (view == etEventStartDate) {
            createGlobalDatePickerDialog(this, "yyyy-MM-dd", etEventStartDate).show();
        } else if (view == etEventStartTime) {
            createGlobalTimePickerDialog(this, "HH:mm:00", etEventStartTime).show();
        } else if (view == etEventEndDate) {
            createGlobalDatePickerDialog(this, "yyyy-MM-dd", etEventEndDate).show();
        } else if (view == etEventEndTime){
            createGlobalTimePickerDialog(this, "HH:mm:00", etEventEndTime).show();
        }
    }

    private void callApi () {
        final APIManager api = new APIManager();

        String[] getDataforAuthenticate = getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        String eventID = (currentEventID.isEmpty()) ? "" : currentEventID;
        String event_name = toStringTrim(etEventName);
        String event_description = toStringTrim(etEventDescription);
        String event_start_date = toStringTrim(etEventStartDate);
        String event_start_time = toStringTrim(etEventStartTime);
        String event_end_date = toStringTrim(etEventEndDate);
        String event_end_time = toStringTrim(etEventEndTime);
        String event_start_datetime = String.format("%s %s" , event_start_date , event_start_time);
        String event_end_datetime = String.format("%s %s" , event_end_date , event_end_time);
        String image_base64 = (bitmapContainer == null) ? "" : encodeTobase64(bitmapContainer);

        final Map<String, String> params = new HashMap<>();
        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("eventID" , eventID);
        params.put("event_name" , event_name);
        params.put("event_description" , event_description);
        params.put("event_start_datetime" , event_start_datetime);
        params.put("event_end_datetime" , event_end_datetime);
        params.put("image_base64" , image_base64);
        params.put("image_ext" , imageExtension);
        String eventNameAlert = addorEdit + " Confirmation";
        String message = "Are you sure want to " + addorEdit + " ?";
        AlertDialog.Builder builder = createGlobalAlertDialog(this , eventNameAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                api.updateOrCreateEvent(AddEventActivity.this , layoutView , params);

            }
        });
        builder.show().create();
    }

}