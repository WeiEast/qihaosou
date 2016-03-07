package com.qihaosou.ui.activity;

import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.PatentOutlineBean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:专利详情
 */
public class PatentDetailActivity extends BaseActivity {
    //专利名称
    private TextView patentNameTV;
    //申请号
    private TextView requestNoTV;
    //申请日
    private TextView requestDateTV;
    //授权公布号
    private TextView publicNoTV;
    //授权公布日期
    private TextView publicDateTV;
    //申请人
    private TextView requestPersonTV;
    //发明人
    private TextView inventorTV;
    //专利类型
    private TextView patentCategoryTV;
    //分类号
    private TextView categoryNoTV;
    //分类
    private TextView categoryTV;
    //法律状态
    private TextView legalStatusTV;
    //详细信息
    private TextView detailInfoTV;

    private PatentOutlineBean patentOutlineBean;
    @Override
    protected void init() {
        patentNameTV= (TextView) findViewById(R.id.tv_patent_name);
        requestNoTV= (TextView) findViewById(R.id.tv_request_number);
        requestDateTV= (TextView) findViewById(R.id.tv_request_date);
        publicNoTV= (TextView) findViewById(R.id.tv_public_no);
        publicDateTV= (TextView) findViewById(R.id.tv_public_date);
        requestPersonTV= (TextView) findViewById(R.id.tv_request_person);
        inventorTV= (TextView) findViewById(R.id.tv_inventor);
        patentCategoryTV= (TextView) findViewById(R.id.tv_patent_category);
        categoryNoTV= (TextView) findViewById(R.id.tv_category_no);
        categoryTV= (TextView) findViewById(R.id.tv_category);
        legalStatusTV= (TextView) findViewById(R.id.tv_legal_status);
        detailInfoTV= (TextView) findViewById(R.id.tv_detail_info);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle(getString(R.string.patent_detail));
        patentOutlineBean= (PatentOutlineBean) getIntent().getSerializableExtra("patentOutlineBean");
        getPatentInfo(patentOutlineBean);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_patent_detail;
    }

    private void getPatentInfo(PatentOutlineBean patentOutlineBean){
        patentNameTV.setText(patentOutlineBean.getName());
        requestNoTV.setText(patentOutlineBean.getRegNo());
        requestDateTV.setText(patentOutlineBean.getRegDate());
        publicNoTV.setText(patentOutlineBean.getPublicNo());
        publicDateTV.setText(patentOutlineBean.getPublicDate());
        requestPersonTV.setText(patentOutlineBean.getPerson());
        inventorTV.setText(patentOutlineBean.getInventor());
        patentCategoryTV.setText(patentOutlineBean.getType());
        categoryNoTV.setText(patentOutlineBean.getClassify());
        detailInfoTV.setText(patentOutlineBean.getMark());
    }
}
