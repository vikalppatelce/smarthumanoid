/* HISTORY
 * CATEGORY			 :- ACTIVITY
 * DEVELOPER		 :- VIKALP PATEL
 * AIM      		 :- ADD IPD ACTIVITY
 * DESCRIPTION		 :- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001	   VIKALP PATEL    09/01/2014       				FIX SEARCHING WITH ID.
 * 10002       VIKALP PATEL    10/01/2014      					ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
 * 10003       VIKALP PATEL    13/01/2014       				APPLYING PROPER VALIDATION ON MANDATORY FIELDS.
 * 10004       VIKALP PATEL    13/01/2014       				TOOLTIP FEATURE
 * 10005       VIKALP PATEL    13/01/2014      					ADD SEARCH ALGO ON PATIENT NAME.
 * 10006       VIKALP PATEL    15/01/2014       				INDIVIDUAL SETTINGS
 * 10007       VIKALP PATEL    16/01/2014                       SURGICAL NOTES PICTURE ON OFF
 * 10008       VIKALP PATEL    17/01/2014                       SAVING SURGICAL NOTES IN DATABASE TABLE(PATIENT DETAILS)
 * 10009       VIKALP PATEL    17/01/2014                       ADDED SERVICE TYPE COLUMN IN PATIENT TEMP TABLE
 * 10013       VIKALP PATEL    18/01/2014                       SAVE AND RESTORE STATE OF ACTIVITY
 * 10013-1	   VIKALP PATEL    20/01/2014						LAYOUT RE-DESIGNED	
 * 10014	   VIKALP PATEL    20/01/2014						CUSTOM-TOAST ON SAVE
 * 10015	   VIKALP PATEL    21/01/2014						TOOLTIP EVENT CHANGE TO ONFOCUS
 * 10017       VIKALP PATEL    23/01/2014						TAKE PICTURE REPLACES WITH ADD PICTURE
 * 10018 	   VIKALP PATEL    23/01/2014						IMAGE COMPRESSION ON PICTURES
 * 1000A       VIKALP PATEL    04/02/2014	     RELEASE		ENABLED SURGICAL PICTURES BY DEFAULT
 * 1000A       VIKALP PATEL    04/02/2014        RELEASE    	CLEAR GALLERY ON SAVE
 * 1000A-1     VIKALP PATEL    06/02/2014        RELEASE        ADD NAME OF MEDIA IN PATIENT DETAIL TABLE
 * 1000B       VIKALP PATEL    07/02/2014        RELEASE        ADD VIDEO OPTION
 * U0001       VIKALP PATEL    28/02/2014        UIX            RECORD TRAVERSAL
 * U0002       VIKALP PATEL    01/03/2014        UIX            GESTURE FLING LIKE VIEW PAGER
 * B0001       VIKALP PATEL    01/03/2014        BUG            SUPPRESSED THROWABLE EXCEPTION ON CAMERA
 * BOA01       VIKALP PATEL    05/03/2014        BUG            INTENT FOR PICTURE ONLY
 * P2B01       VIKALP PATEL    11/03/2014        BUG            INTENT FOR VIDEOS ONLY
 * P2B02       VIKALP PATEL    11/03/2014        BUG            IMPORT MP4 VIDEOS ONLY
 * P2B03	   VIKALP PATEL    12/03/2014        BUG            COPY IMPORT VIDEOS
 * P3A02       VIKALP PATEL    21/03/2014        PHASE-III      ADDED SHARED OPTION
 * P3MX01      VIKALP PATEL    27/03/2014        IMPROVE      	ADDED SWITCH INSTEAD OF CHECKBOX AND RADIOGROUP
 * P3MX02	   VIKALP PATEL    28/03/2014        PRIVACY      	ADDED DIALOG FOR SHARING CONTENT
 * P3U001      VIKALP PATEL    07/04/2014        USABILITY      NOT LETTING LOCATION GET FOCUS ON TRAVERSAL AFTER SAVE
 * P3B04       VIKALP PATEL    15/04/2014        BUG            SHARING ON WITH PREV & NEXT POP UP AGREEING
 * P3B05       VIKALP PATEL    24/04/2014        BUG            SHOWING CALENDAR WITH SERVICE DATE ONLY
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
import android.os.StatFs;
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
public class AddSxActivity extends FragmentActivity{

	EditText addSxId;
	EditText addSxName;
	AutoCompleteTextView  addSxLocation;
	EditText addSxAge;
	AutoCompleteTextView addSxTime;
	EditText addSxDuration;
	AutoCompleteTextView addSxLevel;
	EditText addSxDiagnose;
	AutoCompleteTextView addSxProcedure;
	EditText addSxDate;
	AutoCompleteTextView addSxWard;
	Switch addSxEmergency;//ADDED P3MX01
	AutoCompleteTextView addSxTeamMember;
	AutoCompleteTextView addSxType;
	EditText note;
	AutoCompleteTextView addIpdReferredBy;
	Switch radioSexGroup;//ADDED P3MX01
	InitCap initCap;//ADDED 10001
	SharedPreferences pref;//ADDED 10002
	RadioButton btnMale;
	RadioButton btnFemale;
	public static final int SECSETTINGS = 1007;//ADDED 10006
	Gallery gallery;//SA 10007
	LinearLayout surgicalPic;
	Uri currentFileUri;
	final int REQUEST_CAMERA = 1001;
	final int REQUEST_SMARTHUMANOID_CAMERA = 1002; 
	final int REQUEST_VIDEO = 1011;//ADDED 1000B
	final int REQUEST_VIDEO_IMPORT=1012;//ADDED 1000B
	ArrayList<Bitmap> data;
	ArrayList<String> paths;
	ImagesAdapter imagesAdapter = null;//EA 10007
	Cursor patientCursor;//ADDED 10008
	String _id=null;//ADDED 10008
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
	Bundle savingBundle = new Bundle();
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
	SimpleCursorAdapter timeAdapter;
	SimpleCursorAdapter levelAdapter;
	SimpleCursorAdapter procedureAdapter;
	SimpleCursorAdapter wardAdapter;
	SimpleCursorAdapter teamMemberAdapter;
	SimpleCursorAdapter typeAdapter;
	SimpleCursorAdapter referredByAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SA 10002
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			//setTheme(R.style.FullScreen);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10002
		//SA 10013-1
		//setContentView(R.layout.add_sx);
//		setContentView(R.layout.new_add_sx);
		setContentView(R.layout.trav_add_sx);//EDITED U0001
		//EA 10013-1
		
		initializeData();
		initCap = new InitCap();//ADDED 10001
		
		View v = getLayoutInflater().inflate(R.layout.empty_item, null);
		addSxId = (EditText) findViewById(R.id.add_sx_id);
		
		addSxName = (EditText) findViewById(R.id.add_sx_name);
		
		addSxLocation = (AutoCompleteTextView) findViewById(R.id.add_sx_location);
		setLocationAdapter();
		
		addSxAge = (EditText) findViewById(R.id.add_sx_age);
		addSxTime = (AutoCompleteTextView) findViewById(R.id.add_sx_start_time);
		setStartTimeAdapter();
		
		addSxDuration = (EditText) findViewById(R.id.add_sx_duration);
		addSxLevel = (AutoCompleteTextView) findViewById(R.id.add_sx_level);
		setLevelAdapter();
		
		addSxDiagnose = (EditText) findViewById(R.id.add_sx_diagnose);
		addSxProcedure = (AutoCompleteTextView) findViewById(R.id.add_sx_procedure);
		setProcedureAdapter();
				
		isSharing = (Switch)findViewById(R.id.is_shared);//ADDED P3A02
		isSharingPrivate = (Switch)findViewById(R.id.is_shared_privacy);//ADDED P3A03
		
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
		
		addSxDate = (EditText) findViewById(R.id.add_sx_date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if(date.contains("/"))
		{
			date = date.replace("/", "-");
		}
		addSxDate.setText(date);
		
		addSxWard = (AutoCompleteTextView) findViewById(R.id.add_sx_ward);
		setWardAdapter();
		
		addSxEmergency = (Switch) findViewById(R.id.add_sx_manager_emergency);//ADDED P3MX01
		addSxTeamMember = (AutoCompleteTextView) findViewById(R.id.add_sx_team_member);
		setTeamMemberAdapter();
		
		addIpdReferredBy = (AutoCompleteTextView) findViewById(R.id.add_sx_referred_by);
		setReferredByAdapter();
		
		addSxType = (AutoCompleteTextView) findViewById(R.id.add_sx_patient_type);
		setTypeAdapter();
		
		note = (EditText) findViewById(R.id.add_sx_note);
		radioSexGroup = (Switch) findViewById(R.id.radioSex);//ADDED P3MX01
		
//		btnMale = (RadioButton) findViewById(R.id.radioMale);//COMMENTED P3MX01
//		btnFemale = (RadioButton) findViewById(R.id.radioFemale);//COMMENTED P3MX01
		gallery = (Gallery)findViewById(R.id.imagePreview);//SA 10007
		surgicalPic = (LinearLayout)findViewById(R.id.surgicalPic);
		// SA U0001
		txtTitle = (TextView) findViewById(R.id.add_sx_location_text);

		next = (ImageView) findViewById(R.id.btn_header_next);
		prev = (ImageView) findViewById(R.id.btn_header_prev);
		// EA U0001
		
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
		
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// SU 1000B
				String extension = "";
				int i = paths.get(arg2).lastIndexOf('.');
				if (i >= 0) {
					extension = paths.get(arg2).substring(i + 1);
				}
				if (extension.equalsIgnoreCase("mp4")) {
					File file = new File(paths.get(arg2));
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "video/*");
					startActivity(intent);
				} else {
					Intent intent = new Intent(AddSxActivity.this,DetailedImage.class);
					intent.putExtra("url", paths.get(arg2));
//					intent.putExtra("current_image", arg2);
//					intent.putStringArrayListExtra("url", paths);
//					intent.putExtra("servicename", addSxName.getText().toString());
//					intent.putExtra("servicelocation", addSxLocation.getText().toString());
					startActivity(intent);
				}
				// EU 1000B
			}
		});
		//EA 10007
		
		//SA 10004
		if(pref.getBoolean("prefIsToolTip", false)|| pref.getBoolean("prefIsSXToolTip", false)) //EDITED 10006
		{
			onToolTipOn();
		}
		//EA 10004
		
		//SA P3A03
		contactAccordionView = (AccordionView)findViewById(R.id.accordion_view);
		contactsShareDetail = new ArrayList<Contact>();
		chipsBoxLayout = (FlowLayout)findViewById(R.id.chips_box_layout);
		contactsSharePhone = new ArrayList<String>();
		//EA P3A03		
		
		//SA 10007
		data = new ArrayList<Bitmap>();
		imagesAdapter = new ImagesAdapter(this, data);
		gallery.setAdapter(imagesAdapter);
		paths =  new ArrayList<String>();
		//SC 1000A
		/*if(pref.getBoolean("prefIsSXSurgicalNotes", false))
		{
			surgicalPic.setVisibility(View.VISIBLE);
			gallery.setVisibility(View.VISIBLE);
		}
		else
		{
			surgicalPic.setVisibility(View.GONE);
			gallery.setVisibility(View.GONE);
		}*/
		//EC 1000A
		onRestoreInstanceState(savingBundle);
		//EA 10007
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
	}
	
	public void addLocation()
	{
		String s = addSxLocation.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strLocation.contains(new String(s)))
		{
			addContent(DBConstant.Location_Columns.CONTENT_URI, s);
		}
	}
	public void addProcedure()
	{
		String s = addSxProcedure.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strProcedure.contains(new String(s)))
		{
			addContent(DBConstant.Procedure_Columns.CONTENT_URI, s);
		}
	}
	
	public void addWard()
	{
		String s = addSxWard.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strWard.contains(new String(s)))
		{
			addContent(DBConstant.Ward_Columns.CONTENT_URI, s);
		}
	}
	
	public void addTeamMember()
	{
		String s = addSxTeamMember.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strTeamMember.contains(new String(s)))
		{
			addContent(DBConstant.TMEMBER_Columns.CONTENT_URI, s);
		}
	}
	
	public void addType()
	{
		String s = addSxType.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strType.contains(new String(s)))
		{
			addContent(DBConstant.Types_Columns.CONTENT_URI, s);
		}
	}
	
	public void addRef()
	{
		String s = addIpdReferredBy.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strRef.contains(new String(s)))
		{
			addContent(DBConstant.Ref_Columns.CONTENT_URI, s);
		}
	}
	
	public void addStartTime()
	{ 
		String s = addSxTime.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strStartTime.contains(new String(s)))
		{
			addContent(DBConstant.StartTime_Columns.CONTENT_URI, s);
		}
	}
	
	public void addLevel()
	{
		String s = addSxLevel.getText().toString();//EDITED 10001
		if(s!= null && s.length() > 0 && !strLevel.contains(new String(s)))
		{
			addContent(DBConstant.Level_Columns.CONTENT_URI, s);
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
		Intent contactPicker = new Intent(AddSxActivity.this, ContactManager.class);
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
	public static final int LOADING = 101;
	@Override
	protected Dialog onCreateDialog(int id, Bundle b) {
		// TODO Auto-generated method stub
		//String msg = b.getString("message");
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
	
//	SA P3MX02
	@SuppressLint("ValidFragment")
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
//                    Toast.makeText(SmartConsultant.getApplication().getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
                    onNext(v);
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Toast.makeText(SmartConsultant.getApplication().getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
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
			String service_type = "2";
			serviceCursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, null, DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE + "=?", new String[]{service_type}, null);	
			currentRecord = Math.max(serviceCursor.getCount(), 0);
			setText();
		}
		public void setText()
		{
			txtTitle.setText("SX " + (currentRecord+1) +" of " + (serviceCursor.getCount()+1));
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
						
						//Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						//NewExpensesActivity.this.data.add(bm);
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
								AddSxActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
							}
							catch(Exception e)
							{
								Log.e("LoadPathCursor",e.toString());
							}
						} else {
							bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
							AddSxActivity.this.data.add(bm);
						}
						//EA 1000B-2
					}
					imagesAdapter.notifyDataSetChanged();
				}
