package project.aigo.myapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.aigo.myapplication.Activity.HomeActivity;
import project.aigo.myapplication.Activity.LoginActivity;
import project.aigo.myapplication.Object.Donation;
import project.aigo.myapplication.Object.News;
import project.aigo.myapplication.R;

public class ImageSliderAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds;
    private String[] mImageTitles;
    LayoutInflater layoutInflater;

    public ImageSliderAdapter(Context context){
        mContext = context;
        mImageIds = new int[Donation.donationList.size()];
        for(int i=0; i<mImageIds.length; i++){
            mImageIds[i] = Donation.donationList.get(i).getImage_id();
        }
        mImageTitles = new String[Donation.donationList.size()];
        for(int i=0; i<mImageTitles.length; i++){
            mImageTitles[i] = Donation.donationList.get(i).getDonation_title();
        }
    }

    @Override
    public int getCount() {
        return mImageTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide, container, false);

        ImageView imageSlide = view.findViewById(R.id.ivSlide);
        TextView titleSlide = view.findViewById(R.id.tvSlide);

        imageSlide.setImageResource(mImageIds[position]);
        imageSlide.setScaleType(ImageView.ScaleType.FIT_XY);
        imageSlide.getLayoutParams().height = ((mContext.getResources().getDisplayMetrics().heightPixels)/4);
        titleSlide.setText(mImageTitles[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
