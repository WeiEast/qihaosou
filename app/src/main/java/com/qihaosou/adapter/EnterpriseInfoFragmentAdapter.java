package com.qihaosou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qihaosou.R;
import com.qihaosou.ui.fragment.ChangeInfoFragment;
import com.qihaosou.ui.fragment.IndustryInfoFragment;
import com.qihaosou.ui.fragment.KeyPersonFragment;
import com.qihaosou.ui.fragment.MyCollectFragment;
import com.qihaosou.ui.fragment.StockHolderInfoFragment;
import com.qihaosou.ui.fragment.YearReportFragment;

/**
 * Created by Administrator on 2016/1/22.
 */
public class EnterpriseInfoFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    public EnterpriseInfoFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public EnterpriseInfoFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        titles=context.getResources().getStringArray(R.array.enterprise_info);
    }
    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return new IndustryInfoFragment();
        if(position==1)
            return new KeyPersonFragment();
        if(position==2)
            return new ChangeInfoFragment();
        if(position==3)
            return new StockHolderInfoFragment();

        return new YearReportFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
