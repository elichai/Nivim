package com.elichai.nivim;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver { 
	private static final String TAG = "Future";
	private static final String TAG2 = "Present";
	private Context con;
	static Random rn = new Random();
    @SuppressLint("Wakelock")
	public void onReceive(Context context, Intent intent) {
    	this.con = context;
    	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();

        intent.getExtras();
        new StringBuilder();
        wl.release();
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
        	setOnetimeTimer(con);
        }
        else {    		
            	setOnetimeTimer(con);
            	Intent intent2 = new Intent(context, MyDialog.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	context.startActivity(intent2);
        }
   }
    
    
    @SuppressWarnings("deprecation")
	public void setOnetimeTimer(Context context){
    	 try {
    		 int oldD,hour=MainActivity.hour,minute=MainActivity.min;
             //Toast.makeText(context, "AlarmSet: "+hour+","+minute, Toast.LENGTH_LONG).show();
    	 		Calendar cal = new GregorianCalendar();
    	 		cal.setTimeInMillis(System.currentTimeMillis());
    	 		oldD = cal.get(Calendar.DAY_OF_MONTH);
    	 		cal.set( Calendar.HOUR_OF_DAY, hour );
    	 		cal.set( Calendar.MINUTE, minute );
    	 		cal.set(Calendar.SECOND, 00);
    	 		cal.set(Calendar.MILLISECOND, 000);
    	 		
    	 		long future = cal.getTimeInMillis();
    	 		Date date=new Date(System.currentTimeMillis());
    	 		
    	        String Future,Present = date.toString();
    	        Log.v(TAG2, Present);
    	        int PresentH = date.getHours();
    	        int PresentM = (date.getMinutes());
    	        if(hour==PresentH &&minute<=PresentM){future+=(24*60*60*1000);}
    	 		else if(hour<PresentH && oldD>=cal.get(Calendar.DAY_OF_MONTH))future+=(24*60*60*1000);
    	        date.setTime(future);
    	        Future =date.toString();
    	        Log.v(TAG, Future);

    	    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    	        Intent intent = new Intent(context, MyAlarmReceiver.class);
    	        intent.putExtra("Alarm", Boolean.TRUE);
    	        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
    	        am.set(AlarmManager.RTC_WAKEUP, future, pi);
    	       
         } catch (Exception e) {
         	Toast.makeText(context, "AlarmManager faild" + e.getMessage(), Toast.LENGTH_LONG).show();
         	Toast.makeText(context, "AlarmManager faild" + e.getMessage(), Toast.LENGTH_LONG).show();
         }
    }
}