package com.qihaosou.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.VcodeBody;
import com.qihaosou.listener.MyBeanCallBack;
import com.qihaosou.listener.MyTextWacher;
import com.qihaosou.listener.TimeCountListener;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.DensityUtils;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.util.TimeCount;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LoadingDialog;

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
    @Override
    protected void init() {
        phoneET= (EditText) findViewById(R.id.edt_register_number);
        vcodeET= (EditText) findViewById(R.id.edt_register_valid);
        passwordET= (EditText) findViewById(R.id.edt_register_password);
        nicknameET= (EditText) findViewById(R.id.edt_register_nickname);
        btnCode= (Button) findViewById(R.id.btn_register_getvalid);
        btnRegister= (Button) findViewById(R.id.btn_register);
        btnChange= (ToggleButton) findViewById(R.id.tb_change_version);

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
        timeCount.setOnTimeCountListener(new TimeCountListener() {
            @Override
            public void onFinish() {
                btnCode.setEnabled(true);
                btnCode.setText(R.string.get_validcode);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                btnCode.setEnabled(false);
                btnCode.setText(millisUntilFinished /1000+getString(R.string.time_count_tips));
            }
        });
    }

    @Override
    protected void addData() {
        setTitle(getString(R.string.title_register));
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
        }
    }
    MyTextWacher codewatcher;
    MyTextWacher regwatcher;
    private void register(String phone,String password,String nickname,String vcode){
        OkHttpUtils.post(UriHelper.getInstance().getRegisterUrl(phone, password, vcode,nickname)).tag(this).execute(new BeanCallBack<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                if(baseBean!=null){
                    if("0000".equals(baseBean.getCode())){
                        ToastUtil.TextToast(RegisterActivity.this,"注册成功");
                    }else{
                        ToastUtil.TextToast(RegisterActivity.this,baseBean.getMessage()+baseBean.getCode());
                    }
                }
            }
        });
    }
    //获取验证码
    private void getCode(String phone){
        OkHttpUtils.post(UriHelper.getInstance().getVcodeUrl(phone)).tag(this).execute(new BeanCallBack<BaseBean<VcodeBody>>() {
            @Override
            public void onResponse(BaseBean<VcodeBody> vcodeBodyBaseBean) {
                if(vcodeBodyBaseBean!=null){
                    if("0000".equals(vcodeBodyBaseBean.getCode())){
                        VcodeBody vcodeBody=vcodeBodyBaseBean.getBody();
                         L.e("code:::"+vcodeBody.getVcode().toString());
                        timeCount.start();
                    }else{
                        ToastUtil.TextToast(RegisterActivity.this,vcodeBodyBaseBean.getMessage());
                    }
                }
            }
        });
    }


    private void getPhineInfo() {
       L.e("网络类型:" + NetUtils.getAPNType(this)) ;
        L.e("手机号码:" + NetUtils.getPhoneNumber(this)) ;
        L.e("手机IMEI:" + NetUtils.getPhoneIMEI(this)) ;
        L.e("运营商:"+NetUtils.getProviders(this));
        L.e("手机型号:"+NetUtils.getRelase());
        L.e("手机分辨率:"+ DensityUtils.getDisplayWidth(this)+"*"+DensityUtils.getDisplayHeight(this));
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
}
