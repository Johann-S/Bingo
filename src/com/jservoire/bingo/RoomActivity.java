package com.jservoire.bingo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RoomActivity extends Activity
{
	private Button btnHome;
	private Button btnSettings;
	private Button btnBingo;
	private ImageView avatarImageView;
	private View.OnClickListener btnHomeListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	private View.OnClickListener btnSettingsListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	private View.OnClickListener btnBingoListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		btnHome = (Button)findViewById(R.id.btnHome);
		btnHome.setOnClickListener(btnHomeListener);
		btnSettings = (Button)findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(btnSettingsListener);
		btnBingo = (Button)findViewById(R.id.btnBingo);
		btnBingo.setOnClickListener(btnBingoListener);
	}
	
	private void loadPreferences()
	{
		SharedPreferences preferences = getSharedPreferences(SettingsDialogFragment.PREF_FILE, Activity.MODE_PRIVATE);
	}
}
