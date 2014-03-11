package com.jservoire.bingo;

import android.app.Application;

import com.island.android.game.bingo.game.server.local.LocalBingoServer;
import com.island.android.game.bingo.game.server.local.db.BingoDatabaseHelper;

public class BingoApp extends Application
{
	public static LocalBingoServer srv;

	@Override
	public void onCreate()
	{
		BingoDatabaseHelper db = new BingoDatabaseHelper(getApplicationContext());
		db.initDAOs();
		srv = new LocalBingoServer(BingoDatabaseHelper.serverDao, BingoDatabaseHelper.clientDao, BingoDatabaseHelper.gamePlayedDao);
	}
}
