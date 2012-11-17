/**
 * 
 */
package com.hd.phim;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.movie.hdonline.R;

/**
 * @author tuanhd
 * 
 */
public class PlayMovies extends Activity implements OnClickListener {
	private static final String TAG = "VideoViewDemo";
	private VideoView mVideoView;
	private ImageView mPlay;
	private ImageView mReset;
	private ImageView mStop;
	private String path;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.play_movie);
		initVar();
		initView();
		initActions();
	}
	private void initVar(){
		path = this.getIntent().getStringExtra("PATH");
		Log.e("path", path);
		path = "rtsp://116.193.76.73/vod/mp4:sample.mp4";
	}
	private void initView(){
		mVideoView = (VideoView) this.findViewById(R.id.video_view);
		mPlay = (ImageView) this.findViewById(R.id.play);
		mReset = (ImageView) findViewById(R.id.reset);
		mStop = (ImageView) findViewById(R.id.stop);
	}
	private void initActions(){
		mPlay.setOnClickListener(this);
		mReset.setOnClickListener(this);
		mStop.setOnClickListener(this);
	}

	private void playVideo() {
		try {
			if (path == null || path.length() == 0) {
				Toast.makeText(PlayMovies.this, "File URL/path is empty",
						Toast.LENGTH_LONG).show();
			} else {

				System.out.println("else ");
				// If the path has not changed, just start the media player
				if (path.equals("") && mVideoView != null) {
					System.out.println("mVideoView.start() ");

					mVideoView.start();
					mVideoView.requestFocus();
					return;
				}
				mVideoView.setVideoURI(Uri.parse(path));
				mVideoView.start();
				mVideoView.requestFocus();
			}
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
			if (mVideoView != null) {
				mVideoView.stopPlayback();
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.play:
			if(mVideoView.isPlaying()){
				mPlay.setSelected(false);
				mVideoView.pause();
			}else{
				mPlay.setSelected(true);
			playVideo();
			}
			break;
		case R.id.stop:
			mVideoView.stopPlayback();
			mPlay.setSelected(false);
			break;
		case R.id.reset:
			mVideoView.seekTo(0);
			break;
		}
	}
}