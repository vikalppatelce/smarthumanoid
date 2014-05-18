
/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 1000A       VIKALP PATEL   04/02/14         RELEASE          ADDED SX,IPD & OPD IMAGES IN UPLOAD
 * 1000B-3     VIKALP PATEL   11/02/14         RELEASE          ADDED PAYMENT IN API
 * 1000F       VIKALP PATEL   17/02/14         RELEASE          IMAGE SYNC BUG
 * A0001       VIKALP PATEL   27/02/14         PHASE-II         ADDING IMAGE SYNC TRUE
 * S0002       VIKALP PATEL   28/02/14         SECURITY			USER CHECK[DEVICE - SUBSCRIPTION]
 * M0001       VIKALP PATEL   28/02/14         MONETIZE         FETCHING SPONSORER IMAGE FOR THAT USER
 * B0S01       VIKALP PATEL   05/03/14         BUG              UPDATING UI WITH PROPER MESSAGE ON INTERNET CONNECTION LOST.
 * 1000G       VIKALP PATEL   05/03/14         RELEASE          ADDED BANK LOV
 * Z0001       VIKALP PATEL   12/03/14         PHASE-II         NOTIFICATION ON STATUS BAR
 * Z0002       VIKALP PATEL   22/04/14         BUG              POOR CONNECTIVITY OR NO CONNECTION ON NOTIFICATION
 * Z0003       VIKALP PATEL   23/04/14         SUPPRESSED		NOTIFICATION ON STATUS BAR SUPPRESSED(WASTE OF TIME)
 * P3A02       VIKALP PATEL   24/04/14         PHASE-III        ADDED IS SHARED ON SERVICES
 * P3A03       VIKALP PATEL   08/05/14         PHASE-III        ADDED CONTACT SHARING IS PRIVATE ON SERVICES
 * P3A04       VIKALP PATEL   08/05/14         PHASE-III        ADDED CONTACT SHARING ON SERVICES
 * P3A05       VIKALP PATEL   10/05/14         PHASE-III        UPDATE CONTACT SHARING ON SERVICES FROM RESPONSE
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.dto.ExpenseDTO;
import com.netdoers.com.dto.ExpenseDetailsDTO;
import com.netdoers.com.dto.MediaUploadResponse;
import com.netdoers.com.dto.PatientDTO;
import com.netdoers.com.dto.PatientDetailsDTO;
import com.netdoers.com.dto.PatientShareDetailsDTO;
import com.netdoers.com.dto.PaymentDTO;
import com.netdoers.com.dto.RecordingDTO;
import com.netdoers.com.dto.UploadDataResponseDTO;
import com.netdoers.com.dto.lovDTO;
import com.netdoers.com.ui.HomeActivity;
import com.netdoers.com.ui.LoginScreen;
import com.netdoers.com.utils.AppConstants;
import com.smarthumanoid.com.R;

public class UploadData extends Service{

	public static final String BROADCAST_ACTION = "com.netdoers.com.displayevent";
    Intent intent;
    private static final String TAG = "BroadcastService";
    //SA ZOOO1 Z0003
//    public static final int NOTIFICATION_ID = 1;
//    private NotificationManager mNotifyManager;
//    NotificationCompat.Builder mBuilder;
//    PendingIntent contentIntent;
    //EA Z0001
    
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		intent = new Intent(BROADCAST_ACTION);
		Log.i("Service", String.valueOf(System.currentTimeMillis()));
//		mNotifyManager =(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//SA Z0001 Z0003
//		contentIntent = PendingIntent.getActivity(SmartConsultant.getApplication().getApplicationContext(), 0,new Intent(this, HomeActivity.class), 0);
//		mBuilder = new NotificationCompat.Builder(SmartConsultant.getApplication().getApplicationContext());
//		mBuilder.setContentIntent(contentIntent);//EA Z0001
		onStartService();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		return START_NOT_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/*handler.removeCallbacks(sendUpdatesToUI);
		sendUpdatesToUI = null;
		handler = null;*/
	}
	
    
    private void DisplayLoggingInfo(String message) {
    	Log.d(TAG, "entered DisplayLoggingInfo");
    	intent.putExtra("text", message);
    	sendBroadcast(intent);
    }

	ArrayList<lovDTO> location = new ArrayList<lovDTO>();
	ArrayList<lovDTO> procedure = new ArrayList<lovDTO>();
	ArrayList<lovDTO> ward = new ArrayList<lovDTO>();

	ArrayList<lovDTO> teamMember = new ArrayList<lovDTO>();
	ArrayList<lovDTO> type = new ArrayList<lovDTO>();
	ArrayList<lovDTO> ref = new ArrayList<lovDTO>();
	ArrayList<lovDTO> startTime = new ArrayList<lovDTO>();
	ArrayList<lovDTO> level = new ArrayList<lovDTO>();
	ArrayList<lovDTO> modeOfPayment = new ArrayList<lovDTO>();
	ArrayList<lovDTO> bank = new ArrayList<lovDTO>();
	
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
//	SA 1000G
	ArrayList<String> strDepositedBank = new ArrayList<String>();
	ArrayList<lovDTO> depositedBank = new ArrayList<lovDTO>();
