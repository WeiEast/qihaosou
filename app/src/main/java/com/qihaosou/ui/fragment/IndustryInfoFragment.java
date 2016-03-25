package com.qihaosou.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoBody;
import com.qihaosou.callback.IcinfoBeanCallBack;
import com.qihaosou.listener.MyBeanCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.loading.OnLoadingAndRetryListener;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LoadingDialog;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/22
 * Description:工商信息
 */
public class IndustryInfoFragment extends BaseLazyFragment {
    private String uuid;
    LoadingAndRetryManager mLoadingAndRetryManager;
    ScrollView rootView;
    private TextView operNameTV,operateStatusTV,registerDateTV,checkDateTV,startDateTV,endDateTV,registCapiTV,econNoTV,compZipTV,econKindTV,compTelTV,econNameTV,belongOrgTV,unifiedNoTV,compAddressTV,addressTV;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_industryinfo,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    protected void init(View view) {
        uuid=getArguments().getString("uuid");
        //法定代表
        operNameTV= (TextView) view.findViewById(R.id.tv_opername);
        //经营状态
        operateStatusTV= (TextView) view.findViewById(R.id.tv_operate_status);
        //成立日期
        registerDateTV= (TextView) view.findViewById(R.id.tv_register_date);
        //发照日期
        checkDateTV= (TextView) view.findViewById(R.id.tv_check_date);
        // 营业期限自
        startDateTV= (TextView) view.findViewById(R.id.tv_start_date);
        //营业期限至
        endDateTV= (TextView) view.findViewById(R.id.tv_end_date);
        //注册资本
        registCapiTV= (TextView) view.findViewById(R.id.tv_regist_capi);
        //注册号
        econNoTV= (TextView) view.findViewById(R.id.tv_econ_no);
        //邮政编码
        compZipTV= (TextView) view.findViewById(R.id.tv_comp_zip);
        //企业类型
        econKindTV= (TextView) view.findViewById(R.id.tv_econ_kind);
        //企业联系电话
        compTelTV= (TextView) view.findViewById(R.id.tv_comp_tel);
        //企业名称
        econNameTV= (TextView) view.findViewById(R.id.tv_econ_name);
        //登记机构
        belongOrgTV= (TextView) view.findViewById(R.id.tv_belong_org);
        //统一社会信用代码
        unifiedNoTV= (TextView) view.findViewById(R.id.tv_unified_no);
        //企业联系地址
        compAddressTV= (TextView) view.findViewById(R.id.tv_comp_address);
        //住所
        addressTV= (TextView) view.findViewById(R.id.tv_address);

        rootView= (ScrollView) view.findViewById(R.id.root_view);

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onFirstUserVisible() {

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(rootView, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {


                IndustryInfoFragment.this.setRetryEvent(retryView);

            }

        });

        mLoadingAndRetryManager.showLoading();
        loadDate(uuid);


    }

    @Override
    protected int getContentViewLayoutID() {

        return R.layout.fragment_industryinfo;
    }



    private void loadDate(String uuid) {

        OkHttpUtils.post(UriHelper.getInstance().getIcInfoUrl(uuid)).execute(new IcinfoBeanCallBack() {

            @Override
            public void onError(okhttp3.Request request, @Nullable okhttp3.Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getActivity(),e.getMessage());
            }

            @Override
            public void onAfter(@Nullable IcinfoBean icinfoBean, okhttp3.Request request, okhttp3.Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onResponse(IcinfoBean icinfoBean) {
                if(icinfoBean!=null)
                    fullInfo(icinfoBean);
            }
        });
    }

    private void fullInfo(IcinfoBean icinfo) {
        econNameTV.setText(icinfo.getEconName());
        econKindTV.setText(icinfo.getEconKind());
        startDateTV.setText(icinfo.getStartDate());
        operNameTV.setText(icinfo.getOperName());
        registCapiTV.setText(icinfo.getRegistCapi());
        operateStatusTV.setText(icinfo.getRegisterStatus());
        registerDateTV.setText(icinfo.getStartDate());
        checkDateTV.setText(icinfo.getCheckDate());
        econNoTV.setText(icinfo.getEconNo());
        compZipTV.setText(icinfo.getCompZip());
        compTelTV.setText(icinfo.getCompTel());
        belongOrgTV.setText(icinfo.getBelongOrg());
        compAddressTV.setText(icinfo.getCompAddress());
        addressTV.setText(icinfo.getAddress());
    }


    public void setRetryEvent(View retryEvent) {
        View view = retryEvent.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "retry event invoked", Toast.LENGTH_SHORT).show();
                mLoadingAndRetryManager.showLoading();

            }
        });
    }

    
}
