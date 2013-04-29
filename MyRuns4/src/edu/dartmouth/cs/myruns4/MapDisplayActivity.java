package edu.dartmouth.cs.myruns4;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import edu.dartmouth.cs.myruns4.TrackingService.TrackingBinder;

public class MapDisplayActivity extends Activity {

	/****************** member vars *******************/
	private TrackingBinder mTrackingBinder;
	
	private boolean mBound;
	
	private TrackingServiceReceiver receiver = new TrackingServiceReceiver();
	
	private GoogleMap mMap;

	public Context mContext;
	
	public TextView typeStats;
	public TextView avgspeedStats;
	public TextView curspeedStats;
	public TextView climbStats;
	public TextView caloriesStats;
	public TextView distanceStats;
	PolylineOptions rectOptions;
	Polyline polyline;

	public ArrayList<Location> mLocationList;
	private ArrayList<LatLng> mLatLngList;
	
	public ExerciseEntryHelper mEntryHelper;
	public ExerciseEntry mEntry;
	
	private int mTaskType;
	private int mInputType;
	private int mActivityType;
	
	public static final int MENU_ID_DELETE = 0;
	
    public boolean mFirstLoc;
    public Marker curMarker;
    
    private double mDistance;
    private double mAvgSpeed;
    private int mCalories;
    private double mStartTime;
    private double mClimb;
    private double mCurSpeed;
    