//	EA 1000G
	
	//	ArrayList<lovDTO> expense_category;

	ArrayList<PatientDTO> sxPatient = new ArrayList<PatientDTO>();
	ArrayList<PatientDTO> ipdPatient = new ArrayList<PatientDTO>();
	ArrayList<PatientDTO> opdPatient = new ArrayList<PatientDTO>();
	
	//SA 1000A
	ArrayList<PatientDetailsDTO> sxPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();
	ArrayList<PatientDetailsDTO> ipdPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();
	ArrayList<PatientDetailsDTO> opdPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();
	//EA 1000A

	ArrayList<RecordingDTO> recordingDTOs;
	ArrayList<ExpenseDTO> expenseDTOs;
	ArrayList<ExpenseDetailsDTO> expenseDetailsDTOs;
	ArrayList<PaymentDTO> paymentDTOs;//ADDED 1000B-3


	public void onStartService()
	{
		loadLocation();
		loadProcedure();
		loadWard();
		loadTeamMember();
		loadType();
		loadRef();
		loadStartTime();
		loadLevel();
		loadModeOfPayment();
		loadBank();
		loadDepositedBank();//ADDED 1000G
		loadSxPatient();
		loadExpenses();
		loadPayment();//ADDED 1000B-3
		loadRecording();
		uploadServiceData();
	}

	public void loadLocation()
	{
		Cursor c = getContentResolver().query(DBConstant.Location_Columns.CONTENT_URI, null, DBConstant.Location_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			location = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm, 
						c.getInt(c.getColumnIndex(DBConstant.Location_Columns.COLUMN_ID)));
				location.add(dto);
			}
			Log.e("HEMANT : location : ", location.toString());
			c.close();
		}
		
		c = getContentResolver().query(DBConstant.Location_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strLocation = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME));
				strLocation.add(nm);
			}
			Log.e("HEMANT : location : ", location.toString());
			c.close();
		}

	}
	public void loadProcedure()
	{
		Cursor c = getContentResolver().query(DBConstant.Procedure_Columns.CONTENT_URI, null, DBConstant.Procedure_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			procedure = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Procedure_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Procedure_Columns.COLUMN_ID)));
				procedure.add(dto);
			}
			c.close();
			Log.e("HEMANT : procedure : ", procedure.toString());
		}
		
		c = getContentResolver().query(DBConstant.Procedure_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strProcedure = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Procedure_Columns.COLUMN_NAME));
				strProcedure.add(nm);
			}
			c.close();
			Log.e("HEMANT : procedure : ", procedure.toString());
		}


	}
	public void loadWard()
	{
		Cursor c = getContentResolver().query(DBConstant.Ward_Columns.CONTENT_URI, null, DBConstant.Ward_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			ward = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Ward_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Ward_Columns.COLUMN_ID)));
				ward.add(dto);
			}
			c.close();
			Log.e("HEMANT : loadWard : ", ward.toString());
		}
		
		c = getContentResolver().query(DBConstant.Ward_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strWard = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Ward_Columns.COLUMN_NAME));
				strWard.add(nm);
			}
			c.close();
			Log.e("HEMANT : loadWard : ", ward.toString());
		}

	}

	public void loadTeamMember()
	{
		Cursor c = getContentResolver().query(DBConstant.TMEMBER_Columns.CONTENT_URI, null, DBConstant.TMEMBER_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			teamMember = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{

				String nm = c.getString(c.getColumnIndex(DBConstant.TMEMBER_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.TMEMBER_Columns.COLUMN_ID)));
				teamMember.add(dto);
			}
			c.close();
			Log.e("HEMANT : teamMember : ", teamMember.toString());
		}
		
		c = getContentResolver().query(DBConstant.TMEMBER_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strTeamMember = new ArrayList<String>();
			while(c.moveToNext())
			{

				String nm = c.getString(c.getColumnIndex(DBConstant.TMEMBER_Columns.COLUMN_NAME));
				strTeamMember.add(nm);
			}
			c.close();
			Log.e("HEMANT : teamMember : ", teamMember.toString());
		}

	}
	public void loadType()
	{
		Cursor c = getContentResolver().query(DBConstant.Types_Columns.CONTENT_URI, null, DBConstant.Types_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			type = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Types_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Types_Columns.COLUMN_ID)));
				type.add(dto);	
			}
			c.close();
			Log.e("HEMANT : type: ", type.toString());
		}
		
		c = getContentResolver().query(DBConstant.Types_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strType = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Types_Columns.COLUMN_NAME));
				strType.add(nm);
			}
			c.close();
			Log.e("HEMANT : type: ", type.toString());
		}

	}

	public void loadRef()
	{
		Cursor c = getContentResolver().query(DBConstant.Ref_Columns.CONTENT_URI, null, DBConstant.Ref_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			ref = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Ref_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Ref_Columns.COLUMN_ID)));
				ref.add(dto);
			}
			c.close();
			Log.e("HEMANT : ref : ", ref.toString());
		}
		
		c = getContentResolver().query(DBConstant.Ref_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strRef = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Ref_Columns.COLUMN_NAME));
				strRef.add(nm);
			}
			c.close();
			Log.e("HEMANT : ref : ", ref.toString());
		}

	}
	public void loadStartTime()
	{
		Cursor c = getContentResolver().query(DBConstant.StartTime_Columns.CONTENT_URI, null, DBConstant.StartTime_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			startTime = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.StartTime_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.StartTime_Columns.COLUMN_ID)));
				startTime.add(dto);
			}
			c.close();
			Log.e("HEMANT : startTime : ", startTime.toString());
		}
		
		c = getContentResolver().query(DBConstant.StartTime_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strStartTime = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.StartTime_Columns.COLUMN_NAME));
				strStartTime.add(nm);
			}
			c.close();
			Log.e("HEMANT : startTime : ", startTime.toString());
		}

	}

	public void loadLevel()
	{
		Cursor c = getContentResolver().query(DBConstant.Level_Columns.CONTENT_URI, null, DBConstant.Level_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			level = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Level_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Level_Columns.COLUMN_ID)));
				level.add(dto);
			}
			c.close();
			Log.e("HEMANT : level: ", level.toString());
		}
		
		c = getContentResolver().query(DBConstant.Level_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strLevel = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Level_Columns.COLUMN_NAME));
				strLevel.add(nm);
			}
			c.close();
			Log.e("HEMANT : level: ", level.toString());
		}

	}

	public void loadModeOfPayment()
	{
		Cursor c = getContentResolver().query(DBConstant.ModeOfPayment_Columns.CONTENT_URI, null, DBConstant.ModeOfPayment_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			modeOfPayment = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.ModeOfPayment_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.ModeOfPayment_Columns.COLUMN_ID)));
				modeOfPayment.add(dto);
			}
			c.close();
			Log.e("HEMANT : modeOfPayment : ", modeOfPayment.toString());
		}
		
		c = getContentResolver().query(DBConstant.ModeOfPayment_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strModeOfPayment = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.ModeOfPayment_Columns.COLUMN_NAME));
				strModeOfPayment.add(nm);
			}
			c.close();
			Log.e("HEMANT : modeOfPayment : ", modeOfPayment.toString());
		}

	}

	public void loadBank()
	{
		Cursor c = getContentResolver().query(DBConstant.Bank_Columns.CONTENT_URI, null, DBConstant.Bank_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			bank = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Bank_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,
						c.getInt(c.getColumnIndex(DBConstant.Bank_Columns.COLUMN_ID)));
				bank.add(dto);
			}
			c.close();
			Log.e("HEMANT : bank : ", bank.toString());
		}
		
		
		c = getContentResolver().query(DBConstant.Bank_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strBank = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Bank_Columns.COLUMN_NAME));
				strBank.add(nm);
			}
			c.close();
			Log.e("HEMANT : bank : ", bank.toString());
		}

	}
	
//	SA 1000G
	public void loadDepositedBank()
	{
		Cursor c = getContentResolver().query(DBConstant.Deposited_Bank_Columns.CONTENT_URI, null, DBConstant.Bank_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			depositedBank = new ArrayList<lovDTO>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Deposited_Bank_Columns.COLUMN_NAME));
				lovDTO dto = new lovDTO(nm,c.getInt(c.getColumnIndex(DBConstant.Deposited_Bank_Columns.COLUMN_ID)));
				depositedBank.add(dto);
			}
			c.close();
			Log.e("HEMANT : Deposited bank : ", depositedBank.toString());
		}
		
		
		c = getContentResolver().query(DBConstant.Deposited_Bank_Columns.CONTENT_URI, null, null, null, null);

		if(c != null && c.getCount() > 0)
		{
			strDepositedBank = new ArrayList<String>();
			while(c.moveToNext())
			{
				String nm = c.getString(c.getColumnIndex(DBConstant.Deposited_Bank_Columns.COLUMN_NAME));
				strDepositedBank.add(nm);
			}
			c.close();
			Log.e("HEMANT : Deposited bank : ", strDepositedBank.toString());
		}

	}
