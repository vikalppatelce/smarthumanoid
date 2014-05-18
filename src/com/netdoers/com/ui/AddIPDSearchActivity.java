/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM      		:- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION			DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001   	   VIKALP PATEL    09/01/2014							INITCAP VALUES WHILE SAVING INTO DATABASE.
 * 10002	   VIKALP PATEL    09/01/2014       					FIX SEARCHING WITH ID.
 * 10003       VIKALP PATEL    10/01/2014       					APPLYING FULLSCREEN TO THE APPLICATION
 * 10004       VIKALP PATEL    13/01/2014      					    APPLYING PROPER VALIDATION ON MANDATORY FIELDS.
 * 10005       VIKALP PATEL    13/01/2014     						TOOLTIP FEATURE
 * 10006       VIKALP PATEL    13/01/2014       					ADD SEARCH ALGO ON PATIENT NAME.
 * 10007       VIKALP PATEL    15/01/2014       					INDIVIDUAL SETTINGS
 * 10008       VIKALP PATEL    16/01/2014                       	SURGICAL NOTES PICTURE ON OFF
 * 10009       VIKALP PATEL    17/01/2014                       	SAVING SURGICAL NOTES IN DATABASE TABLE(PATIENT DETAILS)
 * 10011       VIKALP PATEL    17/01/2014                       	ADDED SERVICE TYPE COLUMN IN PATIENT TEMP TABLE
 * 10012       VIKALP PATEL    17/01/2014							UPDATING ONLY NON EMPTY DATA IN PATIENT TABLE & SERVICE TABLE
 * 10013       VIKALP PATEL    20/01/2014                       	LAYOUT RE-DESIGNED
 * 10014	   VIKALP PATEL    20/01/2014							CUSTOM-TOAST ON SAVE
 * 10015	   VIKALP PATEL    21/01/2014							TOOLTIP EVENT CHANGE TO ON FOCUS
 * 10017       VIKALP PATEL    21/01/2014							TAKE PICTURE REPLACES WITH ADD PICTURE
 * 10018 	   VIKALP PATEL    23/01/2014						    IMAGE COMPRESSION ON PICTURES
 * 1000A       VIKALP PATEL    04/02/2014	     RELEASE			ENABLED SURGICAL PICTURES BY DEFAULT
 * 1000A       VIKALP PATEL    04/02/2014        RELEASE        	CLEAR GALLERY ON SAVE
 * 1000A-1     VIKALP PATEL    06/02/2014        RELEASE            ADD NAME OF MEDIA IN PATIENT DETAILS TABLE
 * 1000B       VIKALP PATEL    07/02/2014        RELEASE        	ADD VIDEO OPTION
 * U0001       VIKALP PATEL    28/02/2014        UIX            	RECORD TRAVERSAL
 * U0002       VIKALP PATEL    01/03/2014        UIX            	GESTURE FLING LIKE VIEW PAGER
 * B0001       VIKALP PATEL    01/03/2014        BUG           		SUPPRESSED THROWABLE EXCEPTION ON CAMERA
 * BOA01       VIKALP PATEL    05/03/2014        BUG                INTENT FOR PICTURE ONLY
 * P2M01       VIKALP PATEL    11/03/2014        BUG             	SUPPRESSED IMAGE LOADING ON SELECTING SCREEN
 * P2B01       VIKALP PATEL    11/03/2014        BUG                INTENT FOR VIDEOS ONLY
 * P2B02       VIKALP PATEL    11/03/2014        BUG                IMPORT ONLY MP4 VIDEOS
 * P2B03       VIKALP PATEL    12/03/2014        BUG                COPY IMPORT VIDEOS
 * P3A02       VIKALP PATEL    21/03/2014        PHASE-III      ADDED SHARED OPTION
 * P3MX01      VIKALP PATEL    27/03/2014        IMPROVE      	ADDED SWITCH INSTEAD OF CHECKBOX AND RADIOGROUP
 * P3MX02	   VIKALP PATEL    28/03/2014        PRIVACY      	ADDED DIALOG FOR SHARING CONTENT
 * P3U001      VIKALP PATEL    07/04/2014        USABILITY      NOT LETTING LOCATION GET FOCUS ON TRAVERSAL AFTER SAVE
 * P3B04       VIKALP PATEL    15/04/2014        BUG            SHARING ON WITH PREV & NEXT POP UP AGREEING
 * P3B05       VIKALP PATEL    21/04/2014        BUG            DUPLICATE VALUES IN PATIENT NAME TABLE
 * P3B06       VIKALP PATEL    23/04/2014        BUG            UPDATE VALUES IN PATIENT TEMP VALUES
 * P3B07       VIKALP PATEL    24/04/2014        BUG            SERVICE DATE WAS SYS CURRENT DATE WHEN SELECTED FROM SEARCH
 * P3B08       VIKALP PATEL    24/04/2014        BUG            SHOWING CALENDAR WITH SERVICE DATE ONLY
 * P3B09       VIKALP PATEL    25/04/2014        BUG            EMERGENCY NULL POINTER FROM DATABASE
 * P3A03       VIKALP PATEL    06/05/2014        PHASE-III      PUBLIC OR PRIVATE SHARING PRIVACY CONTENT
 * P3B10       VIKALP PATEL    16/05/2014        BUG            PRIVATE SHARING VISIBILITY  
 * --------------------------------------------------------------------------------------------------------------------       
 */
package com.netdoers.com.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;
import android.widget.Toast;

import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.contactpicker.Contact;
import com.netdoers.com.contactpicker.ContactManager;
import com.netdoers.com.contactpicker.FlowLayout;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.dto.DBConstant.Patient_Contact_Columns;
import com.netdoers.com.dto.DBConstant.Patient_Temp_Contact_Columns;
import com.netdoers.com.utils.AccordionView;
import com.netdoers.com.utils.AppConstants;
import com.netdoers.com.utils.GalleryMedia;
import com.netdoers.com.utils.ImageCompression;
import com.netdoers.com.utils.InitCap;
import com.netdoers.com.utils.SearchAlgo;
import com.netdoers.com.utils.Switch;
import com.smarthumanoid.com.BuildConfig;
import com.smarthumanoid.com.R;

@SuppressLint("ValidFragment")
public class AddIPDSearchActivity extends FragmentActivity{

	EditText addSxId;
	EditText addSxName;
	AutoCompleteTextView  addSxLocation;
	EditText addSxAge;
	EditText addIpdTotalCount;
	EditText addSxDiagnose;
	EditText addSxDate;
	AutoCompleteTextView addSxWard;
	Switch addSxEmergency;//ADDED P3MX01
	AutoCompleteTextView addIpdReferredBy;
	AutoCompleteTextView addSxType;
	EditText note;
	Switch radioSexGroup;//ADDED P3MX01
	RadioButton btnMale;
	RadioButton btnFemale;
	InitCap initCap;//ADDED 10001
	SharedPreferences pref;//ADDED 10003
	public static final int SECSETTINGS = 1007;//ADDED 10007
	Gallery gallery;//SA 10008
	LinearLayout surgicalPic;
	Uri currentFileUri;
	final int REQUEST_CAMERA = 1001;
	final int REQUEST_SMARTHUMANOID_CAMERA = 1002;
	final int REQUEST_VIDEO = 1011;//ADDED 1000B
	final int REQUEST_VIDEO_IMPORT=1012;//ADDED 1000B
	ArrayList<Bitmap> data;
	ArrayList<String> paths;
	ImagesAdapter imagesAdapter = null;//EA 10008
	Cursor patientCursor;//ADDED 10009
	String p_id=null;//ADDED 10009
	public static final int PIC = 117;//SA 10017
	public static final int IMPORT_PICTURE = 118;
	Uri outputFileUri;//EA 10017
	String compressedPath;//ADDED 10018
	Cursor serviceCursor;//SA U0001
	TextView txtTitle;
	ImageView next;
	Cursor pathsCursor;
	ImageView prev;
	boolean boolInsert = false;
	int currentImage = 0;
	int getImageCount = 0;
	int currentRecord = 0;//EA U0001
	//SA U0002
	final static ViewConfiguration vc = ViewConfiguration.get(SmartConsultant.getApplication().getApplicationContext());
	private static final int SWIPE_MIN_DISTANCE = vc.getScaledPagingTouchSlop();
    private static final int SWIPE_MAX_OFF_PATH = vc.getScaledMinimumFlingVelocity();
    private static final int SWIPE_THRESHOLD_VELOCITY = vc.getScaledTouchSlop();
    private GestureDetector gestureDetector;
    LinearLayout flingLayout;
    View.OnTouchListener gestureListener;	//EA U0002
    Switch isSharing;//ADDED P3A02
    boolean isSharingBoolean = true; //ADDED P3B04
    Switch isSharingPrivate;//SA P3A03
	FlowLayout chipsBoxLayout;
	ArrayList<Contact> contactsShareDetail;
	ArrayList<String> contactsSharePhone;
	public static final int IMPORT_CONTACT = 1010;
	int intContactRepeat = 0;
	AccordionView contactAccordionView;
	Cursor contactsCursor;
	boolean isSharingPrivateBoolean = true;
	boolean deleteAllContacts = false;
	boolean repeatCheck = false;//EA P3A03
	
