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
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.ui;

import com.netdoers.com.SmartConsultant;
import com.smarthumanoid.com.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class PrefsSecondaryActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	SharedPreferences pref;//Added 10005
	@Override
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);
		String calledFrom = getIntent().getStringExtra("caller");
		
		if(calledFrom.equals("payment"))
		{
		PreferenceCategory toolTipCategory = new PreferenceCategory(this);
		toolTipCategory.setTitle("Help");
		screen.addPreference(toolTipCategory);
		CheckBoxPreference toolTipCheckBoxPref = new CheckBoxPreference(this);
		toolTipCheckBoxPref.setTitle("Payment ToolTip");
		if(pref.getBoolean("prefIsToolTip", false))
		{
			toolTipCheckBoxPref.setChecked(true);
		}
		else
		{
			toolTipCheckBoxPref.setChecked(false);	
		}
		toolTipCheckBoxPref.setKey("prefIsPaymentToolTip");
		toolTipCheckBoxPref.setSummary(R.string.tooltip_summary);
		
		toolTipCategory.addPreference(toolTipCheckBoxPref);
		}
		
		if(calledFrom.equals("expense"))
		{
		PreferenceCategory toolTipCategory = new PreferenceCategory(this);
		toolTipCategory.setTitle("Help");
		screen.addPreference(toolTipCategory);
		CheckBoxPreference toolTipCheckBoxPref = new CheckBoxPreference(this);
		toolTipCheckBoxPref.setTitle("Expense ToolTip");
		toolTipCheckBoxPref.setKey("prefIsExpenseToolTip");
		toolTipCheckBoxPref.setSummary(R.string.tooltip_summary);
		if(pref.getBoolean("prefIsToolTip", false))
			toolTipCheckBoxPref.setChecked(true);	
		else
		toolTipCheckBoxPref.setChecked(false);
		
		toolTipCategory.addPreference(toolTipCheckBoxPref);
		}
		
		if(calledFrom.equals("ipd"))
		{
		PreferenceCategory toolTipCategory = new PreferenceCategory(this);
		toolTipCategory.setTitle("Help");
		screen.addPreference(toolTipCategory);
		CheckBoxPreference toolTipCheckBoxPref = new CheckBoxPreference(this);
		toolTipCheckBoxPref.setTitle("IPD ToolTip");
		toolTipCheckBoxPref.setKey("prefIsIPDToolTip");
		toolTipCheckBoxPref.setSummary(R.string.tooltip_summary);
		if(pref.getBoolean("prefIsToolTip", false))
			toolTipCheckBoxPref.setChecked(true);	
		else
		toolTipCheckBoxPref.setChecked(false);
		
		toolTipCategory.addPreference(toolTipCheckBoxPref);
		
//		PreferenceCategory surgicalNotesCategory = new PreferenceCategory(this);
//		surgicalNotesCategory.setTitle("Surgical Notes");
//		screen.addPreference(surgicalNotesCategory);
//		CheckBoxPreference checkBoxPref = new CheckBoxPreference(this);
//		checkBoxPref.setTitle("Surgical Notes Pictures");
//		checkBoxPref.setKey("prefIsIPDSurgicalNotes");
//		checkBoxPref.setSummary(R.string.surgical_notes_summary);
//		checkBoxPref.setChecked(false);
//		surgicalNotesCategory.addPreference(checkBoxPref);
		}
		
		
		if(calledFrom.equals("opd"))
		{
		PreferenceCategory toolTipCategory = new PreferenceCategory(this);
		toolTipCategory.setTitle("Help");
		screen.addPreference(toolTipCategory);
		CheckBoxPreference toolTipCheckBoxPref = new CheckBoxPreference(this);
		toolTipCheckBoxPref.setTitle("OPD ToolTip");
		toolTipCheckBoxPref.setKey("prefIsOPDToolTip");
		toolTipCheckBoxPref.setSummary(R.string.tooltip_summary);
		if(pref.getBoolean("prefIsToolTip", false))
			toolTipCheckBoxPref.setChecked(true);	
		else
		toolTipCheckBoxPref.setChecked(false);
		
		toolTipCategory.addPreference(toolTipCheckBoxPref);
		
//		PreferenceCategory surgicalNotesCategory = new PreferenceCategory(this);
//		surgicalNotesCategory.setTitle("Surgical Notes");
//		screen.addPreference(surgicalNotesCategory);
//		CheckBoxPreference checkBoxPref = new CheckBoxPreference(this);
//		checkBoxPref.setTitle("Surgical Notes Pictures");
//		checkBoxPref.setKey("prefIsOPDSurgicalNotes");
//		checkBoxPref.setSummary(R.string.surgical_notes_summary);
//		checkBoxPref.setChecked(false);
//		surgicalNotesCategory.addPreference(checkBoxPref);
		}
		
		if(calledFrom.equals("sx"))
		{
		PreferenceCategory toolTipCategory = new PreferenceCategory(this);
		toolTipCategory.setTitle("Help");
		screen.addPreference(toolTipCategory);
		CheckBoxPreference toolTipCheckBoxPref = new CheckBoxPreference(this);
		toolTipCheckBoxPref.setTitle("SX ToolTip");
		toolTipCheckBoxPref.setKey("prefIsSXToolTip");
		toolTipCheckBoxPref.setSummary(R.string.tooltip_summary);
		if(pref.getBoolean("prefIsToolTip", false))
			toolTipCheckBoxPref.setChecked(true);	
		else
		toolTipCheckBoxPref.setChecked(false);
		
		toolTipCategory.addPreference(toolTipCheckBoxPref);
		
//		PreferenceCategory surgicalNotesCategory = new PreferenceCategory(this);
//		surgicalNotesCategory.setTitle("Surgical Notes");
//		screen.addPreference(surgicalNotesCategory);
//		CheckBoxPreference checkBoxPref = new CheckBoxPreference(this);
//		checkBoxPref.setTitle("Surgical Notes Pictures");
//		checkBoxPref.setKey("prefIsSXSurgicalNotes");
//		checkBoxPref.setSummary(R.string.surgical_notes_summary);
//		checkBoxPref.setChecked(true);
//		surgicalNotesCategory.addPreference(checkBoxPref);
		}
		setPreferenceScreen(screen);
	}
	 @Override
	protected void onResume(){
	        super.onResume();
	        // Set up a listener whenever a key changes
	        getPreferenceScreen().getSharedPreferences()
	            .registerOnSharedPreferenceChangeListener(this);
	    }
	@Override
	protected void onPause() {
	        super.onPause();
	        // Unregister the listener whenever a key changes
	        getPreferenceScreen().getSharedPreferences()
           .unregisterOnSharedPreferenceChangeListener(this);
	    }
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
	}
}
