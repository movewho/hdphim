package com.hd.phim.Utility;

/**
 * 
 */

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.movie.hdonline.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * @author chinhnq
 *
 */
public class CheckConnectInternet {
	
	public static boolean checkInternetConnection(Context context) {
		boolean isOk = false;
		ConnectivityManager conMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (conMgr.getActiveNetworkInfo() != null

		&& conMgr.getActiveNetworkInfo().isAvailable()

		&& conMgr.getActiveNetworkInfo().isConnected()) {
			if(checkConnectServer(context))
			isOk = true;

		}
		return isOk;
	}
	private static boolean checkConnectServer(Context context){
		boolean isOk = true;
		  try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpGet = new HttpPost(context.getResources().getString(R.string.base_url));
            HttpResponse response = httpclient.execute(httpGet);
            Log.w("da vao day", ""+response.getStatusLine().getStatusCode());
               if (response.getStatusLine().getStatusCode() >= 400) {
            	   Log.w("fhkshfdhsfhdjk", "sdhfksdhjkgkjds");
               isOk = false;
               }
            } catch (Exception e) {}
		  return isOk;
	}
}