	//final static int[] to = new int[] { android.R.id.text1 };
	final static int[] to = new int[] { R.id.txtText};
    final static String[] from = new String[] { DBConstant.Patient_Columns.COLUMN_NAME };
    final static String[] projection = { 
    										DBConstant.Patient_Columns.COLUMN_ID, 
    										DBConstant.Patient_Columns.COLUMN_NAME, 
    										DBConstant.Patient_Columns.COLUMN_TYPE,
    										DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE
    									};
	
    SimpleCursorAdapter adapter;
	
	SimpleCursorAdapter locationAdapter;
	SimpleCursorAdapter wardAdapter;
	SimpleCursorAdapter typeAdapter;
	SimpleCursorAdapter referredByAdapter;
	
	String _id; // 0
	String custId; // 1
	String name; // 2
	String location;//8
	String age; //3
	String totalCount; //4
	String diagnosis; //5
	String date;//11
	String ward;//12
	String emergency;//13
	String ref;//7
	String type;//6
	String noteStr;//17
	String sex;
	String shared;//ADDED P3A02
	String sharedPrivate;//ADDED P3A03
	
	int searchBy = 0;
	
	String _pre_id = null;
	
	String _name_id = null;
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
		//SA 10013
		//setContentView(R.layout.add_ipd);
//		setContentView(R.layout.new_add_ipd);
		setContentView(R.layout.trav_add_ipd);//EDITED U0001
		//EA 10013
		initializeData();
		
		_name_id = getIntent().getStringExtra("name");
		 _pre_id = getIntent().getStringExtra("_id");
		
		searchBy = getIntent().getIntExtra("searchBy", 0);
		initCap = new InitCap();//ADDED 10001
		Cursor c = null;
		
		if(searchBy == 0)
		{
			//SU 10001
			//c = getContentResolver().query(DBConstant.Patient_Temp_Columns.CONTENT_URI, null, "_id=?", new String[]{_name_id}, null);
			c = getContentResolver().query(DBConstant.Patient_Temp_Columns.CONTENT_URI, null, "_id=?", new String[]{_pre_id}, null);
			//EU 10001
		}
		else if(searchBy == 1)
		{
			//SU 10001
			//c = getContentResolver().query(DBConstant.Patient_Temp_Columns.CONTENT_URI, null, "_id=?", new String[]{_name_id}, null);
			c = getContentResolver().query(DBConstant.Patient_Temp_Columns.CONTENT_URI, null, "_id=?", new String[]{_pre_id}, null);
			//EU 10001
		}
		
		if(c != null && c.getCount() > 0)
		{
			c.moveToFirst();
			custId = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_CUSTOM_ID));
			name = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_NAME));
			age = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_AGE));
			totalCount = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_TOTALCOUNT));
			diagnosis = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_DIAGNOSIS));
			type = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_TYPE));
			ref = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_REF));
			location = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_LOCATION));
			date = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_DATE));
			ward = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_WARD));
			emergency = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_EMERGENCY));
			shared = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_SHARING));//ADDED P3A02
			sharedPrivate = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_SHARING_PRIVATE));//ADDED P3A03
			noteStr = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_NOTE));
			sex = c.getString(c.getColumnIndex(DBConstant.Patient_Temp_Columns.COLUMN_SEX));
			c.close();
		}
		
		addSxId = (EditText) findViewById(R.id.add_sx_id);
		addSxId.setText(custId);
		addSxName = (EditText) findViewById(R.id.add_sx_name);
		//setNameAdapter();
		addSxName.setText(name);
		isSharing = (Switch)findViewById(R.id.is_shared);//ADDED P3A02
		isSharingPrivate = (Switch)findViewById(R.id.is_shared_privacy);//ADDED P3A03
		//SA P3A03
		contactAccordionView = (AccordionView)findViewById(R.id.accordion_view);
		contactsShareDetail = new ArrayList<Contact>();
		chipsBoxLayout = (FlowLayout)findViewById(R.id.chips_box_layout);
		contactsSharePhone = new ArrayList<String>();
		//EA P3A03
//		SA P3MX02
		isSharing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
//				if (isChecked)
				if (isChecked && isSharingBoolean)//ADDED P3B04
				{
					DialogFragment newFragment = new FireSharingDialogFragment();
				    newFragment.show(getSupportFragmentManager(), "No location found.");
//				    isSharingPrivate.setEnabled(true);//ADDED P3A03
				    isSharingPrivate.setVisibility(View.VISIBLE);//ADD P3B10
				}
//				SA P3A03
				if(isChecked && !isSharingPrivateBoolean)
				{
//					isSharingPrivate.setEnabled(true);
					isSharingPrivate.setVisibility(View.VISIBLE);//ADD P3B10
				}
				if(!isChecked)
				{
//					isSharingPrivate.setEnabled(false);
					isSharingPrivate.setVisibility(View.GONE);//ADD P3B10
					isSharingPrivate.setChecked(false);
					contactAccordionView.setVisibility(View.GONE);
					chipsBoxLayout.setVisibility(View.GONE);
//					contactsShareDetail.removeAll(contactsShareDetail);
//					contactsSharePhone.removeAll(contactsSharePhone);
//					chipsBoxLayout.removeAllViews();
				}
//				EA P3A03
			}
		});
//		EA P3MX02
//		SA P3A03
		isSharingPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
						if (isChecked && isSharingPrivateBoolean) {
							contactAccordionView.setVisibility(View.VISIBLE);
							chipsBoxLayout.setVisibility(View.VISIBLE);
//							Intent contactPicker = new Intent(AddSxActivity.this, ContactManager.class);
//							startActivityForResult(contactPicker, IMPORT_CONTACT);
						} else {
							contactAccordionView.setVisibility(View.GONE);
							chipsBoxLayout.setVisibility(View.GONE);
							contactsShareDetail.clear();
							contactsSharePhone.clear();
							deleteAllContacts = true;
						}
			}
		});
//		EA P3A03
		// SA U0001
		txtTitle = (TextView) findViewById(R.id.add_sx_location_text);

		next = (ImageView) findViewById(R.id.btn_header_next);
		prev = (ImageView) findViewById(R.id.btn_header_prev);
		// EA U0001
		
		gallery = (Gallery)findViewById(R.id.imagePreview);//SA 10008
		surgicalPic = (LinearLayout)findViewById(R.id.surgicalPic);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//SU 1000B
				String extension = "";
				int i = paths.get(arg2).lastIndexOf('.');
				if (i >= 0) {
				    extension = paths.get(arg2).substring(i+1);
				}
				if(extension.equalsIgnoreCase("mp4"))
				{
					File file = new File(paths.get(arg2));
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "video/*");
					startActivity(intent);
				}
				else
				{
					Intent intent = new Intent(AddIPDSearchActivity.this, DetailedImage.class);
					intent.putExtra("url", paths.get(arg2));
					startActivity(intent);	
				}
				//EU 1000B
			}
		});
		//EA 10008
		
		addSxLocation = (AutoCompleteTextView) findViewById(R.id.add_sx_location);
		addSxLocation.setText(location);
		setLocationAdapter();
		
		addSxAge = (EditText) findViewById(R.id.add_sx_age);		
		addSxAge.setText(age);
		addIpdTotalCount = (EditText) findViewById(R.id.add_sx_totalCount);		
		addIpdTotalCount.setText(totalCount);
		
		addSxDiagnose = (EditText) findViewById(R.id.add_sx_diagnose);		
		addSxDiagnose.setText(diagnosis);
		
		addSxDate = (EditText) findViewById(R.id.add_sx_date);
		if(date != null && date.length() > 0)
		{
			addSxDate.setText(date);
		}
//		SC P3B07 
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		String date = sdf.format(new Date(System.currentTimeMillis()));
//		if(date.contains("/"))
//		{
//			date = date.replace("/", "-");
//		}
//		addSxDate.setText(date);
//		EC P3B07
		
		addSxWard = (AutoCompleteTextView) findViewById(R.id.add_sx_ward);
		addSxWard.setText(ward);
		setWardAdapter();
		
		addSxEmergency = (Switch) findViewById(R.id.add_sx_manager_emergency);//ADDED P3MX01
//		SA P3B09
		try {
			if (Integer.parseInt(emergency) > 0) {
				addSxEmergency.setChecked(true);
			} else {
				addSxEmergency.setChecked(false);
			}
		} catch (Exception e) {
			addSxEmergency.setChecked(false);
		}