//				enableDisableButton();
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
//						Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
//						AddSxActivity.this.data.add(bm);
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
								AddSxActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
							}
							catch(Exception e)
							{
								Log.e("LoadPathCursor",e.toString());
							}
						} else {
							bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
							AddSxActivity.this.data.add(bm);
						}
						//EA 1000B-2
					}
					imagesAdapter.notifyDataSetChanged();
				}
//				enableDisableButton();
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
//						Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
//						AddSxActivity.this.data.add(bm);
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
								AddSxActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
							}
							catch(Exception e)
							{
								Log.e("LoadPathCursor",e.toString());
							}
						} else {
							bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
							AddSxActivity.this.data.add(bm);
						}
						//EA 1000B-2
					}
					imagesAdapter.notifyDataSetChanged();
				}
				
//				enableDisableButton();
			}
			else
			{
				_id = null;
				currentRecord = Math.max(serviceCursor.getCount(), 0);
				setText();
				isSharingBoolean = true; // ADDED P3B04
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
				isSharingBoolean = true; // ADDED P3B04
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
				String diagnose = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS));
				String date = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DATE));
				String ward = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_WARD));
				String referredby = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_REF));
				String _note = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NOTE));
				String type = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TYPE));
				String emergency = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_EMERGENCY));
				String sex = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SEX));
				String time = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_STARTTIME));
				String duration = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DURATION));
				String level = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_LEVEL));
				String procedure = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_PROCEDURE));
				String member = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TEAM_MEMBER));
				String isSharingEnabled = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING));//ADDED P3A02
				String isSharingPrivateEnabled = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING_PRIVATE));//ADDED P3A03
				
				
				
				addSxId.setText(_id); 
				addSxName.setText(name); 
				addSxLocation.setText(location); 
				addSxAge.setText(age); 
				addSxDiagnose.setText(diagnose); 
				addSxDate.setText(date);
				addSxWard.setText(ward);
				addIpdReferredBy.setText(referredby);
				addSxType.setText(type);
				note.setText(_note);
				addSxTime.setText(time);
				addSxDuration.setText(duration);
				addSxLevel.setText(level);
				addSxProcedure.setText(procedure);
				addSxTeamMember.setText(member);
				if (Integer.parseInt(emergency) > 0) {
					addSxEmergency.setChecked(true);
				} else {
					addSxEmergency.setChecked(false);
				}
				if (sex.equalsIgnoreCase("male")) {
//					btnMale.setChecked(true);
					radioSexGroup.setChecked(false);//ADDED P3MX01
				} else {
//					btnFemale.setChecked(true);
					radioSexGroup.setChecked(true);//ADDED P3MX01
				}
				isSharingBoolean = false;//ADDED P3B04
				isSharingPrivateBoolean = false; //ADDED P3A03
