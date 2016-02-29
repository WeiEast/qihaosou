package com.qihaosou.ui.activity;

import android.support.v7.widget.Toolbar;

import com.qihaosou.R;
import com.qihaosou.app.MyApplication;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/12
 * Description:
 */
public abstract class BaseActivity  extends BaseAppCompatActivity {
    protected Toolbar mToolbar;
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();
        addListener();
        addData();
    }
    protected MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }
    //初始化控件
    protected abstract void init();
    //添加事件监听
    protected abstract void addListener();
    //添加数据
    protected abstract void addData();

}
