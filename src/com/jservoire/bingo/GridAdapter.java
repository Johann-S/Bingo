package com.jservoire.bingo;

import com.island.android.game.bingo.game.GamePlayed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter
{
	private Context ctx;
	private int[] tmpTab;
	private GamePlayed game;
	private LayoutInflater mInflater;

	public GridAdapter(final Context _ctx,GamePlayed _game)
	{
		ctx = _ctx;
		game = _game;
		mInflater = LayoutInflater.from(ctx);
		tmpTab = game.grid;
	}

	@Override
	public int getCount() {
		return tmpTab.length;
	}

	@Override
	public Object getItem(final int index) {
		return tmpTab[index];
	}

	@Override
	public long getItemId(final int index) {
		return index;
	}
	
	public int getValueItem(final int index) {
		return tmpTab[index];
	}

	@Override
	public View getView(final int index, final View convertView, final ViewGroup parent)  
	{
		View cell;
		String valTxt = Integer.toString(tmpTab[index]);

		if (convertView == null) {
			cell = mInflater.inflate(R.layout.cell_gridview, parent, false);
		}
		else {
			cell = convertView;
		}

		TextView cellVal = (TextView)cell.findViewById(R.id.txtCell);
		cellVal.setText(valTxt);

		return cell;
	}

}