//				SA P3A02
				if (Integer.parseInt(isSharingEnabled) > 0) {
					isSharing.setChecked(true);
				} else {
					isSharing.setChecked(false);
				}
//				EA P3A02
//				SA P3A03
				if (Integer.parseInt(isSharingPrivateEnabled) > 0) {
					isSharingPrivate.setChecked(true);
				} else {
					isSharingPrivate.setChecked(false);
				}
//				EA P3A03
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
					//paths = new ArrayList<String>();
					int colIndex = pathsCursor.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL);
					while(pathsCursor.moveToNext())
					{
						paths.add(pathsCursor.getString(colIndex));
					}
				}
			}
		}
		
//		SA P3A03
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
							
							TextView t = new TextView(AddSxActivity.this);
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
//		SA P3A03
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
	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	}
	
	public void startManagerScreen(int index, int reqCode)
	{
		Intent i = new Intent(this, ManageLOVActivity.class);
		i.putExtra("index", index);
		startActivityForResult(i, reqCode);
	}
	int currentRequestCode = -1;
	String currentSelection = null;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null)
		{
			currentSelection = data.getStringExtra("data");
		}
		currentRequestCode = requestCode;
		
		if(resultCode == RESULT_OK)
		{
			clearDataStructure();
			onDataLoaded(currentSelection);
		}
		//SA 10007
		Bitmap bm = null;
		if (resultCode == RESULT_OK) 
		{
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
				AddSxActivity.this.data.add(bm);
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
					try {
						bm = BitmapFactory.decodeFile(currentFileUri.getPath(),btmapOptions);
					} catch (Exception e) {
						Log.e("currentFileUri", "currentFileUri" + e.toString());
//						throw new UndeclaredThrowableException(e); COMMENTED B0001
					}
					AddSxActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					try {
						compressedPath = ImageCompression.compressImage(currentFileUri.getPath());// ADDED 10018
						galleryAddPic();
						paths.add(compressedPath);// EDITED 10018
					} catch (Exception e) {
						Log.e("Image-Compression",
								"imageCompression" + e.toString());
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
					AddSxActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
					galleryAddPic();
					paths.add(compressedPath);//EDITED 10018
				} else {
					Toast.makeText(getApplicationContext(), "Error while taking picture!", Toast.LENGTH_SHORT).show();
				}
			}
			
			// SA 1000B
						if (requestCode == REQUEST_VIDEO) {
							if (resultCode == RESULT_OK) {
								//SA U0001
								if(currentRecord+1 < serviceCursor.getCount()+1)
								{
									currentImage = paths.size();	
								}
								//EA U0001
								// BitmapFactory.Options btmapOptions = new
								// BitmapFactory.Options();
								// btmapOptions.inSampleSize = 2;
								// bm =
								// BitmapFactory.decodeFile(currentFileUri.getPath(),btmapOptions);
								bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND);
								try {
									Resources r = getResources();
									Drawable[] layers = new Drawable[2];
									layers[0] = new BitmapDrawable(bm);
									layers[1] = r.getDrawable(R.drawable.play_icon);
									LayerDrawable layerDrawable = new LayerDrawable(layers);
									AddSxActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
								} catch (Exception e) {
									Log.e("Creating Thumbnail",e.toString());
								}
								imagesAdapter.notifyDataSetChanged();
								galleryAddPic();
								paths.add(currentFileUri.getPath());
							} else {
								CustomToast.showToastMessage(getApplicationContext(),
										"Error while taking video!");
							}
						}

						if (requestCode == REQUEST_VIDEO_IMPORT) {
							if (resultCode == RESULT_OK) {
//								SC P2B02
								//SA U0001
//								if(currentRecord+1 < serviceCursor.getCount()+1)
//								{
//									currentImage = paths.size();	
//								}
								//EA U0001
//								Uri selectedImage = data.getData();
//								String[] filePathColumn = { MediaColumns.DATA };
//								Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//								cursor.moveToFirst();
//								int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//								String videoPath = cursor.getString(columnIndex);
//								cursor.close();
//								getVideoPath();
//								try {
//									copy(new File(videoPath),
//											new File(currentFileUri.getPath()));
//								} catch (IOException e) {
//									Log.e("IMPORT_VIDEO", e.toString());
//								}
			//
//								bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND);
			//
//								try {
//									Resources r = getResources();
//									Drawable[] layers = new Drawable[2];
//									layers[0] = new BitmapDrawable(bm);
//									layers[1] = r.getDrawable(R.drawable.play_icon);
//									LayerDrawable layerDrawable = new LayerDrawable(layers);
//									AddIPDActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
//								} catch (Exception e) {
//									Log.e("Creating Thumbnail",e.toString());
//								}
//								imagesAdapter.notifyDataSetChanged();
//								galleryAddPic();
//								paths.add(currentFileUri.getPath());
//								EC P2B02 SA P2B02
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
//									SC P2B03
//									try {
//										copy(new File(videoPath),new File(currentFileUri.getPath()));
//									} catch (Exception e) {
//										Log.e("COPY VIDEO", e.toString());
//									}

//									bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND); EC P2B03
									bm = ThumbnailUtils.createVideoThumbnail(videoPath,MediaStore.Video.Thumbnails.MICRO_KIND);//ADDED P2B03

									try {
										Resources r = getResources();
										Drawable[] layers = new Drawable[2];
										layers[0] = new BitmapDrawable(bm);
										layers[1] = r.getDrawable(R.drawable.play_icon);
										LayerDrawable layerDrawable = new LayerDrawable(layers);
										AddSxActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
									} catch (Exception e) {
										Log.e("Creating Thumbnail",e.toString());
									}
//									SA P2B03
									try {
										new CopyMediaTask(videoPath,currentFileUri).execute();
									} catch (Exception e) {
										Log.e("COPY VIDEO", e.toString());
									}
//									EA P2B03
									imagesAdapter.notifyDataSetChanged();
									galleryAddPic();
									paths.add(currentFileUri.getPath());
								}
								else{
									CustomToast.showToastMessage(getApplicationContext(),"Please add mp4 videos only!");	
								}
//								EA P2B02
							} 
							else {
								CustomToast.showToastMessage(getApplicationContext(),"Error while importing video!");
							}
						}
						// EA 1000B
						