//	EA 1000G

	public void loadSxPatient()
	{
		String _id; // 0
		String custId; // 1
		String name; // 2
		String age; //3
		String totalCount; //4
		String diagnosis; //5
		String service_type;//6
		String type;//6
		String ref;//7
		String location;//8
		String duration;//9
		String startTime;//10
		String date;//11
		String ward;//12
		String emergency;//13
		String teamMember;//14
		String procedure;//15
		String level;//16
		String noteStr;//17
		String refer;//8
		String sex;
		String status;
		String sx_watch;
		String is_shared;//ADDED P3A02
		String is_shared_private;//ADDED P3A03

		Cursor c = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, null, DBConstant.Patient_Columns.COLUMN_SYNC_STATUS + "=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			sxPatient = new ArrayList<PatientDTO>();
			ipdPatient = new ArrayList<PatientDTO>();;
			opdPatient = new ArrayList<PatientDTO>();;
			while(c.moveToNext())
			{
				_id  = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_ID));
				custId = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_CUSTOM_ID));
				name = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NAME));
				age = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_AGE));
				totalCount = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TOTALCOUNT));
				diagnosis = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DIAGNOSIS));
				type = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TYPE));
				service_type = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SERVICE_TYPE));
				ref = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_REF));
				location = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_LOCATION));
				duration = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DURATION));
				startTime = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_STARTTIME));
				date = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_DATE));
				ward = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_WARD));
				emergency = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_EMERGENCY));
				teamMember = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_TEAM_MEMBER));
				procedure = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_PROCEDURE));
				level = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_LEVEL));
				noteStr = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_NOTE));
				refer = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_REF));
				sex = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SEX));
				sx_watch = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SX_WATCH));
				status = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SYNC_STATUS));
				is_shared = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING));//ADDED P3A02
				is_shared_private = c.getString(c.getColumnIndex(DBConstant.Patient_Columns.COLUMN_SHARING_PRIVATE));//ADDED P3A03

//				PatientDTO patientDTO = new PatientDTO(_id, custId, name, age, totalCount, diagnosis, type, ref, location, startTime, duration, date, ward, emergency, teamMember, procedure, level, noteStr, sex, status, service_type, sx_watch);
//				PatientDTO patientDTO = new PatientDTO(_id, custId, name, age, totalCount, diagnosis, type, ref, location, startTime, duration, date, ward, emergency, teamMember, procedure, level, noteStr, sex, status, service_type, sx_watch,is_shared);//ADDED P3A02
				PatientDTO patientDTO = new PatientDTO(_id, custId, name, age, totalCount, diagnosis, type, ref, location, startTime, duration, date, ward, emergency, teamMember, procedure, level, noteStr, sex, status, service_type, sx_watch,is_shared,is_shared_private);//ADDED P3A03
				if(service_type != null && service_type.contains("2"))
				{
					sxPatient.add(patientDTO);
				}
				else if(service_type != null && service_type.contains("1"))
				{
					ipdPatient.add(patientDTO);
				}
				else if(service_type != null && service_type.contains("0"))
				{
					opdPatient.add(patientDTO);
				}
			}
			c.close();
			
			//SA 1000A
			for(int i = 0; i < sxPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID +"=?", new String []{sxPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientDetailsDTO> SxPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_ID));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_NAME));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS));
						String url = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL));
						PatientDetailsDTO dto = new PatientDetailsDTO(_id_,_name,_pid, _type, url, null);
						SxPatientDetailsDTOs.add(dto);
					}
					sxPatient.get(i).setPaths(SxPatientDetailsDTOs);
				}

			}
			
			for(int i = 0; i < ipdPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID +"=?", new String []{ipdPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientDetailsDTO> SxPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_ID));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_NAME));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS));
						String url = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL));
						PatientDetailsDTO dto = new PatientDetailsDTO(_id_,_name,_pid, _type, url, null);
						SxPatientDetailsDTOs.add(dto);
					}
					ipdPatient.get(i).setPaths(SxPatientDetailsDTOs);
				}

			}
			
			for(int i = 0; i < opdPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Details_Columns.CONTENT_URI, null, DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID +"=?", new String []{opdPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientDetailsDTO> SxPatientDetailsDTOs = new ArrayList<PatientDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_ID));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_NAME));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_PATIENT_TYPE));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS));
						String url = c.getString(c.getColumnIndex(DBConstant.Patient_Details_Columns.COLUMN_URL));
						PatientDetailsDTO dto = new PatientDetailsDTO(_id_,_name,_pid, _type, url, null);
						SxPatientDetailsDTOs.add(dto);
					}
					opdPatient.get(i).setPaths(SxPatientDetailsDTOs);
				}

			}
			//EA 1000A
			
			
			//SA P3A04
			for(int i = 0; i < sxPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Contact_Columns.CONTENT_URI, null, DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID +"=?", new String []{sxPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientShareDetailsDTO> SxPatientShareDTOs = new ArrayList<PatientShareDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_ID));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_TYPE));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME));
						String _number = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS));
						PatientShareDetailsDTO dto = new PatientShareDetailsDTO(_id_, _pid, _type, _name, _number, null);
						SxPatientShareDTOs.add(dto);
					}
					sxPatient.get(i).setContactPaths(SxPatientShareDTOs);
				}

			}
			
			for(int i = 0; i < ipdPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Contact_Columns.CONTENT_URI, null, DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID +"=?", new String []{ipdPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientShareDetailsDTO> SxPatientShareDTOs = new ArrayList<PatientShareDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_ID));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_TYPE));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME));
						String _number = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS));
						PatientShareDetailsDTO dto = new PatientShareDetailsDTO(_id_, _pid, _type, _name, _number, null);
						SxPatientShareDTOs.add(dto);
					}
					ipdPatient.get(i).setContactPaths(SxPatientShareDTOs);
				}

			}
			
			for(int i = 0; i < opdPatient.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Patient_Contact_Columns.CONTENT_URI, null, DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID +"=?", new String []{opdPatient.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<PatientShareDetailsDTO> SxPatientShareDTOs = new ArrayList<PatientShareDetailsDTO>();

					while(c.moveToNext())
					{
						String _id_ = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_ID));
						String _pid = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID));
						String _type = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_TYPE));
						String _name = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NAME));
						String _number = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_CONTACT_NUMBER));
						String _status = c.getString(c.getColumnIndex(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS));
						PatientShareDetailsDTO dto = new PatientShareDetailsDTO(_id_, _pid, _type, _name, _number, null);
						SxPatientShareDTOs.add(dto);
					}
					opdPatient.get(i).setContactPaths(SxPatientShareDTOs);
				}

			}
			//EA P3A04
		}
	}

	public void loadExpenses()
	{
		String _id;
		String date;
		String amount;
		String paument_mode;
		String description;
		String category;
		String status;

		Cursor c = getContentResolver().query(DBConstant.Expeses_Columns.CONTENT_URI, null, DBConstant.Expeses_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);
		if( c != null && c.getCount() > 0)
		{
			expenseDTOs = new ArrayList<ExpenseDTO>();
			while(c.moveToNext())
			{
				_id = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
				date = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_DATE));
				amount = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_AMOUNT));
				paument_mode = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_PAYMENT_MODE));
				description = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_DESCRIPTION));
				category = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_CATEGORY));
				status = c.getString(c.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_SYNC_STATUS));
				expenseDTOs.add(new ExpenseDTO(_id, date, amount, paument_mode, description, category, status, null));
			}
			c.close();

			for(int i = 0; i < expenseDTOs.size(); i++)
			{
				c = getContentResolver().query(DBConstant.Expeses_Details_Columns.CONTENT_URI, null, DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID +"=?", new String []{expenseDTOs.get(i).get_id()}, null);
				if( c != null && c.getCount() > 0)
				{
					ArrayList<ExpenseDetailsDTO> expenseDetailsDTOs = new ArrayList<ExpenseDetailsDTO>();

					while(c.moveToNext())
					{
						String _exid = c.getString(c.getColumnIndex(DBConstant.Expeses_Details_Columns.COLUMN_ID));
						String name = c.getString(c.getColumnIndex(DBConstant.Expeses_Details_Columns.COLUMN_NAME));
						String exp_id = c.getString(c.getColumnIndex(DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID));
						String url = c.getString(c.getColumnIndex(DBConstant.Expeses_Details_Columns.COLUMN_URL));
						ExpenseDetailsDTO dto = new ExpenseDetailsDTO(_exid, name, exp_id, url);
						expenseDetailsDTOs.add(dto);
					}
					expenseDTOs.get(i).setPaths(expenseDetailsDTOs);
				}

			}
		}
	}
	
	//SA 1000B-3
	public void loadPayment()
	{
		String _id;
		String received_date;
		String service_date;
		String src;
		String reconcile;
		String mode;
		String cheque;
		String inhand;
		String tdsper,tdsAmt;
		String amount;
		String location;
		String bank;
		String status;

		Cursor c = getContentResolver().query(DBConstant.Payment_Columns.CONTENT_URI, null, DBConstant.Payment_Columns.COLUMN_SYNC_STATUS +"=?", new String[]{"0"}, null);
		if( c != null && c.getCount() > 0)
		{
			paymentDTOs = new ArrayList<PaymentDTO>();
			while(c.moveToNext())
			{
				_id = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_ID));
				received_date = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_RECEIVED_DATE));
				service_date = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_SERVICED_DATE));
				src = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_PAYMENT_SOURCE));
				reconcile = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_RECONCILE));
				mode = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_PAYMENT_MODE));
				cheque = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_CHEQUE));
				inhand = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_IN_HAND));
				tdsper = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_TDS_PER));
				tdsAmt= c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_TDS_AMT));
				amount= c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_AMOUNT));
				location= c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_LOCATION));
				bank = c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_BANK));
				status= c.getString(c.getColumnIndex(DBConstant.Payment_Columns.COLUMN_SYNC_STATUS));
				paymentDTOs.add(new PaymentDTO(_id, null,received_date,service_date,src,reconcile,
						mode, cheque,inhand, tdsper,tdsAmt,amount,location,bank,null, status, null));
			}
			c.close();
		}
	}
	//EA 1000B-3

	public void loadRecording()
	{
		Cursor c = getContentResolver().query(DBConstant.Recoding_Columns.CONTENT_URI, null, DBConstant.Recoding_Columns.COLUMN_SYNCKEDSTATUS +"=?", new String[]{"0"}, null);

		if(c != null && c.getCount() > 0)
		{
			recordingDTOs = new ArrayList<RecordingDTO>();

			while(c.moveToNext())
			{
				String _id = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_ID));
				String type = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_TYPE)); 
				String location = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_LOCATION));
				String date = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_DATE));
				String url = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_URL));
				String status = c.getString(c.getColumnIndex(DBConstant.Recoding_Columns.COLUMN_SYNCKEDSTATUS));

				RecordingDTO rec = new RecordingDTO(_id, type, location, date, url, status);
				recordingDTOs.add(rec);
			}
		}
	}	
