package com.qihaosou.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.qihaosou.R;
import com.qihaosou.bean.IcBean;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoBody;
import com.qihaosou.listener.MyBeanCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.loading.OnLoadingAndRetryListener;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.view.LoadingDialog;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/22
 * Description:工商信息
 */
public class IndustryInfoFragment extends BaseFragment {
    LoadingAndRetryManager mLoadingAndRetryManager;
    ScrollView rootView;
    //法定代表 经营状态 成立日期 发照日期   营业期限自 营业期限至 注册资本 注册号 邮政编码 企业类型 企业联系电话 企业名称 登记机构 统一社会信用代码 企业联系地址 住所
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
        operNameTV= (TextView) view.findViewById(R.id.tv_opername);
        operateStatusTV= (TextView) view.findViewById(R.id.tv_operate_status);
        registerDateTV= (TextView) view.findViewById(R.id.tv_register_date);
        checkDateTV= (TextView) view.findViewById(R.id.tv_check_date);
        startDateTV= (TextView) view.findViewById(R.id.tv_start_date);
        endDateTV= (TextView) view.findViewById(R.id.tv_end_date);
        registCapiTV= (TextView) view.findViewById(R.id.tv_regist_capi);
        econNoTV= (TextView) view.findViewById(R.id.tv_econ_no);
        compZipTV= (TextView) view.findViewById(R.id.tv_comp_zip);
        econKindTV= (TextView) view.findViewById(R.id.tv_econ_kind);
        compTelTV= (TextView) view.findViewById(R.id.tv_comp_tel);
        econNameTV= (TextView) view.findViewById(R.id.tv_econ_name);
        belongOrgTV= (TextView) view.findViewById(R.id.tv_belong_org);
        unifiedNoTV= (TextView) view.findViewById(R.id.tv_unified_no);
        compAddressTV= (TextView) view.findViewById(R.id.tv_comp_address);
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
        final String uuid="1f236e6b71484e06abf96130b6fe64aa";
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               loadDate(uuid);
            }
        },2000);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_industryinfo;
    }



    private void loadDate(String uuid) {

//        if(NetUtils.isNetworkConnected(mContext)){
//            GsonRequest<IcBean> gsonRequest=new GsonRequest<IcBean>(Request.Method.POST, UriHelper.getInstance().getIcInfoUrl(uuid), new Response.Listener<IcBean>() {
//                @Override
//                public void onResponse(IcBean response) {
//                    if(response!=null){
//                        mLoadingAndRetryManager.showContent();
//                        fulldate(response);
//                    }else{
//                        mLoadingAndRetryManager.showEmpty();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                }
//            });
//            VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
//        }else{
//            mLoadingAndRetryManager.showEmpty();
//        }

        OkHttpUtils.post(UriHelper.getInstance().getIcInfoUrl(uuid)).execute(new MyBeanCallBack<String>() {
            @Override
            public void onResponse(String s) {
                L.e("login callback::" + s);
            }
        });
    }

    private void fulldate(IcBean response) {
        IcinfoBean icinfoBean=response.getBody().getIcinfo();
        operNameTV.setText(icinfoBean.getOperName());
        operateStatusTV.setText(icinfoBean.getRegisterStatus());
        registerDateTV.setText(icinfoBean.getStartDate());
        checkDateTV.setText(icinfoBean.getCheckDate());
        startDateTV.setText(icinfoBean.getTeamStart());
        endDateTV.setText(icinfoBean.getTeamEnd());
        registCapiTV.setText(icinfoBean.getRegistCapi());
        econNoTV.setText(icinfoBean.getEconNo());
        compZipTV.setText(icinfoBean.getCompZip());
        econKindTV.setText(icinfoBean.getEconKind());
        compTelTV.setText(icinfoBean.getCompTel());
        econNameTV.setText(icinfoBean.getEconName());
        belongOrgTV.setText(icinfoBean.getBelongOrg());
        compAddressTV.setText(icinfoBean.getCompAddress());
        addressTV.setText(icinfoBean.getAddress());
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
