/**
 * 
 */
package com.hd.phim;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.hd.phim.Utility.CallIntentPlayMovies;
import com.movie.hdonline.R;

/**
 * @author tuanhd
 * 
 */
public class PlayMovies extends Activity implements OnClickListener, OnCheckedChangeListener {
	private static final String TAG = "VideoViewDemo";
	private VideoView mVideoView;
	private ImageView mPlay;
	private JSONObject mItemMovies;
	private String path;
	private TextView mTxtTitleVideo;
	private TextView mTxtUpBy;
	private TextView mTxtCountViews;
	private TextView mTxtComments;
	private RadioButton mRbtnComment;
	private ViewFlipper mViewFlipper;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.play_movie);
		initVar();
		initView();
		initActions();
	}
	private void initVar(){
		try {
			mItemMovies = new JSONObject(getIntent().getStringExtra("OBJ"));
			path = mItemMovies.getString("URL");
		} catch (JSONException e) {
			Log.e("PlayMovies - get URL", e.toString());
		}
		path = "rtsp://s1.hdonline.vn/m1/mp4:phimle/2012/03/WarHorse.mp4";
	}
	private void initView(){
		mVideoView = (VideoView) this.findViewById(R.id.video_view);
		mPlay = (ImageView) this.findViewById(R.id.btn_play);
		mTxtTitleVideo = (TextView) this.findViewById(R.id.play_title);
		mTxtUpBy = (TextView) this.findViewById(R.id.play_txt_post_by);
		mTxtCountViews = (TextView) this.findViewById(R.id.play_count_views);
		mTxtComments = (TextView) this.findViewById(R.id.play_videos_comment);
		mRbtnComment = (RadioButton) this.findViewById(R.id.play_btn_comment);
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.play_viewfliper_comment);
	}
	private void initActions(){
		mPlay.setOnClickListener(this);
		try {
			mTxtTitleVideo.setText(mItemMovies.getString("TITLE"));
			mTxtCountViews.setText(mItemMovies.getString("VIEWED"));
			mTxtUpBy.setText(mItemMovies.getString("UPDATE"));
			mTxtComments.setText(mItemMovies.getString("ACTOR"));
		} catch (JSONException e) {
			Log.e("PlayMovies -- initActions", e.toString());
		}
		mRbtnComment.setOnCheckedChangeListener(this);
		playVideo();
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
		if(v == mPlay){
		CallIntentPlayMovies.play(path, this);
		this.finish();
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		if(arg1){
			mViewFlipper.setInAnimation(this,R.anim.fade_in_right);
			mViewFlipper.setOutAnimation(this,R.anim.fade_out_right);
			mViewFlipper.setDisplayedChild(1);
		}else{
			mViewFlipper.setInAnimation(this,R.anim.fade_in_left);
			mViewFlipper.setOutAnimation(this,R.anim.fade_out_left);
			mViewFlipper.setDisplayedChild(0);
		}
	}
}