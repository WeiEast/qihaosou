package com.qihaosou.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qihaosou.R;
import com.qihaosou.bean.OpenIdCatalog;


/**
 * Author: Created by wenjundu
 * Date:on 2016/3/29
 * Description:联合登录
 */
public class UniteLoginActivity  extends BaseActivity{

    private SimpleDraweeView headerIcon;//头像

    private TextView loginCategoryTV;//登录平台

    private TextView userName;

    private Button registerBtn;

    private Button loginBtn;
    private String platform,header_url,username;
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

    }

    @Override
    protected void addData() {
        setTitle("联合登录");
        Intent intent=getIntent();
        if(intent!=null){
            platform=intent.getExtras().getString("platform");
            header_url=intent.getExtras().getString("header_url");
            username=intent.getExtras().getString("username");
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
}