//						SA P3A03
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
											TextView t = new TextView(AddSxActivity.this);
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
												TextView t = new TextView(AddSxActivity.this);
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
//						EA P3A03
		}
		//EA 10007
		// SA 10006
	if (requestCode == SECSETTINGS) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
			onSaveInstanceState(savingBundle);
		}
		// EA 10006
	}
	public void onManageLocation(View v)
	{
		startManagerScreen(0, 100);
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
	
	public void onManageStartTime(View v)
	{
		startManagerScreen(6, 101);
	}
	
	public void onManageLevel(View v)
	{
		startManagerScreen(7, 102);
	}
	
	public void onManageProcedure(View v)
	{
		startManagerScreen(1, 103);
	}
	
	public void onManageWard(View v)
	{
		startManagerScreen(2, 104);
	}
	
	public void onManageTeamMemeber(View v)
	{
		startManagerScreen(3, 105);
	}
	
	public void onManagePatientType(View v)
	{
		startManagerScreen(4, 106);
	}
	public void onManageReferredBy(View v)
	{
		startManagerScreen(5, 107);
	}
	
	public void clearDataStructure()
	{

	}
	public void save()
	{
		Bundle b = new Bundle();
		b.putString("message", "Saving SX.");
		showDialog(LOADING, b);
		ContentValues contentValues = new ContentValues();
		ContentValues contentValuesTemp = new ContentValues();
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		String nm=initCap.toTitleCase(addSxName.getText().toString());//ADDED 10001
		contentValues.put(DBConstant.Patient_Columns.COLUMN_NAME, nm);//EDITED 10001
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_NAME, nm);//EDITED 10001
		
		ContentValues name = new ContentValues();
		name.put(DBConstant.Patient_Name_Columns.COLUMN_NAME, nm);//EDITED 10001
		name.put(DBConstant.Patient_Name_Columns.COLUMN_CUSTOM_ID, addSxId.getText().toString());
		//SA 10005
    	name.put(DBConstant.Patient_Name_Columns.COLUMN_NAME_SEARCHALGO, initCap.toTitleCase(SearchAlgo.getNameSearchAlgo(nm)));
		//EA 10005

		
		String loc = "";
		{
			loc = initCap.toTitleCase(addSxLocation.getText().toString());//EDITED 10001
		}
		
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_LOCATION, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_LOCATION, loc);
		name.put(DBConstant.Patient_Name_Columns.COLUMN_LOCATION, loc);
		addLocation();//24092013
		
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_AGE, addSxAge.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_AGE, addSxAge.getText().toString());
		
		loc = "";
		{
			loc = addSxTime.getText().toString();
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_STARTTIME, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_STARTTIME, loc);
		addStartTime();//24092013
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_DURATION, addSxDuration.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_DURATION, addSxDuration.getText().toString());
		
		{
			loc = addSxLevel.getText().toString();
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_LEVEL, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_LEVEL, loc);
		addLevel();//24092013
		String strDiag = initCap.toTitleCase(addSxDiagnose.getText().toString());//	ADDED 10001
		contentValues.put(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS, strDiag);//EDITED 10001
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_DIAGNOSIS, strDiag);//EDITED 10001

		loc = "";
		{
			loc = initCap.toTitleCase(addSxProcedure.getText().toString());//EDITED 10001
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_PROCEDURE, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_PROCEDURE, loc);
		addProcedure();//24092013

		loc = "";
		if(!addSxDate.getText().toString().equals("Date : DD-MM-YYYY"))
		{
			loc = addSxDate.getText().toString(); 
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_DATE, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_DATE, loc);
		name.put(DBConstant.Patient_Name_Columns.COLUMN_DATE, loc);
		
		
		//getContentResolver().insert(DBConstant.Patient_Name_Columns.CONTENT_URI, name);

		loc = "";
		{
			loc = initCap.toTitleCase(addSxWard.getText().toString());//EDITED 10001
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_WARD, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_WARD, loc);
		addWard();//24092013

		contentValues.put(DBConstant.Patient_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked() ? "1" : "0");
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_EMERGENCY, addSxEmergency.isChecked() ? "1" : "0");
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SHARING, isSharing.isChecked() ? "1" : "0");//SA P3A02
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SHARING, isSharing.isChecked() ? "1" : "0");//EA P3A02
		
		//SA P3A03
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SHARING_PRIVATE, isSharingPrivate.isChecked() ? "1" : "0");
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SHARING_PRIVATE, isSharingPrivate.isChecked() ? "1" : "0");
		//EA P3A03
		loc = "";
		{
			loc = initCap.toTitleCase(addSxTeamMember.getText().toString());//EDITED 10001
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_TEAM_MEMBER, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_TEAM_MEMBER, loc);
		addTeamMember();//24092013
		
		loc = "";
		{
			loc = initCap.toTitleCase(addSxType.getText().toString());//EDITED 10001
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_TYPE, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_TYPE, loc);
		addType();//24092013
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE, 2);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SERVICE_TYPE, 2);//ADDED 10009
		
		contentValues.put(DBConstant.Patient_Columns.COLUMN_NOTE, note.getText().toString());
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_NOTE, note.getText().toString());

		String sex = null;
		if(radioSexGroup.isChecked())//ADDED P3MX01
		{
//			sex = "Male";
			sex = "Female";//ADDED P3MX01
		}
		else //if(btnFemale.isChecked()) ADDED P3MX01
		{
//			sex = "Female";
			sex = "Male";//ADDED P3MX01
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SEX, sex);
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SX_WATCH, 0);
		
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SEX, sex);
		
		loc = "";
		{
			loc = initCap.toTitleCase(addIpdReferredBy.getText().toString());//EDITED 10001
		}
		contentValues.put(DBConstant.Patient_Columns.COLUMN_REF, loc);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_REF, loc);
		contentValues.put(DBConstant.Patient_Columns.COLUMN_SYNC_STATUS, 0);
		contentValuesTemp.put(DBConstant.Patient_Temp_Columns.COLUMN_SYNC_STATUS, 0);
		addRef();//24092013
		
		
