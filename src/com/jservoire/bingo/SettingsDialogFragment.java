package com.jservoire.bingo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.preference.PreferenceManager;


public class SettingsDialogFragment extends DialogFragment
{
	private DialogInterface.OnClickListener saveListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}	

	};
	
	private DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private ToggleButton activMusic;
	private CompoundButton.OnCheckedChangeListener eventActMusic = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			setMusicPreference(isChecked);			
		}
	};
	
	public static SettingsDialogFragment newInstance(String title) {
		return new SettingsDialogFragment();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		super.onCreateDialog(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vDialog = inflater.inflate(R.layout.dialog_settings,null);
		activMusic = (ToggleButton)vDialog.findViewById(R.id.toggleMusic);
		activMusic.setOnCheckedChangeListener(eventActMusic);
		activMusic.setChecked(getMusicPreference());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(vDialog);
		builder.setTitle(getResources().getString(R.string.action_settings));
		builder.setPositiveButton(getResources().getString(R.string.saveSettings), saveListener);
		builder.setNegativeButton(getResources().getString(R.string.cancelSettings), cancelListener);
		
		return (Dialog)builder.create();
	}
	
	private void setMusicPreference(Boolean value)
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("activMusic", value);
		editor.commit();		
	}
	
	private Boolean getMusicPreference()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		return preferences.getBoolean("activMusic", false);		
	}
}
