package com.qihaosou.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.qihaosou.R;
import com.qihaosou.ui.fragment.ChangeInfoFragment;
import com.qihaosou.ui.fragment.IndustryInfoFragment;
import com.qihaosou.ui.fragment.KeyPersonFragment;
import com.qihaosou.ui.fragment.StockHolderInfoFragment;
import com.qihaosou.ui.fragment.YearReportFragment;
import com.qihaosou.util.L;

import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class EnterpriseInfoFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private List<Fragment> list;
    public EnterpriseInfoFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
    public EnterpriseInfoFragmentAdapter(Context context,FragmentManager fm,List<Fragment> list) {
        super(fm);
        titles=context.getResources().getStringArray(R.array.enterprise_info);
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
