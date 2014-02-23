package com.jservoire.bingo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
	
	private Button btnAvatar;
	private View.OnClickListener clickAvatar = new View.OnClickListener() 
	{	
		@Override
		public void onClick(View v) 
		{
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, 0);
		}
	};
	
	
	private TextView textDelay;
	private int delay;
	private String strAvatar;
	private Boolean hasAvatar;
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
		
		btnAvatar = (Button)vDialog.findViewById(R.id.buttonAvatar);
		btnAvatar.setOnClickListener(clickAvatar);
		
		loadPreferences();
		activMusic.setChecked(musicEnable);
		seekDelayBar.setProgress(delay);
		textDelay.setText(Integer.toString(delay));
		
		hasAvatar = false;
		btnAvatar.setText(getResources().getString(R.string.chooseAvatar));
		if ( strAvatar != null ) 
		{
			hasAvatar = true;
			btnAvatar.setText(getResources().getString(R.string.changeAvatar));
			Bitmap avatar = loadAvatar();
			Drawable imgDraw = new BitmapDrawable(getResources(),avatar);
			imgDraw.setBounds(0,0,100,100);
			btnAvatar.setCompoundDrawables(imgDraw,null,null,null);
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(vDialog);
		builder.setTitle(getResources().getString(R.string.action_settings));
		builder.setPositiveButton(getResources().getString(R.string.saveSettings), saveListener);
		builder.setNegativeButton(getResources().getString(R.string.cancelSettings), cancelListener);
		
		return (Dialog)builder.create();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) 
	{
	    switch(requestCode) 
	    { 
		    case 0:
		        if (resultCode == Activity.RESULT_OK )
		        {  
		            Uri selectedImage = imageReturnedIntent.getData();
					try 
					{
						InputStream imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
						Bitmap bmpImg = BitmapFactory.decodeStream(imageStream);
						
						Drawable imgDraw = new BitmapDrawable(getResources(),bmpImg);
						imgDraw.setBounds(0,0,100,100);
						btnAvatar.setCompoundDrawables(imgDraw,null,null,null);
						
						saveImage(bmpImg);
					} 
					catch (FileNotFoundException e) {
						e.printStackTrace();
					}
		            
		        }
		        break;
	    }
	}
	
	private void loadPreferences()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		musicEnable = preferences.getBoolean("activMusic", false);
		delay = preferences.getInt("delay", 3);
		strAvatar = preferences.getString("avatar",null);
	}
	
	private void savePreferences()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("activMusic", musicEnable);
		editor.putInt("delay", delay);
		editor.putString("avatar", strAvatar);
		editor.commit();
	}
	
	private void saveImage(final Bitmap bmp)
	{
	    File savePath = new File(Environment.getExternalStorageDirectory()+"/Bingo/");
        if ( !savePath.exists() && !savePath.isDirectory()) {
        	savePath.mkdir();
        }
        
	    if ( savePath != null )
	    {
	    	try 
	    	{
		        OutputStream out = new FileOutputStream(savePath.getPath()+"/avatar.jpg"); 
		        strAvatar = savePath.getPath()+"/avatar.jpg";
		        bmp.compress(Bitmap.CompressFormat.JPEG, 85, out);
			} 
	    	catch (IOException e) {
				e.printStackTrace();
			}
	    }
	}
	
	private Bitmap loadAvatar()
	{
		File avatar = new File(Environment.getExternalStorageDirectory()+"/Bingo/avatar.jpg");
		return BitmapFactory.decodeFile(avatar.getAbsolutePath());
	}
}
