package com.qihaosou.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.listener.MyBeanCallBack;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.DensityUtils;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.view.LoadingDialog;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:注册页面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    private EditText phoneET,vcodeET,passwordET,nicknameET;
    private Button btnCode,btnRegister;
    private ToggleButton btnChange;
    @Override
    protected void init() {
        phoneET= (EditText) findViewById(R.id.edt_register_number);
        vcodeET= (EditText) findViewById(R.id.edt_register_valid);
        passwordET= (EditText) findViewById(R.id.edt_register_password);
        nicknameET= (EditText) findViewById(R.id.edt_register_nickname);
        btnCode= (Button) findViewById(R.id.btn_register_getvalid);
        btnRegister= (Button) findViewById(R.id.btn_register);
        btnChange= (ToggleButton) findViewById(R.id.tb_change_version);
    }

    @Override
    protected void addListener() {
        btnCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
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
                if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(nickname) && TextUtils.isEmpty(vcode)){
                    showToast("参数为空");
                    return;
                }
               register(phone,password,nickname,vcode);
                break;
            case R.id.btn_register_getvalid://获取验证
                break;
        }
    }

    private void register(String phone,String password,String nickname,String vcode){
//        if(NetUtils.isNetworkConnected(this)){
//            final LoadingDialog loadingDialog=new LoadingDialog(RegisterActivity.this);
//            loadingDialog.show();
//            GsonRequest<BaseBean> gsonRequest=new GsonRequest<BaseBean>(Request.Method.POST, UriHelper.getInstance().getRegisterUrl(phone, password, vcode, nickname),                    new Response.Listener<BaseBean>() {
//                        @Override
//                        public void onResponse(BaseBean response) {
//                            loadingDialog.dismiss();
//                            showToast(response.getMessage());
//                            getPhineInfo();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            loadingDialog.dismiss();
//                        }
//                    }
//            );
//            VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
//        }
        OkHttpUtils.post(UriHelper.getInstance().getRegisterUrl(phone, password, vcode,nickname)).execute(new MyBeanCallBack<String>() {
            @Override
            public void onResponse(String s) {
                L.e("login callback::"+s);
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
}
