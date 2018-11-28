package project.aigo.myapplication.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Activity.GlobalActivity;
import project.aigo.myapplication.R;

import static android.app.Activity.RESULT_OK;
import static project.aigo.myapplication.Activity.GlobalActivity.DEFAULT_IMAGE;

public class AddNewsFragment extends Fragment implements View.OnClickListener {
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

    public AddNewsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup parent , Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        layoutView = getActivity().findViewById(R.id.newsActivity);
        View view = inflater.inflate(R.layout.fragment_news_add , parent , false);
        etNewsTitle = view.findViewById(R.id.etNewsTitle);
        etNewsContent = view.findViewById(R.id.etNewsContent);
        btnAddNews = view.findViewById(R.id.btnAddNews);
        ivNewsImage = view.findViewById(R.id.ivNewsImage);

        bundle = getArguments();
        if (bundle != null) {
            String[] arrayNews = bundle.getStringArray("arrayNews");
            currentNewsID = (arrayNews != null) ? arrayNews[0] : "";
            String currentTitle = (arrayNews != null) ? arrayNews[1] : "";
            String currentDescription = (arrayNews != null) ? arrayNews[2] : "";
            String currentImgSrc = (arrayNews != null) ? arrayNews[3] : DEFAULT_IMAGE;
            currentImgSrc = (currentImgSrc.equals("null")) ? DEFAULT_IMAGE : currentImgSrc;
            Picasso.get().load(currentImgSrc).into(ivNewsImage);
            ivNewsImage.setScaleType(ImageView.ScaleType.FIT_XY);
            ivNewsImage.getLayoutParams().height = ((getActivity().getResources().getDisplayMetrics().heightPixels) / 4);
            etNewsTitle.setText(currentTitle);
            etNewsContent.setText(currentDescription);
        } else {
            Picasso.get().load(DEFAULT_IMAGE).into(ivNewsImage);
        }

        addorEdit = (bundle == null) ? "Add" : "Edit";
        String btnNews = addorEdit.toUpperCase() + " NEWS";
        btnAddNews.setText(btnNews);

        btnAddNews.setOnClickListener(this);
        ivNewsImage.setOnClickListener(this);

        return view;
    }


    @Override
    public void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uri = result.getUri();
            imageExtension = getImageName(uri);
            try {
                bitmapContainer = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , result.getUri());

            } catch (IOException e) {
                e.printStackTrace();
            }

            ivNewsImage.setImageBitmap(bitmapContainer);
        }
    }

    private String getImageName ( Uri selectedImage ) {
        String path = selectedImage.getPath();

        return "." + path.substring(Objects.requireNonNull(path).lastIndexOf('.') + 1 , path.length());

    }


    @Override
    public void onClick ( View view ) {
        if (view == btnAddNews) {
            callApi();
        } else if (view == ivNewsImage) {
            CropImage.activity().setAspectRatio(3 , 1).start(getActivity() , this);
        }
    }

    public String encodeTobase64 ( Bitmap image ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG , 100 , baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b , Base64.DEFAULT);
    }

    private void callApi () {
        final APIManager api = new APIManager();

        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(getActivity());
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : "";
        String newsID = (currentNewsID.isEmpty()) ? "" : currentNewsID;
        String title = globalActivity.toStringTrim(etNewsTitle);
        String description = globalActivity.toStringTrim(etNewsContent);
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
        AlertDialog.Builder builder = globalActivity.createGlobalAlertDialog(getActivity() , titleAlert , message);
        builder.setPositiveButton("Yes" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                api.updateOrCreateNews(getActivity() , layoutView , params , AddNewsFragment.this);

            }
        });
        builder.show().create();
    }

}