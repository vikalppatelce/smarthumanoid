/* HISTORY
 * CATEGORY 			:- UI | PAYMENT
 * DEVELOPER			:- VIKALP PATEL
 * AIM     				:- ADD PAYMENT
 * DESCRIPTION 			:- ADD PAYMENT IN THE APPLICATION
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION 		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001       VIKALP PATEL   07/01/2014        				ADD PAYMENT
 * 10002       VIKALP PATEL   09/01/2014						ADD AUTO SUGGEST ON LOCATION FIELD
 * 10003       VIKALP PATEL   09/01/2014        				ADD AUTO SUGGEST ON DEPOSITED FIELD
 * 10004       VIKALP PATEL   09/01/2014        				INIT CAP
 * 10005       VIKALP PATEL   10/01/2014        				ADD FULLSCREEN ON APPLICATION FROM PREFERENCES
 * 10006       VIKALP PATEL   13/01/2014        				TOOLTIP FEATURE
 * 10007       VIKALP PATEL   15/01/2014        				INDIVIDUAL SETTINGS
 * 10013	   VIKALP PATEL   20/01/2014						LAYOUT RE-DESIGNED
 * 10014	   VIKALP PATEL   20/01/2014						CUSTOM-TOAST ON SAVE
 * 10015       VIKALP PATEL   21/01/2014						TOOLTIP EVENT CHANGE TO ONFOCUS
 * 10016-1     VIKALP PATEL   21/01/2014						ADDED VALIDATION ON PAYMENT
 * U0001       VIKALP PATEL   03/03/2014        UIX             RECORD TRAVERSAL
 * U0002       VIKALP PATEL   03/03/2014        UIX             GESTURE FLING LIKE VIEW PAGER
 * P2B01       VIKALP PATEL   11/03/2014        BUG             INHAND AMOUNT NOT CLEARED ON LAST
 * P2B02	   VIKALP PATEL   11/03/2014        BUG             TDS CALCULATION FORMULA CHANGE
 * P3B03	   VIKALP PATEL   07/04/2014        BUG             ON PREV IF CASH IS ON TDS PER CHANGE TO 0.
 * P3M01       VIKALP PATEL   14/04/2014        MODI			LAST DATE OF PREVIOUS MONTH ON SERVICE DATE
 * P3B04	   VIKALP PATEL   15/04/2014        BUG             CHEQUE WAS NOT GETTING FOCUS.
 * P3B05       VIKALP PATEL    24/04/2014        BUG            SHOWING CALENDAR WITH SERVICE DATE & RECEIVED DATE ONLY
 * --------------------------------------------------------------------------------------------------------------------
 */

package com.netdoers.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;

import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.utils.InitCap;
import com.netdoers.com.utils.Switch;
import com.smarthumanoid.com.R;

@SuppressLint({ "ValidFragment", "NewApi" })
public class AddPaymentActivity extends FragmentActivity {

	EditText recDate, serDate;
	EditText chqNo, inHand;
	EditText tdsPer, tdsAmt, amount;
	EditText strCheque;//,location,bank;//COMMENETED 10002
	AutoCompleteTextView location,bank;//SA10002
	final static int[] to = new int[] { R.id.txtText};//
	ArrayList<String> strLocation = new ArrayList<String>();//EA10002
	ArrayList<String> strDepositedBank = new ArrayList<String>();//ADDED 10003
	Switch radioPayMode,radioPaySrc;//ADDED P3MX01
	Switch isReconcileChk;//ADDED P3MX01
	SimpleCursorAdapter locationAdapter;//ADDED 10002
	SimpleCursorAdapter depositedBankAdapter;//ADDED 10003
	InitCap initCap;//Added 10004
	SharedPreferences pref;//Added 10005
	public static final int SECSETTINGS = 1001;//ADDED 10007
	Cursor paymentCursor;//SA U0001
	TextView txtTitle;
	ImageView next;
	ImageView prev;
	boolean boolInsert = false;
	RadioButton radioOther, radioPrf, radioCash, radioCheque;
	String _id=null;
	int currentRecord = 0;//EA U0001
	//SA U0002
	final static ViewConfiguration vc = ViewConfiguration.get(SmartConsultant.getApplication().getApplicationContext());
	private static final int SWIPE_MIN_DISTANCE = vc.getScaledPagingTouchSlop();
    private static final int SWIPE_MAX_OFF_PATH = vc.getScaledMinimumFlingVelocity();
    private static final int SWIPE_THRESHOLD_VELOCITY = vc.getScaledTouchSlop();
    private GestureDetector gestureDetector;
    LinearLayout flingLayout;
    View.OnTouchListener gestureListener;	//EA U0002
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SA 10005
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			//setTheme(R.style.FullScreen);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10005
		//SA 10013
		//setContentView(R.layout.add_payment);
		setContentView(R.layout.new_add_payment);
		//EA 10013
		initCap = new InitCap();//Added 10004
		initializeData();

