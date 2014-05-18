/*HISTORY
* CATEGORY			 :- BROADCAST RECEIVER
* DEVELOPER			 :- VIKALP PATEL
* AIM				 :- NETWORK
* DESCRIPTION		 :- SYNC ON INTERNET CONNECTED
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX 		DEVELOPER 			DATE 			FUNCTION		DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* B0001         VIKALP PATEL     28/02/14           RECEIVER         START SERVICE ON WIFI/3G
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.netdoers.com.SmartConsultant;
import com.netdoers.com.service.UploadData;

public class NetworkReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo activeWifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo activeHighNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE_HIPRI);
		
		ComponentName comp = new ComponentName(context.getPackageName(),UploadData.class.getName());
		
		boolean isMobileConnected = activeNetInfo != null&& activeNetInfo.isConnectedOrConnecting();
		Log.i("NetworkReceiver", "Mobile connected " + isMobileConnected);
		if (isMobileConnected) {
//			context.startService((intent.setComponent(comp)));
		}
		

		boolean isHighSpeedConnected = activeHighNetInfo != null && activeHighNetInfo.isConnectedOrConnecting();
		Log.i("NetworkReceiver", "High Speed connected " + isHighSpeedConnected);
		if (isHighSpeedConnected) {
			if(SmartConsultant.getPreferences().getUserLoginDTO().getSign_id()!=null)
			{
			context.startService((intent.setComponent(comp)));
			}
		}
		
		
		boolean isWifiConnected = activeWifiInfo != null && activeWifiInfo.isConnectedOrConnecting();
		Log.i("NetworkReceiver", "Wifi connected " + isWifiConnected);
		if (isWifiConnected) {
			if(SmartConsultant.getPreferences().getUserLoginDTO().getSign_id()!=null)
			{
				context.startService((intent.setComponent(comp)));	
			}
		}
	}
}