//		EA P3B09
//      SA P3A02
		if(Integer.parseInt(shared) > 0)
		{
			isSharing.setChecked(true);
		}
		else
		{
			isSharing.setChecked(false);
		}
//		EA P3A02
//      SA P3A03
		if(Integer.parseInt(sharedPrivate) > 0)
		{
			isSharingPrivate.setChecked(true);
		}
		else
		{
			isSharingPrivate.setChecked(false);
		}
//		EA P3A03

		addIpdReferredBy = (AutoCompleteTextView) findViewById(R.id.add_sx_referred_by);
		addIpdReferredBy.setText(ref);
		setReferredByAdapter();
		
		addSxType = (AutoCompleteTextView) findViewById(R.id.add_sx_patient_type);
		addSxType.setText(type);
		setTypeAdapter();
		
		note = (EditText) findViewById(R.id.add_sx_note);
		note.setText(noteStr);
		
		radioSexGroup = (Switch) findViewById(R.id.radioSex);//ADDED P3MX01
		
//		btnMale = (RadioButton) findViewById(R.id.radioMale);
//		btnFemale = (RadioButton) findViewById(R.id.radioFemale);
		
		if(sex!= null && sex.equalsIgnoreCase("male"))
		{
//			btnMale.setChecked(true);
//			btnFemale.setChecked(false);
			radioSexGroup.setChecked(false);//ADDED P3MX01
		}
		else if(sex!= null && sex.equalsIgnoreCase("female"))
		{
//			btnMale.setChecked(false);
//			btnFemale.setChecked(true);
			radioSexGroup.setChecked(true);//ADDED P3MX01
		}
		
		loadServiceCursor();//ADDED U0001
		// SA U0002
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		flingLayout = (LinearLayout) findViewById(R.id.flingLayout);
		flingLayout.setOnTouchListener(gestureListener);
		// EA U0002
		
		//SA 10005
		if(pref.getBoolean("prefIsToolTip", false)|| pref.getBoolean("prefIsIPDToolTip", false)) //EDITED 10007
		{
			onToolTipOn();
		}
		//EA 10005

		// SA 10008
		data = new ArrayList<Bitmap>();
		imagesAdapter = new ImagesAdapter(this, data);
		gallery.setAdapter(imagesAdapter);
		paths = new ArrayList<String>();
		//SC 1000A
		/*if (pref.getBoolean("prefIsIPDSurgicalNotes", false)) {
			surgicalPic.setVisibility(1);
			gallery.setVisibility(1);
		}*/
		//EC 1000A
		// EA 10008
		
//		loadSearchPathsCursor();//ADDED U0001 //COMMENTED P2M01
	}
	
	ArrayList<String> strLocation = new ArrayList<String>();
	ArrayList<String> strWard = new ArrayList<String>();

	ArrayList<String> strType = new ArrayList<String>();
	ArrayList<String> strRef = new ArrayList<String>();

//	SA P3MX02
	public class FireSharingDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(Html.fromHtml(getString(R.string.shareprivacycontent)))
                   .setPositiveButton(getString(R.string.shareprivacybutton), new DialogInterface.OnClickListener() {
                       @Override
					public void onClick(DialogInterface dialog, int id) {
                           // FIRE ZE MISSILES!
                       }
                   })
                   .setNegativeButton(getString(R.string.shareprivacybuttoncancel), new DialogInterface.OnClickListener() {
                       @Override
					public void onClick(DialogInterface dialog, int id) {
                           // FIRE ZE MISSILES!
                    	   isSharing.setChecked(false);
                       }
                   })
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    	@Override
                    	public boolean onKey (DialogInterface dialog, int keyCode, KeyEvent event) {
                    		if (keyCode == KeyEvent.KEYCODE_BACK) {
                    			return true;
                    		}
                    		return false;
                    	}
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
//	EA P3MX02
	//SA U0002
		class MyGestureDetector extends SimpleOnGestureListener {
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	            	View v = new View(SmartConsultant.getApplication().getApplicationContext());
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                    onNext(v);
	                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
	                    onPrev(v);
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
		//EA U0002

	
	
	//SA U0001
	public void loadServiceCursor()
	{
		String service_type = "1";
		serviceCursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, null, DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE + "=?", new String[]{service_type}, null);	
		currentRecord = Math.max(serviceCursor.getCount(), 0);
		setText();
	}
	public void setText()
	{
		txtTitle.setText("IPD Search " + (currentRecord+1) +" of " + (serviceCursor.getCount()+1));
	}
	public void onPrev(View v)
	{
		Log.e(">>>>>>>>> onPrev >>>", currentRecord+"");
		if(currentRecord > 0)
		{
			currentRecord--;
			setText();
			populateTextFields(currentRecord);
			serviceCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			loadContactsCursor(currentRecord);//ADDED P3A03
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							AddIPDSearchActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						AddIPDSearchActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
		}
	}
	public void onFirst(View v)
	{
		Log.e(">>>>>>>>> onFirst >>>", currentRecord+"");
		if(currentRecord > 0)
		{
			currentRecord = 0;
			setText();
			populateTextFields(currentRecord);
			serviceCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			loadContactsCursor(currentRecord);//ADDED P3A03
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							AddIPDSearchActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						AddIPDSearchActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
		}
	}
	
	public void onNext(View v)
	{
		Log.e(">>>>>>>>> onNext >>>", currentRecord+"");
		currentRecord++;
		if(currentRecord < serviceCursor.getCount())
		{
			
			setText();
			populateTextFields(currentRecord);
			serviceCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			loadContactsCursor(currentRecord);//ADDED P3A03
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data.removeAll(data);//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							AddIPDSearchActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						AddIPDSearchActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
		}
		else
		{
			_id = null;
			currentRecord = Math.max(serviceCursor.getCount(), 0);
			setText();
			isSharingBoolean = true; //ADDED P3B04
			isSharingPrivateBoolean = true; //ADDED P3A03
			clearField();
		}
	}
	
	public void onLast(View v)
	{
		Log.e(">>>>>>>>> onLast >>>", currentRecord+"");
		_id = null;
		currentRecord = serviceCursor.getCount();
		{
			currentRecord = Math.max(serviceCursor.getCount(), 0);
			clearField();
			isSharingBoolean = true; //ADDED P3B04
			isSharingPrivateBoolean = true; //ADDED P3A03
			setText();
		}
	}
	
	public void populateTextFields(int index)
	{
		if(serviceCursor != null && serviceCursor.getCount() > index)
		{
			serviceCursor.moveToPosition(index);
			_id 		= serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID));
			String name = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));
			String location = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_LOCATION));
			String age = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_AGE));
			String count = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TOTALCOUNT));
			String diagnose = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS));
			String date = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DATE));
			String ward = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_WARD));
			String referredby = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_REF));
			String _note = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NOTE));
			String type = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TYPE));
			String emergency = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_EMERGENCY));
			String sex = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SEX));
			String isSharingEnabled = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING));//ADDED P3A02
			String isSharingPrivateEnabled = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING_PRIVATE));//ADDED P3A03
			
			
			addSxId.setText(_id); 
			addSxName.setText(name); 
			addSxLocation.setText(location); 
			addSxAge.setText(age); 
			addIpdTotalCount.setText(count); 
			addSxDiagnose.setText(diagnose); 
			addSxDate.setText(date);
			addSxWard.setText(ward);
			addIpdReferredBy.setText(referredby);
			addSxType.setText(type);
			note.setText(_note);
			if (Integer.parseInt(emergency) > 0) {
				addSxEmergency.setChecked(true);
			} else {
				addSxEmergency.setChecked(false);
			}
			if (sex.equalsIgnoreCase("male")) {
				radioSexGroup.setChecked(false);//ADDED P3MX01
//				btnMale.setChecked(true);
			} else {
				radioSexGroup.setChecked(true);//ADDED P3MX01
//				btnFemale.setChecked(true);
			}
			isSharingBoolean = false; //ADDED P3B04
			isSharingPrivateBoolean = false; //ADDED P3A03
//			SA P3A02
			if (Integer.parseInt(isSharingEnabled) > 0) {
				isSharing.setChecked(true);
			} else {
				isSharing.setChecked(false);
			}
//			EA P3A02
//			SA P3A03
			if (Integer.parseInt(isSharingPrivateEnabled) > 0) {
				isSharingPrivate.setChecked(true);
			} else {
				isSharingPrivate.setChecked(false);
			}
//			EA P3A03
			isSharingBoolean = true;//ADDED P3B04
			isSharingPrivateBoolean = true; //ADDED P3A03
		}
	}

	public void loadPathsCursor(int index)
	{
		paths = new ArrayList<String>();
		if(serviceCursor != null && serviceCursor.getCount() > index)
		{
			serviceCursor.moveToPosition(index);
			String id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
			pathsCursor = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID + "=?", new String[]{id}, null);
			if(pathsCursor != null && pathsCursor.getCount() > 0)
			{
				int colIndex = pathsCursor.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL);
				while(pathsCursor.moveToNext())
				{
					paths.add(pathsCursor.getString(colIndex));
				}
			}
		}
	}
