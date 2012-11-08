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

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hd.phim.Outstanding.LoadData;
import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.Utility.ConverDecimalToPercent;
import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.network.GetDataJsonFromServer;
import com.loopj.android.image.SmartImageView;
import com.movie.hdonline.R;

/**
 * @author hdtua_000
 *
 */
public class Favorite extends BaseFragment implements OnItemClickListener, OnClickListener {

	private View mContentView;
	private ListView mListOutstanding;
	private ListAdaperReview mAdapter;
	private List<NameValuePair> mListParams;
	
	private JSONObject mItemFilm;
	private ViewFlipper mViewDetail;
	private ListView mListDetail;
	private SmartImageView mSmartImgDetail;
	private TextView mTxtTitleDetail;
	private TextView mTxtCountDetail;
	private TextView mTxtTimeDetail;
	private Button mBtnLike;
	private ListAdaperReview mAdapterDetail;
	private Button mBtnBack;
	private TextView mTxtTitleFilm;
	private String url;
	private ProgressBar mProgressDetail;
	private TextView mTxtListData;
	private View footerListSearch;
	private View footerListDetail;
	private static int countListSearch;
	private static int countListDetail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.outstanding, null);

        return mContentView;
	}

	@Override
	protected void initVariables() {
		mListParams =  new ArrayList<NameValuePair>();
		mListParams.add(new BasicNameValuePair("format", "json"));
		mListParams.add(new BasicNameValuePair("page", "1"));
	}

	@Override
	protected void initControls() {
		mListOutstanding = (ListView) mContentView.findViewById(R.id.outstanding_listView);
		mViewDetail = (ViewFlipper) mContentView.findViewById(R.id.view_outstanding);
		mProgressDetail = (ProgressBar) mContentView.findViewById(R.id.detail_progress_update);
		mSmartImgDetail = (SmartImageView) mContentView.findViewById(R.id.image_movie_detail);
		mListDetail = (ListView) mContentView.findViewById(R.id.list_film_related);
		mTxtTitleDetail = (TextView) mContentView.findViewById(R.id.txt_title_detail);
		mTxtCountDetail = (TextView) mContentView.findViewById(R.id.txt_count_view_detail);
		mTxtTimeDetail = (TextView) mContentView.findViewById(R.id.text_time_detail);
		mBtnLike = (Button) mContentView.findViewById(R.id.btn_like);
		mBtnBack = (Button) mContentView.findViewById(R.id.btn_detail_back);
		mTxtTitleFilm = (TextView) mContentView.findViewById(R.id.title_detail_film);
		mTxtListData = (TextView) mContentView.findViewById(R.id.txt_not_data);
		mTxtListData.setVisibility(View.GONE);
		mTxtTitleFilm.setSelected(true);
		
		
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		if(null == mAdapter){
			countListSearch = 1;
			countListDetail = 1;
			url = listLink[5];
		loadListFilm(url,mListParams);
		}else{
			mListOutstanding.setAdapter(mAdapter);
		}
		footerListSearch = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list, null, false);
		footerListDetail = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list, null, false);
		mListDetail.addFooterView(footerListDetail);
		mListOutstanding.addFooterView(footerListSearch);
		mListOutstanding.setOnItemClickListener(this);
		mListDetail.setOnItemClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnLike.setOnClickListener(this);
	}
	private void loadListFilm(String url, List<NameValuePair> listParams){
		if (CheckConnectInternet
				.checkInternetConnection(getActivity().getApplicationContext())){
		LoadData loadData = new LoadData(listParams);
		loadData.execute(url);
		}else{
			showToast(getString(R.string.not_connect_internet));
		}
	}
	class LoadData extends AsyncTask<String, Boolean, JSONArray>{

		private JSONArray jsonArray;
		private List<NameValuePair> listParams;
		public LoadData(List<NameValuePair> listParams){
			this.listParams = listParams;
		}
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
			mProgressDetail.setVisibility(View.VISIBLE);
			}else{
				mProgressDetail.setVisibility(View.INVISIBLE);
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
		mTxtListData.setVisibility(View.GONE);
		if(mViewDetail.getDisplayedChild() == 0){
			if(null == mAdapter){
				mAdapter = new ListAdaperReview(mContext,0, listJson,false);
				mListOutstanding.setBackgroundColor(Color.BLUE);
				mListOutstanding.setAdapter(mAdapter);
				}else{
					int n = listJson.size();
					for (int i=0; i<n; i++){
						mAdapter.add(listJson.get(i));
					}
					mAdapter.notifyDataSetChanged();
				}
				}else if(mViewDetail.getDisplayedChild() == 1){
					if(null == mAdapterDetail){
					mAdapterDetail = new ListAdaperReview(mContext, 0, listJson,false);
					mListDetail.setBackgroundColor(Color.BLUE);
					mListDetail.setAdapter(mAdapterDetail);
					}else{
						int n = listJson.size();
						for (int i=0; i<n; i++){
							mAdapterDetail.add(listJson.get(i));
						}
						mAdapterDetail.notifyDataSetChanged();
					}
				}
	}
	private void showToast(String message){
		mTxtListData.setVisibility(View.VISIBLE);
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(mViewDetail.getDisplayedChild() == 0){
			if(view == footerListSearch){
				countListSearch++;
				onCreateParams(url, countListSearch);
			}else{
				mItemFilm = mAdapter.getItem(position);
				showDetail(mItemFilm);
				onCreateParams(url, countListDetail);
			}
		}else if(view == footerListDetail){
				countListDetail++;
				onCreateParams(url, countListDetail);
			}else{
			mItemFilm = mAdapterDetail.getItem(position);
			showDetail(mItemFilm);
			}
	}
	private void onCreateParams(String url, int page){
		ArrayList<NameValuePair> mListRelated = new ArrayList<NameValuePair>();
		mListRelated.add(new BasicNameValuePair("format", "json"));
		mListRelated.add(new BasicNameValuePair("page", ""+page));
		loadListFilm(url,mListRelated);
	}

	@Override
	public void onClick(View v) {
		if(v == mBtnLike){
			
		}else if(v == mBtnBack){
			mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_left);
			mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_left);
			mViewDetail.showPrevious();
		}
	}
	
	private void showDetail(JSONObject data){
		try {
			mSmartImgDetail.setImageUrl(data.getString("IMG"));
			mTxtTitleDetail.setText(data.getString("TITLE"));
			mTxtCountDetail.setText(ConverDecimalToPercent.converDecimalToPercent(data.getString("IMDB"))+"% "+data.getString("VIEWED")+" "+mContext.getString(R.string.count));
			mTxtTimeDetail.setText(data.getString("TIME")+" "+data.getString("UPDATE"));
			mTxtTitleFilm.setText(data.getString("NAME"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(mViewDetail.getDisplayedChild() == 0){
		mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_right);
		mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_right);
		mViewDetail.showNext();
		}
	}
}