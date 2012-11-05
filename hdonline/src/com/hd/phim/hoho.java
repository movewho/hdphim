package com.hd.phim;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hd.phim.ReviewMore.LoadData;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.network.GetDataJsonFromServer;
import com.movie.hdonline.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class hoho extends Activity{
	private View mContentView;
	private ListView mListView;
	private ArrayList<JSONObject> jsonData;
	private List<NameValuePair> listParams;
	private ListAdaperReview listAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_review_more);
		
		jsonData = new ArrayList<JSONObject>();
		listParams =  new ArrayList<NameValuePair>();
		listParams.add(new BasicNameValuePair("format", "json"));
		listParams.add(new BasicNameValuePair("page", "1"));
		
		mListView = (ListView)findViewById(R.id.list_review_count);
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		LoadData loadData = new LoadData();
		loadData.execute(new String[]{listLink[1]});
		
		
		listAdapter = new ListAdaperReview(hoho.this.getApplicationContext(),0, jsonData);
		mListView.setBackgroundColor(Color.BLUE);
		
		mListView.setAdapter(listAdapter);

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
			try {
				for (int i = 0; i < len; i++) {
					
						jsonData.add(i, result.getJSONObject(i));
	
				}


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
	}
}