//	SA P3A03
	public void loadContactsCursor(int index)
	{
		contactsShareDetail.clear();
		contactsSharePhone.clear();
		contactsShareDetail = new ArrayList<Contact>();
		contactsSharePhone = new ArrayList<String>();
		deleteAllContacts = false;
		if(serviceCursor != null && serviceCursor.getCount() > index)
		{
			serviceCursor.moveToPosition(index);
			String id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
			contactsCursor = getContentResolver().query(DBConstant.Patient_Contact_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID + "=?", new String[]{id}, null);
			
			FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(2, 2, 2, 2);
			
			chipsBoxLayout.removeAllViews();
			
			if(contactsCursor != null && contactsCursor.getCount() > 0)
			{
				contactAccordionView.setVisibility(View.VISIBLE);
				chipsBoxLayout.setVisibility(View.VISIBLE);
				
				int colNoIndex = contactsCursor.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER);
				int colNameIndex = contactsCursor.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME);
				Contact c;
				while(contactsCursor.moveToNext())
				{
					c = new Contact();
					c.setContactName(contactsCursor.getString(colNameIndex));
					c.setContactNumber(contactsCursor.getString(colNoIndex));
					contactsShareDetail.add(c);
					contactsSharePhone.add(contactsCursor.getString(colNoIndex));
				}
				
				Iterator<Contact> iterContacts = contactsShareDetail.iterator();
				while (iterContacts.hasNext()) {
						final Contact contact = iterContacts.next();
						
						TextView t = new TextView(AddIPDSearchActivity.this);
						t.setLayoutParams(params);
						t.setTextSize(16f);
						t.setPadding(3, 3, 3, 3);
						t.setText(contact.getContactName());
						t.setTextColor(Color.WHITE);
						t.setBackgroundResource(R.drawable.chips_bg);
						t.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.drawable.ic_delete_light), null);
						chipsBoxLayout.addView(t);
						t.setTag(contact);
						t.setOnClickListener(new View.OnClickListener() {
						@Override
					    public void onClick(View v) {
								// TODO Auto-generated method stub
						try {
							contactsShareDetail.remove(contact);
							contactsSharePhone.remove(contact.getContactNumber());
							chipsBoxLayout.removeView(v);
							if (contactsShareDetail.size() == 0) {
								deleteAllContacts = true;
							}
							} catch (Exception e) {
							Log.e("Remove Chips", e.toString());
								}
							}
						});
				}
				
			}
		}
	}
//	SA P3A03
	
	public void createAdapter()
	{
		data = new ArrayList<Bitmap>();
		imagesAdapter = new ImagesAdapter(this, data);
		gallery.setAdapter(imagesAdapter);
	}
	//EA U0001
	@Override
	protected void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	}
	//SA U0001
	public void loadSearchPathsCursor()
	{
		paths = new ArrayList<String>();
			pathsCursor = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID + "=?", new String[]{_pre_id}, null);
			if(pathsCursor != null && pathsCursor.getCount() > 0)
			{
				//paths = new ArrayList<String>();
				int colIndex = pathsCursor.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL);
				while(pathsCursor.moveToNext())
				{
					paths.add(pathsCursor.getString(colIndex));
				}
			}
			createAdapter();
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
					Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
					AddIPDSearchActivity.this.data.add(bm);
				}
				imagesAdapter.notifyDataSetChanged();
			}
	}
	//EA U0001
	
	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	}
	
	public void initializeData()
	{
		strLocation = SmartConsultant.getApplication().loadLocation();
		strWard = SmartConsultant.getApplication().loadWard();
		strType = SmartConsultant.getApplication().loadType();
		strRef = SmartConsultant.getApplication().loadRef();
	}
	
	public void addLocation()
	{
		String s = initCap.toTitleCase(addSxLocation.getText().toString());//EDITED 10001
		if(s!= null && s.length() > 0 && !strLocation.contains(new String(s)))
		{
			addContent(DBConstant.Location_Columns.CONTENT_URI, s);
		}
	}
	
	
	public void addWard()
	{
		String s = initCap.toTitleCase(addSxWard.getText().toString());//EDITED 10001
		if(s!= null && s.length() > 0 && !strWard.contains(new String(s)))
		{
			addContent(DBConstant.Ward_Columns.CONTENT_URI, s);
		}
	}
	
	
	
	public void addType()
	{
		String s = initCap.toTitleCase(addSxType.getText().toString());//EDITED 10001
		if(s!= null && s.length() > 0 && !strType.contains(new String(s)))
		{
			addContent(DBConstant.Types_Columns.CONTENT_URI, s);
		}
	}
	
	public void addRef()
	{
		String s = initCap.toTitleCase(addIpdReferredBy.getText().toString());//EDITED 10001
		if(s!= null && s.length() > 0 && !strRef.contains(new String(s)))
		{
			addContent(DBConstant.Ref_Columns.CONTENT_URI, s);
		}
	}
	
	
	
	public void addContent(Uri uri, String str)
	{
		ContentValues values = new ContentValues();
		str = initCap.toTitleCase(str);//ADDED 10001
		values.put(DBConstant.Location_Columns.COLUMN_NAME, str);
		values.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 0);
		getContentResolver().insert(uri, values);
	}
//	SA P3A03
	public void onContactAdd(View v)
	{
		Intent contactPicker = new Intent(AddIPDSearchActivity.this, ContactManager.class);
		startActivityForResult(contactPicker, IMPORT_CONTACT);
	}
	public void onContactClear(View v)
	{
		contactsShareDetail.clear();
		contactsSharePhone.clear();
		deleteAllContacts = true;
		contactsShareDetail = new ArrayList<Contact>();
		contactsSharePhone = new ArrayList<String>();
		chipsBoxLayout.removeAllViews();
	}
