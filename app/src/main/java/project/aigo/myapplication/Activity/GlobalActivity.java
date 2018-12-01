package project.aigo.myapplication.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void showDatePickerDialog ( View v ) {
        DatePickerFragment newFragment = new DatePickerFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        newFragment.show(fragmentManager , "datePicker");
    }
}