//	SA B0S01
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
//	EA B0S01
	
	public void uploadServiceData()
	{
		JSONObject finalJSON = new JSONObject();
		JSONObject lov = new JSONObject();
		JSONObject tables = new JSONObject();
		try
		{
			if(location != null && location.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getLocationJSON(location);
				lov.put("location", locationData);
			}
			if(procedure != null && procedure.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getProcedureJSON(procedure);
				lov.put("diagnose_procedure", locationData);
			}
			if(ward != null && ward.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getWardJSON(ward);
				lov.put("ward", locationData);
			}
			if(teamMember != null && teamMember.size() > 0)
			{
				JSONArray  locationData = RequestBuilder.getTeam_MemberJSON(teamMember);
				lov.put("team_member", locationData);
			}
			if(type != null && type.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getPatient_TypeJSON(type);
				lov.put("patient_type", locationData);
			}
			if(ref != null && ref.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getReferred_ByJSON(ref);
				lov.put("referred_by", locationData);
			}
			if(startTime != null && startTime.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getStart_TimeJSON(startTime);
				lov.put("start_time", locationData);
			}
			if(level != null && level.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getSurgery_LevelJSON(level);
				lov.put("surgery_level", locationData);
			}
			if(modeOfPayment != null && modeOfPayment.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getPayment_ModeJSON(modeOfPayment);
				lov.put("payment_mode", locationData);
			}
			/*if(bank != null && bank.size() > 0)
			{
				JSONObject locationData = RequestBuilder.getBankJSON(bank);
				finalJSON.put("bank", locationData);
			}*/

			if(bank != null && bank.size() > 0)
			{
				JSONArray locationData = RequestBuilder.getExpense_CategoryJSON(bank);
				lov.put("expense_category", locationData);
			}
			
			// SA 1000G
			try {
				if (depositedBank != null && depositedBank.size() > 0) {
					JSONArray locationData = RequestBuilder.getDepositedBankJSON(depositedBank);
					lov.put("bank", locationData);
				}
			} catch (Exception e) {
			}
			// EA 1000G

			JSONArray locationData = RequestBuilder.getsxJSON(sxPatient, ipdPatient, opdPatient);
			tables.put("service", locationData);
			
			//SA 1000B-3
			if(paymentDTOs != null && paymentDTOs.size() > 0)
			{
				JSONArray pay = RequestBuilder.getPaymentData(paymentDTOs);
				tables.put("payment", pay);
			}
			//EA 1000B-3
			
			if(recordingDTOs != null && recordingDTOs.size() > 0)
			{
				JSONArray rec = RequestBuilder.getRecordingData(recordingDTOs);
				tables.put("service_audio", rec);
			}
			if(expenseDTOs != null && expenseDTOs.size() > 0)
			{
				JSONArray exp = RequestBuilder.getExpensesData(expenseDTOs);
				tables.put("expense", exp);

				JSONArray imagesDetails = new JSONArray();
				for(int i = 0; i < expenseDTOs.size(); i++)
				{//SA 1000F
					ArrayList<ExpenseDetailsDTO> images = null;
					try {
						images = expenseDTOs.get(i).getPaths();
						for (int j = 0; j < images.size(); j++) {
							JSONObject t = RequestBuilder.getImageDetails(images.get(j));
							imagesDetails.put(t);
						}
					} catch (Exception e) {
						Log.e("expense_image", e.toString());
					}//EA 1000F
				}
				tables.put("expense_image", imagesDetails);	
			}
			
			//SA 1000A
			if ((sxPatient != null && sxPatient.size() > 0)|| (ipdPatient != null && ipdPatient.size() > 0)|| (opdPatient != null && opdPatient.size() > 0))
			{
				JSONArray imagesDetails = new JSONArray();
				if(sxPatient!=null && sxPatient.size() > 0)
				{
					for(int i = 0; i < sxPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientDetailsDTO> images = null;
						try {
							images = sxPatient.get(i).getPaths();
							for (int j = 0; j < images.size(); j++) {
								JSONObject t = RequestBuilder.getPatientImageDetails(images.get(j));
								imagesDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_image-SX", e.toString());
						}
						//EA 1000F
					}
				}
				
				if(ipdPatient!=null && ipdPatient.size() > 0)
				{
					for(int i = 0; i < ipdPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientDetailsDTO> images = null;
						try {
							images = ipdPatient.get(i).getPaths();
							for (int j = 0; j < images.size(); j++) {
								JSONObject t = RequestBuilder.getPatientImageDetails(images.get(j));
								imagesDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_image-IPD", e.toString());
						}
						//EA 1000F
					}
				}
				
				if(opdPatient!=null && opdPatient.size() > 0)
				{
					for(int i = 0; i < opdPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientDetailsDTO> images = null;
						try {
							images = opdPatient.get(i).getPaths();
							for (int j = 0; j < images.size(); j++) {
								JSONObject t = RequestBuilder.getPatientImageDetails(images.get(j));
								imagesDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_image-OPD", e.toString());
						}
						//EA 1000F
					}
				}
				tables.put("service_image", imagesDetails);
			}
			//EA 1000A
			
			//SA P3A04
			if ((sxPatient != null && sxPatient.size() > 0)|| (ipdPatient != null && ipdPatient.size() > 0)|| (opdPatient != null && opdPatient.size() > 0))
			{
				JSONArray contactsDetails = new JSONArray();
				if(sxPatient!=null && sxPatient.size() > 0)
				{
					for(int i = 0; i < sxPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientShareDetailsDTO> contacts = null;
						try {
							contacts = sxPatient.get(i).getContactPaths();
							for (int j = 0; j < contacts.size(); j++) {
								JSONObject t = RequestBuilder.getPatientContactDetails(contacts.get(j));
								contactsDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_contact-SX", e.toString());
						}
						//EA 1000F
					}
				}
				
				if(ipdPatient!=null && ipdPatient.size() > 0)
				{
					for(int i = 0; i < ipdPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientShareDetailsDTO> contacts = null;
						try {
							contacts = ipdPatient.get(i).getContactPaths();
							for (int j = 0; j < contacts.size(); j++) {
								JSONObject t = RequestBuilder.getPatientContactDetails(contacts.get(j));
								contactsDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_contact-IPD", e.toString());
						}
						//EA 1000F
					}
				}
				
				if(opdPatient!=null && opdPatient.size() > 0)
				{
					for(int i = 0; i < opdPatient.size(); i++)
					{
						//SA 1000F
						ArrayList<PatientShareDetailsDTO> contacts = null;
						try {
							contacts = opdPatient.get(i).getContactPaths();
							for (int j = 0; j < contacts.size(); j++) {
								JSONObject t = RequestBuilder.getPatientContactDetails(contacts.get(j));
								contactsDetails.put(t);
							}
						} catch (Exception e) {
							Log.e("service_contact-OPD", e.toString());
						}
						//EA 1000F
					}
				}
				tables.put("service_sharing", contactsDetails);
			}
			//EA P3A04

		}
		catch (Exception e) {
			// TODO: handle exception
			Log.e("JSONCREATING-uploaddataservice()", e.toString());
		}		
		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String currentSIMImsi = mTelephonyMgr.getDeviceId();
		
		/*
		 * X0001
		 */
		
		SmartConsultant.getPreferences().setDeviceId(currentSIMImsi);
		
		JSONObject jsonObject = RequestBuilder.getServicesData(currentSIMImsi, lov, tables);
		Log.e("HEMANT--------------->>>>>>>>>>>>>>", jsonObject.toString());
		Log.e("HEMANT--------------->>>>>>>>>>>>>>", tables.toString());
		UploadTask uploadTask = new UploadTask();
		uploadTask.execute(new JSONObject[]{jsonObject});
	}

	private class UploadTask extends AsyncTask<JSONObject, Void, Void>
	{
		String error = null;
		Boolean success = true;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001 Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected Void doInBackground(JSONObject... params) {
			// TODO Auto-generated method stub
			JSONObject dataToSend = params[0];
			
			String jsonString = dataToSend.toString();
			/*String s = dataToSend.toString();
			
			if(s != null)
			{
				s = s.replaceAll("\\\\", "");
			}
			try {
				dataToSend = new JSONObject(new String(s));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			try {
				DisplayLoggingInfo("Uploading data to server");
				String str = ServiceDelegate.postData(AppConstants.URLS.BASE_URL, dataToSend);
				
				//str = "{\"tables\":{\"service\":[]},\"lov\":{\"location\":[\"L 1\"],\"expense_category\":[],\"patient_type\":[\"OPD\",\"IPD\",\"SX\"],\"payment_mode\":[\"M2\",\"M1\"],\"diagnose_procedure\":[],\"referred_by\":[\"R2\",\"R1\"],\"start_time\":[],\"surgery_level\":[\"Level : 7\",\"Level : 6\",\"Level : 5\",\"Level : 4\",\"Level : 3\",\"Level : 2\",\"Level : 1\"],\"team_member\":[],\"ward\":[]}}";
				UploadDataResponseDTO responseDTO = ResponseParser.getUploadDataResponse(str);

				//SA S0002
				try
				{
					JSONObject jsonObject = new JSONObject(new String(str));
					if(jsonObject.has("success"))
					{
						success = jsonObject.getBoolean("success");
					}
					if(jsonObject.has("error"))
					{
						error = jsonObject.getString("error");
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}				//EA S0002
				
				try//SA M0001
				{
					JSONObject jsonObject = new JSONObject(new String(str));
					if(jsonObject.has("app_sponser_image"))
					{
						String image = jsonObject.getString("app_sponser_image");
						SmartConsultant.getApplication();
						SmartConsultant.getPreferences().setSponsorImage(image);
						Log.i("Sponsor-Image", image);
					}
				}
				catch(Exception e)
				{
					
				}//EA M0002

				
				Log.i("Upload - Response", str);
				if(responseDTO != null && success)//EDITED S0002
				{
					// update services
					
					String service = responseDTO.getServive();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Patient_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI, contentValues, null,null);
							//Log.e("ROWS UPDATED : services : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									try
									{
										int col = getContentResolver().update(DBConstant.Patient_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Columns.COLUMN_ID +"=?", new String[]{s});
										Log.e("ROWS UPDATED : services : ", col +"");
									}
									catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}
							}
							
						}
					}	
					
					// expense
					service = responseDTO.getExpense();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Expeses_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, DBConstant.Expeses_Columns.COLUMN_ID +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, null,null);
							//Log.e("ROWS UPDATED : expense : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									try {
										int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, DBConstant.Expeses_Columns.COLUMN_ID +"=?", new String[]{s});
										Log.e("ROWS UPDATED : expense : ", col +"");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						}
					}	
					
					//SA 1000B-3
					// payment
					service = responseDTO.getPayment();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Payment_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, DBConstant.Expeses_Columns.COLUMN_ID +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, null,null);
							//Log.e("ROWS UPDATED : expense : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									try {
										int col = getContentResolver().update(DBConstant.Payment_Columns.CONTENT_URI, contentValues, DBConstant.Payment_Columns.COLUMN_ID +"=?", new String[]{s});
										Log.e("ROWS UPDATED : payment : ", col +"");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						}
					}
					//EA 1000B-3
					
					// service_audio
					service = responseDTO.getService_audio();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Recoding_Columns.COLUMN_SYNCKEDSTATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Recoding_Columns.CONTENT_URI, contentValues, DBConstant.Recoding_Columns.COLUMN_ID +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Recoding_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : service_audio : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									try {
										int col = getContentResolver().update(DBConstant.Recoding_Columns.CONTENT_URI, contentValues, DBConstant.Recoding_Columns.COLUMN_ID +"=?", new String[]{s});
										Log.e("ROWS UPDATED : service_audio : ", col +"");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}
					}	
					
					// location
					service = responseDTO.getLocation();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Location_Columns.CONTENT_URI, contentValues, DBConstant.Location_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Location_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : location : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strLocation.contains(new String(s)))
									{
										try 
										{
											int col = getContentResolver().update(DBConstant.Location_Columns.CONTENT_URI, contentValues, DBConstant.Location_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : location : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Location_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// expense_category
					service = responseDTO.getExpense_category();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Bank_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col =  getContentResolver().update(DBConstant.Bank_Columns.CONTENT_URI, contentValues, DBConstant.Bank_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col =  getContentResolver().update(DBConstant.Bank_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : expense_category : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strBank.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.Bank_Columns.CONTENT_URI, contentValues, DBConstant.Bank_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : expense_category : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Bank_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// patient_type
					service = responseDTO.getPatient_type();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Types_Columns.COLUMN_SYNC_STATUS, String.valueOf(1));
							/*String[] data =  service.split(",");
							int col = getContentResolver().update(DBConstant.Types_Columns.CONTENT_URI, contentValues, null, null);
							//col = getContentResolver().update(DBConstant.Types_Columns.CONTENT_URI, contentValues, DBConstant.Types_Columns.COLUMN_NAME +" =?", new String[]{"IPD"});*/
							
							String[] data =  service.split(",");
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strType.contains(new String(s)))
									{
										try 
										{
											int col = getContentResolver().update(DBConstant.Types_Columns.CONTENT_URI, contentValues, DBConstant.Types_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : patient_type : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Types_Columns.CONTENT_URI, s);
									}
								}
							}
							//Log.e("ROWS UPDATED : patient_type : ", col +"");
						}
					}	
					
					// payment_mode
					service = responseDTO.getPayment_mode();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.ModeOfPayment_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.ModeOfPayment_Columns.CONTENT_URI, contentValues, DBConstant.ModeOfPayment_Columns.COLUMN_NAME+" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.ModeOfPayment_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : payment_mode : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strModeOfPayment.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.ModeOfPayment_Columns.CONTENT_URI, contentValues, DBConstant.ModeOfPayment_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : payment_mode : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.ModeOfPayment_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// procedure
					service = responseDTO.getProcedure();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Procedure_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Procedure_Columns.CONTENT_URI, contentValues, DBConstant.Procedure_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Procedure_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : procedure : ", col +"");
							
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strProcedure.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.Procedure_Columns.CONTENT_URI, contentValues, DBConstant.Procedure_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : procedure : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Procedure_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// referred_by
					service = responseDTO.getReferred_by();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Ref_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Ref_Columns.CONTENT_URI, contentValues, DBConstant.Ref_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Ref_Columns.CONTENT_URI, contentValues,  null, null);
							//Log.e("ROWS UPDATED : referred_by : ", col +"");
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strRef.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.Ref_Columns.CONTENT_URI, contentValues, DBConstant.Ref_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : referred_by : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Ref_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// start_time
					service = responseDTO.getStart_time();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.StartTime_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.StartTime_Columns.CONTENT_URI, contentValues, DBConstant.StartTime_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.StartTime_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : start_time : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strStartTime.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.StartTime_Columns.CONTENT_URI, contentValues, DBConstant.StartTime_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : StartTime_Columns : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.StartTime_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// surgery_level
					service = responseDTO.getSurgery_level();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Level_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Level_Columns.CONTENT_URI, contentValues, DBConstant.Level_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Level_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : surgery_level : ", col +"");
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strLevel.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.Level_Columns.CONTENT_URI, contentValues, DBConstant.Level_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : Level_Columns : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Level_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}	
					
					// team_member
					service = responseDTO.getTeam_member();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.TMEMBER_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.TMEMBER_Columns.CONTENT_URI, contentValues, DBConstant.TMEMBER_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.TMEMBER_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : team_member : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strTeamMember.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.TMEMBER_Columns.CONTENT_URI, contentValues, DBConstant.TMEMBER_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : TMEMBER_Columns : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.TMEMBER_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}
					
					// ward
					service = responseDTO.getWard();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Ward_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.TMEMBER_Columns.CONTENT_URI, contentValues, DBConstant.TMEMBER_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.TMEMBER_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : team_member : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									if(strWard.contains(new String(s)))
									{
										try {
											int col = getContentResolver().update(DBConstant.Ward_Columns.CONTENT_URI, contentValues, DBConstant.Ward_Columns.COLUMN_NAME +"=?", new String[]{s});
											Log.e("ROWS UPDATED : Ward_Columns : ", col +"");
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										addContent(DBConstant.Ward_Columns.CONTENT_URI, s);
									}
								}
							}
						}
					}

