package com.elichai.nivim.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.elichai.nivim.MyAlarmReceiver;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class tools {
	private static final String TAG = "MyActivity";
	
	private static MyAlarmReceiver alarm;
	static AdView a;
	public static void alarm(final Context con) {
		new Runnable() {
		    public void run() {
		    	  alarm = new MyAlarmReceiver();
		          if(alarm != null){
		      		alarm.setOnetimeTimer(con);
		      	}else{
		      		Toast.makeText(con, "Alarm is null", Toast.LENGTH_SHORT).show();
		      	}
		    }
		}.run();
	}
	public static  AdView admob(final Context con) {
		new Runnable() {
		    public void run() {
		  		try {
		  			//Advertisement:
		  		    a = new AdView((Activity) con, AdSize.SMART_BANNER, "a150361b89186e1");
		  		    AdRequest adRequest = new AdRequest();
		  		    adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		  		    adRequest.addTestDevice("014E0F5019010019");
		  		    a.loadAd(adRequest);
		  		    Log.v(TAG, "Ad Succed!!");
		  		    } catch (Exception e) {
		  		    	Log.e(TAG, "Ad Failed" + e.getMessage());
		  		       	Toast.makeText(con, "Ad Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
		  		      }
		    }
		}.run();
		return a;
	}
}
