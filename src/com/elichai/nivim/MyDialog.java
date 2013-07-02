package com.elichai.nivim;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class MyDialog extends Activity {
	static Random rn = new Random();
	public static final String PREFS = "com.elichai.nivim";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] descriptions = getResources().getStringArray(R.array.descriptions);
		String[] idom = getResources().getStringArray(R.array.nivim_list);
		SharedPreferences settings = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
		if(settings.getBoolean("chkbox", true)) create(this, descriptions, idom);
//    	else Toast.makeText(context, "Dialog-ChkBox Disabled", Toast.LENGTH_LONG).show(); //Debug option
//       finish();
	}
public static void create (final Context context,String[] descriptions, String[] idom) {	
	SharedPreferences settings = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
	
	
	AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setCancelable(true);
    int rnd = settings.getInt("rnd", -1);
    if(rnd==-1){builder.setTitle("שגיאה");builder.setMessage("שגיאה");}
    else{
        builder.setTitle(idom[rnd]);
        try{
        	builder.setMessage(descriptions[rnd]);
        } catch (Exception e) {
        	Toast.makeText(context, "PopUp-Random faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        builder.setInverseBackgroundForced(false);
        builder.setPositiveButton("סגור",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                        ((Activity) context).onBackPressed();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        }
	}
}
