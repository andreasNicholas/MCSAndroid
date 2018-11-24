package project.aigo.myapplication.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.UUID;

import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

import static android.app.Activity.RESULT_OK;

public class adminAddNewsFragment extends Fragment {
    EditText etNewsTitle, etNewsImage, etNewsVideo, etNewsContent;
    Button btnAddNews;
    String imgPath;
    String imageResultName = null;
    Bitmap bitmapContainer;
    Fragment fragment = this;

    public adminAddNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_add_news, parent, false);
        etNewsTitle = view.findViewById(R.id.etNewsTitle);
        etNewsImage = view.findViewById(R.id.etNewsImage);
        etNewsVideo = view.findViewById(R.id.etNewsVideo);
        etNewsContent = view.findViewById(R.id.etNewsContent);
        btnAddNews = view.findViewById(R.id.btnAddNews);
        Bundle bundle;

        try {
            bundle = getArguments();
        } catch(Exception e){
            bundle = null;
        }

        if (bundle == null) {
            //IF THERES NO BUNDLE, ITS ADD MODE
            btnAddNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (v == btnAddNews) {
                    //laravel validation for TITLE, IMAGE, AND CONTENT, video is optional
                    //if(true){
                    News news = new News(etNewsTitle.getText().toString(), etNewsImage.getText().toString(), "", etNewsContent.getText().toString(), bitmapContainer);
                    News.newsList.add(news);
                    //sementara data tidak muncul karena di clear dan input ulang di adminNewsViewFragment.java

                    uploadMultipart(imageResultName, imgPath, bitmapContainer);

                    FragmentManager fm = getFragmentManager();
                    android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new adminNewsViewFragment());
                    fragmentTransaction.commit();
                    //else snackbar
                }
                }
            });

            etNewsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Activity activity = getActivity();
                    if (v == etNewsImage) CropImage.activity().setAspectRatio(3,1).start(activity, fragment);
                }
            });

            return view;
        } else {
            //IF THERES NO BUNDLE, EDIT MODE, GET BUNDLE
            btnAddNews.setText("EDIT");

            //SET DATA TO DISPLAY BY GET ID, LATER GET BY DATABASE
            int newsIndex=0;
            for(int i=0; i<News.newsList.size();i++){
                if(News.newsList.get(i).equals(bundle.get("newsTitle").toString())){
                    newsIndex = i;
                    break;
                }
                else continue;
            }

            etNewsTitle.setText(News.newsList.get(newsIndex).getNews_title());
            etNewsImage.setText(News.newsList.get(newsIndex).getImage_name());
            etNewsContent.setText(News.newsList.get(newsIndex).getNews_content());
            //END

            btnAddNews = view.findViewById(R.id.btnAddNews);

            final int finalNewsIndex = newsIndex;
            btnAddNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v == btnAddNews && btnAddNews.getText().toString().equals("EDIT")) {
                        //laravel validation for TITLE, IMAGE, AND CONTENT, video is optional
                        //if(true){
                        News.newsList.elementAt(finalNewsIndex).setNews_title(etNewsTitle.getText().toString());
                        News.newsList.elementAt(finalNewsIndex).setImage_name(etNewsImage.getText().toString());
                        News.newsList.elementAt(finalNewsIndex).setVideo_name("");
                        News.newsList.elementAt(finalNewsIndex).setNews_content(etNewsContent.getText().toString());
                        News.newsList.elementAt(finalNewsIndex).setImage_bitmap(bitmapContainer);
                        //sementara data tidak muncul karena di clear dan input ulang di adminNewsViewFragment.java

                        //UNTUK UPLOAD
                        uploadMultipart(imageResultName, imgPath, bitmapContainer);

                        FragmentManager fm = getFragmentManager();
                        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new adminNewsViewFragment());
                        fragmentTransaction.commit();
                        //else snackbar
                    }
                }
            });

            etNewsImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Activity activity = getActivity();
                    if (v == etNewsImage) CropImage.activity().setAspectRatio(3,1).start(activity, fragment);
                }
            });

            return view;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri selectedImage = result.getUri();
            etNewsImage.setText(getImageName(selectedImage));

            Bitmap bitmap= null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getUri());
                bitmapContainer = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getImageName(Uri selectedImage) {
        if (selectedImage.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                imageResultName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }

        if (imageResultName == null) {
            imageResultName = selectedImage.getPath();
            imgPath = imageResultName;
            imageResultName = imageResultName.substring(imageResultName.lastIndexOf('/')+1, imageResultName.length());
        }
        return imageResultName;
    }

    public void uploadMultipart(String name, String path, Bitmap bitmap) {
        //getting the actual path of the image
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            /*new MultipartUploadRequest(this, uploadId, SyncStateContract.Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload*/

        } catch (Exception exc) {
            Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}