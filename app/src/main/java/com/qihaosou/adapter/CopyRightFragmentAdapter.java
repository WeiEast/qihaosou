package com.qihaosou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qihaosou.R;
import com.qihaosou.ui.fragment.copyright.SoftwareCopyRightFragment;
import com.qihaosou.ui.fragment.copyright.WorkCopyRightFragment;

import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:
 */
public class CopyRightFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> list;
    public CopyRightFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public CopyRightFragmentAdapter(Context context,FragmentManager fm,List<Fragment> list){
        super(fm);
        titles=context.getResources().getStringArray(R.array.copy_right);
        this.list=list;
    }
    @Override
    public Fragment getItem(int position) {
       return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
