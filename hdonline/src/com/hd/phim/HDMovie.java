/**
 * 
 */
package com.hd.phim;

import java.util.ArrayList;
import java.util.Collections;
import shared.ui.actionscontentview.ActionsContentView;

import com.hd.phim.Utility.Configs;
import com.hd.phim.custome.CustomViewPager;
import com.hd.phim.custome.CustomeTextView;
import com.hd.phim.data.adapter.SitesAdapter;
import com.hd.phim.data.sectionlist.SectionListAdapter;
import com.hd.phim.data.sectionlist.SectionListItem;
import com.hd.phim.data.sectionlist.SectionListView;
import com.hd.phim.data.sectionlist.StandardArrayAdapter;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * @author nguyenquocchinh
 * 
 */
public class HDMovie extends FragmentActivity implements OnItemClickListener, OnClickListener{

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

	   // private ViewFlipper viewFliperActionView;
    private Animation animationRightToLeft;
    private Animation animationLeftToRight;
    private ImageButton buttonSwitchlayout;
    private ImageButton buttonSwitchOnMenu;
    private ActionsContentView actionContentView;
    private SectionListView menuList;
	
    
    private StandardArrayAdapter arrayAdapter;
    private SectionListAdapter sectionAdapter;
    private SectionListView listView;
    private  ArrayList<String>ListInput=new ArrayList<String>();
    private  ArrayList<ArrayList<String>> ListItems =new ArrayList<ArrayList<String>>();

  //-------------------------------------------------------------------------------
    
    public  ArrayList<String> getListString()
    {
    	String[] theloai = getResources().getStringArray(R.array.bycat_label);
        ArrayList<String> pairList=new ArrayList<String>();
        for (int i = 0; i < theloai.length; i++) {
			pairList.add(i, theloai[i]);
		}
        Collections.sort(pairList);
        return pairList;
    }
//-------------------------------------------------------------------------
  //-------------------------------------------------------------------------
    public  ArrayList<ArrayList<String>>GetListItiems (ArrayList<String>listInPut)
    {
        ArrayList<ArrayList<String>> Items =new ArrayList<ArrayList<String>>();
        ArrayList<String>Item;
        for(int i=0;i<listInPut.size();)
        {
            Item=new ArrayList<String>();
            String s1=listInPut.get(i).substring(0, 2);//String.valueOf(listInPut.get(i).charAt(0));
            for(int j =i+1;j<listInPut.size();++j)
            {
                String s2=listInPut.get(j).substring(0, 2);//String.valueOf(listInPut.get(j).charAt(0));
                if(s1.equals(s2))
                {
                    Item.add(listInPut.get(j));
                    listInPut.remove(j);
                    j=j-1;
                }
            }
            Item.add(listInPut.get(i));
            listInPut.remove(i);
            Items.add(Item);
        }
        return Items;
        
    }
    
    ArrayList<SectionListItem> exampleArray=new ArrayList<SectionListItem>();
    public ArrayList<SectionListItem> getListItems(ArrayList<ArrayList<String>>Arr)
    {
        //GetListItiems(getListString());
        for(int i=0;i<Arr.size();++i)
        {
            String title = Arr.get(i).get(0).substring(0, 2);//String.valueOf(Arr.get(i).get(0).charAt(0));
            for(int j=0;j<Arr.get(i).size();++j)
            {
                exampleArray.add(new SectionListItem(Arr.get(i).get(j).substring(3, Arr.get(i).get(j).length()),getLabel(title)));
            }
        }
        return exampleArray;
    }
//----------------------------------------------------------------------------------
  
public String getLabel(String title)
{
	String[] section = getResources().getStringArray(R.array.selection_list);
	String[] sectionKey = getResources().getStringArray(R.array.selection_list_key);
	for (int i = 0; i < section.length; i++) {
		if(title.equals(sectionKey[i]))
		{
			return section[i];
		}
	}
	
	return "#";
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.action_menu);

        animationRightToLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_right);   
        
        animationLeftToRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_left);
        
        actionContentView = (ActionsContentView) findViewById(R.id.content);
        actionContentView.setClickable(true);
        
        menuList = (SectionListView) findViewById(R.id.actions);
        //final SitesAdapter actionsAdapter = new SitesAdapter(getApplicationContext(), R.array.bycat_counttry, R.array.bycat_counttry, R.array.iconsArrayRes);
        ListInput=getListString();
        ListItems=GetListItiems(ListInput);
        getListItems(ListItems);
        arrayAdapter = new StandardArrayAdapter(this, R.id.title, exampleArray);
        sectionAdapter = new SectionListAdapter(getLayoutInflater(), arrayAdapter);
        menuList.setAdapter(sectionAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				actionContentView.showContent();
			}
        	        	
		}); 
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mCustomViewPage = (CustomViewPager) findViewById(R.id.pager);
		mCustomViewPage.setPagingEnabled(false);
		mTabsAdapter = new TabsAdapter(this, mTabHost, mCustomViewPage);
		
		titleNoiBat = getResources().getString(R.string.title_noi_bat);
		titleXemNhieuNhat = getResources().getString(R.string.title_xem_nhieu_nhat);
		titleTimKiem = getResources().getString(R.string.title_timkiem);
		titleMucUaThich = getResources().getString(R.string.title_muc_ua_thich);
		titleThem = getResources().getString(R.string.title_them);
		
		mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_LIST_NOIBAT), titleNoiBat, Outstanding.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.button_outstanding), Configs.SCREEN_NOI_BAT);

        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_XEM_NHIEU_NHAT),titleXemNhieuNhat, ReviewMore.class, null,mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.button_reviewmore), Configs.SCREEN_XEM_NHIEU_NHAT);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_TIM_KIEM),titleTimKiem,Outstanding.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.button_search), Configs.SCREEN_TIM_KIEM);

        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_MUC_UA_THICH),titleMucUaThich, Favorite.class, null,mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.button_favorite), Configs.SCREEN_MUC_UA_THICH);
        
        mTabsAdapter.addTab(mTabHost.newTabSpec(TAB_THEM), titleThem, More.class, null, mTabHost.getTabWidget(), R.layout.tab_indicator, getResources().getDrawable(R.drawable.button_more), Configs.SCREEN_THEM);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
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
