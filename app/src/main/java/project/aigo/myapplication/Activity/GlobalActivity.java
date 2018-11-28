package project.aigo.myapplication.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GlobalActivity extends AppCompatActivity {

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

    public String[] getDataforAuthenticate (Context context){
        SharedPreferences sp = context.getSharedPreferences("spLogin" , MODE_PRIVATE);

        final String id = sp.getString("id","");
        final String remember_token = sp.getString("remember_token", "");

        return new String[]{id, remember_token};
    }

    public ProgressDialog showProgressDialog(Context context, String[]... array){
        ProgressDialog progressDialog = new ProgressDialog(context);

        String title =  (array.length == 0) ? "Loading..." : String.valueOf(array[0]);
        String message = (array.length == 0) ? "Please Wait" : String.valueOf(array[1]);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);


        return progressDialog;
    }
}