//SA U0001
		if(currentRecord+1 < serviceCursor.getCount()+1)
		{
			serviceCursor.moveToPosition(currentRecord);
			String update_id = serviceCursor.getString(serviceCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
			getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI,contentValues,DBConstant.Patient_Columns.COLUMN_ID + "=?",new String[]{update_id});
			getContentResolver().update(DBConstant.Patient_Name_Columns.CONTENT_URI,name,DBConstant.Patient_Name_Columns.COLUMN_ID + "=?",new String[]{update_id});
			getContentResolver().update(DBConstant.Patient_Temp_Columns.CONTENT_URI,contentValuesTemp,DBConstant.Patient_Temp_Columns.COLUMN_ID + "=?",new String[]{update_id});
					
			getImageCount = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID + "=?",new String[]{update_id}, null).getCount();
			boolInsert = false;	
		}
		else
		{
			getContentResolver().insert(DBConstant.Patient_Columns.CONTENT_URI,contentValues);
			getContentResolver().insert(DBConstant.Patient_Name_Columns.CONTENT_URI, name);
			Uri uri = getContentResolver().insert(DBConstant.Patient_Temp_Columns.CONTENT_URI, contentValuesTemp);
			boolInsert = true;
		}
		//EA U0001

		
//		getContentResolver().insert(DBConstant.Patient_Columns.CONTENT_URI, contentValues);
//		Uri uri = getContentResolver().insert(DBConstant.Patient_Temp_Columns.CONTENT_URI, contentValuesTemp);
//		
//		String myId = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);
//		name.put(DBConstant.Patient_Name_Columns.COLUMN_RED_ID, myId);
//		getContentResolver().insert(DBConstant.Patient_Name_Columns.CONTENT_URI, name);
		
		//SA 10008
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
				//EA U0001
