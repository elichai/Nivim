package com.elichai.nivim;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmReceiver extends BroadcastReceiver { 
	private static final String TAG = "Future";
	private static final String TAG2 = "Present";
	public static final String PREFS = "com.elichai.nivim";
	private Context con;
	static Random rn = new Random();
	@SuppressLint({ "Wakelock", "NewApi" })

	public void onReceive(Context context, Intent intent) {
    	this.con = context;
    	SharedPreferences settings = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    	Editor editor = settings.edit();
    	PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressWarnings("deprecation")
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "YOUR TAG");
        //Acquire the lock
        wl.acquire();
        intent.getExtras();
        new StringBuilder();
        
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
        	setOnetimeTimer(con);
        	wl.release();
        }
        else {    		
            	setOnetimeTimer(con);
            	wl.release();
            	String[] descriptions = context.getResources().getStringArray(R.array.descriptions);
            	String[] idom = context.getResources().getStringArray(R.array.nivim_list);
            	int rnd = rn.nextInt(descriptions.length-1);
            	editor.putInt("rnd", rnd);
            	editor.commit();
            	CharSequence tickerText = idom[rnd];
            	int ic = R.drawable.icon;
            	Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
            			ic);
            	Intent Dintent = new Intent(context, MyDialog.class);
            	intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            	PendingIntent contentIntent = PendingIntent.getActivity(context, 0, Dintent, 0);
            	
            	NotificationManager notificationManager = 
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            	//Built Notification
            	Notification noti;
            	noti = new Notification.Builder(context)
                .setContentTitle(tickerText)
                .setContentText("לחץ לפירוש")
                .setLargeIcon(icon)
                .setSmallIcon(ic)
                .setContentIntent(contentIntent).build();
//                .addAction(ic,"התעלם", contentIntent)
//                .addAction(ic,"פירוש", contentIntent)
            	noti.flags |= Notification.FLAG_AUTO_CANCEL;
            	noti.defaults |= Notification.DEFAULT_VIBRATE; //Vibrate
            	noti.defaults |= Notification.DEFAULT_LIGHTS; //LED
            	notificationManager.notify(Context.MODE_PRIVATE, noti);
        }
   }
    
    @SuppressWarnings("deprecation")
	public void setOnetimeTimer(Context context){
    	SharedPreferences settings = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    	if(settings.getBoolean("chkbox", true)){
    		try {
    			int oldD,hour=settings.getInt("hour", 11),minute=settings.getInt("min", 0);
//    			Toast.makeText(context, "AlarmSet: "+hour+","+minute, Toast.LENGTH_LONG).show(); //Debug option
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
    	 			if(hour==PresentH &&minute<=PresentM)future+=(24*60*60*1000);
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
//    	else Toast.makeText(context, "Alarm-ChkBox Disabled", Toast.LENGTH_LONG).show(); //Debug option
    }
}