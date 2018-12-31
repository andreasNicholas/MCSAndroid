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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;

import project.aigo.myapplication.APIManager;
import project.aigo.myapplication.R;

public class EditProfileActivity extends GlobalActivity implements View.OnClickListener {
    private EditText etEditEmail, etEditPassword, etEditAddress, etEditPhone, etEditDOB, etEditName;
    private ImageView ivEditPP;
    private String imageExtension = "";
    private Bitmap bitmapContainer;
    RoundedBitmapDrawable roundedBitmapDrawable;

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
        ivEditPP = (ImageView)findViewById(R.id.ivEditPP);

        final SharedPreferences sp = this.getSharedPreferences("spLogin" , MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run () {
                etEditName.setText(sp.getString("name" , null));
                etEditEmail.setText(sp.getString("email" , null));
                etEditAddress.setText(sp.getString("address" , null));
                etEditPhone.setText(sp.getString("phone" , null));
                etEditDOB.setText(sp.getString("birthdate" , null));
            }
        } , 0);

        ivEditPP.setOnClickListener(this);
        etEditDOB.setOnClickListener(this);
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
            ivEditPP.setImageDrawable(roundedBitmapDrawable);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == ivEditPP)        CropImage.activity().setAspectRatio(1 , 1).start(this);
        else if(view == etEditDOB)  createGlobalDatePickerDialog(this, "yyyy-MM-dd", etEditDOB).show();
    }
}