		recDate = (EditText) findViewById(R.id.receive_date);
		serDate = (EditText) findViewById(R.id.service_date);
		radioPaySrc = (Switch)findViewById(R.id.radioPaySrc);//SA P3MX01
		radioPayMode = (Switch)findViewById(R.id.radioPayMode);
		
		radioPaySrc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
					isReconcileChk.setChecked(true);		
				else
					isReconcileChk.setChecked(false);
			}
		});
		
		radioPayMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
					tdsPer.setText("0");
				else
					tdsPer.setText("10");		
			}
		});
		
		
		
		isReconcileChk = (Switch)findViewById(R.id.add_pay_reconcile);//EA P3MX01
		tdsPer=(EditText)findViewById(R.id.add_pay_tds_per);
		inHand = (EditText)findViewById(R.id.add_pay_tds_in_hand);
		tdsAmt = (EditText)findViewById(R.id.add_pay_tds_amt);
		amount = (EditText)findViewById(R.id.add_pay_amt);
		strCheque= (EditText)findViewById(R.id.add_pay_chq);
//		radioOther  = (RadioButton)findViewById(R.id.radioOther);SC P3MX01
//		radioPrf  = (RadioButton)findViewById(R.id.radioPrf);
//		radioCash  = (RadioButton)findViewById(R.id.radioCash);
//		radioCheque  = (RadioButton)findViewById(R.id.radioCheque);EC P3MX01

		
		// SA U0001
		txtTitle = (TextView) findViewById(R.id.add_sx_location_text);

		next = (ImageView) findViewById(R.id.btn_header_next);
		prev = (ImageView) findViewById(R.id.btn_header_prev);
		// EA U0001
		
		loadPaymentCursor();//ADDED U0001
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
		
		//location = (EditText)findViewById(R.id.add_pay_location); //SC10002
		//bank=(EditText)findViewById(R.id.add_pay_bank); //EC10002
		location = (AutoCompleteTextView)findViewById(R.id.add_pay_location); //SA10002
		bank=(AutoCompleteTextView)findViewById(R.id.add_pay_bank);//EA10002
		
		tdsPer.setText("10");
		setLocationAdapter();//ADDED 10002
		setDepositedBankAdapter();//ADDED 10003

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if (date.contains("/")) {
			date = date.replace("/", "-");
		}
		
		recDate.setText(date);
		
//		SA P3M01
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		
		String lastDate = sdf.format(lastDateOfPreviousMonth);
		if (lastDate.contains("/")) {
			lastDate = lastDate.replace("/", "-");
		}
