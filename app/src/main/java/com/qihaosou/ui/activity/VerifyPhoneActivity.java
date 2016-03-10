package com.qihaosou.ui.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.lzy.okhttputils.request.BaseRequest;
import com.qihaosou.R;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.bean.VcodeBean;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.callback.VcodeBeanCallBack;
import com.qihaosou.listener.MyTextWacher;
import com.qihaosou.listener.TimeCountListener;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.TimeCount;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LoadingDialog;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/13
 * Description:验证手机
 */
public class VerifyPhoneActivity extends BaseActivity implements View.OnClickListener{
    private EditText phoneET,validcodeET,passwordET;
    private Button submitBtn,validcodeBtn;
    private TimeCount timeCount;
    private MyTextWacher codewatcher;
    private MyTextWacher regwatcher;
    @Override
    protected void init() {
        phoneET= (EditText) findViewById(R.id.edt_phone);
        validcodeET= (EditText) findViewById(R.id.edt_validcode);
        passwordET= (EditText) findViewById(R.id.edt_password);
        submitBtn= (Button) findViewById(R.id.btn_submit);
        validcodeBtn= (Button) findViewById(R.id.btn_validcode);
        timeCount = new TimeCount(60000, 1000);//构造CountDownTimer对象
        codewatcher=new MyTextWacher(validcodeBtn,phoneET);
        regwatcher=new MyTextWacher(submitBtn,phoneET,validcodeET,passwordET);
    }

    @Override
    protected void addListener() {
        submitBtn.setOnClickListener(this);
        validcodeBtn.setOnClickListener(this);
        phoneET.addTextChangedListener(codewatcher);
        phoneET.addTextChangedListener(regwatcher);
        validcodeET.addTextChangedListener(regwatcher);
        passwordET.addTextChangedListener(regwatcher);
        timeCount.setOnTimeCountListener(new TimeCountListener() {
            @Override
            public void onFinish() {
                validcodeBtn.setEnabled(true);
                validcodeBtn.setText(R.string.get_validcode);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                validcodeBtn.setEnabled(false);
                validcodeBtn.setText(String.format(getString(R.string.time_count_tips),millisUntilFinished / 1000));
            }
        });

    }

    @Override
    protected void addData() {
        setTitle(getString(R.string.reset_password));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_verify_phone;
    }

    @Override
    public void onClick(View v) {
        String phone=phoneET.getText().toString();
        String code=validcodeET.getText().toString();

        switch (v.getId()){
            case R.id.btn_submit://提交
                String password=passwordET.getText().toString();
                submitChange(phone,code,password);
                break;
            case R.id.btn_validcode://获取验证码
                if(phone.length()<11) {
                    ToastUtil.TextToast(VerifyPhoneActivity.this, getString(R.string.phone_format_error));
                    return;
                }else{
                    getCode(phone);
                }
                    break;

        }
    }

    //提交修改
    private void submitChange(String phone ,String vcode,String passwrod){
        final LoadingDialog loadingDialog=new LoadingDialog(VerifyPhoneActivity.this);
        OkHttpUtils.post(UriHelper.getInstance().rePasswordUrl(phone,vcode,passwrod)).tag(this).execute(new QihaosouBeanCallBack() {

            @Override
            public void onBefore(BaseRequest request) {
                loadingDialog.show();
            }

            @Override
            public void onAfter(@Nullable QihaosouBean qihaosouBean, Request request, Response response, @Nullable TaskException e) {
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(VerifyPhoneActivity.this, e.getMessage());
            }

            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                ToastUtil.TextToast(VerifyPhoneActivity.this,qihaosouBean.getMessage());
            }
        });
    }


    //获取验证码
    private void getCode(String phone){
        final LoadingDialog loadingDialog=new LoadingDialog(this);
        OkHttpUtils.post(UriHelper.getInstance().getVcodeUrl(phone)).tag(this).execute(new VcodeBeanCallBack() {
            @Override
            public void onAfter(@Nullable VcodeBean vcodeBean, Request request, Response response, @Nullable TaskException e) {
                loadingDialog.dismiss();
            }

            @Override
            public void onBefore(BaseRequest request) {
                loadingDialog.show();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(),e.getMessage());
                validcodeBtn.setEnabled(true);
            }

            @Override
            public void onResponse(VcodeBean vcodeBean) {
                if(vcodeBean!=null){
                    L.e(vcodeBean.getVcode());
                }
                validcodeBtn.setEnabled(false);
                validcodeET.setText(vcodeBean.getVcode());
                timeCount.start();
            }
        });
    }

}
