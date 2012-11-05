/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class ReviewMore extends BaseFragment implements OnClickListener{
	
	private View mContentView;
	private ListView mListView;
	private List<NameValuePair> listParams;
	private ListAdaperReview listAdapter;
	private RadioButton mBtnPreWeek;
	private RadioButton mBtnPreDay;
	private RadioButton mBtnPreAll;
	private ProgressBar mProgressUpdate;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.list_review_more, null);
        return mContentView;
	}

	@Override
	protected void initVariables() {
		
		
		
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("format", "json"));
		listParams.add(new BasicNameValuePair("page", "1"));
	}
	@Override
	protected void initControls() {
		mListView = (ListView) mContentView.findViewById(R.id.list_review_count);
		mBtnPreAll = (RadioButton) mContentView.findViewById(R.id.button_pre_all);
		mBtnPreWeek = (RadioButton) mContentView.findViewById(R.id.button_pre_week);
		mBtnPreDay = (RadioButton) mContentView.findViewById(R.id.button_pre_day);
		mProgressUpdate = (ProgressBar) mContentView.findViewById(R.id.review_more_progress_update);
		
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		if(null == listAdapter){
		loadListFilm(listLink[1]);
		}else{
			mListView.setAdapter(listAdapter);
		}
		
		mBtnPreAll.setOnClickListener(this);
		mBtnPreAll.setTag(listLink[1]);
		mBtnPreAll.setChecked(true);
		mBtnPreWeek.setOnClickListener(this);
		mBtnPreWeek.setTag(listLink[3]);
		mBtnPreDay.setOnClickListener(this);
		mBtnPreDay.setTag(listLink[2]);
		mProgressUpdate.setVisibility(View.GONE);
	}

	class LoadData extends AsyncTask<String, Boolean, JSONArray>{

		private JSONArray jsonArray;
		@Override
		protected JSONArray doInBackground(String... params) {
			publishProgress(true);
			for (String param : params) {
				
				try {
					jsonArray = GetDataJsonFromServer.getJSONfromURL(param, listParams, 80, "HD Online version for Android");
				} catch (SSLPeerUnverifiedException e) {
					e.printStackTrace();
				}
			}
			publishProgress(false);
			return jsonArray;
		}
		@Override
		protected void onProgressUpdate(Boolean... values) {
			super.onProgressUpdate(values);
			if(values[0]){
			mProgressUpdate.setVisibility(View.VISIBLE);
			}else{
				mProgressUpdate.setVisibility(View.GONE);
			}
		}
		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
			if(null != result){
			int len = result.length();
			ArrayList<JSONObject> jsonData = new ArrayList<JSONObject>();
			try {
				for (int i = 0; i < len; i++) {
						jsonData.add(i, result.getJSONObject(i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			updateListView(jsonData);
			}else{
				showToast(getString(R.string.not_json_data));
			}
		}
	}
private void updateListView(ArrayList<JSONObject> listJson){
	listAdapter = new ListAdaperReview(mContext,0, listJson);
	mListView.setBackgroundColor(Color.BLUE);
	mListView.setAdapter(listAdapter);
}

@Override
public void onClick(View v) {
	loadListFilm(v.getTag().toString());
}

private void loadListFilm(String url){
	if (CheckConnectInternet
			.checkInternetConnection(getActivity().getApplicationContext())){
	LoadData loadData = new LoadData();
	loadData.execute(url);
	}else{
		showToast(getString(R.string.not_connect_internet));
	}
}
private void showToast(String message){
	Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
}
}