//		EA P3M01
		
		serDate.setText(lastDate);
		
		inHand.addTextChangedListener(textWatcher);
		tdsPer.addTextChangedListener(textWatcher);
		//tdsAmt.addTextChangedListener(watcher);
		//SA 10006
		if(pref.getBoolean("prefIsToolTip", false) || pref.getBoolean("prefIsPaymentToolTip", false))
		{
			onToolTipOn();
		}
		//EA 10006
	}
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
		public void loadPaymentCursor()
		{
			paymentCursor = getContentResolver().query(DBConstant.Payment_Columns.CONTENT_URI, null, null, null, null);	
			currentRecord = Math.max(paymentCursor.getCount(), 0);
			setText();
		}
		public void setText()
		{
			txtTitle.setText("Payment " + (currentRecord+1) +" of " + (paymentCursor.getCount()+1));
		}
		public void onPrev(View v)
		{
			Log.e(">>>>>>>>> onPrev >>>", currentRecord+"");
			if(currentRecord > 0)
			{
				currentRecord--;
				setText();
				populateTextFields(currentRecord);
				paymentCursor.moveToPosition(currentRecord);
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
				paymentCursor.moveToPosition(currentRecord);
			}
		}
		
		public void onNext(View v)
		{
			Log.e(">>>>>>>>> onNext >>>", currentRecord+"");
			currentRecord++;
			if(currentRecord < paymentCursor.getCount())
			{
				
				setText();
				populateTextFields(currentRecord);
				paymentCursor.moveToPosition(currentRecord);
				
			}
			else
			{
				_id = null;
				currentRecord = Math.max(paymentCursor.getCount(), 0);
				setText();
				clearField();
			}
		}
		
		public void onLast(View v)
		{
			Log.e(">>>>>>>>> onLast >>>", currentRecord+"");
			_id = null;
			currentRecord = paymentCursor.getCount();
			{
				currentRecord = Math.max(paymentCursor.getCount(), 0);
				clearField();
//				radioCheque.setChecked(true);
//				radioOther.setChecked(true);
//				radioPayMode.setChecked(false); //SA P3MX01
//				radioPaySrc.setChecked(true); //EA P3MX01
				setText();
			}
		}
		
		public void populateTextFields(int index)
		{
			if(paymentCursor != null && paymentCursor.getCount() > index)
			{
				paymentCursor.moveToPosition(index);
				String receivedDate = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_RECEIVED_DATE));
				String serviceDate = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_SERVICED_DATE));
				String cheque = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_CHEQUE));
				String _inHand = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_IN_HAND));
				String _tdsPer = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_TDS_PER));
				String _tdsAmt = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_TDS_AMT));
				String _amount = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_AMOUNT));
				String _location = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_LOCATION));
				String _bank = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_BANK));
				String concile = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_RECONCILE));
				String src = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_PAYMENT_SOURCE));
				String mode = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_PAYMENT_MODE));

				recDate.setText(receivedDate);
				serDate.setText(serviceDate);
				strCheque.setText(cheque);
				inHand.setText(_inHand);
				tdsPer.setText(_tdsPer);
				tdsAmt.setText(_tdsAmt);
				amount.setText(_amount);
				location.setText(_location);
				bank.setText(_bank);
				if (Integer.parseInt(concile) > 0) {
					isReconcileChk.setChecked(true);
				} else {
					isReconcileChk.setChecked(false);
				}
				
				if (src.equalsIgnoreCase("other")) {
					radioPaySrc.setChecked(false);//ADDED P3MX01
				} else {
					radioPaySrc.setChecked(true);//ADDED P3MX01
				}
				
				if (Integer.parseInt(mode) > 0) { //mode.equalsIgnoreCase("cheque") EDITED P3B03
					radioPayMode.setChecked(true);//ADDED P3MX01
					tdsPer.setText(_tdsPer);//ADDED P3B03
				} else {
					radioPayMode.setChecked(false);//ADDED P3MX01
				}
			}
		}
		//EA U0001
	
	public void onReceiveTime(View v) {
		DialogFragment newFragment = new ReceivedDatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "Received Date");
	}
	
	public void onServiceTime(View v) {
		DialogFragment newFragment = new ServiceDatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "Service Date");
	}
	
	//SA10002
	public void initializeData()
	{
		strLocation = SmartConsultant.getApplication().loadLocation();
		strDepositedBank = SmartConsultant.getApplication().loadDepositedBank();//ADDED 10003
	}
	public void addContent(Uri uri, String str)
	{
		ContentValues values = new ContentValues();
		str = initCap.toTitleCase(str);//ADDED 10004
		values.put(DBConstant.Location_Columns.COLUMN_NAME, str);
		values.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 0);
		getContentResolver().insert(uri, values);
	}
	public void addLocation()
	{
		String s = initCap.toTitleCase(location.getText().toString());//EDITED 10004
		if(s!= null && s.length() > 0 && !strLocation.contains(new String(s)))
		{
			addContent(DBConstant.Location_Columns.CONTENT_URI, s);
		}
	}
	//EA10002
	
	//SA 10003
	public void addDepositedBank()
	{
		String s = initCap.toTitleCase(bank.getText().toString());//EDITED 10004
		if(s!= null && s.length() > 0 && !strDepositedBank.contains(new String(s)))
		{
			addContent(DBConstant.Deposited_Bank_Columns.CONTENT_URI, s);
		}
	}
	//EA 10003
	
	public void onRadioPaySrc(View v)
	{
		boolean checked = ((RadioButton)v).isChecked();
		
		switch(v.getId())
		{
		case R.id.radioPrf:
			if(checked)
			{
				isReconcileChk.setChecked(true);
			}
			break;
		case R.id.radioOther:
			if(checked)
			{
				isReconcileChk.setChecked(false);
			}
			break;
		}
		
	}
	
	
	public void onRadioPayMode(View v)
	{
		boolean checked = ((RadioButton)v).isChecked();
		
		switch(v.getId())
		{
		case R.id.radioCheque:
			if(checked)
			{
				tdsPer.setText("10");
			}
			break;
		case R.id.radioCash:
			if(checked)
			{
				tdsPer.setText("0");
			}
			break;
		}
		
	}
	
	//SA10002
	public void setLocationAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
	    String[] from = new String[] { DBConstant.Location_Columns.COLUMN_NAME };
	    
		locationAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/
				new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
        location.setAdapter(locationAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
        location.setOnItemClickListener(new OnItemClickListener() {
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
	//EA10002
	
	//SA10003
		public void setDepositedBankAdapter()
		{
			//int[] to = new int[] { android.R.id.text1 };
		    String[] from = new String[] { DBConstant.Deposited_Bank_Columns.COLUMN_NAME };
		    
			depositedBankAdapter = 
	            /*new SimpleCursorAdapter(this, 
	                    android.R.layout.simple_dropdown_item_1line, null,
	                    from, to);*/
					new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
	        bank.setAdapter(depositedBankAdapter);

	        // Set an OnItemClickListener, to update dependent fields when
	        // a choice is made in the AutoCompleteTextView.
	        bank.setOnItemClickListener(new OnItemClickListener() {
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
	        depositedBankAdapter.setCursorToStringConverter(new CursorToStringConverter() {
	            @Override
				public String convertToString(android.database.Cursor cursor) {
	                // Get the label for this row out of the "state" column
	                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
	                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Deposited_Bank_Columns.COLUMN_NAME));
	                return str;
	            }
	        });
	      // Set the FilterQueryProvider, to run queries for choices
	        // that match the specified input.
	        depositedBankAdapter.setFilterQueryProvider(new FilterQueryProvider() {
	            @Override
				public Cursor runQuery(CharSequence constraint) {
	                // Search for states whose names begin with the specified letters.
	                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
	            	//Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
	            	
	            	Cursor cursor = getContentResolver().query(DBConstant.Deposited_Bank_Columns.CONTENT_URI, null, DBConstant.Deposited_Bank_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
	            	
	                return cursor;
	            }
	        });
		}
		//EA10003

	
	
	
	public void onSave(View v)
	{
		if((serDate.getText() != null && serDate.getText().toString().length() > 0) 
				&& (location.getText() != null && location.getText().toString().length() > 0)
				&& (bank.getText() != null && bank.getText().toString().length() > 0)
				) //ADDED 10016-1
		{
			if(!radioPayMode.isChecked())//ADDED P3MX01 if(radioPayMode.getCheckedRadioButtonId()!=-1)
			{
//				int selectedId = radioPayMode.getCheckedRadioButtonId();
//                RadioButton rdn = (RadioButton) findViewById(selectedId);
//			    if(rdn.getText().toString().equalsIgnoreCase("Cheque"))
//			    {
			    	if(strCheque.getText()!=null && strCheque.getText().toString().length() > 0)
			    	{
						save();
						loadPaymentCursor();// SA U0001
						clearField1();
					}
			    	else
			    	{
//			    	SU P3B04	
//			    	strCheque.requestFocus();
//			    	strCheque.setError("Please enter cheque no");			    		
			    	validateFields();
//			    	EU P3B04
			    	}
			    } 
			    else
			    {
			    	save();
			    	loadPaymentCursor();//SA U0001
			    	clearField1();
			    }
//			}
		}
		else
		{
			validateFields();
		}
		//Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
	}
	public void clearField1() {
		// addSxDate.setText("Date : DD-MM-YYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if (date.contains("/")) {
			date = date.replace("/", "-");
		}
		recDate.setText(date);
		
//		SA P3M01
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		
		String lastDate = sdf.format(lastDateOfPreviousMonth);
		if (lastDate.contains("/")) {
			lastDate = lastDate.replace("/", "-");
		}
//		EA P3M01
		serDate.setText(lastDate);

		strCheque.setText("");
		location.setText("");
		bank.setText("");
		inHand.setText("");
		amount.setText("");
		radioPaySrc.setChecked(true);//SA P3MX01
		radioPayMode.setChecked(true);//EA P3MX01
	}

	public void onSaveMain(View v)
	{
		if((serDate.getText() != null && serDate.getText().toString().length() > 0) 
				&& (location.getText() != null && location.getText().toString().length() > 0)
				&& (bank.getText() != null && bank.getText().toString().length() > 0)
				)//ADDED 10016-1
		{
			if(!radioPayMode.isChecked())//ADDED P3MX01 if(radioPayMode.getCheckedRadioButtonId()!=-1)
			{
//			int selectedId = radioPayMode.getCheckedRadioButtonId();
//            RadioButton rdn = (RadioButton) findViewById(selectedId);
//		    if(rdn.getText().toString().equalsIgnoreCase("Cheque"))
//		    {
		    	if(strCheque.getText()!=null && strCheque.getText().toString().length() > 0)
		    	{save(); 
		    	finish();}
		    	else
		    	{
//		    	 SU P3B04
//		    	strCheque.requestFocus();
//		    	strCheque.setError("Please enter cheque no");
		    		validateFields();
//		    	 EU P3B04
		    	}
		    } 
		    else
		    {
		    	save();
		    	finish();
		    }
//		}
	}
		else
		{
			validateFields();
		}
	}
	
	//SA 10016-1
    public void validateFields()
    {
    	
    	if((bank.getText().toString() == null || bank.getText().toString()
				.length() == 0))
		{
    		bank.requestFocus();
    		bank.setError("Please enter bank name");
		}
    	if((location.getText().toString() == null || location.getText().toString()
				.length() == 0))
		{
    		location.requestFocus();
    		location.setError("Please enter location");
		}
//    	SA P3B04
    	if(!radioPayMode.isChecked())
		{
		    	if(strCheque.getText()!=null && strCheque.getText().toString().length() > 0)
		    	{
		    	}
		    	else
		    	{
		    		strCheque.requestFocus();
			    	strCheque.setError("Please enter cheque no");		    		
		    	}
    	
		}
//    	EA P3B04
    	
    	if((serDate.getText().toString() == null || serDate.getText().toString()
				.length() == 0))
		{
			serDate.setError("Please enter service date");
		}
    }
	//EA 10016-1
	public void onCancel(View v)
	{
		finish();
	}
	
	
	
	public void save()
	{
		//Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
		Bundle b = new Bundle();
		b.putString("message", "Saving IPD");
		showDialog(LOADING, b);
		ContentValues contentValues = new ContentValues();
		ContentValues contentValuesTemp = new ContentValues();
		
		String svRecvDate = recDate.getText().toString().trim();
		String svServDate = serDate.getText().toString().trim();
		String svPaySrc="";
		String svReconcile="";
		String svPayType="";
		String svCheque="", svInHand="",svTdsPer="",svTdsAmt="",svAmt="",svLocation="",svBank="";
//		RadioButton paySrc = (RadioButton)findViewById(radioPaySrc.getCheckedRadioButtonId()); SC P3MX01
//		RadioButton payType = (RadioButton)findViewById(radioPayMode.getCheckedRadioButtonId()); 
//		svPaySrc=initCap.toTitleCase(paySrc.getText().toString().trim());// EDITED 10004	EC P3MX01
		
		if(isReconcileChk.isChecked())
		{
			svReconcile="1";
		}
		else
		{
			svReconcile="0";
		}
//		SA P3MX01
//			svPayType=payType.getText().toString().trim();
//			if(svPayType.equalsIgnoreCase("Cheque"))
//				svPayType = "1";
//			else
//				svPayType = "0";
		if (radioPayMode.isChecked()) {
			svPayType = "1";
		} else {
			svPayType = "0";
		}
		if (radioPaySrc.isChecked()) {
			svPaySrc = "Professional";
		} else {
			svPaySrc = "Other";
		}
		//		EA P3MX01
			
		svCheque = strCheque.getText().toString().trim();
		svInHand = inHand.getText().toString().trim();
		svTdsPer = tdsPer.getText().toString().trim();
		svTdsAmt = tdsAmt.getText().toString().trim();
		svAmt = amount.getText().toString().trim();
		svLocation = initCap.toTitleCase(location.getText().toString().trim());//EDITED 10004
		svBank = initCap.toTitleCase(bank.getText().toString().trim());//EDITED 10004
		addLocation();//ADDED 10002
		addDepositedBank();//ADDED 10003
		//contentValues.put(DBConstant.Payment_Columns.COLUMN_CUSTOM_ID, SmartConsultant.getPreferences().getIMEINo());
		//contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_CUSTOM_ID, SmartConsultant.getPreferences().getIMEINo());
		contentValues.put(DBConstant.Payment_Columns.COLUMN_RECEIVED_DATE, svRecvDate);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_RECEIVED_DATE, svRecvDate);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_SERVICED_DATE, svServDate);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_SERVICED_DATE, svServDate);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_PAYMENT_SOURCE, svPaySrc);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_PAYMENT_SOURCE, svPaySrc);

		contentValues.put(DBConstant.Payment_Columns.COLUMN_PAYMENT_MODE, svPayType);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_PAYMENT_MODE, svPayType);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_RECONCILE, svReconcile);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_RECONCILE, svReconcile);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_CHEQUE, svCheque);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_CHEQUE, svCheque);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_IN_HAND, svInHand);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_IN_HAND, svInHand);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_TDS_PER, svTdsPer);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_TDS_PER, svTdsPer);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_TDS_AMT, svTdsAmt);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_TDS_AMT, svTdsAmt);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_AMOUNT, svAmt);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_AMOUNT, svAmt);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_LOCATION, svLocation);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_LOCATION, svLocation);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_BANK, svBank);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_BANK, svBank);
		
		contentValues.put(DBConstant.Payment_Columns.COLUMN_PY_WATCH,  "");
		contentValues.put(DBConstant.Payment_Columns.COLUMN_SYNC_STATUS, 0);
		contentValuesTemp.put(DBConstant.Payment_Temp_Columns.COLUMN_SYNC_STATUS, 0);
		

