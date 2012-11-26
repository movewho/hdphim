/**
 * 
 */
package com.hd.phim;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdapterMore;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class More extends BaseFragment implements OnItemClickListener, OnClickListener{
	
	private View mContentView;
	private ListView mListView;
	private Button mBtnLogin;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.list_more, null);

        return mContentView;
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initView() {
		mListView = (ListView) mContentView.findViewById(R.id.listViewMore);
		mBtnLogin = (Button) mContentView.findViewById(R.id.list_more_btn_login);
	}
	@Override
	protected void initActions() {
		final ListAdapterMore listAdapter = new ListAdapterMore(getActivity().getApplicationContext(), R.array.titleArrayRes, R.array.urlsArrayRes, R.array.iconsArrayRes);
		mListView.setCacheColorHint(Color.TRANSPARENT);
        mListView.requestFocus(0);
		mListView.setAdapter(listAdapter);
		mListView.setOnItemClickListener(this);
		mBtnLogin.setOnClickListener(this);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if(position == 5){
			getActivity().finish();
		}
	}

	@Override
	public void onClick(View v) {
		if(v == mBtnLogin){
			Intent i = new Intent(getActivity(), Login.class);
			startActivity(i);
			getActivity().finish();
		}
	}

	


	
}
