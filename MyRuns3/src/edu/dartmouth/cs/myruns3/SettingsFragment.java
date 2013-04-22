package edu.dartmouth.cs.myruns3;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import edu.dartmouth.cs.myruns2.R;

public class SettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
	}
}
