/**
 * 
 */
package com.hd.phim.Utility;

import com.movie.hdonline.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;



/**
 * @author tuanhd
 *
 */
public class DialogChangeSubStyle extends Dialog {

	public DialogChangeSubStyle(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_change_sub);
	}

}
