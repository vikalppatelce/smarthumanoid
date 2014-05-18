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
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.smarthumanoid.com.R;
import com.netdoers.com.dto.DBConstant;

public class CustomSqlCursorAdapter extends SimpleCursorAdapter implements
		OnClickListener {

	private Context context;

	private Cursor currentCursor;
	
	private Uri uri;
	public CustomSqlCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, Uri uri) {
		super(context, layout, c, from, to);
		this.currentCursor = c;
		this.context = context;
		this.uri = uri;

	}

	@Override
	public View getView(int pos, View inView, ViewGroup parent) {
		View v = inView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.lov_item, null);
		}

		this.currentCursor.moveToPosition(pos);

		TextView txtTitle = (TextView) v.findViewById(R.id.txtLovValue);
		txtTitle.setText(this.currentCursor.getString(currentCursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_NAME)));
		
		Button removeLOV = (Button) v.findViewById(R.id.btnRemoveLOV);
		removeLOV.setOnClickListener(this);
		removeLOV.setTag(this.currentCursor.getString(currentCursor.getColumnIndex(DBConstant.Location_Columns.COLUMN_ID)));
		return (v);
	}

	public void ClearSelections() {
		this.currentCursor.requery();

	}

	@Override
	public void onClick(View v) {
		Button b = (Button) v;
		String _id = (String) b.getTag();
		this.context.getContentResolver().delete(this.uri, "_id=?", new String[] { _id });
	}
}
