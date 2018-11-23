package project.aigo.myapplication.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import java.util.Calendar;

public class DatePickerFragment extends android.app.DialogFragment {

    public DatePickerFragment () {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog ( Bundle savedInstanceState ) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}
