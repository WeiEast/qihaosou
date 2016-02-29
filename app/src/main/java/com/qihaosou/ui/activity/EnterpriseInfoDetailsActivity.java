package com.qihaosou.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.qihaosou.R;
import com.qihaosou.adapter.EnterpriseInfoFragmentAdapter;
import com.qihaosou.smarttablayout.SmartTabLayout;

/**
 * Created by Administrator on 2016/1/22.
 */
public class EnterpriseInfoDetailsActivity extends BaseActivity{

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    private EnterpriseInfoFragmentAdapter adapter;
    @Override
    protected void init() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        smartTabLayout= (SmartTabLayout) findViewById(R.id.viewpagertab);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("中商咨询有限公司");
        adapter=new EnterpriseInfoFragmentAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_enterprise_infodetails;
    }

}
