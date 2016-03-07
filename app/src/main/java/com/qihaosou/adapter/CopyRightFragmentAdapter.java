package com.qihaosou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qihaosou.R;
import com.qihaosou.ui.fragment.copyright.SoftwareCopyRightFragment;
import com.qihaosou.ui.fragment.copyright.WorkCopyRightFragment;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:
 */
public class CopyRightFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    public CopyRightFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public CopyRightFragmentAdapter(Context context,FragmentManager fm){
        super(fm);
        titles=context.getResources().getStringArray(R.array.copy_right);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WorkCopyRightFragment();
            case 1:
                return new SoftwareCopyRightFragment();
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
