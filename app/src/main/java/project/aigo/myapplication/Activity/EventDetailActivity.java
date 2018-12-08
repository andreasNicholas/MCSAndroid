package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

import project.aigo.myapplication.R;

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class EventDetailActivity extends AppCompatActivity {
    TextView tvEventName, tvEventDescription, tvEventDatetime;
    ImageView ivEventImage;
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        tvEventName = findViewById(R.id.tvEventName);
        tvEventDescription = findViewById(R.id.tvEventDescription);
        tvEventDatetime = findViewById(R.id.tvEventDatetime);
        ivEventImage = findViewById(R.id.ivEventImage);

        Intent intent = getIntent();

        String currentEventName = intent.getStringExtra("event_name");
        String currentEventDescription = intent.getStringExtra("event_description");
        String currentEventImage = intent.getStringExtra("imageSrc");
        String currentEventStartDateTime = intent.getStringExtra("event_start_datetime");
        String currentEventEndDateTime = intent.getStringExtra("event_end_datetime");
        String eventDate = String.format("%s - %s" , currentEventStartDateTime , currentEventEndDateTime);
        tvEventName.setText(currentEventName);
        tvEventDescription.setText(currentEventDescription);
        tvEventDatetime.setText(eventDate);

        Picasso.get().load(currentEventImage).into(ivEventImage);
        ivEventImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivEventImage.getLayoutParams().height = ((getResources().getDisplayMetrics().heightPixels) / 4);
    }

    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            try {
                bitmapContainer = MediaStore.Images.Media.getBitmap(getContentResolver() , result.getUri());

            } catch (IOException e) {
                e.printStackTrace();
            }

            ivEventImage.setImageBitmap(bitmapContainer);
        }
    }
}
