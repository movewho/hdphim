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
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

/**
 * @author hdtua_000
 *
 */
public class Favorite extends BaseFragment {

	private View mContentView;
	private ListView mListOutstanding;
	private ListAdaperReview mAdapter;
	private List<NameValuePair> listParams;
	private TextView mTxtTitle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.list_view_more, null);

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
		mTxtTitle = (TextView) mContentView.findViewById(R.id.title);
		mTxtTitle.setText(getString(R.string.title_muc_ua_thich));
		mListOutstanding = (ListView) mContentView.findViewById(R.id.outstanding_listView);
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		if(null == mAdapter){
		loadListFilm(listLink[5]);
		}else{
			mListOutstanding.setAdapter(mAdapter);
		}
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
		mAdapter = new ListAdaperReview(mContext,0, listJson);
		mListOutstanding.setBackgroundColor(Color.BLUE);
		mListOutstanding.setAdapter(mAdapter);
	}
	private void showToast(String message){
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}