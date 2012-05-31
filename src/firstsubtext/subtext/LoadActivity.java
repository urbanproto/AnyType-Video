package firstsubtext.subtext;


/**
 * This class starts all of the global variables.  It launches the application and moves on
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import data.Shape;
import firstsubtext.subtext.R.id;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.view.ViewGroup.LayoutParams;

//each activity is a state. 
//this is the photo capture activity. It takes a picture 
public class LoadActivity extends Activity {

	protected static final String TAG = null;
	private static Globals environment;
	private static int stage = 0; // keeps a record of which shape we're
									// capturing

	public void loadShapes() {
		int ndx = 0;
		int count = 0;
		InputStream is;
		ArrayList x = new ArrayList();
		ArrayList y = new ArrayList();
		Iterator it;
		String[] data = new String[2];

		for (int id = 0; id < 5; id++) {
			x.clear();
			y.clear();
			
			try {

				String str = "";
				Log.d("Load Shape", "Switch " + id);

				if(id == 0) is = this.getResources().openRawResource(R.raw.sa);
				else if(id == 1) is = this.getResources().openRawResource(R.raw.sb);
				else if(id == 2) is = this.getResources().openRawResource(R.raw.sc);
				else if(id == 3)is = this.getResources().openRawResource(R.raw.sd);
				else is = this.getResources().openRawResource(R.raw.se);
				

				Log.d("Load Shape", "Shape " + id + "Loaded");
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				if (is != null) {
					while ((str = reader.readLine()) != null) {
						Log.d("Load Shape", "Reading: " + str);
						data = str.split(",");
						x.add(Integer.valueOf(data[0].trim()));
						y.add(Integer.valueOf(data[1].trim()) * -1);
					}
				}
				is.close();

			} catch (IOException e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}

			// add the points to the arrays
			it = x.iterator();
			int[] x_points = new int[x.size()];
			ndx = 0;
			while (it.hasNext()) {
				x_points[ndx++] = (Integer) it.next();
			}

			it = y.iterator();
			int[] y_points = new int[y.size()];
			ndx = 0;
			while (it.hasNext()) {
				y_points[ndx++] = (Integer) it.next();
			}

			environment.getShape(id).setPoints(x_points, y_points);

		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		environment = new Globals(
				new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));

		loadShapes();

		setContentView(R.layout.main);

		
		Log.d("Load Activity", "Loaded");
		Intent intent = new Intent(this, CaptureActivity.class);
		startActivity(intent);


	}

	@Override
	public void onRestart() {

	}

	@Override
	protected void onPause() {
		super.onPause();
	}



}