//					SA P3A05
					// service_contact
					service = responseDTO.getService_contact();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Patient_Contact_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, DBConstant.Expeses_Columns.COLUMN_ID +" LIKE ?",data);
							//int col = getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI, contentValues, null,null);
							//Log.e("ROWS UPDATED : expense : ", col +"");
							
							if(data.length > 0)
							{
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
									try {
										int col = getContentResolver().update(DBConstant.Patient_Contact_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Contact_Columns.COLUMN_PATIENT_ID +"=?", new String[]{s});
										Log.e("ROWS UPDATED : service_contact : ", col +"");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							
						}
					}	
//					EA P3A05
					
					
//					SA 1000G
					// deposited_bank_category
					service = responseDTO.getDepositedbank();
					if(service!= null && !service.equals("[]") && !service.equals(""))
					{
						service = service.substring(1, service.length() - 1);
						{
							ContentValues contentValues = new ContentValues();
							contentValues.put(DBConstant.Deposited_Bank_Columns.COLUMN_SYNC_STATUS, 1);
							String[] data =  service.split(",");
							//int col =  getContentResolver().update(DBConstant.Bank_Columns.CONTENT_URI, contentValues, DBConstant.Bank_Columns.COLUMN_NAME +" LIKE ?",data);
							//int col =  getContentResolver().update(DBConstant.Bank_Columns.CONTENT_URI, contentValues, null, null);
							//Log.e("ROWS UPDATED : expense_category : ", col +"");
							
							if(data.length > 0)
							{
								getContentResolver().delete(DBConstant.Deposited_Bank_Columns.CONTENT_URI, null, null);
								
								for(int i = 0; i < data.length; i++)
								{
									String s = data[i];
									if(data[i].startsWith("\"") && data[i].endsWith("\""))
									{
										s = data[i].substring(1, data[i].length() - 1);
									}
//									if(strDepositedBank.contains(new String(s)))
//									{
//										try {
//											int col = getContentResolver().update(DBConstant.Deposited_Bank_Columns.CONTENT_URI, contentValues, DBConstant.Deposited_Bank_Columns.COLUMN_NAME +"=?", new String[]{s});
//											Log.e("ROWS UPDATED : deposited bank : ", col +"");
//										} catch (Exception e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//									}
//									else
//									{
										addContent(DBConstant.Deposited_Bank_Columns.CONTENT_URI, s);
//									}
								}
							}
						}
					}	
					// EA 1000G
					
				}
				else//SA S0002
				{
					SmartConsultant.getPreferences().getUserLoginDTO().setPassHash(null);
					SmartConsultant.getPreferences().getUserLoginDTO().setSign_id(null);
					SmartConsultant.getPreferences().getUserLoginDTO().setUserName(null);
					Intent i = new Intent(SmartConsultant.getApplication().getApplicationContext(),LoginScreen.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.putExtra("error", error);
					startActivity(i);
				}//EA S0002
				Log.e("UploadTask", str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			/*if(result!=null && result.isStatus())
			{
			}
			else
			{
			}*/
//			SA B0S01
//			DisplayLoggingInfo("Data submitted successfully.");
//			uploadMediaFiles();	
//			mNotifyManager.cancelAll();//ADDED Z0001 Z0003
			//SA Z0001 Z0003
			if(syncCompleteAtData())
			{
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			}
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002 Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("Data submitted successfully.");
				uploadMediaFiles();	
			}
//			EA B0S01
		}
	}
	
	public void addContent(Uri uri, String str)
	{
		ContentValues values = new ContentValues();
		values.put(DBConstant.Location_Columns.COLUMN_NAME, str);
		values.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 1);
		getContentResolver().insert(uri, values);
	}
	public void uploadMediaFiles()
	{
		boolean stopService = false;
		if(recordingDTOs != null && recordingDTOs.size() > 0)
		{
			UploadMediaTask uploadMediaTask = new UploadMediaTask();
			uploadMediaTask.execute();
			stopService=true;
		}
		if(expenseDTOs != null && expenseDTOs.size() > 0)
		{
			uploadImages();
			stopService=true;
		}
		//SA 1000A
		if(sxPatient!=null && sxPatient.size() > 0)
		{
			uploadSXImages();
			stopService=true;
		}
		if(ipdPatient!=null && ipdPatient.size() > 0)
		{
			uploadIPDImages();
			stopService=true;
		}
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			uploadOPDImages();
			stopService=true;
		}
		//EA 1000A
		if(stopService)
		{
			stopService();
		}
	}
	
