package project.aigo.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.Object.Sport;
import project.aigo.myapplication.R;

public class EditProfileActivity extends GlobalActivity implements View.OnClickListener {
    private EditText etEditEmail, etEditPassword, etEditAddress, etEditPhone, etEditDOB, etEditName;
    private ImageView ivEditPP;
    private Button btnEditProfile;
    private String imageExtension = "";
    private Bitmap bitmapContainer;
    private RoundedBitmapDrawable roundedBitmapDrawable;
    private HashMap<String, String> paramsForEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setTitle("Edit My Profile");

        etEditEmail = findViewById(R.id.etEditEmail);
        etEditPassword = findViewById(R.id.etEditPassword);
        etEditAddress = findViewById(R.id.etEditAddress);
        etEditPhone = findViewById(R.id.etEditPhone);
        etEditDOB = findViewById(R.id.etEditDOB);
        etEditName = findViewById(R.id.etEditName);
        ivEditPP = findViewById(R.id.ivEditPP);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        mapParamsGetProfile();
        callApiGetProfile();

        final SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {
                etEditName.setText(sp.getString("name", null));
                etEditEmail.setText(sp.getString("email", null));
                etEditAddress.setText(sp.getString("address", null));
                etEditPhone.setText(sp.getString("phone", null));
                etEditDOB.setText(sp.getString("birthdate", null));
                /*
                if (sp.getString("profilepicture", null) != (null)){
                    bitmapContainer = decodeBase64(sp.getString("profilepicture", null));
                    roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapContainer);
                    roundedBitmapDrawable.setCircular(true);
                    ivEditPP.setImageDrawable(roundedBitmapDrawable);
                }*/
            }
        } , 0);

        ivEditPP.setOnClickListener(this);
        etEditDOB.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
    }

    private void mapParamsGetProfile() {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        paramsForEditProfile = new HashMap<>();

        paramsForEditProfile.put("userID", id);
        paramsForEditProfile.put("remember_token" , remember_token);
    }

    private void callApiGetProfile() {
        APIManager apiManageraddBranch = new APIManager();
        apiManageraddBranch.getProfile(this, this.findViewById(R.id.editProfileActivity) ,paramsForEditProfile);
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
                roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmapContainer);
                roundedBitmapDrawable.setCircular(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivEditPP.setImageBitmap(bitmapContainer);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == ivEditPP)        CropImage.activity().setAspectRatio(1 , 1).start(this);
        else if(view == etEditDOB)  createGlobalDatePickerDialog(this, "yyyy-MM-dd", etEditDOB).show();
        else if(view == btnEditProfile){
            mapParamsEditProfile();
            callApiEditProfile();

            final SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("profilepicture", encodeTobase64(bitmapContainer));
            editor.commit();
            ivEditPP.setImageBitmap(bitmapContainer);
        }
    }

    private void mapParamsEditProfile () {
        GlobalActivity globalActivity = new GlobalActivity();
        String[] getDataforAuthenticate = globalActivity.getDataforAuthenticate(this);
        String id = getDataforAuthenticate != null ? getDataforAuthenticate[0] : "";
        String remember_token = getDataforAuthenticate != null ? getDataforAuthenticate[1] : null;
        String name = etEditName.getText().toString();
        String email = etEditEmail.getText().toString();
        String password = etEditPassword.getText().toString();
        String address = etEditAddress.getText().toString();
        String phone = etEditPhone.getText().toString();
        String birthdate = etEditDOB.getText().toString();
        paramsForEditProfile = new HashMap<>();

        paramsForEditProfile.put("userID", id);
        paramsForEditProfile.put("remember_token" , remember_token);
        paramsForEditProfile.put("name" , name);
        paramsForEditProfile.put("email" , email);
        paramsForEditProfile.put("password" , password);
        paramsForEditProfile.put("address" , address);
        paramsForEditProfile.put("phone" , phone);
        paramsForEditProfile.put("birthdate" , birthdate);
        paramsForEditProfile.put("profilepicture", encodeTobase64(bitmapContainer));
        paramsForEditProfile.put("extension", imageExtension);
    }

    private void callApiEditProfile() {
        APIManager apiManagerEditProfile = new APIManager();
        apiManagerEditProfile.editProfile(this, this.findViewById(R.id.editProfileActivity) ,paramsForEditProfile);
    }
}
