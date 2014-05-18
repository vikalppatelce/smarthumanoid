/* HISTORY
 * CATEGORY 		:- UI | DASHBOARD
 * DEVELOPER		:- VIKALP PATEL
 * AIM      		:- DASHBOARD
 * DESCRIPTION 		:- APPLICATION MAIN DASHBOARD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001       VIKALP PATEL    07/01/2014     				   ADDING PAYMENT SCREEN
 * 10002       VIKALP PATEL    07/01/2014 					   SETTINGS/SETUP MENU
 * 10003       VIKALP PATEL    07/01/2014      				   ADDING UPDATED DASHBOARD WITH PAYMENT SCREEN
 * 10004	   VIKALP PATEL    10/01/2014					   ADDING PREFERENCES SETUP
 * 10005       VIKALP PATEL    10/01/2014       			   SETTING APPLICATION IN FULL SCREEN MODE
 * 10006       VIKALP PATEL    13/01/2014      				   ADDED SEARCH ON THE BASIS OF SEARCH ALGORIGTHM
 * 10007       VIKALP PATEL    15/01/2014       			   ADDED DELETE OPTION WITH SEARCH OPTION DIRECTLY
 * 10008       VIKALP PATEL    17/01/2014                      DELETE PATIENT RECORD DISCARD ITS PICTURES ALSO
 * 10009       VIKALP PATEL    18/01/2014                      DASHBOARD FROM PREFERENCES
 * 1000A       VIKALP PATEL    04/02/2014  					   PUT SETUP MENU AT PAYMENT OPTION.
 * B0001       VIKALP PATEL    03/03/2014        BUG           SEARCH TEXT HINT REFLECT ON SELECTED OPTION
 * P3001       VIKALP PATEL    10/03/2014                      ADDING FLING ON HOME SCREEN FOR SOCIAL SCREEN.
 * P3A01       VIKALP PATEL    19/03/2014                      ADDED DRAWER
 * P3MX01      VIKALP PATEL    28/03/2014       IMPROVE        HIDE KEYBOARD ON SUGGESTION SELECTION FOR SEARCH
 * P3B01       VIKALP PATEL    25/04/2014       BUG            SEARCH AND ADD SERVICE STILL NOT FETCHING SERVICE DATA
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.utils.AppConstants;
import com.netdoers.com.utils.SearchAlgo;
import com.smarthumanoid.com.R;

@SuppressLint("ValidFragment")
public class HomeActivity extends SherlockFragmentActivity{
	AutoCompleteTextView searchText;
	//final static int[] to = new int[] { android.R.id.text1};
	final static int[] to = new int[] { R.id.txtText, R.id.txtLocation, R.id.txtDate};
    final static String[] fromName = new String[] { DBConstant.Patient_Name_Columns.COLUMN_NAME,DBConstant.Patient_Name_Columns.COLUMN_LOCATION,
		DBConstant.Patient_Name_Columns.COLUMN_DATE};
    final static String[] fromId = new String[] { DBConstant.Patient_Name_Columns.COLUMN_NAME,DBConstant.Patient_Name_Columns.COLUMN_LOCATION,
		DBConstant.Patient_Name_Columns.COLUMN_DATE};
    final static String[] projection = { 
    										DBConstant.Patient_Temp_Columns.COLUMN_ID, 
    										DBConstant.Patient_Temp_Columns.COLUMN_NAME,
    										DBConstant.Patient_Temp_Columns.COLUMN_CUSTOM_ID,
    										DBConstant.Patient_Temp_Columns.COLUMN_LOCATION,
    										DBConstant.Patient_Temp_Columns.COLUMN_DATE,
    									};
    
    int searchBy = 0; // 0 - name, 1 - id // static int searchBy = -1;
    Cursor myLocationCursor;
    
    String[] data;
    String name = null;
    SharedPreferences pref;//ADDED 10005
    LinearLayout homeLayout;//SA P3001
    final static ViewConfiguration vc = ViewConfiguration.get(SmartConsultant.getApplication().getApplicationContext());
	private static final int SWIPE_MIN_DISTANCE = vc.getScaledPagingTouchSlop();
    private static final int SWIPE_MAX_OFF_PATH = vc.getScaledMinimumFlingVelocity();
    private static final int SWIPE_THRESHOLD_VELOCITY = vc.getScaledTouchSlop();
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;	//EA P3001
    String width;//SA P3A01
    ActionBar actionBar;
    WebView socialWebView;
    boolean canGoBack= false;
    Button retry;
    RelativeLayout retryLayout;
    ProgressBar webviewProgress;
    SlidingMenu menu;//EA P3A01
    RelativeLayout loginLayout;//SA P3MX02
    TextView txtLoginDetails;
    Button btnLogin;//EA P3MX02
    
    public static final int SETTINGS = 1001;//ADDED 10005
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.options_screen); SU10003
		//SA 10005
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			//setTheme(R.style.FullScreen);
//			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10005
		//SA 1000A
//		setContentView(R.layout.options_screen_updated);
//		setContentView(R.layout.options_screen);
		//EA 1000A
		//setContentView(R.layout.option_screen_dashboard);//EU10003
		//checkOptions();
		
		SmartConsultant.getApplication();
		//		SA P3A01
		if(SmartConsultant.getPreferences().getScreenWidth()!=null)
		{
		SmartConsultant.getApplication();
		width = SmartConsultant.getPreferences().getScreenWidth();	
		}
		else
		{
		width  =  String.valueOf(HomeActivity.this.getWindowManager().getDefaultDisplay().getWidth());	
		}
        setContentView(R.layout.socialscreen);
        actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Smart Consultant");
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidth(-15);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setFadeDegree(0.35f);
        menu.setBehindWidth((Integer.parseInt(width) - 50));
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.options_screen_updated);
        menu.toggle();
        socialWebView = (WebView) findViewById(R.id.webview);
        retry = (Button) findViewById(R.id.retry);
        retryLayout = (RelativeLayout) findViewById(R.id.retry_layout);
        
        btnLogin = (Button) findViewById(R.id.socialbtnlogin);//SA P3MX02
        txtLoginDetails = (TextView) findViewById(R.id.sociallogindetails);
        txtLoginDetails.setText(Html.fromHtml(getString(R.string.logindetails)));
        loginLayout = (RelativeLayout) findViewById(R.id.social_login_layout);//EA P3MX02
        
        webviewProgress = (ProgressBar) findViewById(R.id.webview_progressbar);
        
//        webviewProgress.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(SmartConsultant.getApplication().getApplicationContext())
//        .color(0x03AFD2)
//        .interpolator(new DecelerateInterpolator())
//        .sectionsCount(4)
//        .separatorLength(8)         //You should use Resources#getDimensionPixelSize
//        .strokeWidth(3f)            //You should use Resources#getDimension
//        .speed(2.0f)                 //2 times faster
//        .progressiveStartSpeed(2)
//        .progressiveStopSpeed(3.4f)
//        .reversed(false)
//        .mirrorMode(false)
//        .progressiveStart(true)
////        .progressiveStopEndedListener(mListener) //called when the stop animation is over
//        .build());
        
        WebSettings webSettings = socialWebView.getSettings();
        socialWebView.setWebViewClient(new MyWebViewClient());
        webSettings.setJavaScriptEnabled(true);
        if(isNetworkAvailable())
        {
//        	SA P3MX02
//        	webviewProgress.setVisibility(View.VISIBLE);
//        	socialWebView.setVisibility(View.VISIBLE);
//        	socialWebView.loadUrl(AppConstants.URLS.SOCIAL_URL);
        	if(SmartConsultant.getPreferences().getUserLoginDTO().getSign_id()!=null)
        	{
        		webviewProgress.setVisibility(View.VISIBLE);
            	socialWebView.setVisibility(View.VISIBLE);
            	socialWebView.loadUrl(AppConstants.URLS.SOCIAL_URL+SmartConsultant.getPreferences().getUserLoginDTO().getSign_id());
        	}
        	else
        	{
        		loginLayout.setVisibility(View.VISIBLE);
        	}
//        	EA P3MX02
        }
        else
        {
        	retryLayout.setVisibility(View.VISIBLE);
        }
//		EA P3A01
		
//		SA P3001
		homeLayout = (LinearLayout)findViewById(R.id.login_screen);
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            @Override
			public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        homeLayout.setOnTouchListener(gestureListener);
//        EA P3001
		
		String s = "[AMPUTATION, Close Reduction (CMR), CMR WITH K - WIRING, CRUSH INJURY, DEBRIDEMENT, Implant Removal , Patella fixation TBW, Radius \\/ Ulna nailing, Radius \\/ Ulna Plating, Tibia Interlock nailing or plating]";
		if(s.contains("\\/"))
		{
			s = s.replace("\\/", "/");
		}
		new SelectDataTask().execute(DBConstant.Location_Columns.CONTENT_URI);
		
		searchText = (AutoCompleteTextView) findViewById(R.id.search_text);
		// Create a SimpleCursorAdapter for the State Name field.
        /*SimpleCursorAdapter adapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    fromName, to);
        searchText.setAdapter(adapter);*/

        searchText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				searchText.setText("");
				showDialog(SEARCH);
				name = null;
			}
		});
        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        searchText.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                if(searchBy == 0)
                {
                	name = cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));
                }
                else
                {
                	name = cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID));
                }
                _id = cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_ID));              
                _ref_Id = cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_RED_ID));
                // Update the parent class's TextView
