package project.aigo.myapplication.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.IOException;
import project.aigo.myapplication.R;
import static android.app.Activity.RESULT_OK;
import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailFragment extends Fragment {
    TextView tvEventName, tvEventDescription, tvEventDatetime;
    ImageView ivEventImage;
    View layoutView;
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;

    public EventDetailFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        layoutView = getActivity().findViewById(R.id.eventActivity);

        View view = inflater.inflate(R.layout.fragment_event_detail , container , false);

        tvEventName = view.findViewById(R.id.tvEventName);
        tvEventDescription = view.findViewById(R.id.tvEventDescription);
        tvEventDatetime = view.findViewById(R.id.tvEventDatetime);
        ivEventImage = view.findViewById(R.id.ivEventImage);

        Bundle bundle = getArguments();
        String[] arrayEvent = bundle.getStringArray("arrayEvent");
        String currentEventName = (arrayEvent != null) ? arrayEvent[0] : "";
        String currentEventDescription = (arrayEvent != null) ? arrayEvent[1] : "";
        String currentEventImage = (arrayEvent != null) ? arrayEvent[2] : DEFAULT_IMAGE;
        String currentEventStartDateTime = (arrayEvent != null) ? arrayEvent[3] : "";
        String currentEventEndDateTime = (arrayEvent != null) ? arrayEvent[4] : "";
        String eventDate = String.format("%s - %s" , currentEventStartDateTime , currentEventEndDateTime);
        tvEventName.setText(currentEventName);
        tvEventDescription.setText(currentEventDescription);
        tvEventDatetime.setText(eventDate);

        Picasso.get().load(currentEventImage).into(ivEventImage);
        ivEventImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivEventImage.getLayoutParams().height = ((getActivity().getResources().getDisplayMetrics().heightPixels) / 4);
        return view;
    }

    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            try {
                bitmapContainer = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , result.getUri());

            } catch (IOException e) {
                e.printStackTrace();
            }

            ivEventImage.setImageBitmap(bitmapContainer);
        }
    }

}
