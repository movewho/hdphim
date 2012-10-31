/**
 * 
 */
package com.hd.phim.custome;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author nguyenquocchinh
 *
 */
public class CustomeListView extends ListView{

	public CustomeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomeListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
