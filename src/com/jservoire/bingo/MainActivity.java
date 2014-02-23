package com.jservoire.bingo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends FragmentActivity 
{
	private Button btnStart;
	private Button btnSettings;
	private ListView prevList;
	private View.OnClickListener startListener = new View.OnClickListener() 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private View.OnClickListener settingListener = new View.OnClickListener() 
	{
		@Override
		public void onClick(View v) 
		{
			FragmentManager fm = getSupportFragmentManager();
			SettingsDialogFragment.newInstance("Test").show(fm, "test");
		}
	};
	
	private ListView.OnItemClickListener listListener = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
		{

		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnStart = (Button)findViewById(R.id.btnStartGame);
		btnStart.setOnClickListener(startListener);		
		btnSettings = (Button)findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(settingListener);
		
		prevList = (ListView)findViewById(R.id.listViewPrev);
		prevList.setAdapter(new PrevGamesAdapter(this));
		prevList.setOnItemClickListener(listListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
