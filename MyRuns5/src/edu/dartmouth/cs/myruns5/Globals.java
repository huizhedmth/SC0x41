package edu.dartmouth.cs.myruns5;

//All the global constants are put here
public abstract class Globals {

	// Debugging tag for the whole project
	public static final String TAG = "MyRuns";
	
	// Const for distance/time conversions
	public static final double KM2MILE_RATIO = 1.609344;
	public static final double KILO = 1000;
	public static final int SECONDS_PER_HOUR = 3600;
	
	// Motion sensor buffering related consts 
	public static final int ACCELEROMETER_BUFFER_CAPACITY = 2048;
	public static final int ACCELEROMETER_BLOCK_CAPACITY = 64;
	
	// Table schema, column names
	public static final String KEY_ROWID = "_id";
	public static final String KEY_INPUT_TYPE = "input_type";
	public static final String KEY_ACTIVITY_TYPE = "activity_type";
	public static final String KEY_DATE_TIME = "date_time";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_DISTANCE = "distance";
	public static final String KEY_AVG_PACE = "avg_pace";
	public static final String KEY_AVG_SPEED = "avg_speed";
	public static final String KEY_CALORIES = "calories";
	public static final String KEY_CLIMB = "climb";
	public static final String KEY_HEARTRATE = "heartrate";
	public static final String KEY_COMMENT = "comment";
	public static final String KEY_PRIVACY = "privacy";
	public static final String KEY_GPS_DATA = "gps_data";
	public static final String KEY_TRACK = "track";
	
	// All activity types in a string array.
	// "Standing" is not an exercise type... it's there for activity recognition result
	// There was a debate about using "Standing" or "Still". The authority decided using
	// "Standing"...
	public static final String[] ACTIVITY_TYPES = {"Running", "Cycling", "Walking", "Hiking", 
		"Downhill Skiing", "Cross-Country Skiing", "Snowboarding", "Skating", "Swimming", 
		"Mountain Biking", "Wheelchair", "Elliptical", "Other",  "Standing"};
	
	// Int encoded activity types
	public static final int ACTIVITY_TYPE_ERROR = -1;
	public static final int ACTIVITY_TYPE_RUNNING = 0;
	public static final int ACTIVITY_TYPE_CYCLING = 1;
	public static final int ACTIVITY_TYPE_WALKING = 2;
	public static final int ACTIVITY_TYPE_HIKING = 3;
	public static final int ACTIVITY_TYPE_DOWNHILL_SKIING = 4;
	public static final int ACTIVITY_TYPE_CROSS_COUNTRY_SKIING = 5;
	public static final int ACTIVITY_TYPE_SNOWBOARDING = 6;
	public static final int ACTIVITY_TYPE_SKATING = 7;
	public static final int ACTIVITY_TYPE_SWIMMING = 8;
	public static final int ACTIVITY_TYPE_MOUNTAIN_BIKING = 9;
	public static final int ACTIVITY_TYPE_WHEELCHAIR = 10;
	public static final int ACTIVITY_TYPE_ELLIPTICAL = 11;
	public static final int ACTIVITY_TYPE_OTHER = 12;
	public static final int ACTIVITY_TYPE_STANDING = 13; 
	public static final int ACTIVITY_TYPE_UNKNOWN = 14;
	
	// Consts for input types. 
	public static final int INPUT_TYPE_ERROR = -1;
	public static final int INPUT_TYPE_MANUAL = 0;
	public static final int INPUT_TYPE_GPS = 1;
	public static final int INPUT_TYPE_AUTOMATIC = 2;
	
	// Consts for task types
	public static final String KEY_TASK_TYPE = "TASK_TYPE";
	public static final int TASK_TYPE_ERROR = -1;
	public static final int TASK_TYPE_NEW = 1;
	public static final int TASK_TYPE_HISTORY = 2;
	
	// Stat titles 
	public static final String TYPE_STATS = "Type: ";
	public static final String AVG_SPEED_STATS = "Ave speed: ";
	public static final String CUR_SPEED_STATS = "Cur speed: ";
	public static final String CLIMB_STATS = "Climb: ";
	public static final String CALORIES_STATS = "Calories: ";
	public static final String DISTANCE_STATS = "Diatance: ";
	
	public static final int[] INFERENCE_MAPPING = {13, 2, 0};

	
}