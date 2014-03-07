package com.jservoire.bingo;

import java.util.List;

import Interfaces.PreferencesListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.island.android.game.bingo.game.GamePlayed;
import com.island.android.game.bingo.game.interfaces.IBingoClient;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RoomActivity extends FragmentActivity implements PreferencesListener, IBingoClient
{
	private Button btnHome;
	private Button btnSettings;
	private Button btnBingo;
	private ImageView avatarImageView;
	private GridView gridView;
	private boolean musicEnabled;
	private int delay;
	private GridAdapter adapter;
	private View.OnClickListener btnHomeListener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			finish();
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
			BingoApp.srv.notifyBingo(RoomActivity.this);
		}
	};

	private GridView.OnItemClickListener caseListener = new GridView.OnItemClickListener() 
	{
		@Override
		public void onItemClick(final AdapterView<?> arg0, final View cell, final int index,final long arg3) 
		{
			ImageView circleView = (ImageView)cell.findViewById(R.id.circleImgView);
			int intVisi = ( circleView.getVisibility() == View.INVISIBLE ) ? View.VISIBLE:View.INVISIBLE;
			circleView.setVisibility(intVisi);
			//BingoApp.srv.notifyNumberDaubed(RoomActivity.this, value, daubed);
		}
	};

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

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
		BingoApp.srv.setNumbersDelay(delay);
	}

	@Override
	public void onClientVictory(final String arg0, final String arg1) {
		// TODO Auto-generated method stub

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
		gridView = (GridView)findViewById(R.id.gridView);
		adapter = new GridAdapter(this);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(caseListener);
		avatarImageView = (ImageView)findViewById(R.id.avatarImageView);
		loadPreferences();

		// Begin play
		BingoApp.srv.notifyRegistration(this);

		// New game
		BingoApp.srv.notifyReadyForGame(this, 0);
	}

	@Override
	public void onGamesPlayedList(final List<GamePlayed> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameToPlayRestored(final GamePlayed arg0) {
		// TODO Auto-generated method stub
		// Call after notifyReadyForGame
	}

	@Override
	public void onNewNumber(final int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPreferencesChanged()
	{
		loadPreferences();		
		Crouton.makeText(this,getResources().getString(R.string.savePref),Style.CONFIRM).show();
	}

	@Override
	public void onRoundOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWrongBingo() {
		// TODO Auto-generated method stub

	}
}
