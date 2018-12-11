package project.aigo.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class AddNewsActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etNewsTitle, etNewsContent;
    ImageView ivNewsImage;
    Button btnAddNews;
    String imageExtension = "";
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;
    String currentNewsID = "";
    View layoutView;
    String addorEdit;
    GlobalActivity globalActivity;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        // Inflate the layout for this fragment
        etNewsTitle = findViewById(R.id.etNewsTitle);
        etNewsContent = findViewById(R.id.etNewsContent);
        btnAddNews = findViewById(R.id.btnAddNews);
        ivNewsImage = findViewById(R.id.ivNewsImage);

        bundle = getIntent().getExtras();
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
            GlobalActivity globalActivity = new GlobalActivity();
            globalActivity.snackShort(this.ivNewsImage, "THIS IS EDIT");
        } else {
            Picasso.get().load(DEFAULT_IMAGE).into(ivNewsImage);
            GlobalActivity globalActivity = new GlobalActivity();
            globalActivity.toastShort(this.getBaseContext(), "THIS IS NOT EDIT");
        }

        addorEdit = (bundle == null) ? "Add" : "Edit";
        String btnNews = addorEdit.toUpperCase() + " NEWS";
        btnAddNews.setText(btnNews);

        btnAddNews.setOnClickListener(this);
        ivNewsImage.setOnClickListener(this);
    }

    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            imageExtension = globalActivity.getImageName(uri);
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

        globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        String newsID = (currentNewsID.isEmpty()) ? "" : currentNewsID;
        String title = globalActivity.toStringTrim(etNewsTitle);
        String description = globalActivity.toStringTrim(etNewsContent);
        String image_base64 = (bitmapContainer == null) ? "" : globalActivity.encodeTobase64(bitmapContainer);

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
        AlertDialog.Builder builder = globalActivity.createGlobalAlertDialog(this , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                api.updateOrCreateNews(activity, layoutView , params);

            }
        });
        builder.show().create();
    }

}