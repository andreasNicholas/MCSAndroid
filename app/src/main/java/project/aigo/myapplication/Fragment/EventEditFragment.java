package project.aigo.myapplication.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.R;

import static android.app.Activity.RESULT_OK;
import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class EventEditFragment extends Fragment implements View.OnClickListener {
    EditText etEventName, etEventDescription, etEventStartDate, etEventStartTime, etEventEndDate, etEventEndTime;
    ImageView ivEventImage;
    Button btnEditEvent;
    String imageExtension = "";
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;
    String currentEventID = "";
    View layoutView;
    String addorEdit;
    GlobalActivity globalActivity;

    public EventEditFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup parent , Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        globalActivity = new GlobalActivity();
        layoutView = getActivity().findViewById(R.id.eventActivity);
        View view = inflater.inflate(R.layout.fragment_event_edit , parent , false);
        etEventName = view.findViewById(R.id.etEventName);
        etEventDescription = view.findViewById(R.id.etEventDescription);
        etEventStartDate = view.findViewById(R.id.etEventStartDate);
        etEventStartTime = view.findViewById(R.id.etEventStartTime);
        etEventEndDate = view.findViewById(R.id.etEventEndDate);
        etEventEndTime = view.findViewById(R.id.etEventEndTime);
        btnEditEvent = view.findViewById(R.id.btnEditEvent);
        ivEventImage = view.findViewById(R.id.ivEventImage);

        bundle = getArguments();
        if (bundle != null) {
            String[] arrayEvent = bundle.getStringArray("arrayEvent");
            currentEventID = (arrayEvent != null) ? arrayEvent[0] : "";
            String currentEventName = (arrayEvent != null) ? arrayEvent[1] : "";
            String currentEventDescription = (arrayEvent != null) ? arrayEvent[2] : "";
            String currentEventImage = (arrayEvent != null) ? arrayEvent[3] : DEFAULT_IMAGE;
            String currentEventStartDate = (arrayEvent != null) ? arrayEvent[4] : "";
            String currentEventStartTime = (arrayEvent != null) ? arrayEvent[5] : "";
            String currentEventEndDate = (arrayEvent != null) ? arrayEvent[6] : "";
            String currentEventEndTime = (arrayEvent != null) ? arrayEvent[7] : "";
            currentEventImage = (currentEventImage.equals("null")) ? DEFAULT_IMAGE : currentEventImage;
            Picasso.get().load(currentEventImage).into(ivEventImage);
            ivEventImage.setScaleType(ImageView.ScaleType.FIT_XY);
            ivEventImage.getLayoutParams().height = ((getActivity().getResources().getDisplayMetrics().heightPixels) / 4);
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

        etEventStartDate.setOnClickListener(this);
        etEventStartTime.setOnClickListener(this);
        etEventEndDate.setOnClickListener(this);
        etEventEndTime.setOnClickListener(this);
        btnEditEvent.setOnClickListener(this);
        ivEventImage.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            imageExtension = globalActivity.getImageName(uri);
            try {
                bitmapContainer = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , result.getUri());

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
            CropImage.activity().setAspectRatio(3 , 1).start(getActivity() , this);
        } else if (view == etEventStartDate) {
            globalActivity.createGlobalDatePickerDialog(getActivity(), "yyyy-MM-dd", etEventStartDate).show();
        } else if (view == etEventStartTime) {
           globalActivity.createGlobalTimePickerDialog(getActivity(), "HH:mm:00", etEventStartTime).show();
        } else if (view == etEventEndDate) {
            globalActivity.createGlobalDatePickerDialog(getActivity(), "yyyy-MM-dd", etEventEndDate).show();
        } else if (view == etEventEndTime){
            globalActivity.createGlobalTimePickerDialog(getActivity(), "HH:mm:00", etEventEndTime).show();
        }
    }

    private void callApi () {
        final APIManager api = new APIManager();

        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        String eventID = (currentEventID.isEmpty()) ? "" : currentEventID;
        String event_name = globalActivity.toStringTrim(etEventName);
        String event_description = globalActivity.toStringTrim(etEventDescription);
        String event_start_date = globalActivity.toStringTrim(etEventStartDate);
        String event_start_time = globalActivity.toStringTrim(etEventStartTime);
        String event_end_date = globalActivity.toStringTrim(etEventEndDate);
        String event_end_time = globalActivity.toStringTrim(etEventEndTime);
        String event_start_datetime = String.format("%s %s" , event_start_date , event_start_time);
        String event_end_datetime = String.format("%s %s" , event_end_date , event_end_time);
        String image_base64 = (bitmapContainer == null) ? "" : globalActivity.encodeTobase64(bitmapContainer);

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
        AlertDialog.Builder builder = globalActivity.createGlobalAlertDialog(getActivity() , eventNameAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                api.updateOrCreateEvent(getActivity() , layoutView , params , EventEditFragment.this);

            }
        });
        builder.show().create();
    }

}