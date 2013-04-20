package com.elichai.nivim;

import com.elichai.nivim.R;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class About extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		TextView textView = (TextView) findViewById(R.id.versionInfo);
		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo("com.elichai.nivim", PackageManager.GET_META_DATA);
			textView.setText("Version: " + pInfo.versionName);
		} catch (NameNotFoundException e) {
		}
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		
		webView.loadUrl("file:///android_asset/nivim_about.html");
	}
	
}