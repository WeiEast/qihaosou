package com.qihaosou.ui.activity;

import android.support.annotation.Nullable;
import android.widget.ScrollView;
import android.widget.TextView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.DishonestyBean;
import com.qihaosou.callback.DishonestyCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Author: Created by wenjundu
 * Date:on 2016/3/9
 * Description:
 */
public class DishonestyDetailActivity extends BaseActivity {
    private ScrollView scrollView;
    //失信公司名称
    private TextView iNameTV;
    //组织机构号
    private TextView cardNumTV;
    //法定代表人
    private TextView legalPersonTV;
    //省份
    private TextView areaNameTV;
    //执行依据文号
    private TextView gistNumTV;
    //执行法院
    private TextView gistCourtTV;
    //立案时间
    private TextView regDateTV;
    //做出执行依据单位
    private TextView gistUnitTV;
    //被执行人履行情况
    private TextView performanceTV;
    //生效法律文书确定的义务
    private TextView dutyTV;
    //失信被执行人行为具体情形
    private TextView disruptTypeNameTV;
    //发布日期
    private TextView publishDateTV;
    private  String autoId,uuid;
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected void init() {
        scrollView= (ScrollView) findViewById(R.id.scroll_view);
        iNameTV= (TextView) findViewById(R.id.tv_iname);
        cardNumTV= (TextView) findViewById(R.id.tv_cardnum);
        legalPersonTV= (TextView) findViewById(R.id.tv_legal_person);
        areaNameTV= (TextView) findViewById(R.id.tv_areaname);
        gistNumTV= (TextView) findViewById(R.id.tv_gist_number);
        gistCourtTV= (TextView) findViewById(R.id.tv_gist_court);
        regDateTV= (TextView) findViewById(R.id.tv_reg_date);
        gistUnitTV= (TextView) findViewById(R.id.tv_gist_unit);
        performanceTV= (TextView) findViewById(R.id.tv_performance);
        dutyTV= (TextView) findViewById(R.id.tv_duty);
        disruptTypeNameTV= (TextView) findViewById(R.id.tv_disrupt_type_name);
        publishDateTV= (TextView) findViewById(R.id.tv_publish_date);
        mLoadingAndRetryManager=LoadingAndRetryManager.generate(scrollView, null);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("失信详情");
        autoId=getIntent().getExtras().getString("autoID");
        uuid=getIntent().getExtras().getString("uuid");
        getDishonestyDetail(autoId,uuid);
        mLoadingAndRetryManager.showLoading();
    }
    private void getDishonestyDetail(String AutoID,String uuid){

        OkHttpUtils.post(UriHelper.getInstance().getDishonestyDetailUrl(AutoID,uuid)).tag(this).execute(new DishonestyCallBack() {

            @Override
            public void onAfter(@Nullable DishonestyBean dishonestyBean, Request request, Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(DishonestyDetailActivity.this,e.toString());
            }

            @Override
            public void onResponse(DishonestyBean dishonestyBean) {
                fullInfo(dishonestyBean);
            }
        });
    }
    private void fullInfo(DishonestyBean dishonestyBean){
        iNameTV.setText(dishonestyBean.getIname());
        cardNumTV.setText(dishonestyBean.getCardNum());
        legalPersonTV.setText(dishonestyBean.getBusinessEntity());
        areaNameTV.setText(dishonestyBean.getAreaName());
        gistNumTV.setText(dishonestyBean.getGistID());
        gistCourtTV.setText(dishonestyBean.getCourtName());
        regDateTV.setText(dishonestyBean.getRegDate());
        gistUnitTV.setText(dishonestyBean.getGistUnit());
        performanceTV.setText(dishonestyBean.getPerformance());
        dutyTV.setText(dishonestyBean.getDuty());
        disruptTypeNameTV.setText(dishonestyBean.getDisruptTypeName());
        publishDateTV.setText(dishonestyBean.getPublishDate());
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_dishonesty_detail;
    }
}
