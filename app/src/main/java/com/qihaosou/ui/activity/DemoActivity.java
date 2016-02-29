package com.qihaosou.ui.activity;

import com.qihaosou.R;
import com.qihaosou.view.ProgressBarView;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/27
 * Description:
 */
public class DemoActivity extends BaseActivity {
    private ProgressBarView  view;
    @Override
    protected void init() {
        view= (ProgressBarView) findViewById(R.id.demobar);
        view.setProgress(1600);
        view.setMax(2000);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("Demo");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_demo;
    }
}
