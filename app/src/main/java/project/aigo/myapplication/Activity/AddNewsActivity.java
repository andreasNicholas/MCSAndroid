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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.R;


public class AddNewsActivity extends GlobalActivity implements View.OnClickListener {
    private EditText etNewsTitle, etNewsContent;
    private ImageView ivNewsImage;
    private Button btnAddNews;
    private String imageExtension = "";
    private Bitmap bitmapContainer;
    private String currentNewsID = "";
    private View layoutView;
    private String addorEdit;
    private TextView textView7;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        textView7 = findViewById(R.id.textView7);
        layoutView = findViewById(R.id.addNewsActivity);
        etNewsTitle = findViewById(R.id.etNewsTitle);
        etNewsContent = findViewById(R.id.etNewsContent);
        btnAddNews = findViewById(R.id.btnAddNews);
        ivNewsImage = findViewById(R.id.ivNewsImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String[] arrayNews = bundle.getStringArray("arrayNews");
            currentNewsID = (arrayNews != null) ? arrayNews[0] : "";
            String currentTitle = (arrayNews != null) ? arrayNews[1] : "";
            String currentDescription = (arrayNews != null) ? arrayNews[2] : "";
            String currentImgSrc = (arrayNews != null) ? arrayNews[3] : DEFAULT_IMAGE;
            currentImgSrc = (currentImgSrc.equals("null")) ? DEFAULT_IMAGE : currentImgSrc;
            Picasso.get().load(currentImgSrc).into(ivNewsImage);
            ivNewsImage.setScaleType(ImageView.ScaleType.FIT_XY);
            ivNewsImage.getLayoutParams().height = ((this.getResources().getDisplayMetrics().heightPixels) / 4);
            etNewsTitle.setText(currentTitle);
            etNewsContent.setText(currentDescription);
        } else {
            Picasso.get().load(DEFAULT_IMAGE).into(ivNewsImage);
        }

        addorEdit = (bundle == null) ? "Add" : "Edit";
        String btnNews = addorEdit.toUpperCase() + " NEWS";
        btnAddNews.setText(btnNews);
        getSupportActionBar().setTitle(addorEdit+" News");
        textView7.setText(addorEdit+" News");

        btnAddNews.setOnClickListener(this);
        ivNewsImage.setOnClickListener(this);
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

            ivNewsImage.setImageBitmap(bitmapContainer);
        }
    }

    @Override
    public void onClick ( View view ) {
        if (view == btnAddNews) {
            callApi();
        } else if (view == ivNewsImage) {
            CropImage.activity().setAspectRatio(3 , 1).start(this);
        }
    }

    private void callApi () {
        final APIManager api = new APIManager();

        String[] getDataforAuthenticate = getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        String newsID = (currentNewsID.isEmpty()) ? "" : currentNewsID;
        String title = toStringTrim(etNewsTitle);
        String description = toStringTrim(etNewsContent);
        String image_base64 = (bitmapContainer == null) ? "" : encodeTobase64(bitmapContainer);

        final Map<String, String> params = new HashMap<>();
        params.put("userID" , id);
        params.put("remember_token" , remember_token);
        params.put("newsID" , newsID);
        params.put("title" , title);
        params.put("description" , description);
        params.put("image_base64" , image_base64);
        params.put("image_ext" , imageExtension);
        String titleAlert = addorEdit + " Confirmation";
        String message = "Are you sure want to " + addorEdit + " ?";
        AlertDialog.Builder builder = createGlobalAlertDialog(this , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                api.updateOrCreateNews(AddNewsActivity.this , layoutView , params);

            }
        });
        builder.show().create();
    }

}