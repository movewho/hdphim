/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;

import com.hd.phim.Utility.Configs;
import com.hd.phim.custome.CustomViewPager;
import com.movie.hdonline.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * @author nguyenquocchinh
 * 
 */
public class HDMovie extends Activity {

	private TabHost mTabHost;
	private CustomViewPager mCustomViewPage;
	private TabsAdapter mTabsAdapter;
	private static TextView txtTitle;

	public final static String TAB_LIST_NOIBAT = "Noi bat";

	private final static String TAB_XEM_NHIEU_NHAT = "Xem nhieu nhat";

	private final static String TAB_TIM_KIEM = "Tim mkiem";

	private final static String TAB_MUC_UA_THICH = "Muc ua thich";

	private final static String TAB_THEM = "Them";

	private static String mCurrentTab = TAB_LIST_NOIBAT;
	
	private String titleNoiBat;
	private String titleXemNhieuNhat;
	private String titleTimKiem;
	private String titleMucUaThich;
	private String titleThem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mCustomViewPage = (CustomViewPager) findViewById(R.id.pager);
		
		mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_LIST_NOIBAT), titleNoiBat, TextView.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.btn_dook), Configs.SCREEN_NOI_BAT);

        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_XEM_NHIEU_NHAT),titleXemNhieuNhat, TextView.class, null,mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.btn_downloadmore), Configs.SCREEN_XEM_NHIEU_NHAT);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_TIM_KIEM),titleTimKiem,TextView.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.btn_list_story), Configs.SCREEN_TIM_KIEM);

        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_MUC_UA_THICH),titleMucUaThich, TextView.class, null,mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.btn_setup), Configs.SCREEN_MUC_UA_THICH);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_THEM), titleThem, TextView.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.btn_setup), Configs.SCREEN_THEM);
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
		private final Context mContext;

		private final TabHost mTabHost;

		private final ViewPager mViewPager;

		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final String tag;

			private final Class<?> clss;

			private Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost,
				ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		/**
		 * This method set icon and text for each tab.
		 * 
		 * @param tabSpec
		 * @param title
		 * @param clss
		 * @param args
		 * @param mTabWidget
		 * @param layout
		 * @param icon
		 */
		public void addTab(TabHost.TabSpec tabSpec, String title,
				Class<?> clss, Bundle args, TabWidget mTabWidget, int layout,
				Drawable icon, int screenId) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();
			TabInfo info = new TabInfo(tag, clss, args);
			// TabSpec - Indicator - Content
			View tabIndicator = LayoutInflater.from(
					mContext.getApplicationContext()).inflate(layout, null);// inflate(layout,
																			// mTabWidget,
																			// false)
			tabSpec.setIndicator(tabIndicator);

			txtTitle = (TextView) tabIndicator.findViewById(R.id.txtTabTitle);
			txtTitle.setText(title);
			txtTitle.setCompoundDrawablesWithIntrinsicBounds(null, icon, null,
					null);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();

			// hide Layout hide screen
			switch (position) {
			case 0:
				mCurrentTab = TAB_LIST_NOIBAT;
				break;
			case 1:
				mCurrentTab = TAB_XEM_NHIEU_NHAT;
				break;
			case 2:
				mCurrentTab = TAB_TIM_KIEM;
				break;
			case 3:
				mCurrentTab = TAB_MUC_UA_THICH;
				break;
			case 4:
				mCurrentTab = TAB_THEM;
				break;
			}
			mViewPager.setCurrentItem(position);
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {

			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			// mCurrentTab = position == 4 ? TAB_DOWNLOADING : TAB_LIST;
			if (position == 1) {

			} else if (position == 2) {

			}
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}
}