//	EA P3A03
	
	public void startManagerScreen(int index, int reqCode)
	{
		Intent i = new Intent(this, ManageLOVActivity.class);
		i.putExtra("index", index);
		startActivityForResult(i, reqCode);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String currentSelection = null;
		if(data != null)
		{
			currentSelection = data.getStringExtra("data");
		}
		if(resultCode == RESULT_OK && currentSelection != null)
		{
			switch(requestCode)
			{
				case 100:
					location = currentSelection;
					break;
				case 104: 
					ward = currentSelection;
					break;
				case 107:
					ref = currentSelection;
					break;
				case 106:
					type = currentSelection;
					break;
						
			}
			clearDataStructure();
			onDataLoaded(currentSelection);
		}
		
		
		// SA 10008
		Bitmap bm = null;
		if (resultCode == RESULT_OK) {
			//SA 10017
			if (requestCode == IMPORT_PICTURE) {
				//SA U0001
				if(currentRecord+1 < serviceCursor.getCount()+1)
				{
					currentImage = paths.size();	
				}
				//EA U0001
				
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaColumns.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				getImagePath();
				try {
					copy(new File(picturePath), new File(currentFileUri.getPath()));
					} 
				catch (IOException e) 
				{
					Log.e("IMPORT_PICTURE", e.toString());
				}
		         
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				btmapOptions.inSampleSize = 2;
				bm = BitmapFactory.decodeFile(picturePath,btmapOptions);
				AddIPDSearchActivity.this.data.add(bm);
				imagesAdapter.notifyDataSetChanged();
				compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
				galleryAddPic();
				paths.add(compressedPath);//EDITED 10018
			}
			//EA 10017
			
			if (requestCode == REQUEST_CAMERA) {
				if(resultCode == RESULT_OK)
	    		{
					//SA U0001
					if(currentRecord+1 < serviceCursor.getCount()+1)
					{
						currentImage = paths.size();	
					}
					//EA U0001
	    			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 2;
					try
					{
						bm = BitmapFactory.decodeFile(currentFileUri.getPath(), btmapOptions);	
					}
					catch(Exception e)
					{
						Log.e("currentFileUri", "currentFileUri"+e.toString());
//						throw new UndeclaredThrowableException(e); COMMENTED B0001
					}
					AddIPDSearchActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					try
					{
						compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
						galleryAddPic();
						paths.add(compressedPath);//EDITED 10018
					}
					catch(Exception e)
					{
//						throw new UndeclaredThrowableException(e); COMMENTED B0001
					}
	    		}
			}

			if (requestCode == REQUEST_SMARTHUMANOID_CAMERA) {

				if (resultCode == RESULT_OK) {
					//SA U0001
					if(currentRecord+1 < serviceCursor.getCount()+1)
					{
						currentImage = paths.size();	
					}
					//EA U0001
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 2;
					bm = BitmapFactory.decodeFile(currentFileUri.getPath(),
							btmapOptions);
					AddIPDSearchActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
					galleryAddPic();
					paths.add(compressedPath);//EDITED 10018
				} else {
					Toast.makeText(getApplicationContext(),
							"Error while taking picture!", Toast.LENGTH_SHORT)
							.show();
				}
			}
			//SA 1000B
			if(requestCode == REQUEST_VIDEO)
			{
				if(resultCode == RESULT_OK)
				{
					//SA U0001
					if(currentRecord+1 < serviceCursor.getCount()+1)
					{
						currentImage = paths.size();	
					}
					//EA U0001
					//BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					//btmapOptions.inSampleSize = 2;
					//bm = BitmapFactory.decodeFile(currentFileUri.getPath(),btmapOptions);
					bm= ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(), MediaStore.Video.Thumbnails.MICRO_KIND);
					try {
						Resources r = getResources();
						Drawable[] layers = new Drawable[2];
						layers[0] = new BitmapDrawable(bm);
						layers[1] = r.getDrawable(R.drawable.play_icon);
						LayerDrawable layerDrawable = new LayerDrawable(layers);
						AddIPDSearchActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
					} catch (Exception e) {
						Log.e("Creating Thumbnail",e.toString());
					}
					imagesAdapter.notifyDataSetChanged();
					galleryAddPic();
					paths.add(currentFileUri.getPath());
				}
				else
				{
					CustomToast.showToastMessage(getApplicationContext(), "Error while taking video!");
				}
			}
			
			if(requestCode == REQUEST_VIDEO_IMPORT)
			{
				if(resultCode == RESULT_OK)
				{
//					SC P2B02
					//SA U0001
//					if(currentRecord+1 < serviceCursor.getCount()+1)
//					{
//						currentImage = paths.size();	
//					}
					//EA U0001
//					Uri selectedImage = data.getData();
//					String[] filePathColumn = { MediaColumns.DATA };
//					Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//					cursor.moveToFirst();
//					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//					String videoPath = cursor.getString(columnIndex);
//					cursor.close();
//					getVideoPath();
//					try {
//						copy(new File(videoPath),
//								new File(currentFileUri.getPath()));
//					} catch (IOException e) {
//						Log.e("IMPORT_VIDEO", e.toString());
//					}
//
//					bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND);
//
//					try {
//						Resources r = getResources();
//						Drawable[] layers = new Drawable[2];
//						layers[0] = new BitmapDrawable(bm);
//						layers[1] = r.getDrawable(R.drawable.play_icon);
//						LayerDrawable layerDrawable = new LayerDrawable(layers);
//						AddIPDActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
//					} catch (Exception e) {
//						Log.e("Creating Thumbnail",e.toString());
//					}
//					imagesAdapter.notifyDataSetChanged();
//					galleryAddPic();
//					paths.add(currentFileUri.getPath());
//					EC P2B02 SA P2B02
					Uri selectedImage = data.getData();
					String[] filePathColumn = { MediaColumns.DATA };
					Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
					cursor.moveToFirst();
					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String videoPath = cursor.getString(columnIndex);
					cursor.close();
					
					if(videoPath.endsWith(".mp4"))
					{
						if(currentRecord+1 < serviceCursor.getCount()+1)
						{
							currentImage = paths.size();	
						}
						
						getVideoPath();
//						SC P2B03
//						try {
//							copy(new File(videoPath),new File(currentFileUri.getPath()));
//						} catch (Exception e) {
//							Log.e("COPY VIDEO", e.toString());
//						}

//						bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND); EC P2B03
						bm = ThumbnailUtils.createVideoThumbnail(videoPath,MediaStore.Video.Thumbnails.MICRO_KIND);//ADDED P2B03

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							AddIPDSearchActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						} catch (Exception e) {
							Log.e("Creating Thumbnail",e.toString());
						}
//						SA P2B03
						try {
							new CopyMediaTask(videoPath,currentFileUri).execute();
						} catch (Exception e) {
							Log.e("COPY VIDEO", e.toString());
						}
//						EA P2B03
						imagesAdapter.notifyDataSetChanged();
						galleryAddPic();
						paths.add(currentFileUri.getPath());
					}
					else{
						CustomToast.showToastMessage(getApplicationContext(),"Please add mp4 videos only!");	
					}
//					EA P2B02
				}
				else
				{
					CustomToast.showToastMessage(getApplicationContext(), "Error while importing video!");
				}
			}
			//EA 1000B
//			SA P3A03
		if (requestCode == IMPORT_CONTACT) {
		  if (resultCode == RESULT_OK) {
			if(data.hasExtra(Contact.CONTACTS_DATA)) {
				
				FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT, FlowLayout.LayoutParams.WRAP_CONTENT);
				params.setMargins(2, 2, 2, 2);
				
				ArrayList<Contact> contacts = data.getParcelableArrayListExtra(Contact.CONTACTS_DATA);
				if(contacts != null) {
					intContactRepeat += 1;
					if (intContactRepeat == 2) {
						repeatCheck = true;
					}
					if (currentRecord + 1 < serviceCursor.getCount() + 1) {
						repeatCheck = true;
					}
					
					Iterator<Contact> iterContacts = contacts.iterator();
					while (iterContacts.hasNext()) {
							final Contact contact = iterContacts.next();
							if(!repeatCheck)
							{
								contactsShareDetail.add(contact);
								contactsSharePhone.add(contact.getContactNumber());
								TextView t = new TextView(AddIPDSearchActivity.this);
								t.setLayoutParams(params);
								t.setTextSize(16f);
								t.setPadding(3, 3, 3, 3);
								t.setText(contact.getContactName());
								t.setTextColor(Color.WHITE);
								t.setBackgroundResource(R.drawable.chips_bg);
								t.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.drawable.ic_delete_light), null);
								chipsBoxLayout.addView(t);
								t.setTag(contact);
								t.setOnClickListener(new View.OnClickListener() {
								@Override
							    public void onClick(View v) {
										// TODO Auto-generated method stub
								try {
									contactsShareDetail.remove(contact);
									contactsSharePhone.remove(contact.getContactNumber());
									chipsBoxLayout.removeView(v);
									if (contactsShareDetail.size() == 0) {
										deleteAllContacts = true;
									}
									} catch (Exception e) {
									Log.e("Remove Chips", e.toString());
										}
									}
								});
							}
							else if(repeatCheck && !contactsSharePhone.contains(contact.getContactNumber()))
							{
								    contactsShareDetail.add(contact);
								    contactsSharePhone.add(contact.getContactNumber());
									TextView t = new TextView(AddIPDSearchActivity.this);
									t.setLayoutParams(params);
									t.setTextSize(16f);
									t.setPadding(3, 3, 3, 3);
									t.setText(contact.getContactName());
									t.setTextColor(Color.WHITE);
									t.setBackgroundResource(R.drawable.chips_bg);
									t.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(R.drawable.ic_delete_light), null);
									chipsBoxLayout.addView(t);
									t.setTag(contact);
									t.setOnClickListener(new View.OnClickListener() {
									@Override
								    public void onClick(View v) {
											// TODO Auto-generated method stub
									try {
										contactsShareDetail.remove(contact);
										contactsSharePhone.remove(contact.getContactNumber());
										chipsBoxLayout.removeView(v);
										if (contactsShareDetail.size() == 0) {
											deleteAllContacts = true;
										}
										} catch (Exception e) {
										Log.e("Remove Chips", e.toString());
										}
									  }
									});
							}
					}
				}
			}
		  }
		}
//			EA P3A03
		}
		// EA 10008
		// SA 10007
		if (requestCode == SECSETTINGS) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
		// EA 10007
	}
	public void onManageLocation(View v)
	{
		startManagerScreen(0, 100);
	}
		
	public void onManageWard(View v)
	{
		startManagerScreen(2, 104);
	}
