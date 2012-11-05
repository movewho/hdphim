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
import android.widget.Button;
import android.widget.ListView;

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
	private Button mBtnPreWeek;
	private Button mBtnPreDay;
	private Button mBtnPreAll;
	
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
		mBtnPreAll = (Button) mContentView.findViewById(R.id.button_pre_all);
		mBtnPreWeek = (Button) mContentView.findViewById(R.id.button_pre_week);
		mBtnPreDay = (Button) mContentView.findViewById(R.id.button_pre_day);
		
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		if(null == listAdapter){
		loadListFilm(listLink[1]);
		}else{
			mListView.setAdapter(listAdapter);
		}
		
		mBtnPreAll.setOnClickListener(this);
		mBtnPreAll.setTag(listLink[1]);
		mBtnPreAll.setPressed(true);
		mBtnPreWeek.setOnClickListener(this);
		mBtnPreWeek.setTag(listLink[3]);
		mBtnPreDay.setOnClickListener(this);
		mBtnPreDay.setTag(listLink[2]);
	}

	class LoadData extends AsyncTask<String, Void, JSONArray>{

		private JSONArray jsonArray;
		@Override
		protected JSONArray doInBackground(String... params) {
			
			for (String param : params) {
				
				try {
					jsonArray = GetDataJsonFromServer.getJSONfromURL(param, listParams, 80, "HD Online version for Android");
				} catch (SSLPeerUnverifiedException e) {
					e.printStackTrace();
				}
			}
			return jsonArray;
		}

		@Override
		protected void onPostExecute(JSONArray result) {
			super.onPostExecute(result);
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
		}
	}
private void updateListView(ArrayList<JSONObject> listJson){
	listAdapter = new ListAdaperReview(mContext,0, listJson);
	mListView.setBackgroundColor(Color.BLUE);
	mListView.setAdapter(listAdapter);
}

@Override
public void onClick(View v) {
	if(v == mBtnPreAll){
		mBtnPreAll.setPressed(true);
		mBtnPreDay.setClickable(true);
		mBtnPreWeek.setClickable(true);
		mBtnPreAll.setClickable(false);
	}else if(v == mBtnPreWeek){
		mBtnPreWeek.setPressed(true);
		mBtnPreDay.setClickable(true);
		mBtnPreWeek.setClickable(false);
		mBtnPreAll.setClickable(true);
	}else if(v == mBtnPreDay){
		mBtnPreDay.setPressed(true);
		mBtnPreDay.setClickable(false);
		mBtnPreWeek.setClickable(true);
		mBtnPreAll.setClickable(true);
	}
	loadListFilm(v.getTag().toString());
}

private void loadListFilm(String url){
	LoadData loadData = new LoadData();
	loadData.execute(url);
}
}
