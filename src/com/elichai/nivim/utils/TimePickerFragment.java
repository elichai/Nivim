package com.elichai.nivim.utils;

import com.elichai.nivim.MyAlarmReceiver;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private MyAlarmReceiver alarm;
    public static final String PREFS = "com.elichai.nivim";
    //final CheckBox chk=null;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		SharedPreferences settings = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        // Use the current time as the default values for the picker
	    int hour = settings.getInt("hour", 11);
	    int minute = settings.getInt("min", 0);
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @SuppressLint("CommitPrefEdits")
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    	Context con = getActivity();
    	SharedPreferences settings = con.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    	Editor editor = settings.edit();
    	editor.putInt("hour", hourOfDay);
    	editor.putInt("min", minute);
    	editor.commit();
//        Toast.makeText(con, "TimeGet: "+hourOfDay+","+minute, Toast.LENGTH_LONG).show(); //Debug option
        alarm = new MyAlarmReceiver();
        if(alarm != null){alarm.setOnetimeTimer(con);}
    	else
    		Toast.makeText(con, "Alarm is null", Toast.LENGTH_SHORT).show();
        if(!settings.getBoolean("chkbox", true)) {
        	Toast.makeText(con, "ההתראה לא נקבעה כי לא איפשרת את זה בתיבת הסימון", Toast.LENGTH_LONG).show();
        	Toast.makeText(con, "ההתראה לא נקבעה כי לא איפשרת את זה בתיבת הסימון", Toast.LENGTH_LONG).show();
        }
    }
}