//                SA P3MX01
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
      			imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
//      		  EA P3MX01
            }
        });
	}
	String _id = null;
	String _ref_Id = null;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new SelectDataTask().execute(DBConstant.Location_Columns.CONTENT_URI);
	}
//	SA P3MX01
	public void Login(View v)
	{
		Intent i = new Intent(HomeActivity.this,SocialLoginScreen.class);
		startActivityForResult(i, HOME_LOGIN);
	}
	final static int HOME_LOGIN = 10011;
//	EA P3MX02
	public void Retry(View v)
	{
		if(isNetworkAvailable())
		{
			retryLayout.setVisibility(View.INVISIBLE);
			socialWebView.setVisibility(View.VISIBLE);
			webviewProgress.setVisibility(View.VISIBLE);
			socialWebView.loadUrl(AppConstants.URLS.SOCIAL_URL+SmartConsultant.getPreferences().getUserLoginDTO().getSign_id());
		}
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
//	SA P3001
	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//					Toast.makeText(HomeActivity.this, "Right-Left", Toast.LENGTH_SHORT).show();
					menu.toggle();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//					Toast.makeText(HomeActivity.this, "Left-Right", Toast.LENGTH_SHORT).show();
					menu.toggle();
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

            @Override
        public boolean onDown(MotionEvent e) {
              return true;
        }
    }