//	SA Z0001
	public boolean syncCompleteAtData()
	{
		if(recordingDTOs != null && recordingDTOs.size() > 0)
		{
		return false;
		}
		if(expenseDTOs != null && expenseDTOs.size() > 0)
		{
			return false;
		}
		//SA 1000A
		if(sxPatient!=null && sxPatient.size() > 0)
		{
			return false;
		}
		if(ipdPatient!=null && ipdPatient.size() > 0)
		{
			return false;
		}
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			return false;
		}
		return true;
	}
	public boolean syncCompleteAtRecordings()
	{
		if(expenseDTOs != null && expenseDTOs.size() > 0)
		{
			return false;
		}
		if(sxPatient!=null && sxPatient.size() > 0)
		{
			return false;
		}
		if(ipdPatient!=null && ipdPatient.size() > 0)
		{
			return false;
		}
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			return false;
		}
		return true;
	}
	public boolean syncCompleteAtExpense()
	{
		if(sxPatient!=null && sxPatient.size() > 0)
		{
			return false;
		}
		if(ipdPatient!=null && ipdPatient.size() > 0)
		{
			return false;
		}
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			return false;
		}
		return true;
	}
	public boolean syncCompleteAtSXImages()
	{
		if(ipdPatient!=null && ipdPatient.size() > 0)
		{
			return false;
		}
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			return false;
		}
		return true;
	}
	public boolean syncCompleteAtIPDImages()
	{
		if(opdPatient!=null && opdPatient.size() > 0)
		{
			return false;
		}
		return true;
	}
