package com.qihaosou.ui.activity;

import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.TenderBean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:
 */
public class TenderDetailActivity extends BaseActivity {
    //中标公告
    private TextView tenderNameTV;
    //公告时间
    private TextView openTimeTV;
    //标讯地区
    private TextView regionTV;
    //公告编号
    private TextView tenderNoTV;
    //采购人
    private TextView procurementTV;
    //代理机构
    private TextView agentorgTV;

    private TenderBean tenderBean;
    @Override
    protected void init() {
        tenderNameTV= (TextView) findViewById(R.id.tv_tender_name);
        openTimeTV= (TextView) findViewById(R.id.tv_tender_opentime);
        regionTV= (TextView) findViewById(R.id.tv_tender_address);
        tenderNoTV= (TextView) findViewById(R.id.tv_tender_no);
        procurementTV= (TextView) findViewById(R.id.tv_tender_procurement);
        agentorgTV= (TextView) findViewById(R.id.tv_tender_agentorg);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("招投标详情");
        tenderBean= (TenderBean) getIntent().getSerializableExtra("TenderBean");
        fullInfo(tenderBean);
    }
    private void fullInfo(TenderBean tenderBean){
        if(tenderBean!=null){
            tenderNameTV.setText(tenderBean.getName());
            openTimeTV.setText(tenderBean.getOpenTime());
            regionTV.setText(tenderBean.getRegion());
            procurementTV.setText(tenderBean.getProcurement());
            agentorgTV.setText(tenderBean.getAgentOrg());
        }
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tender_detial;
    }
}