//		getContentResolver().insert(DBConstant.Payment_Columns.CONTENT_URI, contentValues);
//		
//		Uri uri = getContentResolver().insert(DBConstant.Payment_Temp_Columns.CONTENT_URI, contentValuesTemp);
		
		//SA U0001
				if(currentRecord+1 < paymentCursor.getCount()+1)
				{
					paymentCursor.moveToPosition(currentRecord);
					String update_id = paymentCursor.getString(paymentCursor.getColumnIndex(DBConstant.Payment_Columns.COLUMN_ID));
					getContentResolver().update(DBConstant.Payment_Columns.CONTENT_URI,contentValues,DBConstant.Payment_Columns.COLUMN_ID + "=?",new String[]{update_id});
					getContentResolver().update(DBConstant.Payment_Temp_Columns.CONTENT_URI,contentValuesTemp,DBConstant.Payment_Temp_Columns.COLUMN_ID + "=?",new String[]{update_id});
				}
				else
				{
					getContentResolver().insert(DBConstant.Payment_Columns.CONTENT_URI,contentValues);
					getContentResolver().insert(DBConstant.Payment_Temp_Columns.CONTENT_URI, contentValuesTemp);
				}
				//EA U0001
		
		
	//	String myId = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);
		//name.put(DBConstant.Patient_Name_Columns.COLUMN_RED_ID, myId);
		//getContentResolver().insert(DBConstant.Patient_Name_Columns.CONTENT_URI, name);
		removeDialog(LOADING);
		
		//SA 10014
		CustomToast.showToastMessage(this, "Payment Saved");
		//EA 10014
