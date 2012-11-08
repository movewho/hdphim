/**
 * 
 */
package com.hd.phim.data.sectionlist;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.movie.hdonline.R;

/**
 * @author nguyenquocchinh
 *
 */
public class StandardArrayAdapter extends ArrayAdapter<SectionListItem> 
{

    private final ArrayList<SectionListItem> items;
	private LayoutInflater layout;
	private Context mContext;
	
	public StandardArrayAdapter(Context context,
            final int textViewResourceId, final ArrayList<SectionListItem>items) {
        super(context, textViewResourceId, items);

		this.mContext = context;
		this.layout = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
	}

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layout.inflate(R.layout.example_list_view__item_story, null);
        }
        final SectionListItem currentItem = items.get(position);
        if (currentItem != null) {
            final TextView textView = (TextView) view.findViewById(R.id.title);
            if (textView != null) {
                textView.setText(currentItem.item.toString());
            }
        }
        return view;
    }
}