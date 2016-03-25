package com.qihaosou.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.qihaosou.R;
import com.qihaosou.adapter.CopyRightFragmentAdapter;
import com.qihaosou.adapter.QueryBeginFragmentAdapter;
import com.qihaosou.ui.fragment.copyright.SoftwareCopyRightFragment;
import com.qihaosou.ui.fragment.copyright.WorkCopyRightFragment;
import com.qihaosou.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:著作权
 */
public class CopyRightActivity extends BaseActivity{
    private ViewPager vPager;
    private SlidingTabLayout tabs;
    private CopyRightFragmentAdapter fragmentAdapter;
    private List<Fragment> list;
    @Override
    protected void init() {
        list=new ArrayList<Fragment>();
        Bundle bundle=getIntent().getExtras();
        WorkCopyRightFragment workCopyRightFragment=new WorkCopyRightFragment();
        workCopyRightFragment.setArguments(bundle);
        SoftwareCopyRightFragment softwareCopyRightFragment=new SoftwareCopyRightFragment();
        softwareCopyRightFragment.setArguments(bundle);
        list.add(workCopyRightFragment);
        list.add(softwareCopyRightFragment);
        vPager= (ViewPager)findViewById(R.id.vPager);
        fragmentAdapter=new CopyRightFragmentAdapter(this,getSupportFragmentManager(),list);
        tabs= (SlidingTabLayout)findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.view_tab, R.id.tv_text);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.title_color);
            }
        });

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        vPager.setAdapter(fragmentAdapter);
        tabs.setViewPager(vPager);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_copyright;
    }
}