//	EA P3001
	
	
	public void setNameAdapter()
	{
		/*SimpleCursorAdapter adapter = 
	            new SimpleCursorAdapter(this, 
	                    android.R.layout.simple_dropdown_item_1line, null,
	                    fromName, to);*/
		//SA 10007
		//MyCursorAdapter adapter = new MyCursorAdapter(this, R.layout.edt_det_item, null, fromName, to);
		MySearchCustomAdapter adapter = new MySearchCustomAdapter(this, R.layout.edt_delete_item, null, fromName, to);
	    //EA 10007
		searchText.setAdapter(adapter);
	     // Set the CursorToStringConverter, to provide the labels for the
	        // choices to be displayed in the AutoCompleteTextView.
	        adapter.setCursorToStringConverter(new CursorToStringConverter() {
	            @Override
				public String convertToString(android.database.Cursor cursor) {
	                // Get the label for this row out of the "state" column
	                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
	            	int index = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_NAME);
	            	int index1 = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_LOCATION);
	            	int index2 = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_DATE);
	            	String strName = "";
	            	String strLoc = "";
	            	String strDate = "";
	            	if(index != -1)
	            	{
	            		strName = cursor.getString(index);
	            		strLoc = cursor.getString(index1);
	            		strDate = cursor.getString(index2);
	            		//Log.d("TIMESTAMP",  "ID:- " +cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_ID)) + "Name:- " +cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_NAME))); //VIKALP PATEL
	            		//Toast.makeText(getApplicationContext(), "ID:- " +cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_ID)) + "Name:- " +cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_NAME)), Toast.LENGTH_LONG).show();
	            	}
	                return strName+"-"+strLoc+"-"+strDate;
	            }
	        });
	      // Set the FilterQueryProvider, to run queries for choices
	        // that match the specified input.
	        adapter.setFilterQueryProvider(new FilterQueryProvider() {
	            @Override
				public Cursor runQuery(CharSequence constraint) {
	                // Search for states whose names begin with the specified letters.
	                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
	            	Cursor cursor = getContentResolver().query(DBConstant.Patient_Name_Columns.CONTENT_URI, null, 
	            			//SA 10006			
	            			//DBConstant.Patient_Name_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'" //+ 
	            			//			+ "OR " +
	            			DBConstant.Patient_Name_Columns.COLUMN_NAME_SEARCHALGO + " like '%" + SearchAlgo.getNameSearchAlgo(constraint.toString())+"%'"//ADDED 10006
	            			//EA 10006
	            						//DBConstant.Patient_Name_Columns.COLUMN_CUSTOM_ID + " like '%" + constraint.toString()+"%'"
	            						, null, "0");
	            	Log.i("SearchAlgo", constraint +"-->"+ DBConstant.Patient_Name_Columns.COLUMN_NAME_SEARCHALGO + " like '%" + SearchAlgo.getNameSearchAlgo(constraint.toString())+"%'");//ADDED 10006
	                return cursor;
	            }
	        });
	}
	
	public void setIdAdapter()
	{/*
		SimpleCursorAdapter adapter = 
	            new SimpleCursorAdapter(this, 
	                    android.R.layout.simple_dropdown_item_1line, null,
	                    fromId, to);*/
		//SA 10007
//		MyCursorAdapter adapter = new MyCursorAdapter(this, R.layout.edt_det_item, null, fromId, to);
		MySearchCustomAdapter adapter = new MySearchCustomAdapter(this, R.layout.edt_delete_item, null, fromId, to);
	    //EA 10007

	        searchText.setAdapter(adapter);
	     // Set the CursorToStringConverter, to provide the labels for the
	        // choices to be displayed in the AutoCompleteTextView.
	        adapter.setCursorToStringConverter(new CursorToStringConverter() {
	            @Override
				public String convertToString(android.database.Cursor cursor) {
	                // Get the label for this row out of the "state" column
	                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
	            	String strId = "";
	            	int index = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_NAME);
	            	int index1 = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_LOCATION);
	            	int index2 = cursor.getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_DATE);
	            	String strLoc = "";
	            	String strDate = "";
	            	if(index != -1)
	            	{
	            		strId = cursor.getString(index);
	            		strLoc = cursor.getString(index1);
	            		strDate = cursor.getString(index2);
	            	}
	                return strId+"-"+strLoc+"-"+strDate;
	            }
	        });
	      // Set the FilterQueryProvider, to run queries for choices
	        // that match the specified input.
	        adapter.setFilterQueryProvider(new FilterQueryProvider() {
	            @Override
				public Cursor runQuery(CharSequence constraint) {
	                // Search for states whose names begin with the specified letters.
	                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
	            	Cursor cursor = getContentResolver().query(DBConstant.Patient_Name_Columns.CONTENT_URI, null, 
	            						//DBConstant.Patient_Name_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%' OR " //+ 
	            						DBConstant.Patient_Name_Columns.COLUMN_CUSTOM_ID + " like '%" + constraint.toString()+"%'"
	            						, null, "1");
	                return cursor;
	            }
	        });
	}
	//SA 10007
	@SuppressWarnings("deprecation")
	public void deletePatient(final View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Delete Patient");
		alertDialog.setMessage("Are you sure want to delete it?");
		alertDialog.setButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					String _id=v.getTag().toString();
					boolean d,e,f,g= false;
					d= SmartConsultant.getApplication().getContentResolver().delete(DBConstant.Patient_Name_Columns.CONTENT_URI, "_id=?", new String[] { _id }) > 0;
//					e= SmartConsultant.getApplication().getContentResolver().delete(DBConstant.DISTINCT_CONTENT_URI, "_id=?", new String[] { _id }) > 0;
					//f= SmartConsultant.getApplication().getContentResolver().delete(DBConstant.Patient_Temp_Columns.CONTENT_URI, "_id=?", new String[] { _id }) > 0;
//					g= SmartConsultant.getApplication().getContentResolver().delete(DBConstant.Patient_Details_Columns.CONTENT_URI, "patient_id=?", new String[] { _id }) > 0;//ADDED 10008
						if(d)
							{
							CustomToast.showToastMessage(getApplicationContext(), "Patient record successfully deleted!");
							}
						else
						{
							CustomToast.showToastMessage(getApplicationContext(), "Error while deleting record!");
						}
				Log.i("DELETE", v.getTag().toString());
					}
				catch(Exception e)
				{
					Log.e("DELETE PATIENT:-",e.toString());
				}
			}
		});
		
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CustomToast.showToastMessage(getApplicationContext(), "Delete has been cancelled!");
			}
		});
		alertDialog.show();
		searchText.setText("");
	}
	//EA 10007
	
	public boolean compareString(String str1, String str2)
	{
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();
		
		if(str1.startsWith(str2))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public void startNextActivity(String _id, String name, String type)
	{
		if(type.equalsIgnoreCase("SX"))
		{
			Intent i = new Intent(this, AddSxSearchActivity.class);
			i.putExtra("id", _id);
			i.putExtra("name", name);
			i.putExtra("type", type);
			startActivity(i);
		}
		else if(type.equalsIgnoreCase("IPD"))
		{
			Intent i = new Intent(this, AddIPDSearchActivity.class);
			i.putExtra("id", _id);
			i.putExtra("name", name);
			i.putExtra("type", type);
			startActivity(i);
		}
		else if(type.equalsIgnoreCase("OPD"))
		{
			Intent i = new Intent(this, AddOPDSearchActivity.class);
			i.putExtra("id", _id);
			i.putExtra("name", name);
			i.putExtra("type", type);
			startActivity(i);
		}
	}
	
	public static final int MESSAGE = 101;
	public static final int LOCATION = 102;
	public static final int SEARCH = 103;
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id)
		{
		case MESSAGE:
			AlertDialog.Builder builder1 = new Builder(this);
			builder1.setTitle(R.string.app_name);
			builder1.setMessage("Problem in Login. \nPlease try again later.");
			builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					removeDialog(MESSAGE);
				}
			});
			return builder1.create();
		case LOCATION:
			AlertDialog.Builder locationBuilder = new Builder(this);
			locationBuilder.setTitle("Select Location");
			locationBuilder.setSingleChoiceItems(data, 0, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//myLocationCursor.move(which);
					String str = data[which];//myLocationCursor.getString(myLocationCursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
					//Toast.makeText(HomeActivity.this, str, Toast.LENGTH_LONG).show();
					removeDialog(LOCATION);
					Intent i = null;
					if(currentRecording == 0)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record SX");
						i.putExtra("type",  "0");
					}
					else if(currentRecording == 1)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record IPD");
						i.putExtra("type",  "1");
					}
					else if(currentRecording == 2)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record OPD");
						i.putExtra("type",  "2");
					}
					i.putExtra("loc", str);
					startActivity(i);
				}
			});
			/*locationBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					removeDialog(MESSAGE);
				}
			});*/
			return locationBuilder.create();
			
		case SEARCH:
			AlertDialog.Builder search = new Builder(this);
			String[] items = new String[]{"By Name", "By ID"};
			search.setSingleChoiceItems(items, searchBy, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					removeDialog(SEARCH);
					Intent i = null;
					searchBy = which;
					if(which == 0)
					{
						setNameAdapter();
						searchText.setHint("Name");//ADDED B0001
					}
					else if(which == 1)
					{
						setIdAdapter();
						searchText.setHint("Patient ID");//ADDED B0001
					}
				}
			});
			search.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					name = null;
