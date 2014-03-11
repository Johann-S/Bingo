package com.jservoire.bingo;

import java.util.List;

import Interfaces.PreferencesListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
			// Start a new game
			Intent intent = new Intent(getBaseContext(), RoomActivity.class);
			startActivity(intent);
		}
	};

	private View.OnClickListener settingListener = new View.OnClickListener() 
	{
		@Override
		public void onClick(final View v) 
		{
			// show Settings dialog
			FragmentManager fm = getSupportFragmentManager();
			SettingsDialogFragment.newInstance().show(fm, "test");
		}
	};

	private ListView.OnItemClickListener listListener = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(final AdapterView<?> arg0, final View arg1, final int index,final long arg3) 
		{
			// Resume a game
			GamePlayed game = (GamePlayed)prevList.getAdapter().getItem(index);
			Intent intent = new Intent(getBaseContext(), RoomActivity.class);
			intent.putExtra("when", game.when.getTime());
			startActivity(intent);
		}
	};


	@Override
	public String getId() {
		return "01A";
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent imageReturnedIntent) {
		// allow to notify SettingsDialogFragment on result
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
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

		// Ask server to get all played games
		BingoApp.srv.requestAllPlayedGames(this);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onGamesPlayedList(final List<GamePlayed> listPlayed) {
		// Build list on receive games played
		prevList.setAdapter(new PrevGamesAdapter(this,listPlayed));
	}

	@Override
	public void onPreferencesChanged() {
		// notify user, preferences had changed
		Crouton.makeText(this,getResources().getString(R.string.savePref),Style.CONFIRM).show();
	}
}
