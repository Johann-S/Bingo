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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


public class SettingsDialogFragment extends DialogFragment
{
	private DialogInterface.OnClickListener saveListener = new DialogInterface.OnClickListener() 
	{
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			savePreferences();
		}
	};
	
	private DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() 
	{	
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private ToggleButton activMusic;
	private CompoundButton.OnCheckedChangeListener eventActMusic = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			musicEnable = isChecked;		
		}
	};
	
	private SeekBar seekDelayBar;
	private SeekBar.OnSeekBarChangeListener listenerSeekBar = new SeekBar.OnSeekBarChangeListener() 
	{	
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser){
			textDelay.setText(Integer.toString(progress));
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar){
			delay = seekBar.getProgress();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {}
	};
	
	
	private TextView textDelay;
	private int delay;
	private Boolean musicEnable;
	
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
		
		seekDelayBar = (SeekBar)vDialog.findViewById(R.id.seekBarDelay);
		seekDelayBar.setOnSeekBarChangeListener(listenerSeekBar);	
		textDelay = (TextView)vDialog.findViewById(R.id.textNbDelay);
		
		loadPreferences();
		activMusic.setChecked(musicEnable);
		seekDelayBar.setProgress(delay);
		textDelay.setText(Integer.toString(delay));
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(vDialog);
		builder.setTitle(getResources().getString(R.string.action_settings));
		builder.setPositiveButton(getResources().getString(R.string.saveSettings), saveListener);
		builder.setNegativeButton(getResources().getString(R.string.cancelSettings), cancelListener);
		
		return (Dialog)builder.create();
	}
	
	private void loadPreferences()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		musicEnable = preferences.getBoolean("activMusic", false);
		delay = preferences.getInt("delay", 3);
	}
	
	private void savePreferences()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("activMusic", musicEnable);
		editor.putInt("delay", delay);
		editor.commit();
	}
}