//		clearField();
	}
	//SA 10006
	public void onToolTipOn()
	{
		final Context c = getApplicationContext();
		
		//SU 10015
		strCheque.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tcheque));
			}
		});
		inHand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tinhand));
			}
		});
		tdsPer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, "Modify the % as applicable. The default is 10% for professional income and 0% for other income");
			}
		});
		tdsAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.ttdsamt));
			}
		});
		amount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tamt));
			}
		});
		location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tamtlocation));
			}
		});
		bank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tbank));
			}
		});
		
	}

	//EA 10006

	public void clearField() {
		strCheque.setText("");
		//inHand.setText("");
		// if(lstLocation != null && lstLocation.size() > 0)

		location.setText("");// addSxLocation.setSelection(0);
		bank.setText("");
		//tdsPer.setText("");

		// addSxDate.setText("Date : DD-MM-YYYY");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if (date.contains("/")) {
			date = date.replace("/", "-");
		}
		recDate.setText(date);
//		SA P3M01
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DATE, 1);
		aCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		
		String lastDate = sdf.format(lastDateOfPreviousMonth);
		if (lastDate.contains("/")) {
			lastDate = lastDate.replace("/", "-");
		}
//		EA P3M01
		serDate.setText(lastDate);
		isReconcileChk.setChecked(true);
