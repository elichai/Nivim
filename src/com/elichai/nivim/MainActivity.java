package com.elichai.nivim;

import java.net.URLEncoder;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.net.Uri;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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
import com.elichai.nivim.utils.TimePickerFragment;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.bugsense.trace.BugSenseHandler;

public class MainActivity extends Activity implements OnItemClickListener  {
	ListView list;
	ArrayAdapter<String> adaptr;
	AdView a;
    static Random rn = new Random();
    public static int hour,min;
    private MyAlarmReceiver alarm;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(MainActivity.this, "9730ed70");
        setContentView(R.layout.activity_main);
        String[] s1 = getResources().getStringArray(R.array.nivim_list);
//        for(int i=0;i<s1.length;i++) {
//        	s1[i] = ("<b>"+s1[i]+"</b>");
//        }
        list = (ListView) findViewById(R.id.listView1);
		adaptr = new ArrayAdapter<String>(this,
         	    android.R.layout.simple_list_item_1
         	    , s1);
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
        	Toast.makeText(this, "Ad Faild " + e.getMessage(), Toast.LENGTH_LONG).show();

        }
        alarm = new MyAlarmReceiver();
        if(alarm != null){
    		alarm.setOnetimeTimer(this);
    	}else{
    		Toast.makeText(this, "Alarm is null", Toast.LENGTH_SHORT).show();
    	}
       
    }
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return(super.onCreateOptionsMenu(menu));
        
	}
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
	    PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo("com.elichai.nivim", PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (item.getItemId()) {
			case R.id.about:
				startActivity(new Intent(this, About.class));
				break;
			case R.id.contact:
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
				String uriText;
				uriText =
					    "mailto:e2Apps.dev@gmail.com" + 
					    "?subject=" + URLEncoder.encode("[האפליקציה: פירוש לניבים ולפתגמים]") + 
					    "&body="+URLEncoder.encode("Ver:"+pInfo.versionName+":")+"\n\r";
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
			case R.id.dialog:
				Intent intent2 = new Intent(this, MyDialog.class);
	            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            this.startActivity(intent2);
				break;
			case R.id.TimePick:
				showTimePickerDialog();
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
	@SuppressLint("NewApi")
	public void showTimePickerDialog() {
		
	    TimePickerFragment newFragment = new TimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");
	    }
}