//					searchBy = -1;
					searchBy = 0; //ADDED P3B01
				}
			});
			return search.create();
		}
		return super.onCreateDialog(id);
	}	
	public void onSearchClick(View v)
	{
		
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
			myLocationCursor = result;
			
			if(myLocationCursor != null && myLocationCursor.getCount() > 0)
			{
				data = new String[myLocationCursor.getCount()];
				
				int i = 0;
				while(myLocationCursor.moveToNext())
				{
					data[i] = myLocationCursor.getString(myLocationCursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
					i++;
				}
			}
		}

		@Override
		protected Cursor doInBackground(Uri... arg0) {
			// TODO Auto-generated method stub
			try {
				currentUri = arg0[0];
				return HomeActivity.this.getContentResolver().query(currentUri, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
	}
	public void onAddSXClick(View v)
	{
//		if(name != null && name.length() > 0 && searchText.getText().toString().length() > 0)
		if( (name != null && name.length() > 0 && searchText.getText().toString().length() > 0 )|| (searchText.getText().toString().length() > 0 && !TextUtils.isEmpty(_id))) //ADDED P3B01
		{
			Intent i = new Intent(this, AddSxSearchActivity.class);
			i.putExtra("name", _ref_Id);
			i.putExtra("_id", _id);
			i.putExtra("searchBy", searchBy);
			startActivity(i);
		}
		else
		{
			Intent i = new Intent(this, AddSxActivity.class);
			startActivity(i);
		}
		searchText.setText("");
		name = null;
		_id = null;
//		searchBy = -1; 
		searchBy = 0; //ADDED P3B01
		/*try
		{
			ServiceDelegate.postRecordedFile1(null, RequestBuilder.getUploadData(), AppConstants.URLS.BASE_URL + AppConstants.URLS.MEDIA_UPLOAD_URL);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}*/
	}
	
	int currentRecording = -1;
	public void onRecordSXClick(View v)
	{
		if(myLocationCursor != null && myLocationCursor.getCount() > 0)
		{
			currentRecording = 0;
			/*Intent i = new Intent(this, RecorderActivity.class);
			i.putExtra("title", "Record SX");
			startActivity(i);*/
			
			//showDialog(LOCATION);
			newLocationFragment = new LocationDialogFragment();
			newLocationFragment.show(getSupportFragmentManager(), "No location found.");
		}
		else
		{
			DialogFragment newFragment = new FireMissilesDialogFragment();
		    newFragment.show(getSupportFragmentManager(), "No location found.");
		}
	}
	
	public void onAddIPDClick(View v)
	{
//		if(name != null && name.length() > 0 && searchText.getText().toString().length() > 0)
		if((name != null && name.length() > 0 && searchText.getText().toString().length() > 0) || (searchText.getText().toString().length() > 0 && !TextUtils.isEmpty(_id)))//ADDED P3B01
		{
			Intent i = new Intent(this, AddIPDSearchActivity.class);
			i.putExtra("name", _ref_Id);
			i.putExtra("_id", _id);
			i.putExtra("searchBy", searchBy);
			startActivity(i);
		}
		else
		{	
			Intent i = new Intent(this, AddIPDActivity.class);
			startActivity(i);
		}
		searchText.setText("");
		name = null;
		_id = null;
//		searchBy = -1;
		searchBy = 0; //ADDED P3B01
	}
	
	DialogFragment newLocationFragment = null;
	public void onRecordIPDClick(View v)
	{
		/*Intent i = new Intent(this, RecorderActivity.class);
		i.putExtra("title", "Record IPD");
		startActivity(i);*/
		if(myLocationCursor != null && myLocationCursor.getCount() > 0)
		{
			currentRecording = 1;
			newLocationFragment = new LocationDialogFragment();
			newLocationFragment.show(getSupportFragmentManager(), "No location found.");
		}
		else
		{
			DialogFragment newFragment = new FireMissilesDialogFragment();
		    newFragment.show(getSupportFragmentManager(), "No location found.");
		}
	}
	
	public void onAddOPDClick(View v)
	{
//		if(name != null && name.length() > 0 && searchText.getText().toString().length() > 0)
		if((name != null && name.length() > 0 && searchText.getText().toString().length() > 0) || (searchText.getText().toString().length() > 0 && !TextUtils.isEmpty(_id)))//ADDED P3B01
		{
			Intent i = new Intent(this, AddOPDSearchActivity.class);
			i.putExtra("name", _ref_Id);
			i.putExtra("_id", _id);
			i.putExtra("searchBy", searchBy);
			startActivity(i);
		}
		else
		{
			Intent i = new Intent(this, AddOPDActivity.class);
			startActivity(i);
		}
		searchText.setText("");
		name = null;
		_id = null;
//		searchBy = -1;
		searchBy = 0; //ADDED P3B01
	}
	
	public void onRecordOPDClick(View v)
	{
		/*Intent i = new Intent(this, RecorderActivity.class);
		i.putExtra("title", "Record OPD");
		startActivity(i);*/
		if(myLocationCursor != null && myLocationCursor.getCount() > 0)
		{
			currentRecording = 2;
			newLocationFragment = new LocationDialogFragment();
			newLocationFragment.show(getSupportFragmentManager(), "No location found.");
		}
		else
		{
			DialogFragment newFragment = new FireMissilesDialogFragment();
		    newFragment.show(getSupportFragmentManager(), "No location found.");
		}
	}
	
	public void onSetupClick(View v)
	{
		//SA 1000A
//		Intent i = new Intent(this, SetupHomeActivity.class);
		//EA 1000A
		Intent i = new Intent(this, AddPaymentActivity.class);
		//Intent i = new Intent(this, NewHomeActivity.class);
		startActivity(i);
	}
	
	public void onExpensesClick(View v)
	{
		Intent i = new Intent(this, NewExpensesActivity.class);
		startActivity(i);
	}	
//	SA P3A01
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }
	
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	    	view.loadUrl(url);
//	    	canGoBack = true;
	            return false;
	    }
	    @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

         super.onPageStarted(view, url, favicon);
//         findViewById(R.id.progress1).setVisibility(View.VISIBLE);
         webviewProgress.setVisibility(View.VISIBLE);
        }

       @Override
       public void onPageFinished(WebView view, String url) {
//           findViewById(R.id.progress1).setVisibility(View.GONE);
    	   webviewProgress.setVisibility(View.INVISIBLE);
       }
	}
	
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (canGoBack) {
//				socialWebView.goBack();
//				canGoBack=false;
//			} else {
//				finish();
//			}
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		MenuInflater inflater = getSupportMenuInflater();
	       inflater.inflate(R.menu.menu, menu);
		 return true;
	}
