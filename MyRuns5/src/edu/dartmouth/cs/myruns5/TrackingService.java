package edu.dartmouth.cs.myruns5;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class TrackingService extends Service
	implements LocationListener, SensorEventListener
	{

	public ArrayList<Location> mLocationList;
	
	private int[] mInferenceCount = {0, 0, 0};

	// Location manager
	private LocationManager mLocationManager;
	
	// Intents for broadcasting location/motion updates
	private Intent mLocationUpdateBroadcast;
	private Intent mMotionUpdateBroadcast;
	
	private int mInputType;
	public int mActivityType;
	
	
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private static ArrayBlockingQueue<Double> mAccBuffer;
	private ActivityClassificationTask mActivityClassificationTask;

	
	
	private final IBinder mBinder = new TrackingBinder();
	
	public static final String LOCATION_UPDATE = "location update";
	public static final int NEW_LOCATION_AVAILABLE = 400;
	
	// broadcast 
	public static final String ACTION_MOTION_UPDATE = "motion update";
	public static final String CURRENT_MOTION_TYPE = "new motion type";
	public static final String VOTED_MOTION_TYPE = "voted motion type";
	public static final String ACTION_TRACKING = "tracking action";
	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	
	@Override
	public void onCreate() {
		mLocationList = new ArrayList<Location>();
		mLocationUpdateBroadcast = new Intent();
		mLocationUpdateBroadcast.setAction(ACTION_TRACKING);
		mMotionUpdateBroadcast = new Intent();
		mMotionUpdateBroadcast.setAction(ACTION_MOTION_UPDATE);
		mAccBuffer = new ArrayBlockingQueue<Double>(Globals.ACCELEROMETER_BUFFER_CAPACITY);
		mActivityClassificationTask = new ActivityClassificationTask();
		mActivityType = Globals.ACTIVITY_TYPE_STANDING;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Read inputType, can be GPS or Automatic.
		mInputType = intent.getIntExtra(MapDisplayActivity.INPUT_TYPE, -1);
//				Toast.makeText(getApplicationContext(), String.valueOf(mInputType), Toast.LENGTH_SHORT).show();
				
		// Get LocationManager and set related provider.
	    mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    boolean gpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    
	    if (gpsEnabled)
	    	mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
	    else
	    	mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 5, this);
	 
//    	Toast.makeText(getApplicationContext(), "mInputType: "+String.valueOf(mInputType), Toast.LENGTH_SHORT).show();

	    if (mInputType == Globals.INPUT_TYPE_AUTOMATIC){
	    	// init sensor manager
	    	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    	mAccelerometer = mSensorManager
	    			.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		
	    	// register listener
	    	mSensorManager.registerListener(this, mAccelerometer,
	    			SensorManager.SENSOR_DELAY_FASTEST);
	    	
	    	mActivityClassificationTask.execute();
	    }
	    
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
        .setContentText(notificationText).setSmallIcon(R.drawable.greend)
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
//    	Toast.makeText(getApplicationContext(), "service onDestroy", Toast.LENGTH_SHORT).show();

		// Unregistering listeners
		mLocationManager.removeUpdates(this);
		// Remove notification
	    NotificationManager notificationManager = 
	    		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	    notificationManager.cancelAll();
	    
//    	Toast.makeText(getApplicationContext(), "mInputType: "+String.valueOf(mInputType), Toast.LENGTH_SHORT).show();

	    // unregister listener
	    if (mInputType == Globals.INPUT_TYPE_AUTOMATIC){
//	    	Toast.makeText(getApplicationContext(), "unregister linstener", Toast.LENGTH_SHORT).show();
	    	mSensorManager.unregisterListener(this);

	    }
	    
	    // cancel task
	    mActivityClassificationTask.cancel(true);
	}
	
	public class TrackingBinder extends Binder{
		public TrackingService getService(){
			return TrackingService.this;
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
	
	/************ implement SensorEventLister interface ***********/

	public void onSensorChanged(SensorEvent event) {
//		Toast.makeText(getApplicationContext(), "onSensorChanged", Toast.LENGTH_SHORT).show();
		if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {

			float[] data = event.values;
			double x = data[0];
			double y = data[1];
			double z = data[2];
			
			double m = Math.sqrt(x*x + y*y + z*z);

			// Add m to the mAccBuffer one by one.
			try {
				mAccBuffer.add(m);
			} catch (IllegalStateException e) {
				ArrayBlockingQueue<Double> newBuf = new ArrayBlockingQueue<Double>(2*mAccBuffer.size());
				mAccBuffer.drainTo(newBuf);
				mAccBuffer = newBuf;
				mAccBuffer.add(m);				
			}
//			Toast.makeText(getApplicationContext(), String.valueOf(mAccBuffer.size()), Toast.LENGTH_SHORT).show();
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
	/************ AsyncTask **************/
	private class ActivityClassificationTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... arg0) {
				int blockSize = 0;
				FFT fft = new FFT(Globals.ACCELEROMETER_BLOCK_CAPACITY);
				double[] accBlock = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];
				double[] re = accBlock;
				double[] im = new double[Globals.ACCELEROMETER_BLOCK_CAPACITY];

				double max = Double.MIN_VALUE;

				while (true) {
					try {
						// need to check if the AsyncTask is cancelled or not in the while loop
						if (isCancelled () == true)
					        return null;
						
						ArrayList<Double> featVect = new ArrayList<Double>(Globals.ACCELEROMETER_BLOCK_CAPACITY + 1);

						// Dumping buffer
						accBlock[blockSize++] = mAccBuffer.take().doubleValue();

						if (blockSize == Globals.ACCELEROMETER_BLOCK_CAPACITY) {
							blockSize = 0;

							// time = System.currentTimeMillis();
							max = .0;
							for (double val : accBlock) {
								if (max < val) {
									max = val;
								}
							}

							fft.fft(re, im);

							for (int i = 0; i < re.length; i++) {
								double mag = Math.sqrt(re[i] * re[i] + im[i]
										* im[i]);
								featVect.add(mag);
								im[i] = .0; // Clear the field
							}
							
							// Append max after frequency component
							featVect.add(max);						
							int value = (int) WekaClassifier.classify(featVect.toArray());
							Log.d("mag", String.valueOf(value));
							mInferenceCount[value]++;
							int maxIndex = 0;
							if (mInferenceCount[maxIndex] < mInferenceCount[1]) maxIndex = 1;
							if (mInferenceCount[maxIndex] < mInferenceCount[2]) maxIndex = 2;
							mActivityType = Globals.INFERENCE_MAPPING[maxIndex];
							int currentActivity = Globals.INFERENCE_MAPPING[value];
							// send broadcast with the CURRENT activity type
							mMotionUpdateBroadcast.putExtra(CURRENT_MOTION_TYPE, currentActivity);
							mMotionUpdateBroadcast.putExtra(VOTED_MOTION_TYPE, mActivityType);
							
							sendBroadcast(mMotionUpdateBroadcast);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
		

				}
		}
	}
}
