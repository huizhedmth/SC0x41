package edu.dartmouth.cs.myruns3;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.dartmouth.cs.myruns2.R;

public class StartFragment extends PreferenceFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_start, container, false);
	}
	

}
