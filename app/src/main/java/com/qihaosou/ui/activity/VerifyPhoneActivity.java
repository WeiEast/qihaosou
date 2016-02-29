package com.qihaosou.ui.activity;

import android.view.View;

import com.qihaosou.R;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:验证手机
 */
public class VerifyPhoneActivity extends BaseActivity {
    @Override
    protected void init() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle(getString(R.string.title_verfiy_phone));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_verify_phone;
    }

}
