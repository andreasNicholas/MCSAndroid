package project.aigo.myapplication.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import project.aigo.myapplication.Fragment.DatePickerFragment;

public class GlobalActivity extends AppCompatActivity {

    public static final String DEFAULT_IMAGE = "https://via.placeholder.com/400x400";
    protected static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    protected long mBackPressed;

    public String toStringTrim ( EditText editText ) {
        return editText.getText().toString().trim();
    }

    public void snackShort ( View view , String message ) {
        Snackbar.make(view , message , Snackbar.LENGTH_SHORT).show();
    }

    public void toastShort ( Context context , String message ) {
        Toast.makeText(context , message , Toast.LENGTH_SHORT).show();
    }

    public String route ( String uri ) {
        return "https://mobileapi.bslc.or.id/" + uri;
    }

    @Override
    public void onBackPressed () {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            toastShort(getBaseContext() , "Tap back button in order to exit");
        }

        mBackPressed = System.currentTimeMillis();
    }

    public void loadFragment ( Fragment fragment , int view , Context context , Bundle bundle , String backStack ) {
        if (bundle != null) fragment.setArguments(bundle);
        FragmentManager fm = ((AppCompatActivity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(view , fragment , String.valueOf(fragment.getId()));
        if (backStack != null) fragmentTransaction.addToBackStack(backStack);
        fragmentTransaction.commit();
    }

    public String[] getDataforAuthenticate ( Context context ) {
        SharedPreferences sp = context.getSharedPreferences("spLogin" , MODE_PRIVATE);

        final String id = sp.getString("id" , "");
        final String remember_token = sp.getString("remember_token" , "");

        return new String[]{id , remember_token};
    }

    public String getRole ( Context context ) {
        SharedPreferences sp = context.getSharedPreferences("spLogin" , MODE_PRIVATE);

        return sp.getString("role" , "");
    }

    public ProgressDialog showProgressDialog ( Context context , String[]... array ) {
        ProgressDialog progressDialog = new ProgressDialog(context);

        String title = (array.length == 0) ? "Loading..." : String.valueOf(array[0]);
        String message = (array.length == 0) ? "Please Wait" : String.valueOf(array[1]);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);


        return progressDialog;
    }

    public AlertDialog.Builder createGlobalAlertDialog ( Context context , String title , String message ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("No" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick ( DialogInterface dialogInterface , int i ) {
                dialogInterface.dismiss();
            }
        });

        return builder;
    }

    public String encodeTobase64 ( Bitmap image ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG , 100 , baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b , Base64.DEFAULT);
    }

    public DatePickerDialog createGlobalDatePickerDialog ( Context context, final String format, final EditText editText ){
        final Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(context , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet ( DatePicker datePicker , int i , int i1 , int i2 ) {
                calendar.set(Calendar.YEAR , i);
                calendar.set(Calendar.MONTH , i1);
                calendar.set(Calendar.DAY_OF_MONTH , i2);
                SimpleDateFormat sdf = new SimpleDateFormat(format , Locale.US);
                editText.setText(sdf.format(calendar.getTime()));
            }
        }, calendar
                .get(Calendar.YEAR) , calendar.get(Calendar.MONTH) ,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public TimePickerDialog createGlobalTimePickerDialog(Context context, final String format, final EditText editText){
        final Calendar calendar = Calendar.getInstance();
        return new TimePickerDialog(context , new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet ( TimePicker timePicker , int i , int i1 ) {
                calendar.set(Calendar.HOUR_OF_DAY , i);
                calendar.set(Calendar.MINUTE , i1);
                SimpleDateFormat sdf = new SimpleDateFormat(format , Locale.US);
                editText.setText(sdf.format(calendar.getTime()));
            }
        },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
    }

    public String getImageName ( Uri selectedImage ) {
        String path = selectedImage.getPath();

        return "." + path.substring(Objects.requireNonNull(path).lastIndexOf('.') + 1 , path.length());

    }

}