//	SA P2B03
	private class CopyMediaTask extends AsyncTask<Void, Void, String>
	{
		String videoPath;
		Uri currentFileUri;
		public CopyMediaTask(String v,Uri c)
		{
			videoPath = v;
			currentFileUri = c;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			try {
				copy(new File(videoPath),new File(currentFileUri.getPath()));
			} 
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
//	EA P2B03
	public void onManageReferredBy(View v)
	{
		startManagerScreen(5, 107);
	}
	
	public void onManagePatientType(View v)
	{
		startManagerScreen(4, 106);
	}
	public void clearDataStructure()
	{
	}
	public void onTime(View v)
	{
		DialogFragment newFragment = new FromDatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "From Date");
	}
	
	//SA 10008
		public void onTakePicture(View v)
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			getImagePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	  		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
	  		if(pref.getBoolean("prefSmartHumanoidCamera", false))
	  		{
	  			Intent cameraIntent = new Intent(getApplicationContext(),CameraActivity.class);
	  	        cameraIntent.putExtra("FILE_URI", outputFileUri.toString());
	  	        startActivityForResult(cameraIntent, REQUEST_SMARTHUMANOID_CAMERA);
	  		}
	  		else
	  		{
	  			startActivityForResult(intent, REQUEST_CAMERA);
	  		}
		}

		public static String getVideoName()
		{
			String name = "smartConsultant";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				name = sdf.format(new Date(System.currentTimeMillis()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return name;
		}
		
		private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		    File f = new File(currentFileUri.getPath());
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
		}
		
	//EA 10008

		//SA 10017
		@SuppressWarnings("deprecation")
		public void onAddPicture(View v)
		{
			showDialog(PIC);
		}
		
		//SA 1000B
		public void onTakeVideo(View v)
		{
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			getVideoPath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			startActivityForResult(intent, REQUEST_VIDEO);
		}
		
		public void onImportVideo(View v)
		{
//			SA P2B01
//			Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//			getVideoPath();
//			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//			startActivityForResult(intent, REQUEST_VIDEO_IMPORT);
			
			Intent intent = new Intent();
			intent.setType("video/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO_IMPORT);
//			EA P2B01
		}
		public void getVideoPath() {
			File imageDirectory = null;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) {
				imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH);
			} else {
				imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
			}

			imageDirectory.mkdirs();
			File tempFile = new File(imageDirectory, getVideoName()
					+ AppConstants.VIDEO_EXTENSION);
			outputFileUri = Uri.fromFile(tempFile);
			currentFileUri = outputFileUri;
		}
		// EA 1000B

		
		public void getImagePath()
		{
			File imageDirectory =null;
			String state = Environment.getExternalStorageState();
			if (Environment.MEDIA_MOUNTED.equals(state)) 
			{
				imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH);
			}
			else
			{
				imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
			}

			imageDirectory.mkdirs();
			File tempFile = new File(imageDirectory, getVideoName()+ AppConstants.EXTENSION);
			outputFileUri = Uri.fromFile( tempFile );
			currentFileUri = outputFileUri;
		}
		
		public void copy(File src, File dst) throws IOException {
		    InputStream in = new FileInputStream(src);
		    OutputStream out = new FileOutputStream(dst);

		    // Transfer bytes from in to out
		    byte[] buf = new byte[1024];
		    int len;
		    while ((len = in.read(buf)) > 0) {
		        out.write(buf, 0, len);
		    }
		    in.close();
		    out.close();
		}
		
		public void onImportPicture(View v)
		{
//			SA B0A01
//			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//			startActivityForResult(i, IMPORT_PICTURE);
			
			Intent intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMPORT_PICTURE);
//			EA B0A01
		}
		
		@Override
		protected void onPrepareDialog(int id, Dialog dialog) {
			switch (id) {
			case PIC:
				final AlertDialog alertDialog = (AlertDialog) dialog;
				Button import_picture = (Button) alertDialog.findViewById(R.id.import_picture);
				Button take_picture = (Button) alertDialog.findViewById(R.id.take_picture);
				Button take_video = (Button)alertDialog.findViewById(R.id.take_video);//ADDED 1000B
				Button import_video = (Button)alertDialog.findViewById(R.id.import_video);//ADDED 1000B
				import_picture.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onImportPicture(v);
						alertDialog.dismiss();
					}
				});
				take_picture.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onTakePicture(v);
						alertDialog.dismiss();
					}
				});
				//SA 1000B
				take_video.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onTakeVideo(v);
						alertDialog.dismiss();
					}
				});
				import_video.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						onImportVideo(v);
					alertDialog.dismiss();	
					}
				});
				//EA 1000B
				break;
			}
		}
			
		//EA 10017

	
	class FromDatePickerFragment extends DialogFragment implements OnDateSetListener
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
//			SA P3B08
			int day,mnth,yr;
			try {
				String[] strDate = addSxDate.getText().toString().split("-");
				day = Integer.parseInt(strDate[0]) == 1 ? Integer.parseInt("0"+strDate[0]) : Integer.parseInt(strDate[0]);  
				mnth = Integer.parseInt(strDate[1]) == 1 ? Integer.parseInt("0"+strDate[1])-1 : Integer.parseInt(strDate[1])-1;
				yr = Integer.parseInt(strDate[2]);
			} catch (Exception e) {
				final Calendar c = Calendar.getInstance();
				yr = c.get(Calendar.YEAR);
				mnth = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
			}
