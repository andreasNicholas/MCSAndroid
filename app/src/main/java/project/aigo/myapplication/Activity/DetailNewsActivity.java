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

public class DetailNewsActivity extends AppCompatActivity {

    TextView tvName, tvDescription;
    ImageView ivImage;
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        getSupportActionBar().setTitle("News");
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageSrc = intent.getStringExtra("imageSrc");
        tvName.setText(title);
        tvDescription.setText(description);

        Picasso.get().load(imageSrc).into(ivImage);
        ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivImage.getLayoutParams().height = ((getResources().getDisplayMetrics().heightPixels) / 4);
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

            ivImage.setImageBitmap(bitmapContainer);
        }
    }
}

