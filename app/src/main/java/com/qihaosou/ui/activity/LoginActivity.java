package com.qihaosou.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.LoginBean;
import com.qihaosou.bean.UserBean;
import com.qihaosou.listener.MyBeanCallBack;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LoadingDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private TextView forgetPasswordTV,registerTV;
    private EditText phoneET,passwordET;
    private Button loginBtn;
    private LinearLayout loginQQ,loginWechat;
    private UMShareAPI mShareAPI = null;
    @Override
    protected void init() {
        forgetPasswordTV= (TextView) findViewById(R.id.tv_forget_password);
        registerTV= (TextView) findViewById(R.id.tv_register_acount);
        phoneET= (EditText) findViewById(R.id.edt_login_number);
        passwordET= (EditText) findViewById(R.id.edt_login_password);
        loginBtn= (Button) findViewById(R.id.btn_login_newpage);
        loginQQ= (LinearLayout) findViewById(R.id.ll_thridlogin_qq);
        loginWechat= (LinearLayout) findViewById(R.id.ll_thridlogin_wx);
    }

    @Override
    protected void addListener() {

        forgetPasswordTV.setOnClickListener(this);
        registerTV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginWechat.setOnClickListener(this);
        loginWechat.setOnClickListener(this);


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
                readyGo(VerifyPhoneActivity.class);
                break;
            case R.id.tv_register_acount://注册
                readyGo(RegisterActivity.class);
                break;
            case R.id.btn_login_newpage://登录
                if(TextUtils.isEmpty(phone)&& TextUtils.isEmpty(password))
                    return;
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

    private void Login(String phone,String password) {
        String clientType="android";
        final LoadingDialog loadingDialog=new LoadingDialog(LoginActivity.this);
        loadingDialog.show();
//        if(NetUtils.isNetworkConnected(this)){
//            GsonRequest<LoginBean> request=new GsonRequest<LoginBean>(Request.Method.POST, UriHelper.getInstance().getLoginUrl(phone,password,clientType),new TypeToken<LoginBean>(){}.getType(),
//
//                    new Response.Listener<LoginBean>() {
//                        @Override
//                        public void onResponse(LoginBean response) {
//                            loadingDialog.dismiss();
//                            if("0000".equals(response.getCode())){
//                                showToast(getString(R.string.login_success));
//                                finish();
//                            }else{
//                                L.e(response.getMessage());
//                                showToast(response.getMessage());
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                         @Override
//                         public void onErrorResponse(VolleyError error) {
//                             L.e("loginError:" + error.toString());
//                           loadingDialog.dismiss();
//                         }
//            });
//            VolleyHelper.getInstance().getRequestQueue().add(request);
//        }
        OkHttpUtils.post(UriHelper.getInstance().getLoginUrl(phone,password,clientType)).execute(new MyBeanCallBack<String>() {
            @Override
            public void onResponse(String s) {
                L.e("login callback::"+s);
            }
        });
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            mShareAPI.getPlatformInfo(LoginActivity.this,platform,umAuthListener);
            ToastUtil.TextToast(getApplicationContext(), action+"Authorize succeed");
            L.e(data.toString());
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
}
