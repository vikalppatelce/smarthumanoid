/* HISTORY
* CATEGORY			 :- CAMERA
* DEVELOPER			 :- VIKALP PATEL
* AIM 				 :- CAPTURE IMAGE
* DESCRIPTION 		 :- TAKING PICTURE WITH THE ACTIVITY
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX		 DEVELOPER 		DATE 			FUNCTION 		DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* 10001 	VIKALP PATEL 07/01/2014 						ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
* 10002 	VIKALP PATEL 14/04/2014 		 UIX				FANCY SHUTTER BUTTON
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.netdoers.com.camera.Preview;
import com.smarthumanoid.com.R;

public class CameraActivity extends Activity {
	private static final String TAG = "CameraDemo";
	Camera camera;
	Uri pathUri;
	Preview preview;
	ImageView buttonClick;//EDITED 10002
	final int PICTAKEN = 1001;
	SharedPreferences pref;//ADDED 10001

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			setTheme(R.style.FullScreen);
		}*/
		setContentView(R.layout.camera);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Bundle uriStr = getIntent().getExtras();
		pathUri = Uri.parse(uriStr.getString("FILE_URI"));
		preview = new Preview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		buttonClick = (ImageView) findViewById(R.id.buttonClick);//EDITED 10002
		buttonClick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				preview.camera.takePicture(shutterCallback, rawCallback,
						jpegCallback);
				final Intent picIntent = new Intent(CameraActivity.this,
						PicSaveActivity.class);
				picIntent.putExtra("URI", pathUri.toString());
				galleryAddPic();
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
						startActivityForResult(picIntent, PICTAKEN);
						Log.d("CameraActivity", "PicSaveActivity");
					};
				};
				Message msg = handler.obtainMessage();
				handler.sendMessageDelayed(msg, 1000);
			}
		});

		Log.d(TAG, "onCreate'd");
	}

	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first

	    // Release the Camera because we don't need it when paused
	    // and other activities might need to use it.
	    if (camera != null) {
	        camera.release();
	        Log.d("CameraActivity", "camera released!");
	        camera = null;
	    }
	}
	@Override
	protected void onStop() {
	    super.onStop();
	    if (camera != null) {
	        camera.release();
	        Log.d("CameraActivity", "camera released!");
	        camera = null;
	    }
	}
	@Override
	protected void onDestroy() {
	    super.onStop();
	    if (camera != null) {
	        camera.release();
	        Log.d("CameraActivity", "camera released!");
	        camera = null;
	    }
	}
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(pathUri.getPath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {

			setResult(RESULT_OK);
			finish();
		}
		if (resultCode == RESULT_CANCELED) {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		@Override
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	/** Handles data for raw picture */
	PictureCallback rawCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	/** Handles data for jpeg picture */
	PictureCallback jpegCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			long time = 0;
			try {
				// write to local sandbox file system
				// outStream =
				// CameraDemo.this.openFileOutput(String.format("%d.jpg",
				// System.currentTimeMillis()), 0);
				// Or write to sdcard
				time = System.currentTimeMillis();
				// outStream = new
				// FileOutputStream(String.format("/sdcard/%d.jpg",time));
				outStream = new FileOutputStream(pathUri.getPath());
				outStream.write(data);
				outStream.close();
				Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
				Log.d(TAG, "onPictureTaken - saved: " + pathUri.getPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			Log.d(TAG, "onPictureTaken - jpeg");
		}
	};

	public void onCancel(View v) {
		if (camera != null) {
			camera.release();
			camera = null;
		}
		finish();
		Toast.makeText(getApplicationContext(), "Camera Closed!", Toast.LENGTH_SHORT).show();
	}
}