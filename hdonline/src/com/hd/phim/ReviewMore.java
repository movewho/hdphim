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
public class ReviewMore extends BaseFragment{
	
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
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		LoadData loadData = new LoadData();
		loadData.execute(new String[]{listLink[1]});
		
		
	}

	class LoadData extends AsyncTask<String, Void, JSONArray>{

		private JSONArray jsonArray;
		@Override
		protected JSONArray doInBackground(String... params) {
			
			for (String param : params) {
				
				try {
					jsonArray = GetDataJsonFromServer.getJSONfromURL(param, listParams, 80, "HD Online version for Android");
				} catch (SSLPeerUnverifiedException e) {
					// TODO Auto-generated catch block
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
//				listAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
}
