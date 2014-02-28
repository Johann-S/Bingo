package com.jservoire.bingo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadAsyncTask extends AsyncTask<String,Integer,Boolean>
{
	private Context ctx;
	private NotificationManager notifManager;
	private android.support.v4.app.NotificationCompat.Builder notifBuilder;
	private int ID_NOTIF = 7;

	public DownloadAsyncTask(final Context _ctx)
	{
		ctx = _ctx;

		notifBuilder = new android.support.v4.app.NotificationCompat.Builder(ctx);
		notifBuilder.setContentTitle("Archive Download")
		.setContentText("Download in progress")
		.setSmallIcon(R.drawable.icon58)
		.setProgress(0, 0, true);

		notifManager = (NotificationManager)ctx.getSystemService("notification");
		notifManager.notify(ID_NOTIF, notifBuilder.build());
	}


	private void createDirectory(final String dir,final String location) 
	{ 
		File newDir = new File(location + dir); 
		if ( !newDir.isDirectory() ) { 
			newDir.mkdirs(); 
		} 
	}

	@Override
	protected Boolean doInBackground(final String... params) 
	{
		int count = 0;
		String urlZip = params[0];
		String pathArchive = params[1];
		String path = params[2];
		try 
		{
			URL urlObj = new URL(urlZip);
			URLConnection connection = urlObj.openConnection();
			connection.connect();

			int lenghtOfFile = connection.getContentLength();
			Log.d("Download information", "Lenght of file: " + lenghtOfFile);

			InputStream input = new BufferedInputStream(urlObj.openStream());
			OutputStream output = new FileOutputStream(pathArchive);

			byte data[] = new byte[1024];
			long total = 0;
			while ( (count = input.read(data)) != -1 ) 
			{
				total += count;
				publishProgress((int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
			unZipArchive(pathArchive,path);
		} 
		catch (MalformedURLException e) {
			Log.e("inputURL",e.getLocalizedMessage());
		} 
		catch (IOException e) {
			Log.e("URLConnection",e.getLocalizedMessage());
		}
		return Boolean.TRUE;
	}


	@Override
	protected void onPostExecute(final Boolean result) 
	{
		notifBuilder.setContentText("Download and unzipping complete").setProgress(0, 0, false);
		notifManager.notify(ID_NOTIF, notifBuilder.build());
	}

	@Override
	public void onProgressUpdate(final Integer... tabProgress)
	{
		notifBuilder.setProgress(100, tabProgress[0].intValue(), false);
		notifManager.notify(ID_NOTIF, notifBuilder.build());
	}

	private void unZipArchive(final String pathArchive,final String path)
	{
		try 
		{
			notifBuilder.setContentText("Unzipping in progress...").setProgress(0, 0, true);
			notifManager.notify(ID_NOTIF, notifBuilder.build());
			FileInputStream inputZip = new FileInputStream(pathArchive);
			ZipInputStream zipInput = new ZipInputStream(inputZip);
			ZipEntry entryZip = null;

			while ( (entryZip = zipInput.getNextEntry()) != null ) 
			{ 				
				if ( !entryZip.isDirectory() )
				{
					Log.d("Decompress", "Unzipping " + entryZip.getName());
					FileOutputStream fout = new FileOutputStream(path + entryZip.getName());
					int buffer = 0;
					while ( (buffer = zipInput.read()) != -1 ) {
						fout.write(buffer); 
					}

					zipInput.closeEntry(); 
					fout.close(); 
				}
				else {
					createDirectory(entryZip.getName(), path);
				}
			}
		} 
		catch (FileNotFoundException e) {
			Log.e("Error unzip",e.getLocalizedMessage());
		} 
		catch (IOException e) {
			Log.e("Error unzip",e.getLocalizedMessage());
		}
	} 
}
