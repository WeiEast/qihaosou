package com.qihaosou.ui.activity;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.AnnualBasicBean;
import com.qihaosou.bean.AnnualBean;
import com.qihaosou.callback.AnnualBasicCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.ExpandableLayout;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/25
 * Description:年报详情
 */
public class YearReportDetailsActivity extends BaseActivity implements ExpandableLayout.ExpandableListener{
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private ExpandableLayout assetdataEL,partnersET;//资产信息
    private ScrollView scrollView;
    private ImageView assetdataIVtips,partnersIVtips;
    private AnnualBean annualBean;
    //公司名称
    private TextView compNameTV;
    //注册号
    private TextView compEconNoTV;
    //企业通信地址
    private TextView compAddressTV;
    //电子邮箱
    private TextView comEmailTV;
    //邮政编码
    private TextView zipCodeTV;
    //企业经营状态
    private TextView compStateTV;
    //从业人数
    private TextView workNumTV;
    @Override
    protected void init() {
        assetdataEL= (ExpandableLayout) findViewById(R.id.el_reportdetial_assetdata);
        assetdataEL.setIsExpand(true);
        assetdataIVtips= (ImageView) assetdataEL.getHeaderLayout().findViewById(R.id.iv_expend_tip);
        partnersET= (ExpandableLayout) findViewById(R.id.el_reportdetial_partners);
        partnersET.setIsExpand(true);
        partnersIVtips= (ImageView) partnersET.getHeaderLayout().findViewById(R.id.iv_expend_tip);

        compNameTV= (TextView) findViewById(R.id.tv_company_name);
        compEconNoTV= (TextView) findViewById(R.id.tv_econ_no);
        compAddressTV= (TextView) findViewById(R.id.tv_comp_address);
        comEmailTV= (TextView) findViewById(R.id.tv_comp_email);
        zipCodeTV= (TextView) findViewById(R.id.comp_zip_code);
        compStateTV= (TextView) findViewById(R.id.tv_comp_state);
        workNumTV= (TextView) findViewById(R.id.tv_comp_work_num);

        scrollView= (ScrollView) findViewById(R.id.scroll_view);
        mLoadingAndRetryManager=LoadingAndRetryManager.generate(scrollView,null);
    }

    @Override
    protected void addListener() {
        assetdataEL.setExpandableListener(this);
    }

    @Override
    protected void addData() {
        setTitle("年报详情");
        annualBean= (AnnualBean) getIntent().getExtras().get("AnnualBean");
        mLoadingAndRetryManager.showLoading();
        getAnnualBasic(annualBean);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yearreportdetails;
    }


    @Override
    public void expandable(View view, Boolean isExpand) {
       switch (view.getId()){
           case R.id.el_reportdetial_assetdata:
               if(isExpand){
                   assetdataIVtips.setImageResource(R.mipmap.arrow_up_expand);
               }
               else{
                   assetdataIVtips.setImageResource(R.mipmap.arrow_down_close);
               }
               break;
       }
    }
    private void getAnnualBasic(AnnualBean annualBean ){
        OkHttpUtils.post(UriHelper.getInstance().getAnnualBasicUrl(annualBean)).tag(this).execute(new AnnualBasicCallBack() {
            @Override
            public void onAfter(@Nullable AnnualBasicBean annualBasicBean, Request request, Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(),e.getMessage());
            }

            @Override
            public void onResponse(AnnualBasicBean annualBasicBean) {
                fullInfo(annualBasicBean);
            }
        });
    }

    private void fullInfo(AnnualBasicBean annualBasicBean) {
        compNameTV.setText(annualBasicBean.getCompanyName());
        compEconNoTV.setText(annualBasicBean.getEconNo());
        compAddressTV.setText(annualBasicBean.getCompAddress());
        comEmailTV.setText(annualBasicBean.getEmail());
        zipCodeTV.setText(annualBasicBean.getCompZip());
        compStateTV.setText(annualBasicBean.getCompState());
        workNumTV.setText(annualBasicBean.getWorkNum());
    }
}