//		radioPaySrc.check(0);
//		radioPayMode.check(0);
//		radioPrf.setChecked(true);
//		radioCheque.setChecked(true);
		radioPaySrc.setChecked(true);//SA P3MX01
		radioPayMode.setChecked(false);//EA P3MX01
//		SA P2B01
		inHand.setText("");
//		tdsPer.setText("10");
//		EA P2B01
	}

	public static final int LOADING = 101;
	@Override
	protected Dialog onCreateDialog(int id, Bundle b) {
		String msg = b.getString("message");
		switch(id)
		{
			case LOADING:
				ProgressDialog loadingDialog = new ProgressDialog(this);
				loadingDialog.setMessage(msg);
				loadingDialog.setCancelable(false);
				return loadingDialog;
		}
		return super.onCreateDialog(id);
	}
	
		TextWatcher textWatcher = new TextWatcher() {
		 
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	 
	    }
	 
	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	  
	    }
	 
	    @Override
	    public void afterTextChanged(Editable editable) {
	    
	    	String strHand = inHand.getText().toString().trim();
	    	String strTdsPer = tdsPer.getText().toString().trim();
	    	String strTdsAmt = tdsAmt.getText().toString().trim();
	    	int intInHand, intInTdsPer, intTdsAmt;
			try {
				if ((!strHand.isEmpty())) {
					intInHand = Integer.parseInt(strHand);
				} else {
					intInHand = 0;
				}

				if ((!strTdsPer.isEmpty())) {
					intInTdsPer = Integer.parseInt(strTdsPer);
				} else {
					intInTdsPer = 0;
				}
				intTdsAmt = calculate(intInHand, intInTdsPer);
//				SA P2B02
//				amount.setText(String.valueOf(intInHand + intTdsAmt));
				amount.setText(String.valueOf(intTdsAmt));
//				tdsAmt.setText(String.valueOf(intTdsAmt));
				tdsAmt.setText(String.valueOf(intTdsAmt - intInHand));
//				EA P2B02
				tdsAmt.addTextChangedListener(watcher);
			} catch (Exception e) {

			}
	    }
	};
	
	public int calculate(int inHand, int tdsPer)
	{
		int amount = 0;
//		SA P2B02
//		amount = (inHand * tdsPer)/100;
		amount = (inHand * 100)/(100 - tdsPer);
//		EA P2B02
		return amount;
	}
	
	TextWatcher watcher = new TextWatcher() {
		 
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	 
	    }
	 
	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	  
	    }
	 
	    @Override
	    public void afterTextChanged(Editable editable) {
	    
	    	String strHand = inHand.getText().toString().trim();
	    	String strTdsPer = tdsPer.getText().toString().trim();
	    	String strTdsAmt = tdsAmt.getText().toString().trim();
	    	int intInHand, intInTdsAmt, intTdsPer;
	    	if((!strHand.isEmpty()))
	    	{
		    	intInHand = Integer.parseInt(strHand);
	    	}
	    	else 
	    	{
	    		intInHand = 0;
	    	}
	    	
	    	if((!strTdsAmt.isEmpty()))
	    	{
	    		intInTdsAmt = Integer.parseInt(strTdsPer);
	    	}
	    	else
	    	{
	    		intInTdsAmt= 0;
	    	}
	    	intTdsPer = calculateTdsPer(intInHand,intInTdsAmt);
	    	//amount.setText(String.valueOf(intInHand - intTdsAmt));
	    	//tdsPer.setText(String.valueOf(intTdsPer));
	    	//Toast.makeText(getApplicationContext(), ""+String.valueOf(intTdsPer), Toast.LENGTH_SHORT).show();
	    }
	};
	
	public int calculateTdsPer(int intInHand,int intInTdsAmt)
	{
		int tdsPercentage = 0;
		if(intInTdsAmt > intInHand)
		{
			//Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
			intInTdsAmt = intInHand;
			tdsPercentage = 100;
		}
		else
		{
			try
			{
			tdsPercentage = (intInTdsAmt*100)/intInHand;
			}
			catch(ArithmeticException e)
			{
				tdsPercentage =0;
			}
		}
		return tdsPercentage;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case com.smarthumanoid.com.R.id.mnuHelp:
			Intent i = new Intent(this, HelpActivity.class);
			i.putExtra("caller", "payment");
			startActivity(i);
			return true;
			//SA 10007
		case com.smarthumanoid.com.R.id.mnuSettings:
			Intent in = new Intent(this, PrefsSecondaryActivity.class);
			in.putExtra("caller", "payment");
			startActivityForResult(in, SECSETTINGS);
			return true;
			//EA 10007
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	//SA 10007
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == SECSETTINGS) {
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
	}
    
	//EA 10007
	class ServiceDatePickerFragment extends DialogFragment implements
			OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
//			SA P3B05
			int day,mnth,yr;
			try {
				String[] strDate = serDate.getText().toString().split("-");
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
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			String date;
			monthOfYear++;
			if (dayOfMonth < 10) {
				date = "0" + dayOfMonth + "-";
			} else {
				date = dayOfMonth + "-";
			}
			if (monthOfYear < 10) {
				date += "0" + monthOfYear + "-";
			} else {
				date += monthOfYear + "-";
			}

			date += year;
			// addSxDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
			serDate.setText(date);
		}
	}
	
	
	class ReceivedDatePickerFragment extends DialogFragment implements
			OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
//			SA P3B05
			int day,mnth,yr;
			try {
				String[] strDate = recDate.getText().toString().split("-");
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
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			String date;
			monthOfYear++;
			if (dayOfMonth < 10) {
				date = "0" + dayOfMonth + "-";
			} else {
				date = dayOfMonth + "-";
			}
			if (monthOfYear < 10) {
				date += "0" + monthOfYear + "-";
			} else {
				date += monthOfYear + "-";
			}

			date += year;
			// addSxDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
			recDate.setText(date);
		}
	}
}
