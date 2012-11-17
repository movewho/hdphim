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
import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hd.phim.Utility.CallIntentPlayMovies;
import com.hd.phim.Utility.CheckConnectInternet;
import com.hd.phim.Utility.ConverDecimalToPercent;
import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdaperReview;
import com.hd.phim.data.adapter.ListAdaperReview.OnPlayClickListener;
import com.hd.phim.network.GetDataJsonFromServer;
import com.loopj.android.image.SmartImageView;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class ReviewMore extends BaseFragment implements OnClickListener, OnItemClickListener, OnCheckedChangeListener, OnPlayClickListener{
	
	private View mContentView;
	private ListView mListView;
	private List<NameValuePair> mListParams;
	private ListAdaperReview listAdapter;
	private RadioButton mBtnPreWeek;
	private RadioButton mBtnPreDay;
	private RadioButton mBtnPreAll;
	private ProgressBar mProgressUpdate;
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
	private RadioButton mRdbInfo;
	private TextView mTxtTitleInfo;
	private TextView mTxtContent;
	private Button mBtnBackInfo;
	private RelativeLayout mViewDetailMovie;
	private ImageView mBtnDetailPlay;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.review_more, null);
        return mContentView;
	}

	@Override
	protected void initVariables() {
		mListParams =  new ArrayList<NameValuePair>();
		mListParams.add(new BasicNameValuePair("format", "json"));
		mListParams.add(new BasicNameValuePair("page", "1"));
	}
	@Override
	protected void initView() {
		mViewDetail = (ViewFlipper) mContentView.findViewById(R.id.view_detail_film);
		mListView = (ListView) mContentView.findViewById(R.id.list_review_count);
		mBtnPreAll = (RadioButton) mContentView.findViewById(R.id.button_pre_all);
		mBtnPreWeek = (RadioButton) mContentView.findViewById(R.id.button_pre_week);
		mBtnPreDay = (RadioButton) mContentView.findViewById(R.id.button_pre_day);
		mProgressUpdate = (ProgressBar) mContentView.findViewById(R.id.review_more_progress_update);
		mProgressDetail = (ProgressBar) mContentView.findViewById(R.id.detail_progress_update);
		mViewDetailMovie = (RelativeLayout) mContentView.findViewById(R.id.view_detail_movie);
		mSmartImgDetail = (SmartImageView) mContentView.findViewById(R.id.image_movie_detail);
		mListDetail = (ListView) mContentView.findViewById(R.id.list_film_related);
		mTxtTitleDetail = (TextView) mContentView.findViewById(R.id.txt_title_detail);
		mTxtCountDetail = (TextView) mContentView.findViewById(R.id.txt_count_view_detail);
		mTxtTimeDetail = (TextView) mContentView.findViewById(R.id.text_time_detail);
		mBtnLike = (Button) mContentView.findViewById(R.id.btn_like);
		mBtnBack = (Button) mContentView.findViewById(R.id.btn_detail_back);
		mTxtTitleFilm = (TextView) mContentView.findViewById(R.id.title_detail_film);
		mTxtListData = (TextView) mContentView.findViewById(R.id.txt_not_data);
		mRdbInfo = (RadioButton) mContentView.findViewById(R.id.btn_info);
		mTxtTitleInfo = (TextView) mContentView.findViewById(R.id.title_info_film);
		mTxtContent = (TextView) mContentView.findViewById(R.id.txt_info);
		mBtnBackInfo = (Button) mContentView.findViewById(R.id.btn_info_back);
		footerListSearch = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list, null, false);
		footerListDetail = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_list, null, false);
		mBtnDetailPlay = (ImageView) mContentView.findViewById(R.id.btn_detail_play);
	}
	@Override
	protected void initActions() {
		mListDetail.addFooterView(footerListDetail);
		mListView.addFooterView(footerListSearch);
		mListView.setOnItemClickListener(this);
		mBtnPreAll.setOnClickListener(this);
		mProgressUpdate.setVisibility(View.GONE);
		mBtnLike.setOnClickListener(this);
		mListDetail.setOnItemClickListener(this);
		mBtnBack.setOnClickListener(this);
		mTxtListData.setVisibility(View.GONE);
		mBtnBackInfo.setOnClickListener(this);
		mRdbInfo.setChecked(true);
		mRdbInfo.setOnCheckedChangeListener(this);
		mTxtTitleFilm.setSelected(true);
		mViewDetailMovie.setOnClickListener(this);
		mTxtTitleInfo.setSelected(true);
		String[] listLink = getResources().getStringArray(R.array.link_top_movies);
		if(null == listAdapter){
			countListSearch = 1;
			countListDetail = 1;
			url = listLink[1];
		loadListFilm(listLink[1],mListParams);
		}else{
			mListView.setAdapter(listAdapter);
		}
		mBtnPreAll.setTag(listLink[1]);
		mBtnPreWeek.setOnClickListener(this);
		mBtnPreWeek.setTag(listLink[3]);
		mBtnPreDay.setOnClickListener(this);
		mBtnPreDay.setTag(listLink[2]);
		mBtnDetailPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					onPlayClickListener(mItemFilm.getString("URL"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
@Override
public void onResume() {
	super.onResume();
	mBtnPreAll.setChecked(true);
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
			mProgressUpdate.setVisibility(View.VISIBLE);
			mProgressDetail.setVisibility(View.VISIBLE);
			}else{
				mProgressUpdate.setVisibility(View.GONE);
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
	mListView.setVisibility(View.VISIBLE);
	mListDetail.setVisibility(View.VISIBLE);
	if(mViewDetail.getDisplayedChild() == 0){
		if(null == listAdapter){
	listAdapter = new ListAdaperReview(mContext,0, listJson,false);
	listAdapter.setOnPlayClickListener(this);
	mListView.setBackgroundColor(Color.BLUE);
	mListView.setAdapter(listAdapter);
		}else{
			int n = listJson.size();
			for (int i=0; i<n; i++){
				listAdapter.add(listJson.get(i));
			}
			listAdapter.notifyDataSetChanged();
		}
	}else if(mViewDetail.getDisplayedChild() == 1){
		if(null == mAdapterDetail){
		mAdapterDetail = new ListAdaperReview(mContext, 0, listJson,false);
		mAdapterDetail.setOnPlayClickListener(this);
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

@Override
public void onClick(View v) {
	if(v == mBtnLike){
		
	}else if(v == mBtnBackInfo){
		mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_left);
		mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_left);
		mViewDetail.showPrevious();
	}else if(v == mViewDetailMovie){
		mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_right);
		mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_right);
		mRdbInfo.setSelected(true);
		try {
			showInfo(mItemFilm.getString("NAME"), mItemFilm.getString("DETAIL"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mViewDetail.setDisplayedChild(2);
	}else if(v == mBtnBack){
		mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_left);
		mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_left);
		mViewDetail.showPrevious();
	}else{
		url = v.getTag().toString();
		if(null != listAdapter)
		listAdapter.clear();
	loadListFilm(url,mListParams);
	}
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
private void showToast(String message){
	mTxtListData.setVisibility(View.VISIBLE);
	mListView.setVisibility(View.GONE);
	mListDetail.setVisibility(View.GONE);
	Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	if(mViewDetail.getDisplayedChild() == 0){
		if(view == footerListSearch){
			countListSearch++;
			onCreateParams(url, countListSearch);
		}else{
			mItemFilm = listAdapter.getItem(position);
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
@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	if(isChecked){
		try {
			showInfo(mItemFilm.getString("NAME"), mItemFilm.getString("DETAIL"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}else{
		try {
			showInfo(mItemFilm.getString("NAME"), mItemFilm.getString("ACTOR"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

private void showInfo(String title, String content){
		mTxtTitleInfo.setText(title);
		mTxtContent.setText(content);
}

@Override
public void onPlayClickListener(String url) {
	CallIntentPlayMovies.play(url, getActivity());
}
}