//				patientCursor.moveToLast();
//				_id = patientCursor.getString(patientCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
//				String name_  = patientCursor.getString(patientCursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));

//				for (int i = 0; i < paths.size(); i++) {
//					String url = paths.get(i);
//					String file_name = url.substring(url.lastIndexOf("/") + 1);//ADDED 1000A-1
//					ContentValues temp = new ContentValues();
//					temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID, _id);
//					temp.put(DBConstant.Patient_Details_Columns.COLUMN_NAME, file_name);//ADDED 1000A-1
//					temp.put(DBConstant.Patient_Details_Columns.COLUMN_URL, url);
//					temp.put(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE, 2);
//					temp.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 0);
//					getContentResolver().insert(
//							DBConstant.Patient_Details_Columns.CONTENT_URI, temp);
//					Log.i("Paitent Details", "Patient ID:-"+_id + " --> "+"Name:"+name_+"-->"+url);
//				}
				
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
		//EA 10008
		//SA P3A03
//		if(isSharingPrivate.isChecked())
//		{
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
		removeDialog(LOADING);
		//SA 10014
		//Toast.makeText(this, "SX saved", Toast.LENGTH_LONG).show();
		CustomToast.showToastMessage(this, "SX Saved");
		//EA 10014
		clearField();

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
			validateFields();//ADDED 10003
			//Toast.makeText(this, "Please enter mandatory fields!", Toast.LENGTH_LONG).show(); COMMENTED 10003
			}
		}
	//SA 10003
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
			//EA 10003

			
			//SA 10004
			public void onToolTipOn()
			{
				final Context c = getApplicationContext();
				//SU 10015
				addSxName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tpatientname));
					}
				});
				
				addSxLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tlocation));
					}
				});
				
				addSxId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tpatientid));
					}
				});
				
				addSxAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tage));
					}
				});
				
				addSxDiagnose.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tdiagnose));
					}
				});
				
				addIpdReferredBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tipdreferred));
					}
				});
				
				addSxType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tpatienttype));
					}
				});
				note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tnote));
					}
				});
				addSxDuration.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tduration));
					}
				});
				addSxProcedure.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tprocedure));
					}
				});
				addSxTeamMember.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tmember));
					}
				});
				addSxLevel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tlevel));
					}
				});
				addSxTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.ttime));
					}
				});
				addSxWard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if(hasFocus)
							CustomToast.showToastMessage(c, getString(R.string.tward));
					}
				});
				//EU 10015
			}
			//EA 10004			
			
//SA 10013
	@Override
	protected final void onSaveInstanceState(final Bundle savedInstanceState) {
		  super.onSaveInstanceState(savedInstanceState);
		  savedInstanceState.putString("Name", addSxName.getText().toString());
		  savedInstanceState.putString("ID", addSxId.getText().toString());
		  savedInstanceState.putString("Location", addSxLocation.getText().toString());
		  savedInstanceState.putString("Age", addSxAge.getText().toString());
		  savedInstanceState.putString("Diagnose", addSxDiagnose.getText().toString());
		  savedInstanceState.putString("Ward", addSxWard.getText().toString());
		  savedInstanceState.putString("Referred", addIpdReferredBy.getText().toString());
		  savedInstanceState.putString("Type", addSxType.getText().toString());
		  
		  savedInstanceState.putString("Note", note.getText().toString());
		  savedInstanceState.putString("Time", addSxTime.getText().toString());
		  savedInstanceState.putString("Duration", addSxDuration.getText().toString());
		  savedInstanceState.putString("Level", addSxLevel.getText().toString());
		  savedInstanceState.putString("Procedure", addSxProcedure.getText().toString());
		  savedInstanceState.putString("Ward", addSxWard.getText().toString());
		  
		  savedInstanceState.putString("TeamMember", addSxTeamMember.getText().toString());
		  savedInstanceState.putBoolean("Emergency", addSxEmergency.isChecked());
		}
	
	@Override
	protected final void onRestoreInstanceState(final Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  addSxName.setText(savedInstanceState.getString("Name"));
	  addSxId.setText(savedInstanceState.getString("ID"));
	  addSxLocation.setText(savedInstanceState.getString("Location"));
	  addSxAge.setText(savedInstanceState.getString("Age"));
	  addSxDiagnose.setText(savedInstanceState.getString("Diagnose"));
	  addSxWard.setText(savedInstanceState.getString("Ward"));
	  addIpdReferredBy.setText(savedInstanceState.getString("Referred"));
	  addSxType.setText(savedInstanceState.getString("Type"));
	  
	  note.setText(savedInstanceState.getString("Note"));
	  addSxTime.setText(savedInstanceState.getString("Time"));
	  addSxDuration.setText(savedInstanceState.getString("Duration"));
	  addSxLevel.setText(savedInstanceState.getString("Level"));
	  addSxProcedure.setText(savedInstanceState.getString("Procedure"));
	  addSxWard.setText(savedInstanceState.getString("Ward"));
	  addSxTeamMember.setText(savedInstanceState.getString("TeamMember"));
	  addSxEmergency.setChecked(savedInstanceState.getBoolean("Emergency"));
		}

	@Override
	protected void onNewIntent(Intent intent) {
        setIntent(intent);
        onRestoreInstanceState(savingBundle);
    }
	
	
