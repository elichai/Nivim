package com.elichai.nivim;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

public class MyDialog extends Activity {
	static Random rn = new Random();
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] descriptions = getResources().getStringArray(R.array.descriptions);
        create(this, descriptions);
	}
public static void create (final Context context,String[] descriptions) {	
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("משפט אקראי יומי");
        try{
        	builder.setMessage(Html.fromHtml("<u><b>"+descriptions[rn.nextInt(descriptions.length-1)]+"</b></u>"+ "<br>"));
        } catch (Exception e) {
        	Toast.makeText(context, "PopUp-Random faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        	 try{
             	builder.setMessage("test");
             } catch (Exception ee) {
             	Toast.makeText(context, "PopUp-Message faild" + e.getMessage(), Toast.LENGTH_LONG).show();
             }
        }
        builder.setInverseBackgroundForced(false);
        builder.setPositiveButton("סגור",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(startMain);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	}
}
