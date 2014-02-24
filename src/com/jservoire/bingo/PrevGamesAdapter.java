package com.jservoire.bingo;

import java.sql.Date;
import java.util.ArrayList;

import com.island.android.game.bingo.game.GamePlayed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PrevGamesAdapter extends BaseAdapter
{
	private Context ctx;
	private ArrayList<GamePlayed> listGame;
	private LayoutInflater mInflater;
	
	public PrevGamesAdapter(Context actCtx)
	{
		ctx = actCtx;
		listGame = new ArrayList<GamePlayed>();
		mInflater = LayoutInflater.from(ctx);
		
		for ( int i = 0; i < 5; i++ ) 
		{
			java.util.Date today = new java.util.Date();
			int[] daubed = {4,5,8,9,10};
			int[] gridPlay = {85,45,69,25};
			GamePlayed g = new GamePlayed(Integer.toString(i),new Date(today.getTime()));
			g.durationSecond = (i*10);
			g.numbersDaubed = daubed;
			g.grid = gridPlay;
			listGame.add(g);
		}
	}
	
	@Override
	public int getCount() {
		return listGame.size();
	}

	@Override
	public GamePlayed getItem(int index) {
		return listGame.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) 
	{
		RelativeLayout cellItem;
		GamePlayed game = listGame.get(index);
		
		if (convertView == null) {
			cellItem = (RelativeLayout) mInflater.inflate(R.layout.cell_prev_game, parent, false);
		}
		else {
			cellItem = (RelativeLayout) convertView;
		}
		
		TextView dateText = (TextView)cellItem.findViewById(R.id.dateGame);
		dateText.setText(game.when.toString());
		
		TextView daubedText = (TextView)cellItem.findViewById(R.id.daubedGame);
		int nbDaubed = game.numbersDaubed.length;
		daubedText.setText(Integer.toString(nbDaubed));
		
		return cellItem;
	}

}
