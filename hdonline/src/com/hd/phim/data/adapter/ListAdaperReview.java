/**
 * 
 */
package com.hd.phim.data.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.loopj.android.image.SmartImageView;
import com.movie.hdonline.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author nguyenquocchinh
 *
 */
public class ListAdaperReview extends BaseAdapter{

	private LayoutInflater layout;
	private ArrayList<JSONObject> mJson;
	private Context mContext;
	public ListAdaperReview(Context context, ArrayList<JSONObject> json) {

		this.mContext = context;
		this.mJson = json;
		this.layout = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder;
		if(convertView == null)
		{
			holder = new Holder();
			convertView = layout.inflate(R.layout.item_movie, null);
			
			holder.title = (TextView) convertView.findViewById(R.id.txt_title);
			holder.viewCount = (TextView) convertView.findViewById(R.id.txt_count_view);
			holder.time = (TextView) convertView.findViewById(R.id.txt_title);
			
			convertView.setTag(holder);
		}
		else {
			holder = (Holder) convertView.getTag();
		}
		
		try {
			holder.title.setText(mJson.get(position).getString("TITLE"));
			holder.viewCount.setText(mJson.get(position).getString("VIEWED"));
			holder.time.setText(mJson.get(position).getString("TIME"));
			holder.thumbnai.setImageUrl(mJson.get(position).getString("IMG"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
		return convertView;
	}

	static class Holder
	{
		TextView title;
		TextView viewCount;
		TextView time;
		SmartImageView thumbnai;
	}
}
