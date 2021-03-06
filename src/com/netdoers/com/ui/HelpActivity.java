/*HISTORY
* CATEGORY 			:- CAMERA
* DEVELOPER			:- VIKALP PATEL
* AIM 				:- CAPTURE IMAGE
* DESCRIPTION 		:- TAKING PICTURE WITH THE ACTIVITY
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX 		DEVELOPER 			DATE 				FUNCTION 		DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* 10001 	  VIKALP PATEL 		 10/01/2014 							ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.ui;

import com.netdoers.com.SmartConsultant;
import com.smarthumanoid.com.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class HelpActivity extends Activity{

	WebView webView;
	SharedPreferences pref;//ADDED 10001
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SA 10001
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
		//	setTheme(R.style.FullScreen);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10001

		setContentView(R.layout.help);
		
		String calledFrom = getIntent().getStringExtra("caller");
		webView = (WebView) findViewById(R.id.add_sx_location_text);
		
		TextView title = (TextView) findViewById(R.id.title);
		if(calledFrom.equals("expense"))
		{
			webView.loadUrl("file:///android_asset/addexpense.html");
			title.setText("Help - Add Expense");
		}
		else if(calledFrom.equals("ipd"))
		{
			webView.loadUrl("file:///android_asset/addipd.html");
			title.setText("Help - Add IPD");
		}
		else if(calledFrom.equals("opd"))
		{
			webView.loadUrl("file:///android_asset/addopd.html");
			title.setText("Help - Add OPD");
		}
		else if(calledFrom.equals("sx"))
		{
			webView.loadUrl("file:///android_asset/addsx.html");
			title.setText("Help - Add SX");
		}
		else if(calledFrom.equals("home"))
		{
			webView.loadUrl("file:///android_asset/home.html");
			title.setText("Help - Home");
		}
		else if(calledFrom.equals("list"))
		{
			webView.loadUrl("file:///android_asset/managelist.html");
			title.setText("Help - Manage Lists");
		}
		else if(calledFrom.equals("record"))
		{
			webView.loadUrl("file:///android_asset/recordscreen.html");
			title.setText("Help - Record IPD/OPD/SX");
		}
		else if(calledFrom.equals("setup"))
		{
			webView.loadUrl("file:///android_asset/setupscreen.html");
			title.setText("Help - Setup");
		}
		else if(calledFrom.equals("payment"))
		{
			webView.loadUrl("file:///android_asset/addpayment.html");
			title.setText("Help - Payment");
		}
	}
}
