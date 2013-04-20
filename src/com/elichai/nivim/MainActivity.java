package com.elichai.nivim;

import java.util.Calendar;
import java.util.Random;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.elichai.nivim.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.bugsense.trace.BugSenseHandler;

public class MainActivity extends Activity implements OnItemClickListener  {
	ListView list;
	ArrayAdapter<String> adaptr;
	AdView a;
    static Random rn = new Random();
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(MainActivity.this, "9730ed70");
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView1);
		adaptr = new ArrayAdapter<String>(this,
         	    android.R.layout.simple_list_item_1
         	    , getResources().getStringArray(R.array.nivim_list));
		View header = getLayoutInflater().inflate(R.layout.header, null);
		list.addHeaderView(header);
		list.setAdapter(adaptr);
        list.setOnItemClickListener(this);
        list.setFastScrollEnabled(true);
        try {
        //AdVertisement:
        a = new AdView(this, AdSize.SMART_BANNER, "a150361b89186e1");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.addView(a);
        AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        adRequest.addTestDevice("014E0F5019010019");
        a.loadAd(adRequest);  
        } catch (Exception e) {
        	Toast.makeText(this, "Ad Faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
        	//create();
        	Intent myIntent = new Intent(MainActivity.this , MyAlarmReceiver.class);     
        	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        	PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);
        	Calendar calendar = Calendar.getInstance();
        	calendar.set(Calendar.HOUR_OF_DAY, 19);
        	calendar.set(Calendar.MINUTE, 4);
        	//calendar.set(Calendar.SECOND, 00);
        	alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24*60*60*1000 , pendingIntent);
//        	alarmIntent = new Intent ( null, MyAlarmReceiver.class );
//            pendingIntent = PendingIntent.getBroadcast( this.getApplicationContext(), 234324243, alarmIntent, 0 );
//            alarmManager = ( AlarmManager ) getSystemService( ALARM_SERVICE );
//            alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, ( uploadInterval * 1000 ),( uploadInterval * 1000 ), pendingIntent );
        } catch (Exception e) {
        	Toast.makeText(this, "PopUp faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return(super.onCreateOptionsMenu(menu));
        
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
			case R.id.about:
				startActivity(new Intent(this, About.class));
				break;
			case R.id.contact:
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
				String uriText;

				uriText = "mailto:"+"e2Apps.dev@gmail.com" + 
						"?subject="+"[האפליקציה: פירוש לניבים ולפתגמים]" + 
						"&body=";
				uriText = uriText.replace("", "%20");
				Uri uri = Uri.parse(uriText);

				emailIntent.setData(uri);
				startActivity(android.content.Intent.createChooser(emailIntent,"בחר באיזה אפליקציה ברצונך להשתמש כדי לשלוח את המייל"));
				break;
			case R.id.donate:
				String url1 = "http://goo.gl/cN7bK";
				Intent i1 = new Intent(Intent.ACTION_VIEW);
				i1.setData(Uri.parse(url1));
				startActivity(i1);
				break;
		}
		return false;
	}

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		String[] descriptions = getResources().getStringArray(R.array.descriptions);
		Dialog dialog1 = new Dialog(this, R.style.PauseDialog);
    	dialog1.setContentView(R.layout.dialog1);
		TextView text = (TextView) dialog1.findViewById(R.id.tv1);
		dialog1.setTitle(R.string.Title);
		dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		if (0==position) {
			String url = "http://www.nivon.co.il";
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		} else {
		    text.setText(descriptions[position-1]);
		    dialog1.show();
		}
		
	}
	public void create (/*String[] descriptions*/) {	
		String[] descriptions = getResources().getStringArray(R.array.descriptions);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("משפט אקראי יומי");
        try{
        	builder.setMessage(descriptions[rn.nextInt(395)-1]);
        } catch (Exception e) {
        	Toast.makeText(this, "PopUp-Random faild" + e.getMessage(), Toast.LENGTH_LONG).show();
        	 try{
             	builder.setMessage("test");
             } catch (Exception ee) {
             	Toast.makeText(this, "PopUp-Message faild" + e.getMessage(), Toast.LENGTH_LONG).show();
             }
        }
        builder.setInverseBackgroundForced(false);
        builder.setPositiveButton("סגור",
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
