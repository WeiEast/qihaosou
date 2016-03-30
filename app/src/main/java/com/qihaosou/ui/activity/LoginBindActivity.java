package com.qihaosou.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.lzy.okhttputils.request.BaseRequest;
import com.qihaosou.R;
import com.qihaosou.app.MyAction;
import com.qihaosou.app.MyApplication;
import com.qihaosou.app.SharedPreHelper;
import com.qihaosou.bean.OpenIdCatalog;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.bean.UserBean;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.callback.UserBeanCallBack;
import com.qihaosou.listener.MyTextWacher;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.MaterialDialogUtil;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.util.UIHelper;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/30
 * Description:登录绑定
 */
public class LoginBindActivity extends BaseActivity implements View.OnClickListener{

    private EditText userNameET;
    private EditText passwordET;
    private Button btnLogin;
    private TextView forgotPasswordTV;
    private String uid,platform;
    private MyTextWacher wacher;
    private UserBean MyUserBean;
    private TextView releTipTV;
    @Override
    protected void init() {
        setTitle("登录绑定");
        userNameET= (EditText) findViewById(R.id.edt_login_number);
        passwordET= (EditText) findViewById(R.id.edt_login_password);
        btnLogin= (Button) findViewById(R.id.btn_login_newpage);
        forgotPasswordTV= (TextView) findViewById(R.id.btn_forgot_password);
        releTipTV= (TextView) findViewById(R.id.tv_rele_tip);
        wacher=new MyTextWacher(btnLogin,userNameET,passwordET);

    }

    @Override
    protected void addListener() {
        btnLogin.setOnClickListener(this);
        forgotPasswordTV.setOnClickListener(this);
        userNameET.addTextChangedListener(wacher);
        passwordET.addTextChangedListener(wacher);
    }

    @Override
    protected void addData() {
        Intent intent=getIntent();
        if(intent!=null){
            platform=intent.getExtras().getString("platform");
            uid=intent.getExtras().getString("uid");
            if(OpenIdCatalog.QQ.equals(platform)){
                releTipTV.setText(String.format(getString(R.string.rele_tip),"QQ"));
            }else if(OpenIdCatalog.WECHAT.equals(platform)){
                releTipTV.setText(String.format(getString(R.string.rele_tip),"微信"));
            }
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bind_login;
    }

    @Override
    public void onClick(View v) {
        String userName=userNameET.getText().toString();
        String password=passwordET.getText().toString();
        switch (v.getId()){
            case R.id.btn_login_newpage:
                Login(userName,password);
                break;
            case R.id.btn_forgot_password:
                UIHelper.showResetPasswordActivity(LoginBindActivity.this);
                break;
        }
    }

    private void Login(String phone, final String password) {
        String clientType="android";
        final MaterialDialog loadingDialog= MaterialDialogUtil.getNormalProgressDialog(this, getString(R.string.loading));
        OkHttpUtils.post(UriHelper.getInstance().getLoginUrl(phone, password, clientType)).tag(this).execute(new UserBeanCallBack() {

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
                ToastUtil.TextToast(LoginBindActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(UserBean userBean) {
                MyUserBean=userBean;
                bind(uid);
            }
        });
    }
    private void setLogined(boolean islogin){
        SharedPreHelper.putBooleanShareData("islogin", islogin);

    }

    private void bind(String uid){
        OkHttpUtils.post(UriHelper.getInstance().bindUidUrl(uid)).tag(this).execute(new QihaosouBeanCallBack() {

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(LoginBindActivity.this,e.getMessage());
            }

            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                ToastUtil.TextToast(getApplicationContext(),getString(R.string.login_success));
                try {
                    SharedPreHelper.putUserInfo(MyUserBean);
                    MyApplication.userBean=MyUserBean;
                    MyApplication.userBean.setAvatar(MyUserBean.getAvatar()+".jpg");
                    setLogined(true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent();
                intent.setAction(MyAction.LOGIN_SUCCESSED_ACTION);
                intent.putExtra("userinfo",MyUserBean);
                sendBroadcast(intent);
                UIHelper.showMainActivity(LoginBindActivity.this);
            }
        });
    }
}
