package com.jservoire.bingo;

import java.util.List;

import Interfaces.PreferencesListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.island.android.game.bingo.game.GamePlayed;
import com.island.android.game.bingo.game.interfaces.IStartClient;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends FragmentActivity implements PreferencesListener, IStartClient
{
	private Button btnStart;
	private Button btnSettings;
	private ListView prevList;
	private View.OnClickListener startListener = new View.OnClickListener() 
	{
		@Override
		public void onClick(final View v) {
			Intent intent = new Intent(getBaseContext(), RoomActivity.class);
			startActivity(intent);
		}
	};

	private View.OnClickListener settingListener = new View.OnClickListener() 
	{
		@Override
		public void onClick(final View v) 
		{
			FragmentManager fm = getSupportFragmentManager();
			SettingsDialogFragment.newInstance().show(fm, "test");
		}
	};

	private ListView.OnItemClickListener listListener = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(final AdapterView<?> arg0, final View arg1, final int index,final long arg3) 
		{
			GamePlayed game = (GamePlayed)prevList.getAdapter().getItem(index);
			if ( game.durationSecond > 0 ) {
				Toast.makeText(getBaseContext(), "Jeu : "+game.durationSecond, Toast.LENGTH_SHORT).show();
			}
		}
	};


	@Override
	public String getId() {
		return "01A";
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnStart = (Button)findViewById(R.id.btnStartGame);
		btnStart.setOnClickListener(startListener);		
		btnSettings = (Button)findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(settingListener);

		prevList = (ListView)findViewById(R.id.listViewPrev);
		prevList.setOnItemClickListener(listListener);

		BingoApp.srv.requestAllPlayedGames(this);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onGamesPlayedList(final List<GamePlayed> listPlayed) {
		prevList.setAdapter(new PrevGamesAdapter(this,listPlayed));
	}

	@Override
	public void onPreferencesChanged() {
		Crouton.makeText(this,getResources().getString(R.string.savePref),Style.CONFIRM).show();
	}
}
