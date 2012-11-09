/**
 * 
 */
package com.hd.phim.data.adapter;

import java.util.ArrayList;
import java.util.Formatter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hd.phim.Utility.ConverDecimalToPercent;
import com.loopj.android.image.SmartImageView;
import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class ListAdaperReview extends ArrayAdapter<JSONObject>{

	

	private LayoutInflater layout;
	private ArrayList<JSONObject> mJson;
	private Context mContext;
	private boolean seachFilm;
	
	public ListAdaperReview(Context context, int textViewResourceId,
			ArrayList<JSONObject> objects, boolean isSearch) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.mJson = objects;
		seachFilm = isSearch;
		this.layout = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			holder.time = (TextView) convertView.findViewById(R.id.text_time);
			holder.thumbnai = (SmartImageView) convertView.findViewById(R.id.image_movie);
			convertView.setTag(holder);
		}
		else {
			holder = (Holder) convertView.getTag();
		}
		
		try {
			holder.title.setText(mJson.get(position).getString("TITLE"));
			if(!seachFilm){
				String temp = " <font color=green><b>%s</b></font> %s";
				holder.viewCount.setText(Html.fromHtml(new Formatter().format(temp, ConverDecimalToPercent.converDecimalToPercent(mJson.get(position).getString("IMDB"))+"% ",mJson.get(position).getString("VIEWED")+" "+mContext.getString(R.string.count)).toString()));
				holder.time.setText(mJson.get(position).getString("TIME")+" "+mJson.get(position).getString("UPDATE"));
			}else{
				holder.viewCount.setText(mJson.get(position).getString("VIEWED")+" "+mContext.getString(R.string.count));
				holder.viewCount.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.like), null, null, null);
				holder.time.setText(mJson.get(position).getString("TIME"));

			}
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