//EA 10013			
			
			
	//SA 10007
			public void onTakePicture(View v)
			{
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				getImagePath();//added 10017
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
			
    //EA 10007			
	public void clearField()
	{
		addSxId.setText("");
		addSxName.setText("");
		
		//if(lstLocation != null && lstLocation.size() > 0)
		{
			addSxLocation.setText("");
		}
		
		addSxAge.setText("");
		
		//if(lstStartTime != null && lstStartTime.size() > 0)
		{
			addSxTime.setText("");
		}
		
		addSxDuration.setText("");
		
		//if(lstSurgeryLevel != null && lstSurgeryLevel.size() > 0)
		{
			addSxLevel.setText("");
		}

		addSxDiagnose.setText("");

		//if(lstProcedure != null && lstProcedure.size() > 0)
		{
			addSxProcedure.setText("");
		}

		//addSxDate.setText("Date : DD-MM-YYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if(date.contains("/"))
		{
			date = date.replace("/", "-");
		}
		addSxDate.setText(date);
		
		//if(lstWard != null && lstWard.size() > 0)
		{
			addSxWard.setText("");
		}

		addSxEmergency.setChecked(false);
		isSharing.setChecked(false);//ADDED P3A02
		radioSexGroup.setChecked(false);//ADDED P3MX01
		contactAccordionView.setVisibility(View.GONE);//SA P3A03
		contactsShareDetail.clear();
		contactsSharePhone.clear();
		deleteAllContacts = false;
		chipsBoxLayout.removeAllViews();
		chipsBoxLayout.setVisibility(View.GONE);//EA P3A03
		
		//if(lstTeamMember != null && lstTeamMember.size() > 0)
		{
			addSxTeamMember.setText("");
		}
		
		//if(lstPatientType != null && lstPatientType.size() > 0)
		{
			addSxType.setText("");
		}
		addIpdReferredBy.setText("");
		note.setText("");
		// SA 1000A
//	SA P3U001	
		addSxLocation.setFocusable(false);
		addSxLocation.setFocusableInTouchMode(true);
//	EA P3U001	
		AddSxActivity.this.data.clear();
		paths.clear();
		imagesAdapter.notifyDataSetChanged();
		// EA 1000A
	}
	public void onTime(View v)
	{
		DialogFragment newFragment = new FromDatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "From Date");
	}
	//SA 10017
		@SuppressWarnings("deprecation")
		public void onAddPicture(View v)
		{
			showDialog(PIC);
		}
		//SA 1000B
		public void onTakeVideo(View v) {
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			getVideoPath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			startActivityForResult(intent, REQUEST_VIDEO);
		}

		public void onImportVideo(View v) {
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
			File tempFile = new File(imageDirectory, getVideoName()+ AppConstants.VIDEO_EXTENSION);
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
//				getFreeSize(AppConstants.IMAGE_DIRECTORY_PATH);
			}
			else
			{
				imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
//				getFreeSize(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
			}

			imageDirectory.mkdirs();
			File tempFile = new File(imageDirectory, getVideoName()+ AppConstants.EXTENSION);
			outputFileUri = Uri.fromFile( tempFile );
			currentFileUri = outputFileUri;
		}
		
	public void getFreeSize(String s) {
		try {
			StatFs memStatus = new StatFs(s);
			long megaBytesAvailable = ((long) memStatus.getBlockSize() * (long) memStatus
					.getAvailableBlocks()) / 1048576;
			if (megaBytesAvailable < 5) {
				CustomToast.showToastMessage(AddSxActivity.this, "Low Space in device");
			}
		} catch (Exception e) {
			Log.e("AddSx", e.toString());
		}
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
				// EA 1000B

				break;
			}
		}
			
		//EA 10017
	class FromDatePickerFragment extends DialogFragment implements OnDateSetListener
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
//			SA P3B05
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
//			EA P3B05
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
			validateFields();//ADDED 10003
			//Toast.makeText(this, "Please enter mandatory fields!", Toast.LENGTH_LONG).show(); COMMENTED 10003
		}
		/*save();
		finish();*/
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
	
	/*public void setNameAdapter()
	{
		adapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);
        addSxName.setAdapter(adapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxName.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        adapter.setCursorToStringConverter(new CursorToStringConverter() {
            public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
                return cursor;
            }
        });
	}*/
	public void setLocationAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Location_Columns.COLUMN_NAME };
	    
		/*locationAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    locationAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxLocation.setAdapter(locationAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxLocation.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        locationAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        locationAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Location_Columns.CONTENT_URI, null, DBConstant.Location_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class LocationTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Location_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				while(result.moveToNext())
				{
					lstLocation.add(result.getString(1));
				}
				
				locationAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstLocation);
				locationAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxLocation.setAdapter(locationAdapter);
			}
			StartTimeTask startTimeTask = new StartTimeTask();
			startTimeTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Bundle b = new Bundle();
			b.putString("message", "Getting DB contents");
			showDialog(LOADING, b);
		}
	}*/
	
	public void setStartTimeAdapter()
	{
		
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.StartTime_Columns.COLUMN_NAME };
	    
		/*timeAdapter =  new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    timeAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxTime.setAdapter(timeAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxTime.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        timeAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.StartTime_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        timeAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.StartTime_Columns.CONTENT_URI, null, DBConstant.StartTime_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class StartTimeTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.StartTime_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstStartTime.add(result.getString(1));
				}
				timeAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstStartTime);
				timeAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxTime.setAdapter(timeAdapter);
			}
			LevelTask levelTask = new LevelTask();
			levelTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	
	public void setLevelAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Level_Columns.COLUMN_NAME };
	    
		/*levelAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    
	    levelAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxLevel.setAdapter(levelAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxLevel.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        levelAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Level_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        levelAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Level_Columns.CONTENT_URI, null, DBConstant.Level_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class LevelTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Level_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstSurgeryLevel.add(result.getString(1));
				}
				levelAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstSurgeryLevel);
				levelAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxLevel.setAdapter(levelAdapter);
			}
			ProcedureTask procedureTask = new ProcedureTask();
			procedureTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/

	public void setProcedureAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Procedure_Columns.COLUMN_NAME };
		/*procedureAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    
	    procedureAdapter =  new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxProcedure.setAdapter(procedureAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxProcedure.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        procedureAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Procedure_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        procedureAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Procedure_Columns.CONTENT_URI, null, DBConstant.Procedure_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class ProcedureTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Procedure_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstProcedure.add(result.getString(1));
				}
				procedureAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstProcedure);
				procedureAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxProcedure.setAdapter(procedureAdapter);
			}
			WardTask wardTask = new WardTask();
			wardTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	
	public void setWardAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Ward_Columns.COLUMN_NAME };
		/*wardAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    
	    wardAdapter =  new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxWard.setAdapter(wardAdapter);
        
        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxWard.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
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
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Ward_Columns.CONTENT_URI, null, DBConstant.Ward_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class WardTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Ward_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstWard.add(result.getString(1));
				}
				wardAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstWard);
				wardAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxWard.setAdapter(wardAdapter);
			}
			TeamMemberTask teamMemberTask = new TeamMemberTask();
			teamMemberTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	
	public void setTeamMemberAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.TMEMBER_Columns.COLUMN_NAME };
	    
		/*teamMemberAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    teamMemberAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxTeamMember.setAdapter(teamMemberAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxTeamMember.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        teamMemberAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.TMEMBER_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        teamMemberAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.TMEMBER_Columns.CONTENT_URI, null, DBConstant.TMEMBER_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class TeamMemberTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.TMEMBER_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstTeamMember.add(result.getString(1));
				}
				teamMemberAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstTeamMember);
				teamMemberAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxTeamMember.setAdapter(teamMemberAdapter);
			}
			ReferredByTask referredByTask = new ReferredByTask();
			referredByTask.execute();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	
	public void setReferredByAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Ref_Columns.COLUMN_NAME };
	    
		/*referredByAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    
	    referredByAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addIpdReferredBy.setAdapter(referredByAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addIpdReferredBy.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
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
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Ref_Columns.CONTENT_URI, null, DBConstant.Ref_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class ReferredByTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Ref_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstReferredBy.add(result.getString(1));
				}
				referredByAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstReferredBy);
				referredByAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addIpdReferredBy.setAdapter(referredByAdapter);
			}
			TypeTask referredByTask = new TypeTask();
			referredByTask.execute();

		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	
	public void setTypeAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Types_Columns.COLUMN_NAME };
	    
		/*typeAdapter = 
            new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
	    typeAdapter = new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        addSxType.setAdapter(typeAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        addSxType.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                /*Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String name = cursor.getString(1);
                String _id = cursor.getString(0);
                String type = cursor.getString(2);
                startNextActivity(_id, name, type);*/
                // Update the parent class's TextView
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
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
            	Cursor cursor = getContentResolver().query(DBConstant.Types_Columns.CONTENT_URI, null, DBConstant.Types_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
            	
                return cursor;
            }
        });
	}
	/*private class TypeTask extends AsyncTask<Void, Void, Cursor>
	{

		@Override
		protected Cursor doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				return AddSxActivity.this.getContentResolver().query(DBConstant.Types_Columns.CONTENT_URI, null, null, null, null);
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		@Override
		protected void onPostExecute(Cursor result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(result != null && result.getCount() > 0)
			{
				//result.moveToFirst();
				while(result.moveToNext())
				{
					lstPatientType.add(result.getString(1));
				}
				typeAdapter = new ArrayAdapter<String>(AddSxActivity.this, android.R.layout.simple_AutoCompleteView_item, lstPatientType);
				typeAdapter.setDropDownViewResource(android.R.layout.simple_AutoCompleteView_dropdown_item);
				addSxType.setAdapter(typeAdapter);
			}
			removeDialog(LOADING);
			onDataLoaded();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}*/
	public void onDataLoaded(String data)
	{
		if(currentRequestCode != -1 && currentSelection != null)
		{
			switch(currentRequestCode)
			{
				case 100: // location
					//if(lstLocation!= null && lstLocation.size()>0)
					{
						//int index = lstLocation.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxLocation.setText(data);//(index);
							addSxLocation.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(locationAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxLocation;
							handler.sendMessageDelayed(msg, 200); */
						}
					}
					break;
				case 101: // start time
					//if(lstStartTime!= null && lstStartTime.size()>0)
					{
						//int index = lstStartTime.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxTime.setText(data);//setSelection(index);
							addSxTime.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(timeAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxTime;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 103: // procedure
					//if(lstProcedure!= null && lstProcedure.size()>0)
					{
						//int index = lstProcedure.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxProcedure.setText(data);//setSelection(index);
							addSxProcedure.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(procedureAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxProcedure;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 104: // ward
					//if(lstWard!= null && lstWard.size()>0)
					{
						//int index = lstWard.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxWard.setText(data);//setSelection(index);
							addSxWard.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(wardAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxWard;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 105: // team member
					//if(lstTeamMember!= null && lstTeamMember.size()>0)
					{
						//int index = lstTeamMember.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxTeamMember.setText(data);//setSelection(index);
							addSxTeamMember.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(teamMemberAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxTeamMember;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 102: // level
					//if(lstSurgeryLevel!= null && lstSurgeryLevel.size()>0)
					{
						//int index = lstSurgeryLevel.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxLevel.setText(data);//setSelection(index);
							addSxLevel.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(levelAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxLevel;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 106: // type
					//if(lstPatientType!= null && lstPatientType.size()>0)
					{
						//int index = lstPatientType.indexOf(currentSelection);
						//if(index >=0)
						{
							addSxType.setText(data);//setSelection(index);
							addSxType.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(typeAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addSxType;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
				case 107: // ref
					//if(lstReferredBy!= null && lstReferredBy.size()>0)
					{
						//int index = lstReferredBy.indexOf(currentSelection);
						//if(index >=0)
						{
							addIpdReferredBy.setText(data);//setSelection(index);
							addIpdReferredBy.requestFocus();
							/*Handler handler = new Handler() 
							{
								public void handleMessage(Message msg) {
								    ((AutoCompleteTextView)msg.obj).setAdapter(referredByAdapter);
								};
							};
							Message msg = handler.obtainMessage();
							msg.obj = addIpdReferredBy;
							handler.sendMessageDelayed(msg, 200);*/
						}
					}
					break;
			}
		}
		currentRequestCode = -1;
		currentSelection = null;
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
        	i.putExtra("caller", "sx");
        	startActivity(i);
            return true;
          //SA 10006
		case com.smarthumanoid.com.R.id.mnuSettings:
			Intent in = new Intent(this, PrefsSecondaryActivity.class);
			in.putExtra("caller", "sx");
			onSaveInstanceState(savingBundle);
			startActivityForResult(in, SECSETTINGS);
			return true;
			//EA 10006
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
}

