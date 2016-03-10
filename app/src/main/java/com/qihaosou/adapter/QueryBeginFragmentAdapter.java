package com.qihaosou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.qihaosou.R;
import com.qihaosou.ui.fragment.search.HistorySearchFragment;
import com.qihaosou.ui.fragment.search.MyCollectFragment;

/**
 * Created by Administrator on 2016/1/20.
 */
public class QueryBeginFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    public QueryBeginFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public QueryBeginFragmentAdapter(Context context,FragmentManager fm){
        super(fm);
        titles=context.getResources().getStringArray(R.array.begin_search);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HistorySearchFragment();
            case 1:
                return new MyCollectFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
