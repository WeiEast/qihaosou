package com.qihaosou.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.lzy.okhttputils.https.TaskException;
import com.lzy.okhttputils.request.BaseRequest;
import com.qihaosou.R;
import com.qihaosou.app.MyAction;
import com.qihaosou.app.MyApplication;
import com.qihaosou.app.SharedPreHelper;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.OpenIdCatalog;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.bean.UserBean;
import com.qihaosou.bean.VcodeBean;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.callback.UserBeanCallBack;
import com.qihaosou.callback.VcodeBeanCallBack;
import com.qihaosou.listener.MyTextWacher;
import com.qihaosou.listener.TimeCountListener;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.DensityUtils;
import com.qihaosou.util.L;
import com.qihaosou.util.MaterialDialogUtil;
import com.qihaosou.util.NetUtils;
import com.qihaosou.util.TimeCount;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.util.UIHelper;
import com.qihaosou.view.LoadingDialog;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private EditText phoneET,vcodeET,passwordET,nicknameET;
    private Button btnCode,btnRegister;
    private ToggleButton btnChange;
    private TimeCount timeCount;

    private TextView bindTipTV;
    private TextView btnAgreement;
    private boolean isBind;//是否需要绑定
    private String platform,uid;
    private UserBean  MyUserBean;
    @Override
    protected void init() {
        phoneET= (EditText) findViewById(R.id.edt_register_number);
        vcodeET= (EditText) findViewById(R.id.edt_register_valid);
        passwordET= (EditText) findViewById(R.id.edt_register_password);
        nicknameET= (EditText) findViewById(R.id.edt_register_nickname);
        btnCode= (Button) findViewById(R.id.btn_register_getvalid);
        btnRegister= (Button) findViewById(R.id.btn_register);
        btnChange= (ToggleButton) findViewById(R.id.tb_change_version);
        bindTipTV= (TextView) findViewById(R.id.tv_register_bind_tip);
        btnAgreement= (TextView) findViewById(R.id.tv_agreement);

        codewatcher=new MyTextWacher(btnCode,phoneET);
        regwatcher=new MyTextWacher(btnRegister,phoneET,vcodeET,passwordET,nicknameET);

        timeCount = new TimeCount(60000, 1000);//构造CountDownTimer对象
    }

    @Override
    protected void addListener() {
        btnCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        phoneET.addTextChangedListener(codewatcher);
        phoneET.addTextChangedListener(regwatcher);
        vcodeET.addTextChangedListener(regwatcher);
        passwordET.addTextChangedListener(regwatcher);
        nicknameET.addTextChangedListener(regwatcher);
        btnChange.setOnCheckedChangeListener(this);
        btnAgreement.setOnClickListener(this);
        timeCount.setOnTimeCountListener(new TimeCountListener() {
            @Override
            public void onFinish() {
                btnCode.setEnabled(true);
                btnCode.setText(R.string.get_validcode);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                btnCode.setEnabled(false);
                btnCode.setText(String.format(getString(R.string.time_count_tips),millisUntilFinished / 1000));
            }
        });
    }

    @Override
    protected void addData() {
        Intent intent=getIntent();
        if(MyAction.LOGINACTIVITY_LAUNCH_ACTION.equals(intent.getAction())) {//从登录页启动的
            setTitle(getString(R.string.title_register));
            bindTipTV.setVisibility(View.GONE);
            isBind=false;
        }else if(MyAction.UNITEACTIVITY_LAUNCH_ACTION.equals(intent.getAction())){//从联合登录页启动的

            setTitle(getString(R.string.title_bind_register));
            platform=intent.getExtras().getString("platform");
            uid=intent.getExtras().getString("uid");
            bindTipTV.setVisibility(View.VISIBLE);
            if(OpenIdCatalog.QQ.equals(platform))
                bindTipTV.setText(String.format(getString(R.string.register_tip),"QQ"));
            else if(OpenIdCatalog.WECHAT.equals(platform))
                bindTipTV.setText(String.format(getString(R.string.register_tip),"微信"));
            isBind=true;
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }


    @Override
    public void onClick(View v) {
        String phone=phoneET.getText().toString().trim();
        String password=passwordET.getText().toString().trim();
        String nickname=nicknameET.getText().toString().trim();
        String vcode=vcodeET.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_register://注册
               register(phone,password,nickname,vcode);
                break;
            case R.id.btn_register_getvalid://获取验证
                getCode(phone);
                break;
            case R.id.tv_agreement://服务协议
                UIHelper.showAgreementActivity(RegisterActivity.this);
                break;
        }
    }
    MyTextWacher codewatcher;
    MyTextWacher regwatcher;
    private void register(final String phone, final String password,String nickname,String vcode){
        final MaterialDialog loadingDialog=MaterialDialogUtil.getNormalProgressDialog(this,getString(R.string.requesting));
        OkHttpUtils.post(UriHelper.getInstance().getRegisterUrl(phone, password, vcode, nickname)).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(),e.getMessage());
            }

            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                Login(phone, password);
            }

            @Override
            public void onBefore(BaseRequest request) {
                loadingDialog.show();
            }

            @Override
            public void onAfter(@Nullable QihaosouBean qihaosouBean, Request request, Response response, @Nullable TaskException e) {
                loadingDialog.dismiss();
            }
        });
    }
    //获取验证码
    private void getCode(String phone){
        final MaterialDialog loadingDialog= MaterialDialogUtil.getNormalProgressDialog(this,getString(R.string.requesting));
        OkHttpUtils.post(UriHelper.getInstance().getVcodeUrl(phone)).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                timeCount.start();
                ToastUtil.TextToast(getApplicationContext(), qihaosouBean.getMessage());
            }

            @Override
            public void onBefore(BaseRequest request) {
                loadingDialog.show();
            }

            @Override
            public void onAfter(@Nullable QihaosouBean qihaosouBean, Request request, Response response, @Nullable TaskException e) {
                loadingDialog.dismiss();
            }
        });
    }


    private void getPhineInfo() {
       L.e("网络类型:" + NetUtils.getAPNType(this)) ;
        L.e("手机号码:" + NetUtils.getPhoneNumber(this)) ;
        L.e("手机IMEI:" + NetUtils.getPhoneIMEI(this)) ;
        L.e("运营商:"+NetUtils.getProviders(this));
        L.e("手机型号:"+NetUtils.getRelase());
        L.e("手机分辨率:" + DensityUtils.getDisplayWidth(this) + "*" + DensityUtils.getDisplayHeight(this));
    }

    /**
     * 是否显示密码
     * @param buttonView
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //显示密码
            passwordET.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance());
        } else {
            //隐藏密码
            passwordET.setTransformationMethod(
                    PasswordTransformationMethod.getInstance());
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
                ToastUtil.TextToast(RegisterActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(UserBean userBean) {
                MyUserBean=userBean;
                if(isBind)
                    bind(uid);
                else{
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
                    UIHelper.showMainActivity(RegisterActivity.this);
                }
            }
        });
    }
    private void bind(String uid){
        OkHttpUtils.post(UriHelper.getInstance().bindUidUrl(uid)).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(RegisterActivity.this,e.getMessage());
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
                UIHelper.showMainActivity(RegisterActivity.this);
            }
        });
    }
    private void setLogined(boolean islogin){
        SharedPreHelper.putBooleanShareData("islogin", islogin);

    }
}
