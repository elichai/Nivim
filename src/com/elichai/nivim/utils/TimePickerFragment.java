package com.elichai.nivim.utils;

import java.util.Calendar;

import com.elichai.nivim.MainActivity;
import com.elichai.nivim.MyAlarmReceiver;
import com.elichai.nivim.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private MyAlarmReceiver alarm;
    public static final String PREFS = "com.elichai.nivim";
    //final CheckBox chk=null;
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
	    int hour = c.get(Calendar.HOUR_OF_DAY);
	    int minute = c.get(Calendar.MINUTE);
	   
//	    final CheckBox chk = (CheckBox) getActivity().findViewById(R.id.chk);
//        if (chk.isChecked()) {
//            chk.setChecked(false);
//        }
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
//        MainActivity.hour = hourOfDay;
//        MainActivity.min = minute;
        Toast.makeText(con, "TimeGet: "+hourOfDay+","+minute, Toast.LENGTH_LONG).show();
        alarm = new MyAlarmReceiver();
        if(alarm != null){alarm.setOnetimeTimer(con);}
    	else
    		Toast.makeText(con, "Alarm is null", Toast.LENGTH_SHORT).show();
    }
}
