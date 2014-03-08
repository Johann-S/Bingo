package com.jservoire.bingo;

import java.sql.Date;
import java.util.List;

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
	private List<GamePlayed> listGame;
	private LayoutInflater mInflater;
	
	public PrevGamesAdapter(Context actCtx,List<GamePlayed> _list)
	{
		ctx = actCtx;
		listGame = _list;
		mInflater = LayoutInflater.from(ctx);
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
	
	private int nbDaubed(int[] tabDaubed)
	{
		int nb = 0;
		int nbNumb = tabDaubed.length;
		for ( int i = 0; i < nbNumb; i++ ) 
		{
			if ( tabDaubed[i] == 1 ) {
				nb++;
			}
		}
		return nb;
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
		int nbDaubed = nbDaubed(game.numbersDaubed);
		daubedText.setText(Integer.toString(nbDaubed));
		
		return cellItem;
	}

}
