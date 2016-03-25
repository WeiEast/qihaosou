package com.qihaosou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.HotCompanyBean;
import com.qihaosou.ui.activity.CompanySearchActivity;
import com.qihaosou.view.SlidingTabLayout;
import com.qihaosou.view.StickHeaderViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/15
 * Description:
 */
public class HomeFragment extends Fragment implements View.OnClickListener{
    //private ListView listview;
    StickHeaderViewPager shvp_content;
    SlidingTabLayout stl_stick;
    private TextView btnSearch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, (ViewGroup)null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }

    private void init(View view) {

        shvp_content = (StickHeaderViewPager)view.findViewById(R.id.shvp_content);
        stl_stick = (SlidingTabLayout) view.findViewById(R.id.tabs);
        btnSearch= (TextView) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        setData();
    }

    private void setData() {
        shvp_content.getViewPager().setOffscreenPageLimit(1);
        stl_stick.setDistributeEvenly(true);
        stl_stick.setCustomTabView(R.layout.view_tab, R.id.tv_text);
        stl_stick.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.title_color);
            }
        });

        StickHeaderViewPager.StickHeaderViewPagerBuilder.stickTo(shvp_content).
                setFragmentManager(getChildFragmentManager()).addScrollFragments(
                HotCompanyFragment.newInstance("热门企业"),
                HotCompanyFragment.newInstance("最近浏览"))
                .notifyData();
        stl_stick.setViewPager(shvp_content.getViewPager());
    }

    @Override
    public void onClick(View v) {
        Intent intent =null;
        switch (v.getId()){
            case R.id.btn_search:
                intent=new Intent(getActivity(), CompanySearchActivity.class);
                break;
        }
        if(intent!=null)
            startActivity(intent);
    }
}
