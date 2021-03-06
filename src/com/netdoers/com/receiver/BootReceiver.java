/*HISTORY
* CATEGORY			 :- BROADCAST RECEIVER
* DEVELOPER			 :- VIKALP PATEL
* AIM 				 :- WAKE UP SERVICES ON BOOT
* DESCRIPTION 		 :- SETTING ALARM SERVICE ON BOOT UP - AS BOOTING FLUSHES ALARM MANAGER - AUTOSYNC
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX		 DEVELOPER		 DATE		 FUNCTION			DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* P3AG1     VIKALP PATEL  09/04/2014     AGGRESSIVE         APPLICATION PESTERING TO LOGIN
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.receiver;

import com.netdoers.com.SmartConsultant;
import com.netdoers.com.service.ServiceAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent)
    {   
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
//        	SA P3AG1
        	if(SmartConsultant.getPreferences().getUserLoginDTO().getSign_id() !=null)
        	{
        		ServiceAlarm serviceAlarm = new ServiceAlarm();
//        		ServiceAlarm.startServiceAlarm(SmartConsultant.getPreferences().getSyncFrequency());
        		serviceAlarm.startServiceAlarm(SmartConsultant.getPreferences().getSyncFrequency());
        	}
//        	EA P3AG1
        }
    }
}