//	EA Z0001
	public void uploadImages()
	{
		if(expenseDTOs != null && expenseDTOs.size() > 0)
		{
			UploadImageTask uploadMediaTask = new UploadImageTask();
			uploadMediaTask.execute();
		}
		else
		{
			stopService();
		}
	}
	//SA 1000A
	public void uploadSXImages()
	{
		if(sxPatient != null && sxPatient.size() > 0)
		{
			UploadSXImageTask uploadMediaTask = new UploadSXImageTask();
			uploadMediaTask.execute();
		}
		else
		{
			stopService();
		}
	}
	public void uploadIPDImages()
	{
		if(ipdPatient != null && ipdPatient.size() > 0)
		{
			UploadIPDImageTask uploadMediaTask = new UploadIPDImageTask();
			uploadMediaTask.execute();
		}
		else
		{
			stopService();
		}
	}
	public void uploadOPDImages()
	{
		if(opdPatient != null && opdPatient.size() > 0)
		{
			UploadOPDImageTask uploadMediaTask = new UploadOPDImageTask();
			uploadMediaTask.execute();
		}
		else
		{
			stopService();
		}
	}
	//EA 1000A
	public void stopService()
	{
		DisplayLoggingInfo("Upload finish.");
		try
		{
			removeStickyBroadcast(intent);
// SA Z0001 Z0003
//			mNotifyManager.cancelAll();
//			mBuilder.setContentTitle("SmartConsultant")
//		    .setContentText("Sync complete")
//		    .setSmallIcon(R.drawable.logo);
//			mBuilder.setAutoCancel(true);
//		    mNotifyManager.notify(0, mBuilder.build());
//		    EA Z0001
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		stopSelf();
	}
	private class UploadMediaTask extends AsyncTask<Void, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001  Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			for(int i = 0; i < recordingDTOs.size(); i++)
			{
				try
				{
					String typ = "song";
					String path = recordingDTOs.get(i).getUrl();
					File f = new File(path);
					n  = path.substring(path.lastIndexOf("/") + 1);
					DisplayLoggingInfo("Uploading " + n + "...");
					String s = ServiceDelegate.postRecordedFile1(typ, f, RequestBuilder.getUploadData(), AppConstants.URLS.MEDIA_BASE_URL);
					Log.i("MediaUpload", RequestBuilder.getUploadData().toString());
					MediaUploadResponse uploadResponse = ResponseParser.getMediaUploadResponse(s);
					Log.e("UploadMediaTask : " + path + " : ", s);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			SA B0S01
//			DisplayLoggingInfo("All media files uploaded successfully.");
//			uploadImages();
//			mNotifyManager.cancelAll();//ADDED Z0001 Z0003
			//SA Z0001 Z0003
			if(syncCompleteAtRecordings())
			{
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			}
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002  Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("All media files uploaded successfully.");
				uploadImages();	
			}
//			EA B0S01
			
		}
	}
	
	private class UploadImageTask extends AsyncTask<Void, Void, String>
	{
//		int incr;//ADDED Z0001
//		int chunks;//ADDED Z0001
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001 Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
//			incr=expenseDTOs.size();//ADDED Z0001
//			chunks = 100/incr;//ADDED Z0001
			for(int i = 0; i < expenseDTOs.size(); i++)
			{
				ArrayList<ExpenseDetailsDTO> details = expenseDTOs.get(i).getPaths();
				
				//SA Z0001
//				if (i == expenseDTOs.size() - 1)
//					incr=100;
				
//				mBuilder.setProgress(100, incr, false);
                // Displays the progress bar for the first time.
//                mNotifyManager.notify(0, mBuilder.build());
                //EA Z0001
				
				
				if(details != null && details.size() > 0)
				{
					for(int j = 0; j < details.size(); j++)
					{
						try
						{
							String typ = "image";
							String path = details.get(j).getUrl();
							File f = new File(path);
							n  = path.substring(path.lastIndexOf("/") + 1);
							DisplayLoggingInfo("Uploading " + n + "...");
							String s = ServiceDelegate.postRecordedFile1(typ, f, RequestBuilder.getUploadData(), AppConstants.URLS.MEDIA_BASE_URL);
							MediaUploadResponse uploadResponse = ResponseParser.getMediaUploadResponse(s);
							Log.i("MediaUpload", RequestBuilder.getUploadData().toString());		
							
							//SA A0001
							if(uploadResponse.isSuccess())
							{
								String filename = uploadResponse.getFile_name();
								
								if(!TextUtils.isEmpty(filename))
								{
									try
									{
										ContentValues contentValues = new ContentValues();
										contentValues.put(DBConstant.Expeses_Details_Columns.COLUMN_SYNC_STATUS, 1);
										int col = getContentResolver().update(DBConstant.Expeses_Details_Columns.CONTENT_URI, contentValues, DBConstant.Expeses_Details_Columns.COLUMN_NAME +" LIKE ?",new String[]{filename});
										Log.e("ROWS UPDATED : expense image : ", col +"");
									}
									catch(Exception e)
									{
										Log.e("EXPENSEIMAGE", e.toString());
									}
								}
								
							}
							//EA A0001
							Log.e("UploadMediaTask : " + path + " : ", s);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}
				
//	            incr=incr+chunks;//ADDED Z0001
			}
			//SA Z0001
//			mBuilder.setContentText("Sync complete");
            // Removes the progress bar
//                    .setProgress(0,0,false);
//            mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
            //EA Z0001
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			mNotifyManager.cancelAll(); Z0003
			
//			SA B0S01
//			DisplayLoggingInfo("All expenses images uploaded successfully.");
//			stopService();
			//SA Z0001 Z0003
			if(syncCompleteAtExpense())
			{
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			}
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002  Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("All expenses images uploaded successfully.");
				stopService();	
			}
//			EA B0S01
		}
	}
	//SA 1000A
	private class UploadSXImageTask extends AsyncTask<Void, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001 Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			for(int i = 0; i < sxPatient.size(); i++)
			{
				ArrayList<PatientDetailsDTO> details = sxPatient.get(i).getPaths();
				if(details != null && details.size() > 0)
				{
					for(int j = 0; j < details.size(); j++)
					{
						try
						{
							String typ = "service";
							String path = details.get(j).getUrl();
							File f = new File(path);
							n  = path.substring(path.lastIndexOf("/") + 1);
							DisplayLoggingInfo("Uploading " + n + "...");
							String s = ServiceDelegate.postRecordedFile1(typ, f, RequestBuilder.getUploadData(), AppConstants.URLS.MEDIA_BASE_URL);
							Log.i("MediaUpload", RequestBuilder.getUploadData().toString());
							MediaUploadResponse uploadResponse = ResponseParser.getMediaUploadResponse(s);
							
							//SA A0001
							if(uploadResponse.isSuccess())
							{
								String filename = uploadResponse.getFile_name();
								
								if(!TextUtils.isEmpty(filename))
								{
									try
									{
										ContentValues contentValues = new ContentValues();
										contentValues.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 1);
										int col = getContentResolver().update(DBConstant.Patient_Details_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Details_Columns.COLUMN_NAME +" LIKE ?",new String[]{filename});
										Log.e("ROWS UPDATED : service image : ", col +"");
									}
									catch(Exception e)
									{
										Log.e("SERVICEIMAGE", e.toString());
									}
								}
								
							}
							//EA A0001
							
							Log.e("UploadSXMediaTask : " + path + " : ", s);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}				
			}	
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			SA B0S01
//			DisplayLoggingInfo("All SX images uploaded successfully.");
//			stopService();
//			mNotifyManager.cancelAll();//Z0001 Z0003
			//SA Z0001 Z0003
			if(syncCompleteAtSXImages())
			{
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			}
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002 Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("All SX images uploaded successfully.");
				stopService();	
			}