//			EA P3B08
			// Create a new instance of TimePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, yr, mnth, day);
		}
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			// TODO Auto-generated method stub
			String date;
			monthOfYear++;
			if(dayOfMonth < 10)
			{
				date = "0" + dayOfMonth+"-";
			}
			else 
			{
				date = dayOfMonth+"-";
			}
			if(monthOfYear < 10)
			{
				date += "0" + monthOfYear+"-";
			}
			else
			{
				date += monthOfYear+"-";
			}
			
			date += year;
			//addSxDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
			addSxDate.setText(date);
		}
	}
	public void save()
	{
		//Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
		Bundle b = new Bundle();
		b.putString("message", "Saving IPD");
		showDialog(LOADING, b);
		updateServicePatient();//ADDED 10012
		
		//COMMENTED 10012
		
		/*ContentValues contentValues = new ContentValues();
		ContentValues contentValuesTemp = new ContentValues();
		
		String nm = initCap.toTitleCase(addSxName.getText().toString()); // ADDED 10001
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_NAME, nm);//EDITED 10001
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_NAME, nm);//EDITED 10001
		ContentValues name = new ContentValues();
		name.put(DBConstant.Patient_Name_Columns.COLUMN_NAME, nm);//EDITED 10001
		name.put(DBConstant.Patient_Name_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());//EDITED 10001
		//SA 10006
				name.put(DBConstant.Patient_Name_Columns.COLUMN_NAME_SEARCHALGO, initCap.toTitleCase(SearchAlgo.getNameSearchAlgo(nm)));
		//EA 10006
		
		String loc = "";
		//if(lstLocation != null && lstLocation.size() > 0)
		{
			loc = initCap.toTitleCase(addSxLocation.getText().toString());//EDITED 10001//loc = lstLocation.get(addSxLocation.getSelectedItemPosition());
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_LOCATION, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_LOCATION, loc);
		name.put(DBConstant.Patient_Name_Columns.COLUMN_LOCATION, loc);
		addLocation();
		
		/*contentValues.put(DBConstant.Patient_Columns.COLUMN_AGE, addSxAge.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_AGE, addSxAge.getText().toString());
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_TOTALCOUNT, addIpdTotalCount.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_TOTALCOUNT, addIpdTotalCount.getText().toString());
		
		String strDiag = initCap.toTitleCase(addSxDiagnose.getText().toString());
		contentValues.put(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS, strDiag);//EDITED 10001
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_DIAGNOSIS, strDiag);//EDITED 10001
		
		loc = "";
		if(!addSxDate.getText().toString().equals("Date : DD-MM-YYYY"))
		{
			loc = addSxDate.getText().toString(); 
		}
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_DATE, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_DATE, loc);
		name.put(DBConstant.Patient_Name_Columns.COLUMN_DATE, loc);
		
		//getContentResolver().update(DBConstant.Patient_Name_Columns.CONTENT_URI, name, "_id=?", new String[]{_pre_id});
		
		loc = "";
		//if(lstWard != null && lstWard.size() > 0)
		{
			loc = initCap.toTitleCase(addSxWard.getText().toString());//EDITED 10001//loc = lstWard.get(addSxWard.getSelectedItemPosition());
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_WARD, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_WARD, loc);
		addWard();
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked());
		
		loc = "";
		//if(lstReferredBy != null && lstReferredBy.size() > 0)
		{
			loc = addIpdReferredBy.getText().toString();//loc = lstReferredBy.get(addIpdReferredBy.getSelectedItemPosition());
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_REF, loc);	
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_REF, loc);
		addRef();
		
		loc = "";
		//if(lstPatientType != null && lstPatientType.size() > 0)
		{
			loc = initCap.toTitleCase(addSxType.getText().toString());//EDITED 10001//loc = lstPatientType.get(addSxType.getSelectedItemPosition());
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_TYPE, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_TYPE, loc);
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE, 1);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SERVICE_TYPE, 1);//ADDED 10011
		
		addType();
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_NOTE, note.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_NOTE, note.getText().toString());

		String sex = null;
		if(btnMale.isChecked())
		{
			sex = "Male";
		}
		else if(btnFemale.isChecked())
		{
			sex = "Female";
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SEX, sex);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SEX, sex);
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SX_WATCH, "");
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SYNC_STATUS, 0);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SYNC_STATUS, 0);
		
			getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI, contentValues, "_id=?", new String[]{_pre_id});
			
		
		if(searchBy == 0)
		{
			getContentResolver().update(DBConstant.Patient_Temp_Columns.CONTENT_URI, contentValuesTemp, "_id=?", new String[]{_pre_id});
		}
		else if(searchBy == 1)
		{
			getContentResolver().update(DBConstant.Patient_Temp_Columns.CONTENT_URI, contentValuesTemp, "_id=?", new String[]{_pre_id});
		}
		*/
		
		//COMMENTED 10012
		//SA 10009
		if(paths.size() > 0)
		{
			
			patientCursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, null, null, null, null);
			if (patientCursor != null && patientCursor.getCount() > 0) 
			{
			//SA U0001
			if(currentRecord+1 < serviceCursor.getCount()+1)
			{
				serviceCursor.moveToPosition(currentRecord);
				_id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
			}
			else
			{
				patientCursor.moveToLast();
				_id = patientCursor.getString(patientCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
				String name_  = patientCursor.getString(patientCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));	
			}
			
				//SA U0001
				if(boolInsert)
				{
					for (int i = 0; i < paths.size(); i++) { 
						String url = paths.get(i);
						String file_name = url.substring(url.lastIndexOf("/") + 1);
						ContentValues temp = new ContentValues();
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID, _id);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_NAME, file_name);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_URL, url);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE, 1);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 0);
						getContentResolver().insert(DBConstant.Patient_Details_Columns.CONTENT_URI, temp);
						Log.i("Paitent Details", "Patient ID:-"+_id + " --> "+url);
					}
				}

				
				if(currentImage+1 > getImageCount && !boolInsert)
				{
					for (int i = currentImage; i < paths.size(); i++) { 
						String url = paths.get(i);
						String file_name = url.substring(url.lastIndexOf("/") + 1);
						ContentValues temp = new ContentValues();
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID, _id);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_NAME, file_name);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_URL, url);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE, 1);
						temp.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 0);
						getContentResolver().insert(DBConstant.Patient_Details_Columns.CONTENT_URI, temp);
						Log.i("Updating Paitent Details", "Patient ID:-"+_id + " --> "+url);
					}
					
				}
				//EA U0001
			}
		}
		//EA 10009
		removeDialog(LOADING);
		//SA 10014
		CustomToast.showToastMessage(this, "IPD Saved");
		//EA 10014
		clearField();

	}
	
	//SA 10012
	@SuppressWarnings("unused")
	public void updateServicePatient()
	{
		String nm="";
		String reUse="";
		ContentValues serviceValues = new ContentValues();
		ContentValues patientValues = new ContentValues();
		ContentValues searchValues = new ContentValues();
		if(isDataThere(addSxName))
		{
			nm=initCap.toTitleCase(addSxName.getText().toString());
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_NAME, nm);
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_NAME, nm);
			searchValues.put(DBConstant.Patient_Name_Columns.COLUMN_NAME, nm);
            searchValues.put(DBConstant.Patient_Name_Columns.COLUMN_NAME_SEARCHALGO, initCap.toTitleCase(SearchAlgo.getNameSearchAlgo(nm)));
		}
		if(isDataThere(addSxId))
		{
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		}
		if(isDataThere(addSxLocation))
		{
			reUse="";
			reUse = initCap.toTitleCase(addSxLocation.getText().toString());
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_LOCATION, reUse);
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_LOCATION, reUse);
			searchValues.put(DBConstant.Patient_Name_Columns.COLUMN_LOCATION, reUse);
			addLocation();
		}
		if(isDataThere(addSxAge))
		{
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_AGE, addSxAge.getText().toString());
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_AGE, addSxAge.getText().toString());
		}
		if(isDataThere(addIpdTotalCount))
		{
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_TOTALCOUNT, addIpdTotalCount.getText().toString());
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_TOTALCOUNT, addIpdTotalCount.getText().toString());
		}
		if(isDataThere(addSxDiagnose))
		{
			reUse="";
			reUse=initCap.toTitleCase(addSxDiagnose.getText().toString());
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS, reUse);
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_DIAGNOSIS, reUse);
		}
		if(isDataThere(addSxWard))
		{
			reUse="";
			reUse = initCap.toTitleCase(addSxWard.getText().toString());
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_WARD, reUse);
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_WARD, reUse);
			addWard();
		}
		if(isDataThere(addIpdReferredBy))
		{
			reUse="";
			reUse= addIpdReferredBy.getText().toString();
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_REF, reUse);	
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_REF, reUse);
			addRef();	
		}
		if(isDataThere(addSxType))
		{
			reUse="";
			reUse = initCap.toTitleCase(addSxType.getText().toString());
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_TYPE, reUse);
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_TYPE, reUse);
			addType();
		}
		if(isDataThere(note))
		{
			serviceValues.put(DBConstant.Patient_Columns.COLUMN_NOTE, note.getText().toString());
			patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_NOTE, note.getText().toString());
		}
		
		reUse="";
		if(radioSexGroup.isChecked())//ADDED P3MX01
		{
			sex = "Female";
		}
		else// if(btnFemale.isChecked()) ADDED P3MX01
		{
			sex = "Male";
		}
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SEX, sex);
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_SEX, sex);
		
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SHARING, isSharing.isChecked() ? "1" : "0");//SA P3A02
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_SHARING, isSharing.isChecked() ? "1" : "0" );//EA P3A02
		
		//SA P3A03
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SHARING_PRIVATE, isSharingPrivate.isChecked() ? "1" : "0");
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_SHARING_PRIVATE, isSharingPrivate.isChecked() ? "1" : "0");
		//EA P3A03
		
		reUse="";
		if(!addSxDate.getText().toString().equals("Date : DD-MM-YYYY"))
		{
			reUse = addSxDate.getText().toString(); 
		}
		
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked() ? "1" : "0");
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked() ? "1" : "0");
		
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_DATE, reUse);
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_DATE, reUse);
		searchValues.put(DBConstant.Patient_Name_Columns.COLUMN_DATE, reUse);
		
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SX_WATCH, "");
		
		//SA U0001
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE, "1");
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_SERVICE_TYPE, "1");
		//EA U0001
		
		serviceValues.put(DBConstant.Patient_Columns.COLUMN_SYNC_STATUS, 0);
		patientValues.put(DBConstant.Patient_Temp_Columns.COLUMN_SYNC_STATUS, 0);
		
		
		//SA U0001
				if(currentRecord+1 < serviceCursor.getCount()+1)
				{
					serviceCursor.moveToPosition(currentRecord);
					String update_id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
					getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI,serviceValues,DBConstant.Patient_Columns.COLUMN_ID + "=?",new String[]{update_id});
					getContentResolver().update(DBConstant.Patient_Name_Columns.CONTENT_URI,searchValues,DBConstant.Patient_Name_Columns.COLUMN_ID + "=?",new String[]{update_id});
					getContentResolver().update(DBConstant.Patient_Temp_Columns.CONTENT_URI,patientValues,DBConstant.Patient_Temp_Columns.COLUMN_ID + "=?",new String[]{update_id});
					
					getImageCount = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID + "=?",new String[]{update_id}, null).getCount();
					boolInsert = false;					
				}
				else
				{
					getContentResolver().insert(DBConstant.Patient_Columns.CONTENT_URI,serviceValues);
//					getContentResolver().insert(DBConstant.Patient_Name_Columns.CONTENT_URI, searchValues);
					getContentResolver().update(DBConstant.Patient_Name_Columns.CONTENT_URI,searchValues,DBConstant.Patient_Name_Columns.COLUMN_NAME + "=?",new String[]{nm});//ADDED P3B05
//					Uri uri = getContentResolver().insert(DBConstant.Patient_Temp_Columns.CONTENT_URI, patientValues);
					getContentResolver().update(DBConstant.Patient_Temp_Columns.CONTENT_URI,patientValues,DBConstant.Patient_Temp_Columns.COLUMN_ID + "=?",new String[]{_pre_id});//ADDED P3B06
					boolInsert = true;
				}
				//EA U0001
				//SA P3A03
