/**
 * 
 */
package com.hd.phim.Utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.hd.phim.PlayMovies;

/**
 * @author nguyenquocchinh
 *
 */
public class CallIntentPlayMovies {

	public static void play(String url, Activity activity)
	{
//		Intent i = new Intent(activity, PlayMovies.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.putExtra("PATH", url);
//		activity.startActivity(i);
        String videoUrl = "rtsp://116.193.76.73/vod/mp4:sample.mp4";
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setData(Uri.parse(videoUrl));  
        activity.startActivity(i); 
	}
}
