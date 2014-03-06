package com.jservoire.bingo;

import Interfaces.PreferencesListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RoomActivity extends FragmentActivity implements PreferencesListener
{
	private Button btnHome;
	private Button btnSettings;
	private Button btnBingo;
	private ImageView avatarImageView;
	private boolean musicEnabled;
	private int delay;
	private View.OnClickListener btnHomeListener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub

		}
	};
	private View.OnClickListener btnSettingsListener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) 
		{
			FragmentManager fm = getSupportFragmentManager();
			SettingsDialogFragment.newInstance().show(fm, "test");
		}
	};
	private View.OnClickListener btnBingoListener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub

		}
	};

	private void loadPreferences()
	{
		SharedPreferences preferences = getSharedPreferences(SettingsDialogFragment.PREF_FILE, Context.MODE_PRIVATE);
		String strAvatar = preferences.getString("avatar",null);	
		if ( strAvatar != null && strAvatar.length() > 0 )
		{
			Drawable imgDraw = new BitmapDrawable(getResources(), strAvatar);
			if ( imgDraw != null ) {
				avatarImageView.setImageDrawable(imgDraw);
			}
		}
		musicEnabled = preferences.getBoolean("activMusic",false);
		delay = preferences.getInt("delay",3);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_room);
		btnHome = (Button)findViewById(R.id.btnHome);
		btnHome.setOnClickListener(btnHomeListener);
		btnSettings = (Button)findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(btnSettingsListener);
		btnBingo = (Button)findViewById(R.id.btnBingo);
		btnBingo.setOnClickListener(btnBingoListener);
		avatarImageView = (ImageView)findViewById(R.id.avatarImageView);
		loadPreferences();
	}

	@Override
	public void onPreferencesChanged()
	{
		loadPreferences();		
		Crouton.makeText(this,getResources().getString(R.string.savePref),Style.CONFIRM).show();
	}
}
