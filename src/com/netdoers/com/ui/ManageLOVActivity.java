/*HISTORY
* CATEGORY			 :- LOV
* DEVELOPER			 :- VIKALP PATEL
* AIM 				 :- MANAGING LOV IN APPLICATION
* DESCRIPTION 		 :- MANAGING LOV IN APPLICATION
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX 	DEVELOPER 		DATE 		FUNCTION		DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* 10001    VIKALP PATEL   09/01/2014 					DEPOSITED BANK LOV	
* 10002    VIKALP PATEL   09/01/2014    				INIT CAP
* 10003    VIKALP PATEL   10/01/2014    				ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
* P3MX01   VIKALP PATEL   28/03/2014    				OPEN SPINNER PROGRAMMATICALLY AUTO-OPEN
* P3B01    VIKALP PATEL   19/04/2014     BUG			FINISH ACTIVITY ON CLICK OF VALUE INSIDE LOV
* P3B02    VIKALP PATEL   19/04/2014     BUG			DUPLICATE VALUES IN LOV INSERTION
* --------------------------------------------------------------------------------------------------------------------
*/
package com.netdoers.com.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.smarthumanoid.com.R;
import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.utils.InitCap;

public class ManageLOVActivity extends Activity{

	Spinner spinner;
	ListView listOfLovs;
	EditText newLovValue;
	MyContentObserver myContentObserver;
	boolean isFirstTime = true;
	CursorAdapter adapter;
	MyHandler handler;
	int selectedIndex = 0;
	InitCap initCap;//ADDED 10002
	SharedPreferences pref;//ADDED 10003;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SA 10003
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			//setTheme(R.style.FullScreen);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10003
		setContentView(R.layout.lov_screen);
		initializeData();
		selectedIndex = getIntent().getIntExtra("index", 0);
		isFirstTime = true;
		handler = new MyHandler();
		myContentObserver = new MyContentObserver(handler);
		updateObserver(selectedIndex);
		initCap=new InitCap();//ADDED 10002
		
		spinner = (Spinner) findViewById(R.id.listOfLOV);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(!isFirstTime)
				{
					updateObserver(arg2);
				}
				selectedIndex = arg2;
				isFirstTime = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.listOfLOVS));
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
		try {
			spinner.performClick();// ADDED P3MX01
		} catch (Exception e) {
		    Log.i("LOV", e.toString());
		}
			
		listOfLovs = (ListView) findViewById(R.id.lovValues);
		listOfLovs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				SA P3B01
