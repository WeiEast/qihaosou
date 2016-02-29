package com.qihaosou.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qihaosou.R;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/19
 * Description:邮箱
 */
public class UserEmialActivity extends BaseActivity {
    private EditText editEmail;
    private TextView btnSubmit;
    @Override
    protected void init() {
        setTitle(getString(R.string.email));
        editEmail= (EditText) findViewById(R.id.edt_email);
        btnSubmit= (TextView) findViewById(R.id.btn_email_submit);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_email;
    }

}
