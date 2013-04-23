package com.elichai.nivim;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Toast;
import java.util.Random;

public class MyAlarmReceiver extends BroadcastReceiver { 
	private Context con;
	static Random rn = new Random();
    @SuppressLint("Wakelock")
	public void onReceive(Context context, Intent intent) {
    	this.con = context;
    	try 
        {
    		//String[] descriptions = this.con.getResources().getStringArray(R.array.descriptions);
       	 
//        	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
//            //Acquire the lock
//            wl.acquire();
//
//            intent.getExtras();
//            new StringBuilder();
//            wl.release();
            String[] a ={"a","b"};
            //create(context, a);
            setOnetimeTimer(con);
        } 
        catch (Exception e) 
        {
             Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
 e.printStackTrace();
        }
   }
    
    
    public void setOnetimeTimer(Context context){
    	 try {
    		 int hour=21,minute=11;
    	 	    
    	 		Calendar cal = new GregorianCalendar();
    	 		cal.set( Calendar.HOUR_OF_DAY, hour );
    	 		cal.set( Calendar.MINUTE, minute );
    	 		cal.set(Calendar.SECOND, 00);
    	 		cal.set(Calendar.MILLISECOND, 000);
    	 		
    	 		long future = cal.getTimeInMillis();
    	 		if(hour<=cal.HOUR_OF_DAY){future+=(24*60*60*1000);}

    	    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    	        Intent intent = new Intent(context, MyAlarmReceiver.class);
    	        intent.putExtra("Alarm", Boolean.TRUE);
    	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
    	        am.set(AlarmManager.RTC_WAKEUP, future, pi);
         } catch (Exception e) {
         	Toast.makeText(context, "PopUp faild" + e.getMessage(), Toast.LENGTH_LONG).show();
         }
    	
    }
public static void create (Context context,String[] descriptions) {	
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("���� ����� ����");
        try{
        	//builder.setMessage(descriptions[rn.nextInt(395)+1]);
        	builder.setMessage(descriptions[rn.nextInt(2)]);
        } catch (Exception e) {
        	Toast.makeText(context, "PopUp-Random faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        	 try{
             	builder.setMessage("test");
             } catch (Exception ee) {
             	Toast.makeText(context, "PopUp-Message faild" + e.getMessage(), Toast.LENGTH_LONG).show();
             }
        }
        builder.setInverseBackgroundForced(false);
        builder.setPositiveButton("����",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
	}
}