//			EA B0S01
		}
	}
	private class UploadIPDImageTask extends AsyncTask<Void, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001  Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			for(int i = 0; i < ipdPatient.size(); i++)
			{
				ArrayList<PatientDetailsDTO> details = ipdPatient.get(i).getPaths();
				if(details != null && details.size() > 0)
				{
					for(int j = 0; j < details.size(); j++)
					{
						try
						{
							String typ = "service";
							String path = details.get(j).getUrl();
							File f = new File(path);
							n  = path.substring(path.lastIndexOf("/") + 1);
							DisplayLoggingInfo("Uploading " + n + "...");
							String s = ServiceDelegate.postRecordedFile1(typ, f, RequestBuilder.getUploadData(), AppConstants.URLS.MEDIA_BASE_URL);
							Log.i("MediaUpload", RequestBuilder.getUploadData().toString());
							MediaUploadResponse uploadResponse = ResponseParser.getMediaUploadResponse(s);
							
							//SA A0001
							if(uploadResponse.isSuccess())
							{
								String filename = uploadResponse.getFile_name();
								
								if(!TextUtils.isEmpty(filename))
								{
									try
									{
										ContentValues contentValues = new ContentValues();
										contentValues.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 1);
										int col = getContentResolver().update(DBConstant.Patient_Details_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Details_Columns.COLUMN_NAME +" LIKE ?",new String[]{filename});
										Log.e("ROWS UPDATED : service image : ", col +"");
									}
									catch(Exception e)
									{
										Log.e("SERVICEIMAGE", e.toString());
									}
								}
								
							}
							//EA A0001
							
							
							Log.e("UploadIPDMediaTask : " + path + " : ", s);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}				
			}	
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			SA B0S01
//			DisplayLoggingInfo("All IPD images uploaded successfully.");
//			stopService();
//			mNotifyManager.cancelAll();//ADDED Z0001 Z0003
			//SA Z0001 Z0003
			if(syncCompleteAtIPDImages())
			{
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			}
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002 Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("All IPD images uploaded successfully.");
				stopService();	
			}
//			EA B0S01
		}
	}
	private class UploadOPDImageTask extends AsyncTask<Void, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//SA Z0001 Z0003
//			mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync in progress")
//			    .setSmallIcon(R.drawable.logo);
//			mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			for(int i = 0; i < opdPatient.size(); i++)
			{
				ArrayList<PatientDetailsDTO> details = opdPatient.get(i).getPaths();
				if(details != null && details.size() > 0)
				{
					for(int j = 0; j < details.size(); j++)
					{
						try
						{
							String typ = "service";
							String path = details.get(j).getUrl();
							File f = new File(path);
							n  = path.substring(path.lastIndexOf("/") + 1);
							DisplayLoggingInfo("Uploading " + n + "...");
							String s = ServiceDelegate.postRecordedFile1(typ, f, RequestBuilder.getUploadData(), AppConstants.URLS.MEDIA_BASE_URL);
							Log.i("MediaUpload", RequestBuilder.getUploadData().toString());
							MediaUploadResponse uploadResponse = ResponseParser.getMediaUploadResponse(s);
							
							//SA A0001
							if(uploadResponse.isSuccess())
							{
								String filename = uploadResponse.getFile_name();
								
								if(!TextUtils.isEmpty(filename))
								{
									try
									{
										ContentValues contentValues = new ContentValues();
										contentValues.put(DBConstant.Patient_Details_Columns.COLUMN_SYNC_STATUS, 1);
										int col = getContentResolver().update(DBConstant.Patient_Details_Columns.CONTENT_URI, contentValues, DBConstant.Patient_Details_Columns.COLUMN_NAME +" LIKE ?",new String[]{filename});
										Log.e("ROWS UPDATED : service image : ", col +"");
									}
									catch(Exception e)
									{
										Log.e("SERVICEIMAGE", e.toString());
									}
								}
								
							}
							//EA A0001
							
							Log.e("UploadOPDMediaTask : " + path + " : ", s);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
				}				
			}	
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			DisplayLoggingInfo("All OPD images uploaded successfully.");
//			stopService();
//			SA B0S01
//			DisplayLoggingInfo("All IPD images uploaded successfully.");
//			stopService();
//			mNotifyManager.cancelAll();//ADDED Z0001 Z0003
			//SA Z0001 Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Sync complete")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
			//EA Z0001
			if(!isNetworkAvailable())
			{
				DisplayLoggingInfo("Connection lost. Please try again");
//				SA Z0002 Z0003
//				mBuilder.setContentTitle("SmartConsultant")
//			    .setContentText("Poor connectivity or no connection")
//			    .setSmallIcon(R.drawable.logo);
//				mBuilder.setAutoCancel(true);
//			    mNotifyManager.notify(0, mBuilder.build());
//			    EA Z0002
			}
			else
			{
				DisplayLoggingInfo("All OPD images uploaded successfully.");
				stopService();	
			}
//			EA B0S01
		}
	}
	//EA 1000A
}
