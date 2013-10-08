package com.elichai.nivim;

import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.net.Uri;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elichai.nivim.R;
import com.elichai.nivim.utils.TimePickerFragment;
import com.elichai.nivim.utils.tools;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.bugsense.trace.BugSenseHandler;

public class MainActivity extends Activity implements OnItemClickListener  {
	ListView list;
	ArrayAdapter<String> adapter;
	AdView a;
    private static final String TAG = "NivimDebug";
    static Window win;
    public static final String PREFS = "com.elichai.nivim";
    final double brightness = 0.34;
    final int color = Color.argb(255, (int)(brightness * 255), (int)(brightness * 255), (int)(brightness * 255));

    	public void onCreate(Bundle savedInstanceState) {
    		BugSenseHandler.initAndStartSession(MainActivity.this, "9730ed70");	
    		final Runnable RadView = new Runnable() {
    		    public void run() {
    		  		try {
    		  			//Advertisement:
    		  		    a = new AdView(MainActivity.this, AdSize.SMART_BANNER, "a150361b89186e1");
    		  		    RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
    		  		    layout.addView(a);
    		  		    AdRequest adRequest = new AdRequest();
    		  		    adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
    		  		    adRequest.addTestDevice("014E0F5019010019");
    		  		    a.loadAd(adRequest);
    		  		    Log.v(TAG, "Ad Succed!!");
    		  		    } catch (Exception e) {
    		  		    	Log.e(TAG, "Ad Failed" + e.getMessage());
    		  		       	Toast.makeText(MainActivity.this, "Ad Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
    		  		      }
    		    }
    		};
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadView.run();
        String[] s1 = getResources().getStringArray(R.array.nivim_list);
        list = (ListView) findViewById(R.id.listView1);
		adapter = new ArrayAdapter<String>(this,
         	    android.R.layout.simple_list_item_1
         	    , s1);
		View header = getLayoutInflater().inflate(R.layout.header, null);
		list.addHeaderView(header);
		list.setAdapter(adapter);
        list.setOnItemClickListener(this);
        list.setFastScrollEnabled(true);
        final View v = (View)list.getParent();
        Drawable bg = v.getBackground();
        bg.setColorFilter(color, Mode.SCREEN);
        RadView.run();
        tools.alarm(this);
        SharedPreferences settings = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        if(!settings.contains("hour") && !settings.contains("chkbox")) {
        	Toast.makeText(MainActivity.this, "לא הגדרת שעה לפתגם האקראי, אם אתה רוצה להגדיר לחץ על הגדרות ואז על 'בחר שעה לפתגם אקראי' או בטל את הסימון מתחת, לביטול הפתגם האקראי", Toast.LENGTH_LONG).show();
        	Toast.makeText(MainActivity.this, "לא הגדרת שעה לפתגם האקראי, אם אתה רוצה להגדיר לחץ על הגדרות ואז על 'בחר שעה לפתגם אקראי' או בטל את הסימון מתחת, לביטול הפתגם האקראי", Toast.LENGTH_LONG).show();
        	Toast.makeText(MainActivity.this, "לא הגדרת שעה לפתגם האקראי, אם אתה רוצה להגדיר לחץ על הגדרות ואז על 'בחר שעה לפתגם אקראי' או בטל את הסימון מתחת, לביטול הפתגם האקראי", Toast.LENGTH_LONG).show();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
    	SharedPreferences settings = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
      boolean checked = settings.getBoolean("chkbox", true);
        menu.findItem(R.id.chkbox).setChecked(checked);
        return(super.onCreateOptionsMenu(menu));
        
	}
	@SuppressWarnings("deprecation")
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
	    PackageInfo pInfo = null;
	    SharedPreferences settings = this.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
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
/*			case R.id.dialog: //Debug option
				Intent intent2 = new Intent(this, MyDialog.class); 
	            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            this.startActivity(intent2);
				break; */
			case R.id.TimePick:
//				startActivity(new Intent(this, TimePickerTest.class));
				showTimePickerDialog();
				break;
			case R.id.chkbox: // For Debug
				if (item.isChecked()) item.setChecked(false);
	            else item.setChecked(true);
				Editor editor = settings.edit();
				editor.putBoolean("chkbox", item.isChecked());
				editor.commit();
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