//    EA P3A01
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
//        SA P3A01
        case android.R.id.home:
            menu.toggle();
            return true;
//        EA P3A01
        case R.id.mnuHelp:
        	Intent i = new Intent(this, HelpActivity.class);
        	i.putExtra("caller", "home");
        	startActivity(i);
            return true;
        case R.id.mnuSettings: //SA10002 VIKALP PATEL
        	//SA 10004
        	//Intent iSettings = new Intent(this,SetupHomeActivity.class);
        	Intent iSettings = new Intent(this,PrefsActivity.class);
        	//EA 10004
        	//startActivity(iSettings); //EA10002
        	startActivityForResult(iSettings, SETTINGS);//ADDED 10005
        	return true;
        	//SA 10004
       /* case R.id.mnuSetup: //SA10002 VIKALP PATEL
        	//SA 10004
        	//Intent iSettings = new Intent(this,SetupHomeActivity.class);
        	Intent iSetup = new Intent(this,SetupHomeActivity.class);
        	//EA 10004
        	startActivity(iSetup); //EA10002
        	return true;
        	//EA 10004*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == SETTINGS) {
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
//		SA P3MX02	
		if (requestCode == HOME_LOGIN && resultCode == RESULT_OK) {
			if(isNetworkAvailable())
			{
				View v = new View(this);
				loginLayout.setVisibility(View.INVISIBLE);
				Retry(v);
			}
		} else {
			try {
				String error = data.getStringExtra("error");
				// Toast.makeText(getApplicationContext(),
				// "Invalid login credentials", Toast.LENGTH_LONG).show();
				CustomToast.showToastMessage(SmartConsultant.getApplication()
						.getApplicationContext(), error);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		EA P3MX02
	}
    
    
    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(" No location found. \n Click on Add to manager locations")
                   .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                       @Override
					public void onClick(DialogInterface dialog, int id) {
                           // FIRE ZE MISSILES!
                    	   Intent i = new Intent(HomeActivity.this, ManageLOVActivity.class);
                   			startActivity(i);
                       }
                   })
                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       @Override
					public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    
    public class LocationDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
        	AlertDialog.Builder locationBuilder = new Builder(HomeActivity.this);
			locationBuilder.setTitle("Select Location");
			locationBuilder.setSingleChoiceItems(data, 0, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//myLocationCursor.move(which);
					String str = data[which];//myLocationCursor.getString(myLocationCursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
					//Toast.makeText(HomeActivity.this, str, Toast.LENGTH_LONG).show();
					Intent i = null;
					if(currentRecording == 0)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record SX");
						i.putExtra("type",  "0");
					}
					else if(currentRecording == 1)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record IPD");
						i.putExtra("type",  "1");
					}
					else if(currentRecording == 2)
					{
						i = new Intent(HomeActivity.this, RecorderActivity.class);
						i.putExtra("title", "Record OPD");
						i.putExtra("type",  "2");
					}
					i.putExtra("loc", str);
					startActivity(i);
					newLocationFragment.dismiss();
				}
			});
			return locationBuilder.create();
        }
    }
}
