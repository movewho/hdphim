/**
 * 
 */
package com.hd.phim.custome;

import com.movie.hdonline.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author nguyenquocchinh
 *
 */
public class CustomeTextView extends TextView{

	public CustomeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawableStateChanged() {
		if (isSelected()) {
			setTextColor(Color.WHITE);
			setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), Typeface.BOLD);
		}
		else {
			setTextColor(getResources().getColor(R.color.bfbfbf));
			setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL), Typeface.NORMAL);
		}
		super.drawableStateChanged();
	}

	
}
