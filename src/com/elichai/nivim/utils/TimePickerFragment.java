package com.elichai.nivim.utils;

import java.util.Calendar;

import com.elichai.nivim.MainActivity;
import com.elichai.nivim.MyAlarmReceiver;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private MyAlarmReceiver alarm;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    	Context con = getActivity();
        MainActivity.hour = hourOfDay;
        MainActivity.min = minute;
        //Toast.makeText(con, "TimeGet: "+MainActivity.hour+","+MainActivity.min, Toast.LENGTH_LONG).show();
        alarm = new MyAlarmReceiver();
        if(alarm != null){alarm.setOnetimeTimer(con);}
    	else
    		Toast.makeText(con, "Alarm is null", Toast.LENGTH_SHORT).show();
    }
}
