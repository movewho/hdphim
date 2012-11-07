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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
public class Search extends BaseFragment implements OnItemClickListener, OnClickListener{
	private View mContentView;
	private ListView mListSearch;
	private List<NameValuePair> mListParams;
	private ListAdaperReview listAdapter;
	private Button mBtnDrama;
	private Button mBtnMovieTheater;
	private Button mBtnMovies;
	private ProgressBar mProgressUpdate;
	private ImageButton mImgBtnSearch;
	private EditText mEditFilmName;
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
	private TextView mTxtListSearchData;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.search_film, null);
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
		mViewDetail = (ViewFlipper) mContentView.findViewById(R.id.view_search_film);
		mImgBtnSearch = (ImageButton) mContentView.findViewById(R.id.btn_search);
		mEditFilmName = (EditText) mContentView.findViewById(R.id.edit_filmname);
		mListSearch = (ListView) mContentView.findViewById(R.id.list_search_film);
		mBtnMovies = (Button) mContentView.findViewById(R.id.btn_movies);
		mBtnDrama = (Button) mContentView.findViewById(R.id.btn_drama);
		mBtnMovieTheater = (Button) mContentView.findViewById(R.id.btn_movie_theaters);
		mProgressUpdate = (ProgressBar) mContentView.findViewById(R.id.search_progress_update);
		mTxtListSearchData = (TextView) mContentView.findViewById(R.id.txt_list_search_not_data);
		mTxtListSearchData.setVisibility(View.GONE);
		
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
		
		String[] listLink = getResources().getStringArray(R.array.link_search_movies);
		if(null == listAdapter){
			url = listLink[0];
		loadListFilm(listLink[0],mListParams);
		}else{
			mListSearch.setAdapter(listAdapter);
		}
		
		mListSearch.setOnItemClickListener(this);
		mBtnMovies.setOnClickListener(this);
		mBtnMovies.setTag(listLink[0]);
		mBtnDrama.setOnClickListener(this);
		mBtnDrama.setTag(listLink[1]);
		mBtnMovieTheater.setOnClickListener(this);
		mBtnMovieTheater.setTag(listLink[2]);
		mProgressUpdate.setVisibility(View.INVISIBLE);
		mProgressDetail.setVisibility(View.INVISIBLE);
		mBtnLike.setOnClickListener(this);
		mListDetail.setOnItemClickListener(this);
		mBtnBack.setOnClickListener(this);
		mImgBtnSearch.setOnClickListener(this);
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
				mProgressUpdate.setVisibility(View.INVISIBLE);
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
	mTxtListSearchData.setVisibility(View.GONE);
	mTxtListData.setVisibility(View.GONE);
	if(mViewDetail.getDisplayedChild() == 0){
	listAdapter = new ListAdaperReview(mContext,0, listJson, true);
	mListSearch.setBackgroundColor(Color.BLUE);
	mListSearch.setAdapter(listAdapter);
	}else if(mViewDetail.getDisplayedChild() == 1){
		mAdapterDetail = new ListAdaperReview(mContext, 0, listJson,false);
		mListDetail.setBackgroundColor(Color.BLUE);
		mListDetail.setAdapter(mAdapterDetail);
	}
}

@Override
public void onClick(View v) {
	if(v == mBtnLike){
		
	}else if(v == mBtnBack){
		mViewDetail.setInAnimation(getActivity(),R.anim.fade_in_left);
		mViewDetail.setOutAnimation(getActivity(),R.anim.fade_out_left);
		mViewDetail.showPrevious();
	}else if(v == mImgBtnSearch){
		if(mEditFilmName.getText().length() <= 0){
			mEditFilmName.setError(getString(R.string.edit_empty));
		}
		url = getString(R.string.link_search_movies) + mEditFilmName.getText().toString().trim().replace(" ", "%20")+".html";
		loadListFilm(url,mListParams);
	}else{
		url = v.getTag().toString();
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
	mTxtListSearchData.setVisibility(View.VISIBLE);
	mTxtListData.setVisibility(View.VISIBLE);
	Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	if(mViewDetail.getDisplayedChild() == 0){
	mItemFilm = listAdapter.getItem(position);
	}else {
		mItemFilm = mAdapterDetail.getItem(position);
	}
	showDetail(mItemFilm);
}
private void showDetail(JSONObject data){
	try {
		ArrayList<NameValuePair> mListRelated = new ArrayList<NameValuePair>();
		mListRelated.add(new BasicNameValuePair("format", "json"));
		mListRelated.add(new BasicNameValuePair("page", "1"));
		mSmartImgDetail.setImageUrl(data.getString("IMG"));
		mTxtTitleDetail.setText(data.getString("TITLE"));
		mTxtCountDetail.setText(ConverDecimalToPercent.converDecimalToPercent(data.getString("IMDB"))+"% "+data.getString("VIEWED")+" "+mContext.getString(R.string.count));
		mTxtTimeDetail.setText(data.getString("TIME")+" "+data.getString("UPDATE"));
		mTxtTitleFilm.setText(data.getString("NAME"));
		mListRelated.add(new BasicNameValuePair("bycat", data.getString("CAT")));
		loadListFilm(url,mListRelated);
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