package edu.dartmouth.cs.myruns4;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class TrackingService extends Service
	implements LocationListener
	{

	public ArrayList<Location> mLocationList;

	// Location manager
	private LocationManager mLocationManager;
	
	// Context for "this"
//	private Context mContext;
	
	// Intents for broadcasting location/motion updates
	private Intent mLocationUpdateBroadcast;
	
//	private int mInputType;
	
	private final IBinder mBinder = new TrackingBinder();
	
	public static final String LOCATION_UPDATE = "location update";
	public static final int NEW_LOCATION_AVAILABLE = 400;
	public static final String ACTION = "tracking action";
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {

		// Initialize mContext, mLocationList, mLocationUpdateBroadcast
		// ----------------------Skeleton--------------------------
//		mContext = this;
		mLocationList = new ArrayList<Location>();
		mLocationUpdateBroadcast = new Intent();
		mLocationUpdateBroadcast.setAction(ACTION);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Read inputType, can be GPS or Automatic.
//		int inputType = intent.getIntExtra(MainActivity.INPUT_TYPE, -1);
				
		// Get LocationManager and set related provider.
	    mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    if (gpsEnabled)
	    	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
		// For indoor debugging, can use network cellular location 
	    else
	    	mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
	    
	    
		// Fire the MapDisplayAcitivty
		// mark??	

		// Using pending intent to bring back the MapActivity from notification center.
		// Use NotificationManager to build notification(icon, content, title, flag and pIntent)
		String notificationTitle = "MyRuns";
		String notificationText = "Tracking Location";
		Intent myIntent = new Intent(this, MapDisplayActivity.class);
		PendingIntent pendingIntent 
				= PendingIntent.getActivity(getBaseContext(), 
						0, myIntent, 
						Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Notification notification = new Notification.Builder(this)
        .setContentTitle(notificationTitle)
        .setContentText(notificationText).setSmallIcon(R.drawable.default_profile)
        .setContentIntent(pendingIntent).build();
	    NotificationManager notificationManager = 
	    		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notification.flags = notification.flags
				| Notification.FLAG_ONGOING_EVENT;
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notificationManager.notify(0, notification);
				
			 
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// Unregistering listeners
		mLocationManager.removeUpdates(this);
		// Remove notification
	    NotificationManager notificationManager = 
	    		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.cancelAll();
	}
	
	public class TrackingBinder extends Binder{
		public ArrayList<Location> getLocationList(){
			return mLocationList;
		}
	}

	
	/************ implement LocationLister interface ***********/
	public void onLocationChanged(Location location) {
//    	Toast.makeText(getApplication(), "OnLocationChanged", Toast.LENGTH_SHORT).show();

		// Check whether location is valid, drop if invalid
		// mark
		
		// update location list
		mLocationList.add(location);

		// Send broadcast saying new location is updated

		mLocationUpdateBroadcast.putExtra(TrackingService.LOCATION_UPDATE, TrackingService.NEW_LOCATION_AVAILABLE);
		sendBroadcast(mLocationUpdateBroadcast);

	}
	public void onProviderDisabled(String provider) {}
	public void onProviderEnabled(String provider) {}
	public void onStatusChanged(String provider, int status, Bundle extras) {}

}
