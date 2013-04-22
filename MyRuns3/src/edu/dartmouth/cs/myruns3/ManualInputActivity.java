package edu.dartmouth.cs.myruns3;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import edu.dartmouth.cs.myruns2.R;

public class ManualInputActivity extends ListActivity {
	
	// dialog IDs
	public static final int LIST_ITEM_ID_DATE = 0;
	public static final int LIST_ITEM_ID_TIME = 1;
	public static final int LIST_ITEM_ID_DURATION = 2;
	public static final int LIST_ITEM_ID_DISTANCE = 3;
	public static final int LIST_ITEM_ID_CALORIES = 4;
	public static final int LIST_ITEM_ID_HEARTRATE = 5;
	public static final int LIST_ITEM_ID_COMMENT = 6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_input);
	}
	
	public void onSaveClicked(View view){
		/* todo */
	}
	
	public void onCancelClicked(View view){
		/* todo */
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id){
//		Toast.makeText(getApplicationContext(), "onListItemClick", Toast.LENGTH_SHORT).show();
		super.onListItemClick(l, v, position, id);

		int dialogId=0;
		// Figuring out what dialog to show based on the position clicked
		
		switch (position) {
		case LIST_ITEM_ID_DATE:
			dialogId = MyRunsDialogFragment.DIALOG_ID_DATE;
			break;
		case LIST_ITEM_ID_TIME:
			dialogId = MyRunsDialogFragment.DIALOG_ID_TIME;
			break;
		case LIST_ITEM_ID_DURATION:
			dialogId = MyRunsDialogFragment.DIALOG_ID_DURATION;
			break;
		case LIST_ITEM_ID_DISTANCE:
			dialogId = MyRunsDialogFragment.DIALOG_ID_DISTANCE;
			break;
		case LIST_ITEM_ID_CALORIES:
			dialogId = MyRunsDialogFragment.DIALOG_ID_CALORIES;
			break;
		case LIST_ITEM_ID_HEARTRATE:
			dialogId = MyRunsDialogFragment.DIALOG_ID_HEARTRATE;
			break;
		case LIST_ITEM_ID_COMMENT:
			dialogId = MyRunsDialogFragment.DIALOG_ID_COMMENT;
			break;
		default:
			dialogId = MyRunsDialogFragment.DIALOG_ID_ERROR;
		}

		displayDialog(dialogId);
	}
	/*************** dialog listeners ***************/
	public void onAlertOKClick(){
//		Toast.makeText(getApplicationContext(), "onalertOKClick", Toast.LENGTH_SHORT).show();
	}
	public void onAlertCancelClick(){
//		Toast.makeText(getApplicationContext(), "onalertCancelClick", Toast.LENGTH_SHORT).show();
	}
	/*************** helper function ****************/
	public void displayDialog(int id) {
		DialogFragment fragment = MyRunsDialogFragment.newInstance(id);
		fragment.show(getFragmentManager(),
				getString(R.string.photo_picker_tag)); // mark: tag may not be trivial...
	}
}
