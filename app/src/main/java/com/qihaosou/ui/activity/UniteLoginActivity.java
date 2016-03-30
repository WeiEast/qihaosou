package com.qihaosou.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qihaosou.R;
import com.qihaosou.app.MyAction;
import com.qihaosou.bean.OpenIdCatalog;
import com.qihaosou.util.UIHelper;


/**
 * Author: Created by wenjundu
 * Date:on 2016/3/29
 * Description:联合登录
 */
public class UniteLoginActivity  extends BaseActivity implements View.OnClickListener{

    private SimpleDraweeView headerIcon;//头像

    private TextView loginCategoryTV;//登录平台

    private TextView userName;

    private Button registerBtn;

    private Button loginBtn;
    private String platform,header_url,username,uid;
    @Override
    protected void init() {
        headerIcon= (SimpleDraweeView) findViewById(R.id.iv_avatar);
        loginCategoryTV= (TextView) findViewById(R.id.tv_login_category);
        userName= (TextView) findViewById(R.id.tv_login_name);
        registerBtn= (Button) findViewById(R.id.bt_reg);
        loginBtn= (Button) findViewById(R.id.bt_login);
    }

    @Override
    protected void addListener() {
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    protected void addData() {
        setTitle("联合登录");
        Intent intent=getIntent();
        if(intent!=null){
            platform=intent.getExtras().getString("platform");
            header_url=intent.getExtras().getString("header_url");
            username=intent.getExtras().getString("username");
            uid=intent.getExtras().getString("uid");
            if(OpenIdCatalog.QQ.equals(platform)){
                loginCategoryTV.setText("亲爱的qq用户 : ");
            }else if(OpenIdCatalog.WECHAT.equals(platform)){
                loginCategoryTV.setText("亲爱的微信用户 : ");
            }
            userName.setText(username);
            headerIcon.setImageURI(Uri.parse(header_url));
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login_bind_choose;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle=new Bundle();
        bundle.putString("platform", platform);
        bundle.putString("uid",uid);
        switch (v.getId()){
            case R.id.bt_reg:
                UIHelper.showBindRegisterActivity(UniteLoginActivity.this, MyAction.UNITEACTIVITY_LAUNCH_ACTION,bundle);
                break;
            case R.id.bt_login:
                UIHelper.showLoginBindActivity(UniteLoginActivity.this,bundle);
                break;
        }
    }
}
