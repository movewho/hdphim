/**
 * 
 */
package com.hd.phim.custome;

/**
 * @author nguyenquocchinh
 *
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected View mParent;
	protected static Context mContext;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initActions();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = this.getActivity().getApplicationContext();
		initVariables();
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putString("FIX", "");
		super.onSaveInstanceState(outState);
	}
	
	protected abstract void initVariables();
	
	protected abstract void initView();
	
	protected abstract void initActions();
		
}