    public LatLng firstLatLng;

	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			mTrackingBinder = (TrackingBinder) binder;
			// bind the list with TrackingService obj's mLocationList, using the binder's
			// public method
			mLocationList = mTrackingBinder.getLocationList();
			mBound = true;
		}

		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}
	};

	
	/******************* methods ********************/
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
//		Toast.makeText(getApplicationContext(), "MapDisplayActivity: onCreate", Toast.LENGTH_SHORT).show();
		setContentView(R.layout.activity_map_display);
		
		// initialize member variables
		mBound = false;
		mLatLngList = new ArrayList<LatLng>();
		mContext = this;
		mFirstLoc = true;
		
		// mark: init views here
		typeStats = (TextView) findViewById(R.id.type_stats);
		avgspeedStats = (TextView) findViewById(R.id.avg_speed_stats);
		curspeedStats = (TextView) findViewById(R.id.cur_speed_stats);
		climbStats = (TextView) findViewById(R.id.climb_stats_stats);
		caloriesStats = (TextView) findViewById(R.id.calories_stats);
		distanceStats = (TextView) findViewById(R.id.distance_stats);
		
		mEntryHelper = new ExerciseEntryHelper();
		// Get extras from intent and set the mTaskType, InputType, Row Id and ActivityType
		Intent intent = getIntent();
		mTaskType = intent.getIntExtra(MainActivity.TASK_TYPE, -1);
		mInputType = intent.getIntExtra(MainActivity.INPUT_TYPE, -1);
		mActivityType = intent.getIntExtra(MainActivity.ACTIVITY_TYPE, -1);
		// mark: row id ?
		
		mDistance = 0;
		mCalories = 0;
		mAvgSpeed = 0;
		mStartTime = 0;
		mClimb = 0;
		
		// init mEntry
		mEntry = new ExerciseEntry();
		mEntry.setActivityType(mActivityType);
		mEntry.setInputType(mInputType);
		
		FragmentManager myFragmentManager = getFragmentManager();
		MapFragment mMapFragment 
			= (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		mMap = mMapFragment.getMap();
		
		switch (mTaskType) {

		case Globals.TASK_TYPE_NEW:		
//			Toast.makeText(getApplicationContext(), "case TASK_TYPE_NEW", Toast.LENGTH_SHORT).show();

			intent = new Intent(this, TrackingService.class);
			bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
			startService(intent);
			break;

		case Globals.TASK_TYPE_HISTORY:
			// ----------------------Skeleton--------------------------
//			Toast.makeText(getApplicationContext(), "case TASK_TYPE_HISTORY", Toast.LENGTH_SHORT).show();
			// No longer need "Save" and "Cancel" button in history mode
			Button saveButton = (Button) findViewById(R.id.button_map_save);
			saveButton.setVisibility(View.GONE);
			Button cancelButton = (Button) findViewById(R.id.button_map_cancel);
			cancelButton.setVisibility(View.GONE);

			// Read track from database
//			intent = getIntent();
//			Location[] locations = Utils.fromByteArrayToLocationArray(intent.getStringExtra(HistoryFragment.TRACK).getBytes());
//			mLocationList.clear();
//			for(int i = 0; i < locations.length; i++)
//				mLocationList.add(locations[i]);
				
			// ----------------------Skeleton--------------------------
			
			// Convert the mLocationList to mLatLngList
			// so that you can draw polylines using LatLng objects
	
			
			// ----------------------Skeleton--------------------------
			// Draw marker for the start point
		
			
			// ----------------------Skeleton--------------------------
			// Draw marker for the end point
			
			
			// ----------------------Skeleton--------------------------
			// Draw the GPS traces, set the width, color and use addAll to
			// write a Polyline that goes through all the LatLng points
		
			// ----------------------Skeleton--------------------------
			// Move map center to the 1st point in the route track.
			
			// ----------------------Skeleton--------------------------
			// Clear the mLatLngList
			mLatLngList.clear();
			// ----------------------Skeleton--------------------------
			// Get previous stats from the ExerciseEntry
			

			// ----------------------Skeleton--------------------------
			// Draw the stats on the map
			String type = Globals.TYPE_STATS;
			String avgSpeed = Globals.AVG_SPEED_STATS;
			String curSpeed = Globals.CUR_SPEED_STATS;
			String climb = Globals.CLIMB_STATS;
			String calories = Globals.CALORIES_STATS;
			String distance = Globals.DISTANCE_STATS;
			typeStats.setText(type);
			avgspeedStats.setText(avgSpeed);
			curspeedStats.setText(curSpeed);
			climbStats.setText(climb);
			caloriesStats.setText(calories);
			distanceStats.setText(distance);
			

			
			break;
		default:
			Toast.makeText(getApplicationContext(), "case default", Toast.LENGTH_SHORT).show();

			finish(); // Should never happen.
			return;
		}
	}
	
	@Override
	public void onDestroy(){	
		if (mBound){
			unbindService(mConnection);
			stopService(new Intent(this, TrackingService.class));
		}
		super.onDestroy();
	}
	
	@Override
	public void onPause(){
		if (mTaskType == Globals.TASK_TYPE_NEW)
			unregisterReceiver(receiver);
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if (mTaskType == Globals.TASK_TYPE_NEW){
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(TrackingService.ACTION);
			registerReceiver(receiver, intentFilter);
		}
	}
	
	/******************* button listeners ******************/
	public void onSaveClicked(View v) {

		// disable the button
		Button button = (Button) findViewById(R.id.button_map_save);
		button.setClickable(false);

		// Insert entry into db
		long id=0;
		mEntry.setAvgSpeed(mAvgSpeed);
		mEntry.setCalorie(mCalories);
		mEntry.setClimb(mClimb);
		mEntry.setDistance(mDistance);
		mEntry.setLocationList(mLocationList);
		
		mEntryHelper = new ExerciseEntryHelper(mEntry);
		id = mEntryHelper.insertToDB(this);		
		if (id > 0) 
			Toast.makeText(getApplicationContext(), "Entry #" + id + " saved.",
					Toast.LENGTH_SHORT).show();
		
		// stop service
		// notification has flag auto_cancel set
		Intent intent = new Intent(this, TrackingService.class);
		if(mBound){
			unbindService(mConnection);
			mBound = false;
		}
		stopService(intent);
		
		// go back to MainActivity
		finish();
	}

	public void onCancelClicked(View v) {
		Intent intent = new Intent(this, TrackingService.class);
		if(mBound){
			unbindService(mConnection);
			mBound = false;
		}
		stopService(intent);
		// notification has flag auto_cancel set
		finish();
	}
	
	@Override
	public void onBackPressed() {
		// When back is pressed, similar to onCancelClicked, stop service and the notification.
		if (mTaskType == Globals.TASK_TYPE_NEW) {
			Intent intent = new Intent(this, TrackingService.class);
			if(mBound){
				unbindService(mConnection);
				mBound = false;
			}
			stopService(intent);
			// notification has flag auto_cancel set
			finish();		
		}

		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// If task type is displaying history, also give a menu button
		// To delete the entry
		MenuItem menuitem;
		if (mTaskType == Globals.TASK_TYPE_HISTORY) {
			menuitem = menu.add(Menu.NONE, MENU_ID_DELETE, MENU_ID_DELETE,
					"Delete");
			menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ID_DELETE:
			// Delete entry in database
			ExerciseEntryHelper.deleteEntryInDB(mContext, getIntent().getIntExtra(HistoryFragment.ROW_ID, -1));
			finish();
			return true;
		default:
			finish();
			return false;
		}
	}
	
	/******************* broadcast receiver *****************/
	public class TrackingServiceReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Globals.TAG, "Location update received; ");
        	int rc = intent.getIntExtra(TrackingService.LOCATION_UPDATE, -1);
        	if (rc == TrackingService.NEW_LOCATION_AVAILABLE)
        		new OnLocationUpdateTask().execute();
        	else
        		Toast.makeText(getApplicationContext(), "onReceive: This should not happen", Toast.LENGTH_SHORT).show();
        }
	}
	
	/******************* AsyncTask Class *******************/
	private class OnLocationUpdateTask extends AsyncTask<Void, Void, LatLng> {
		@Override
		protected LatLng doInBackground(Void... params) {
            Log.d(Globals.TAG, "doInBackground");

            Location loc = mLocationList.get(mLocationList.size()-1);
			LatLng latlng = Utils.fromLocationToLatLng(loc);
									
			// save the start point
			if (mFirstLoc) {
				mFirstLoc= false;
				firstLatLng=latlng;
				mStartTime = loc.getTime();
				return latlng;
			}
			else {
	            // update stats
				Location preLoc = mLocationList.get(mLocationList.size()-2);
				mDistance = mDistance + loc.distanceTo(preLoc);
				mAvgSpeed = mDistance / ((loc.getTime()-mStartTime) / 1000); 
				mCurSpeed = loc.distanceTo(mLocationList.get(mLocationList.size()-2)) / ((loc.getTime()-preLoc.getTime()) / 1000);
				mClimb = loc.getAltitude();
				mEntry.setCalorie(20);
				return null;
			}	
			
			// mark: return value?
		}
		
		// The UI update is done in UI thread, here we draw the GPS trace.
		@Override
		protected void onPostExecute(LatLng latlng) {
            Log.d(Globals.TAG, "onPostExecute 0");

			synchronized (mLocationList) {
				
				// ----------------------Skeleton--------------------------
				// Initialization
				// mark ?

				// Convert the mLocationList to mLatLngList 
				for (int i = 0; i < mLocationList.size() ; i++) {
					Location loc = mLocationList.get(i);
					mLatLngList.add(Utils.fromLocationToLatLng(loc)); 
				}

				// draw trace using PolyLine
				rectOptions = new PolylineOptions();
				rectOptions.color(Color.RED);
				for (int i = 0; i < mLatLngList.size(); i++) {
					rectOptions.add(mLatLngList.get(i));
					polyline = mMap.addPolyline(rectOptions);
				}

				// draw start marker
				mMap.addMarker(new MarkerOptions().position(firstLatLng).title("Start Point"));
				
				// draw current marker
				if (curMarker!=null)
					curMarker.remove();
				curMarker = mMap.addMarker(new MarkerOptions().position(mLatLngList.get(mLatLngList.size()-1)).
						title("You Are Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
				
				// Get real-time stats from the Exercise Entry
				String type = Globals.TYPE_STATS + HistoryFragment.activityTypeTable[mEntry.getActivityType()];
				String avgSpeed = Globals.AVG_SPEED_STATS + String.valueOf(mAvgSpeed);
				String curSpeed = Globals.CUR_SPEED_STATS + String.valueOf(mCurSpeed);
				String climb = Globals.CLIMB_STATS + String.valueOf(mClimb);
				String calories = Globals.CALORIES_STATS + String.valueOf(mCalories);
				String distance = Globals.DISTANCE_STATS + String.valueOf(mDistance);

				// Draw the stats on the map
				typeStats.setText(type);
				avgspeedStats.setText(avgSpeed);
				curspeedStats.setText(curSpeed);
				climbStats.setText(climb);
				caloriesStats.setText(calories);
				distanceStats.setText(distance);
				
				// Clear the mLatLngList
				mLatLngList.clear();
			}

			// re-center map if needed
			LatLng loc = Utils.fromLocationToLatLng(mLocationList.get(mLocationList.size()-1));
            Log.d(Globals.TAG, "onPostExecute 5");

			if (isMapNeedRecenter(loc)){
	            Log.d(Globals.TAG, "onPostExecute 6");

				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 17));
				mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null); 
			}		
			
	}
	
		private boolean isMapNeedRecenter(LatLng latlng) {
		
			VisibleRegion vr = mMap.getProjection().getVisibleRegion();
			
			double left = vr.latLngBounds.southwest.longitude;	
			double top = vr.latLngBounds.northeast.latitude;
			double right = vr.latLngBounds.northeast.longitude;
			double bottom = vr.latLngBounds.southwest.latitude;

		
			int rectWidth = (int) Math.abs(right - left);
			int rectHeight = (int) Math.abs(top - bottom);

			int rectCenterX = (int) mMap.getCameraPosition().target.longitude;
			int rectCenterY = (int) mMap.getCameraPosition().target.latitude;

			// Constructs the rectangle
			Rect validScreenRect = new Rect(rectCenterX - rectWidth / 2,
				rectCenterY - rectHeight / 2, rectCenterX + rectWidth / 2,
				rectCenterY + rectHeight / 2);

			return !validScreenRect.contains((int) latlng.longitude,
				(int) latlng.latitude);	
		}
	}
}

