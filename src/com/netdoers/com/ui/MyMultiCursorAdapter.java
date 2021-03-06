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

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class MyMultiCursorAdapter extends SimpleCursorAdapter{

	public MyMultiCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
	}  

	@Override 
	public View getView(int position, View convertView, ViewGroup parent) {  

		//get reference to the row
		View view = super.getView(position, convertView, parent); 
		//check for odd or even to set alternate colors to the row background
		if(position % 2 == 0){  
			view.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else {
			view.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		return view;  
	}  
}
