package com.qihaosou.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qihaosou.R;
import com.qihaosou.adapter.QueryBeginFragmentAdapter;
import com.qihaosou.view.SlidingTabLayout;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/19
 * Description:查企业 --还未开始查询显示的 搜索历史和我的关注页
 */
public class CompanyQueryBeginFragment extends Fragment {
    private SlidingTabLayout tabs;
    private QueryBeginFragmentAdapter adapter;
    private ViewPager mPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_company_querybegin,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }

    private void init(View view) {
        mPager= (ViewPager) view.findViewById(R.id.vPager);
        adapter=new QueryBeginFragmentAdapter(getContext(),getChildFragmentManager());
        tabs= (SlidingTabLayout) view.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.view_tab, R.id.tv_text);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.title_color);
            }
        });
        mPager.setAdapter(adapter);
        tabs.setViewPager(mPager);
    }
}
