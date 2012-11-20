/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.hd.phim.network.GetDataJsonFromServer;
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
	private ImageView mImgLike;
	private ImageView mImgAdd;
	private ArrayList<NameValuePair> listParams;

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
		mImgLike = (ImageView) this.findViewById(R.id.btn_like);
		mImgAdd = (ImageView) this.findViewById(R.id.btn_add);
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
		mImgLike.setOnClickListener(this);
		mImgAdd.setOnClickListener(this);
		playVideo();
	}

	private void playVideo() {
		try {
			if (path == null || path.length() == 0) {
				Toast.makeText(PlayMovies.this, "File URL/path is empty",
						Toast.LENGTH_LONG).show();
			} else {
				mVideoView.setVideoURI(Uri.parse(path));
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
		}else if(v == mImgLike){
			listParams =  new ArrayList<NameValuePair>();
			listParams.add(new BasicNameValuePair("like_movie", "true"));
			try {
				listParams.add(new BasicNameValuePair("film_id", mItemMovies.getString("ID")));
			} catch (JSONException e) {
				Log.e("PlayMovie--onclick--like", e.toString());
			}
			listParams.add(new BasicNameValuePair("value", ""+1));
				ConnectServer connect = new ConnectServer(listParams);
				connect.execute(getString(R.string.link_like_movie));
			}
		else if(v == mImgAdd){
			listParams =  new ArrayList<NameValuePair>();
			listParams.add(new BasicNameValuePair("reloadYF", "true"));
			try {
				listParams.add(new BasicNameValuePair("add_id", mItemMovies.getString("ID")));
			} catch (JSONException e) {
				Log.e("PlayMovie--onclick--like", e.toString());
			}
				ConnectServer connect = new ConnectServer(listParams);
				connect.execute(getString(R.string.link_like_movie));
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
	class ConnectServer extends AsyncTask<String, Void, JSONObject>{
		private List<NameValuePair> listParams;
		private JSONObject movie;
		private ProgressDialog mPrgDialog;
		
		public ConnectServer(List<NameValuePair> listparams){
			this.listParams = listparams;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mPrgDialog = new ProgressDialog(PlayMovies.this);
			mPrgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mPrgDialog.setMessage(getString(R.string.connecting));
		}
		@Override
		protected JSONObject doInBackground(String... params) {
			publishProgress();
			try {
				movie = GetDataJsonFromServer.postJSONfromURL(params[0], listParams, 80, "HD Online version for Android");
			} catch (SSLPeerUnverifiedException e) {
				Log.e("PlayMovie-Asyn-doinBackground", e.toString());
			}
			return movie;
		}
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			mPrgDialog.show();
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			mPrgDialog.dismiss();
			try {
				showToast(result.getString("message"));
			} catch (JSONException e) {
				Log.e("JSONobject", result.toString());
				Log.e("PlayMovie-Asyn-postExecute", e.toString());
			}
		}
		
	}
	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}
}