//				if(isSharingPrivate.isChecked())
//				{
					if(contactsShareDetail.size() > 0 || deleteAllContacts)
					{
						patientCursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, null, null, null, null);
						if (patientCursor != null && patientCursor.getCount() > 0) 
						{
							if(currentRecord+1 < serviceCursor.getCount()+1)
							{
								serviceCursor.moveToPosition(currentRecord);
								_id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
							}
							else
							{
								patientCursor.moveToLast();
								_id = patientCursor.getString(patientCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
							}
							if(boolInsert)
							{
								for (int i = 0; i < contactsShareDetail.size(); i++) { 
									String contactName = contactsShareDetail.get(i).getContactName();
									String contactNo = contactsShareDetail.get(i).getContactNumber();
									ContentValues temp = new ContentValues();
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID, _id);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME, contactName);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER, contactNo);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_TYPE, 0);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS, 0);
									getContentResolver().insert(DBConstant.Patient_Contact_Columns.CONTENT_URI, temp);
									getContentResolver().insert(DBConstant.Patient_Temp_Contact_Columns.CONTENT_URI, temp);
								}
							}
							if(!boolInsert)
							{
								boolean deleteContact	  = SmartConsultant.getApplication().getContentResolver().delete(DBConstant.Patient_Contact_Columns.CONTENT_URI, Patient_Contact_Columns.COLUMN_PATIENT_ID+"=?", new String[]{ _id }) > 0;
								boolean deleteTempContact = SmartConsultant.getApplication().getContentResolver().delete(DBConstant.Patient_Temp_Contact_Columns.CONTENT_URI, Patient_Temp_Contact_Columns.COLUMN_PATIENT_ID+"=?", new String[]{ _id }) > 0;
								if (BuildConfig.DEBUG) {
									Log.i("Delete Contact", "Delete Contact : "+ deleteContact + " Delete Temp Contact : "+ deleteTempContact);
								}
								
								for (int i = 0; i < contactsShareDetail.size(); i++) { 
									String contactName = contactsShareDetail.get(i).getContactName();
									String contactNo = contactsShareDetail.get(i).getContactNumber();
									ContentValues temp = new ContentValues();
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID, _id);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME, contactName);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER, contactNo);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_TYPE, 0);
									temp.put(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS, 0);
									getContentResolver().insert(DBConstant.Patient_Contact_Columns.CONTENT_URI, temp);
									getContentResolver().insert(DBConstant.Patient_Temp_Contact_Columns.CONTENT_URI, temp);
								}
							}
						}
					}
		        //EA P3A03
	}
	
	public boolean isDataThere(EditText e)
	{
		
		if(e.getText()!=null && e.getText().toString().length() > 0)
		{
			return true;
		}
		return false;
	}
	//EA 10012
	
	//SA 10005
	public void onToolTipOn()
	{
		final Context c = getApplicationContext();
		
		//SU 10015
		addSxName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tpatientname));	
			}
		});
		
		addSxLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tlocation));
			}
		});
		addSxId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					  CustomToast.showToastMessage(c, getString(R.string.tpatientid));
			}
		});
		
		addSxAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tage));
			}
		});
		
		addSxDiagnose.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tdiagnose));
			}
		});
		
		addIpdReferredBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tipdreferred));
			}
		});
		
		addSxType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tpatienttype));
			}
		});
		
		addSxWard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tward));
			}
		});
		
		addIpdTotalCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.ttotal));
			}
		});
		note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
		public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tnote));
			}
		});
		//EU 10015
	}
	//EA 10005
	
	
	public void clearField()
	{
		addSxId.setText("");
		addSxName.setText("");
		//if(lstLocation != null && lstLocation.size() > 0)
		{
			addSxLocation.setText("");//addSxLocation.setSelection(0);
		}
		
		addSxAge.setText("");
		

		addSxDiagnose.setText("");


		//addSxDate.setText("Date : DD-MM-YYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if(date.contains("/"))
		{
			date = date.replace("/", "-");
		}
		addSxDate.setText(date);
		isSharing.setChecked(false);//ADDED P3A02		
		{
			addSxWard.setText("");//addSxWard.setSelection(0);
		}

		addSxEmergency.setChecked(false);
		radioSexGroup.setChecked(false);//ADDED P3MX01
		contactAccordionView.setVisibility(View.GONE);//SA P3A03
		contactsShareDetail.clear();
		contactsSharePhone.clear();
		deleteAllContacts = false;
		chipsBoxLayout.removeAllViews();
		chipsBoxLayout.setVisibility(View.GONE);//EA P3A03
		{
			addSxType.setText("");//addSxType.setSelection(0);
		}
		
		String loc = "";
		addIpdTotalCount.setText("");
				
		loc = "";
		{
			addIpdReferredBy.setText("");//addIpdReferredBy.setSelection(0);
		}
				
		note.setText("");
//		SA P3U001	
			addSxLocation.setFocusable(false);
			addSxLocation.setFocusableInTouchMode(true);
//		EA P3U001	

		//SA 1000A
		AddIPDSearchActivity.this.data.clear();
		paths.clear();
		imagesAdapter.notifyDataSetChanged();
		//EA 1000A
	}


	public void onSave(View v)
	{
		if((addSxName.getText() != null && addSxName.getText().toString().length() > 0) 
				&& (addSxLocation.getText() != null && addSxLocation.getText().toString().length() > 0))
		{
			save();
			loadServiceCursor();//SA U0001
		}
		else
		{
			validateFields();//ADDED 10004
		}
	}
	
	//SA 10004
	public void validateFields()
    {
    	if((addSxName.getText().toString() == null || addSxName.getText().toString()
				.length() == 0))
		{
			addSxName.requestFocus();
			addSxName.setError("Please enter patient name");
		}
		if((addSxLocation.getText().toString() == null || addSxLocation.getText()
				.toString().length() == 0))
		{
			addSxLocation.requestFocus();
			addSxLocation.setError("Please enter hospital name");
		}
    }
	//EA 10004
	
	public void onSaveMain(View v)
	{
		if((addSxName.getText() != null && addSxName.getText().toString().length() > 0) 
				&& (addSxLocation.getText() != null && addSxLocation.getText().toString().length() > 0))
		{
			save();
			finish();
		}
		else
		{
			validateFields();//ADDED 10004
		}
	}
	
	public void onCancel(View v)
	{
		finish();
	}
	public void showHideNote(View v)
	{
		if(note.getVisibility() == View.GONE)
		{
			note.setVisibility(View.VISIBLE);
		}
		else
		{
			note.setVisibility(View.GONE);
		}
	}
	
	public void resetData()
	{
	}
	public static final int LOADING = 101;
	@Override
	protected Dialog onCreateDialog(int id, Bundle b) {
//		String msg = b.getString("message");
		switch(id)
		{
			case LOADING:
				ProgressDialog loadingDialog = new ProgressDialog(this);
				loadingDialog.setMessage("Please wait...");
				loadingDialog.setCancelable(false);
				return loadingDialog;
				//SA 10017
			 case PIC:
				 LayoutInflater inflater = LayoutInflater.from(this);
				 View dialogview = inflater.inflate(R.layout.add_pic_dialog, null);
				 AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
				 //dialogbuilder.setTitle("Add Picture");
				 dialogbuilder.setView(dialogview);
				 return dialogbuilder.create();
				 //EA 10017
		}
		return super.onCreateDialog(id);
	}
	public void setLocationAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Location_Columns.COLUMN_NAME };
	    
		locationAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
				new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxLocation.setAdapter(locationAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxLocation.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
}
        });
locationAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        locationAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
            	Cursor cursor = getContentResolver().query(DBConstant.Location_Columns.CONTENT_URI, null, DBConstant.Location_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}

	public void setWardAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Ward_Columns.COLUMN_NAME };
		wardAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
				new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxWard.setAdapter(wardAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxWard.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {

            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        wardAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Ward_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        wardAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Ward_Columns.CONTENT_URI, null, DBConstant.Ward_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}

	
	public void setReferredByAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Ref_Columns.COLUMN_NAME };
	    
		referredByAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
				new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addIpdReferredBy.setAdapter(referredByAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addIpdReferredBy.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {

            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        referredByAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Ref_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        referredByAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                Cursor cursor = getContentResolver().query(DBConstant.Ref_Columns.CONTENT_URI, null, DBConstant.Ref_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	public void setTypeAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Types_Columns.COLUMN_NAME };
	    
		typeAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
				new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxType.setAdapter(typeAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxType.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {

            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        typeAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Types_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        typeAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                Cursor cursor = getContentResolver().query(DBConstant.Types_Columns.CONTENT_URI, null, DBConstant.Types_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}

	public void onDataLoaded(String data)
	{
		{
			{
				addSxLocation.setText(data);//addSxLocation.setSelection(index);
				addSxLocation.requestFocus();
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(locationAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = addSxLocation;
				handler.sendMessageDelayed(msg, 200); 
			}
		}
		
		{
			{
				addSxWard.setText(data);//addSxWard.setSelection(index);
				addSxWard.requestFocus();
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(wardAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = addSxWard;
				handler.sendMessageDelayed(msg, 200);
			}
		}
		
		{
			{
				addSxType.setText(data);//addSxType.setSelection(index);
				addSxType.requestFocus();
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(typeAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = addSxType;
				handler.sendMessageDelayed(msg, 200);
			}
		}
		{
			{
				addIpdReferredBy.setText(data);//addIpdReferredBy.setSelection(index);
				addIpdReferredBy.requestFocus();
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(referredByAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = addIpdReferredBy;
				handler.sendMessageDelayed(msg, 200);
			}
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
        	i.putExtra("caller", "ipd");
        	startActivity(i);
            return true;
          //SA 10007
		case com.smarthumanoid.com.R.id.mnuSettings:
			Intent in = new Intent(this, PrefsSecondaryActivity.class);
			in.putExtra("caller", "ipd");
			startActivityForResult(in, SECSETTINGS);
			return true;
			//EA 10007
        default:
            return super.onOptionsItemSelected(item);
        }
    }   

}