//				TextView textView = (TextView) arg1.findViewById(R.id.txtLovValue);
//		        String text = textView.getText().toString();
//		        System.out.println("Choosen Country = : " + text);
//		        
//				Intent i = new Intent();
//				i.putExtra("data", text);
//				setResult(RESULT_OK, i);
//				finish();
//				EA P3B01
			}
		});
		newLovValue = (EditText) findViewById(R.id.edtLOVValue);
		 new SelectDataTask().execute(getUri(selectedIndex));
	}
	ArrayList<String> strLocation = new ArrayList<String>();
	ArrayList<String> strProcedure = new ArrayList<String>();
	ArrayList<String> strWard = new ArrayList<String>();

	ArrayList<String> strTeamMember = new ArrayList<String>();
	ArrayList<String> strType = new ArrayList<String>();
	ArrayList<String> strRef = new ArrayList<String>();
	ArrayList<String> strStartTime = new ArrayList<String>();
	ArrayList<String> strLevel = new ArrayList<String>();
	ArrayList<String> strModeOfPayment = new ArrayList<String>();
	ArrayList<String> strBank = new ArrayList<String>();
	ArrayList<String> strDepositedBank = new ArrayList<String>();//ADDED 10001
	public void initializeData()
	{
		strLocation = SmartConsultant.getApplication().loadLocation();
		strProcedure = SmartConsultant.getApplication().loadProcedure();
		strWard = SmartConsultant.getApplication().loadWard();
		strTeamMember = SmartConsultant.getApplication().loadTeamMember();
		strType = SmartConsultant.getApplication().loadType();
		strRef = SmartConsultant.getApplication().loadRef();
		strStartTime = SmartConsultant.getApplication().loadStartTime();
		strLevel = SmartConsultant.getApplication().loadLevel();
		strModeOfPayment = SmartConsultant.getApplication().loadModeOfPayment();
		strBank = SmartConsultant.getApplication().loadBank();
		strDepositedBank= SmartConsultant.getApplication().loadDepositedBank();//ADDED 10001
	}
	public void onAddLOVClick(View v)
	{
		if(newLovValue.getText().toString() != null && newLovValue.getText().toString().length() > 0)
		{
			String str = newLovValue.getText().toString();
			//SA 10002
			//str = str.substring(0,1).toUpperCase() + str.substring(1);
			str = initCap.toTitleCase(str);
			//EA 10002
			
			if(isExists(str))
			{
				CustomToast.showToastMessage(getApplicationContext(), "LOV Already Exists!");
			}
			else
			{
				ContentValues values = new ContentValues();
				values.put(DBConstant.Location_Columns.COLUMN_NAME, str);
				values.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 0);
				getContentResolver().insert(getUri(spinner.getSelectedItemPosition()), values);
				newLovValue.setText("");
			}
		}
	}
	
	private class SelectDataTask extends AsyncTask<Uri, Void ,Cursor> {

		Uri currentUri;
		@Override
		protected void onPreExecute() {
			// this.dialog.setMessage("Getting Names...");
			// this.dialog.show();
		}

		// can use UI thread here
		@Override
		protected void onPostExecute(final Cursor result) {

			startManagingCursor(result);
			int[] listFields = new int[] { R.id.txtLovValue };
			String[] dbColumns = new String[] { DBConstant.Location_Columns.COLUMN_NAME };

			ManageLOVActivity.this.adapter = new CustomSqlCursorAdapter(
					ManageLOVActivity.this, R.layout.lov_item,
					result, dbColumns, listFields,currentUri);
			ManageLOVActivity.this.listOfLovs.setAdapter(ManageLOVActivity.this.adapter);
			spinner.setSelection(selectedIndex);
		}

		@Override
		protected Cursor doInBackground(Uri... arg0) {
			// TODO Auto-generated method stub
			try {
				currentUri = arg0[0];
				return ManageLOVActivity.this.getContentResolver().query(currentUri, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
	}

	private class MyContentObserver extends ContentObserver
	{

		public MyContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onChange(boolean selfChange) {
			// TODO Auto-generated method stub
			super.onChange(selfChange);
			adapter.notifyDataSetChanged();
		}
		@Override
		public boolean deliverSelfNotifications() {
			// TODO Auto-generated method stub
			return true;
		}
	}
	
	private class MyHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	}
	public void updateObserver(int index)
	{
		Uri uri = getUri(index);
		if(uri != null)
		{
			getContentResolver().registerContentObserver(uri, true, myContentObserver);
			new SelectDataTask().execute(uri);
		}
	}
	
	public Uri getUri(int index)
	{
		switch(index)
		{
			case 0: //location
					return DBConstant.Location_Columns.CONTENT_URI;
			case 1: //procedure
					return DBConstant.Procedure_Columns.CONTENT_URI;
			case 2: //ward
					return DBConstant.Ward_Columns.CONTENT_URI;
			case 3: //tmember
				return DBConstant.TMEMBER_Columns.CONTENT_URI;
			case 4: //type
					return DBConstant.Types_Columns.CONTENT_URI;
			case 5: //ref
					return DBConstant.Ref_Columns.CONTENT_URI;
			case 6: //start time
					return DBConstant.StartTime_Columns.CONTENT_URI;
			case 7: //level
					return DBConstant.Level_Columns.CONTENT_URI;
			case 8: //mode
					return DBConstant.ModeOfPayment_Columns.CONTENT_URI;
			case 9: //bank - expense category
					return DBConstant.Bank_Columns.CONTENT_URI;
			// SA 10001
			case 10: // deposited bank
					return DBConstant.Deposited_Bank_Columns.CONTENT_URI;
			// EA 10001
			default:
					return null;
		}
	}
	
	public boolean isExists(String s)
	{
		switch(spinner.getSelectedItemPosition())
		{
			case 0: //location
					if(strLocation.contains(new String(s)))
						return true;
					else 
						return  false;
			case 1: //procedure
					if( strProcedure.contains(new String(s)))
						return true;
					else 
						return  false;
			case 2: //ward
					if(strWard.contains(new String(s)))
						return true;
					else 
						return  false;
			case 3: //tmember
					if(strTeamMember.contains(new String(s)))
						return true;
					else 
						return  false;
			case 4: //type
					if(strType.contains(new String(s)))
						return true;
					else 
						return  false;
			case 5: //ref
					if(strRef.contains(new String(s)))
						return true;
					else 
						return  false;
			case 6: //start time
					if(strStartTime.contains(new String(s)))
						return true;
					else 
						return  false;
			case 7: //level
					if(strLevel.contains(new String(s)))
						return true;
					else 
						return  false;
			case 8: //mode
					if(strModeOfPayment.contains(new String(s)))
						return true;
					else 
						return  false;
			case 9: //bank
					if(strBank.contains(new String(s)))
						return true;
					else 
						return  false;
			//SA 10001
			case 10: //bank
				if(strDepositedBank.contains(new String(s)))
					return true;
				else 
					return  false;
			//EA 10001
			default:
					return false;
		}
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.mnuHelp:
        	Intent i = new Intent(this, HelpActivity.class);
        	i.putExtra("caller", "list");
        	startActivity(i);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
}
