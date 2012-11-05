/**
 * 
 */
package com.hd.phim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hd.phim.custome.BaseFragment;
import com.hd.phim.data.adapter.ListAdapterMore;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class More extends BaseFragment{
	
	private View mContentView;
	private ListView mListView;
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
	protected void initControls() {
		mListView = (ListView) mContentView.findViewById(R.id.listViewMore);
		final ListAdapterMore listAdapter = new ListAdapterMore(getActivity().getApplicationContext(), R.array.titleArrayRes, R.array.urlsArrayRes, R.array.iconsArrayRes);
		mListView.setAdapter(listAdapter);
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}


	
}
