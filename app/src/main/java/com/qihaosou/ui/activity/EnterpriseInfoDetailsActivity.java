package com.qihaosou.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.qihaosou.R;
import com.qihaosou.adapter.EnterpriseInfoFragmentAdapter;
import com.qihaosou.smarttablayout.SmartTabLayout;
import com.qihaosou.ui.fragment.ChangeInfoFragment;
import com.qihaosou.ui.fragment.IndustryInfoFragment;
import com.qihaosou.ui.fragment.KeyPersonFragment;
import com.qihaosou.ui.fragment.StockHolderInfoFragment;
import com.qihaosou.ui.fragment.YearReportFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/22.
 */
public class EnterpriseInfoDetailsActivity extends BaseActivity{

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;
    private EnterpriseInfoFragmentAdapter adapter;
    private String uuid;
    private List<Fragment> list;
    private String name;
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

        name=getIntent().getExtras().getString("name");
        uuid=getIntent().getExtras().getString("uuid");
        setTitle(name);
        Bundle bundle=new Bundle();
        bundle.putString("uuid", uuid);
        list=new ArrayList<Fragment>();
        IndustryInfoFragment industryInfoFragment= new IndustryInfoFragment();
        industryInfoFragment.setArguments(bundle);
        KeyPersonFragment keyPersonFragment=new KeyPersonFragment();
        keyPersonFragment.setArguments(bundle);
        ChangeInfoFragment changeInfoFragment=new ChangeInfoFragment();
        changeInfoFragment.setArguments(bundle);
        StockHolderInfoFragment stockHolderInfoFragment=new StockHolderInfoFragment();
        stockHolderInfoFragment.setArguments(bundle);
        YearReportFragment yearReportFragment=new YearReportFragment();
        yearReportFragment.setArguments(bundle);
        list.add(industryInfoFragment);
        list.add(keyPersonFragment);
        list.add(changeInfoFragment);
        list.add(stockHolderInfoFragment);
        list.add(yearReportFragment);
        adapter=new EnterpriseInfoFragmentAdapter(this,getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_enterprise_infodetails;
    }

}
