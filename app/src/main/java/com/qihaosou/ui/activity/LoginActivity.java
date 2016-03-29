package com.qihaosou.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.lzy.okhttputils.request.BaseRequest;
import com.qihaosou.R;
import com.qihaosou.app.MyApplication;
import com.qihaosou.app.SharedPreHelper;
import com.qihaosou.bean.OpenIdCatalog;
import com.qihaosou.bean.UserBean;
import com.qihaosou.callback.UserBeanCallBack;
import com.qihaosou.listener.MyTextWacher;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.MaterialDialogUtil;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.util.UIHelper;
import com.qihaosou.view.LoadingDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public static final String LOGIN_SUCCESSED_ACTION="com.qihaosou.login.successed";
    private TextView forgetPasswordTV,registerTV;
    private EditText phoneET,passwordET;
    private Button loginBtn;
    private LinearLayout loginQQ,loginWechat;
    private UMShareAPI mShareAPI = null;
    private MyTextWacher wacher;
    @Override
    protected void init() {
        forgetPasswordTV= (TextView) findViewById(R.id.tv_forget_password);
        registerTV= (TextView) findViewById(R.id.tv_register_acount);
        phoneET= (EditText) findViewById(R.id.edt_login_number);
        passwordET= (EditText) findViewById(R.id.edt_login_password);
        loginBtn= (Button) findViewById(R.id.btn_login_newpage);
        loginQQ= (LinearLayout) findViewById(R.id.ll_thridlogin_qq);
        loginWechat= (LinearLayout) findViewById(R.id.ll_thridlogin_wx);
        wacher=new MyTextWacher(loginBtn,phoneET,passwordET);

    }

    @Override
    protected void addListener() {

        forgetPasswordTV.setOnClickListener(this);
        registerTV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginWechat.setOnClickListener(this);
        loginQQ.setOnClickListener(this);
        phoneET.addTextChangedListener(wacher);
        passwordET.addTextChangedListener(wacher);

    }

    @Override
    protected void addData() {
        setTitle(getString(R.string.title_login));
        mShareAPI = UMShareAPI.get( this );
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View v) {
        String phone=phoneET.getText().toString();
        String password=passwordET.getText().toString();
        SHARE_MEDIA platform = null;
        switch (v.getId()){
            case R.id.tv_forget_password://忘记密码
                readyGo(ResetPasswordActivity.class);
                break;
            case R.id.tv_register_acount://注册
                readyGo(RegisterActivity.class);
                break;
            case R.id.btn_login_newpage://登录
                Login(phone,password);
                break;
            case R.id.ll_thridlogin_qq://qq登录
                platform = SHARE_MEDIA.QQ;
                break;
            case R.id.ll_thridlogin_wx://微信登录
                platform = SHARE_MEDIA.WEIXIN;
                break;
        }
        if(platform!=null)
            mShareAPI.doOauthVerify(LoginActivity.this, platform, umAuthListener);
    }

    private void Login(String phone, final String password) {
        String clientType="android";
        final MaterialDialog loadingDialog= MaterialDialogUtil.getNormalProgressDialog(this,getString(R.string.loading));
        OkHttpUtils.post(UriHelper.getInstance().getLoginUrl(phone,password,clientType)).tag(this).execute(new UserBeanCallBack() {

            @Override
            public void onBefore(BaseRequest request) {
                loadingDialog.show();
            }

            @Override
            public void onAfter(@Nullable UserBean userBean, Request request, Response response, @Nullable TaskException e) {
               loadingDialog.dismiss();

            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(LoginActivity.this,e.getMessage());
            }

            @Override
            public void onResponse(UserBean userBean) {
                ToastUtil.TextToast(getApplicationContext(),getString(R.string.login_success));
                try {
                    SharedPreHelper.putUserInfo(userBean);
                    MyApplication.userBean=userBean;
                    MyApplication.userBean.setAvatar(userBean.getAvatar()+".jpg");
                    setLogined(true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent();
                intent.setAction(LOGIN_SUCCESSED_ACTION);
                intent.putExtra("userinfo",userBean);
                sendBroadcast(intent);
                finish();
            }
        });
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

               mShareAPI.getPlatformInfo(LoginActivity.this, platform, umUserInfoListener);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.TextToast(getApplicationContext(), "Authorize fail");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.TextToast(getApplicationContext(), "Authorize cancel");
        }
    };

    private UMAuthListener umUserInfoListener=new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            L.e( map.toString());
            L.e(map.get("profile_image_url") + "...." + map.get("screen_name"));
            isBind(map.get("openid"),share_media,map);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    private void setLogined(boolean islogin){
        SharedPreHelper.putBooleanShareData("islogin",islogin);

    }

    //检测是否绑定
    private void isBind(String uid, final SHARE_MEDIA platform, final Map<String, String> openinfo){
        OkHttpUtils.post(UriHelper.getInstance().checkUidUrl(uid)).tag(this).execute(new UserBeanCallBack() {

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                if("102".equals(e.getCode())){//未绑定，跳到绑定页面
                    Bundle bundle=new Bundle();
                    if(platform==SHARE_MEDIA.QQ){
                        bundle.putString("platform", OpenIdCatalog.QQ);
                        bundle.putString("header_url",openinfo.get("profile_image_url"));
                        bundle.putString("username",openinfo.get("screen_name"));
                    }
                    if(platform==SHARE_MEDIA.WEIXIN){
                        bundle.putString("platform",OpenIdCatalog.WECHAT);
                        bundle.putString("header_url",openinfo.get("headimgurl"));
                        bundle.putString("username",openinfo.get("nickname"));
                    }
                    UIHelper.showUniteLoginActivity(LoginActivity.this,bundle);
                }else
                ToastUtil.TextToast(LoginActivity.this,e.getMessage());
            }

            @Override
            public void onResponse(UserBean userBean) {
                ToastUtil.TextToast(getApplicationContext(),getString(R.string.login_success));
                try {
                    SharedPreHelper.putUserInfo(userBean);
                    MyApplication.userBean=userBean;
                    MyApplication.userBean.setAvatar(userBean.getAvatar()+".jpg");
                    setLogined(true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent();
                intent.setAction(LOGIN_SUCCESSED_ACTION);
                intent.putExtra("userinfo",userBean);
                sendBroadcast(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("auth", "on activity re 2");
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }
}
