package edu.dartmouth.cs.myruns3;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import edu.dartmouth.cs.myruns2.R;

public class MainActivity extends Activity {
	public static final String KEY_TAB_INDEX = "tab index";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// set up action bar
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// create fragments
		Fragment startFragment = new StartFragment();
		Fragment historyFragment = new HistoryFragment();
		Fragment settingsFragment = new SettingsFragment();
		
		// create tabs
		ActionBar.Tab startTab = bar.newTab().setText(getString(R.string.startTab_title));
		startTab.setTabListener(new MyTabListener(startFragment, getApplicationContext()));
		
		ActionBar.Tab historyTab = bar.newTab().setText(getString(R.string.historyTab_title));
		historyTab.setTabListener(new MyTabListener(historyFragment, getApplicationContext()));
		
		ActionBar.Tab settingsTab = bar.newTab().setText(getString(R.string.settingsTab_title));
		settingsTab.setTabListener(new MyTabListener(settingsFragment, getApplicationContext()));
		
		// add tabs
		bar.addTab(startTab);
		bar.addTab(historyTab);
		bar.addTab(settingsTab);
		
		// resume state if applicable
		if (savedInstanceState != null){
			bar.setSelectedNavigationItem(savedInstanceState.getInt(KEY_TAB_INDEX));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_TAB_INDEX, getActionBar().getSelectedNavigationIndex());
	}

	public void onStartClicked(View view){
	    Intent intent = new Intent(this, ManualInputActivity.class);
	    startActivity(intent);
	}
}

class MyTabListener implements ActionBar.TabListener{
	public Fragment mFragment;
	public Context mContext;
	
	public MyTabListener(Fragment frgmt, Context cntxt){
		mFragment = frgmt; mContext = cntxt;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft){
		ft.replace(R.id.fragment_container, mFragment);
//		Toast.makeText(mContext, "onTabSelected", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft){
//		Toast.makeText(mContext, "onTabReSelected", Toast.LENGTH_SHORT).show();		
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft){
		ft.remove(mFragment);
//		Toast.makeText(mContext, "onTabUnSelected", Toast.LENGTH_SHORT).show();		
	}
}