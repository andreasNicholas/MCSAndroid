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
public class DetailNewsFragment extends Fragment {
    TextView tvName, tvDescription;
    ImageView ivImage;
    Bitmap bitmapContainer;
    Bundle bundle;
    Uri uri;

    public DetailNewsFragment () {
        // Required empty public constructor
    }


    @Override
    public View onCreateView ( LayoutInflater inflater , ViewGroup container ,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_detail_news , container , false);

        tvName = view.findViewById(R.id.tvName);
        tvDescription= view.findViewById(R.id.tvDescription);
        ivImage = view.findViewById(R.id.ivEventImage);

        Bundle bundle = getArguments();
        String[] arrayEvent = bundle.getStringArray("arrayNews");
        String currentEventName = (arrayEvent != null) ? arrayEvent[0] : "";
        String currentEventDescription = (arrayEvent != null) ? arrayEvent[1] : "";
        String currentEventImage = (arrayEvent != null) ? arrayEvent[2] : DEFAULT_IMAGE;
        tvName.setText(currentEventName);
        tvDescription.setText(currentEventDescription);

        Picasso.get().load(currentEventImage).into(ivImage);
        ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivImage.getLayoutParams().height = ((getActivity().getResources().getDisplayMetrics().heightPixels) / 4);
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

            ivImage.setImageBitmap(bitmapContainer);
        }
    }

}
