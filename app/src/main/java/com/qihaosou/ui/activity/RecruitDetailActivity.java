package com.qihaosou.ui.activity;

import android.webkit.WebView;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.RecruitInfoBean;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:招聘详情
 */
public class RecruitDetailActivity extends BaseActivity {
    //职位名称
    private TextView jobNameTV;
    //工作地点
    private TextView jobAddressTV;
    //发布时间
    private TextView publicDateTV;
    //待遇
    private TextView salaryTV;
    //工作经验
    private TextView experienceTV;
    //工作性质
    private TextView jobTypeTV;
    //学历要求
    private TextView educationTV;
    //招聘人数
    private TextView jobNumberTV;
    //职位描述
    private WebView jobDetailWV;

    private RecruitInfoBean infobean;
    @Override
    protected void init() {
        jobNameTV= (TextView) findViewById(R.id.tv_job_name);
        jobAddressTV= (TextView) findViewById(R.id.tv_job_address);
        publicDateTV= (TextView) findViewById(R.id.tv_job_public_date);
        salaryTV= (TextView) findViewById(R.id.tv_job_salary);
        experienceTV= (TextView) findViewById(R.id.tv_job_experience);
        jobTypeTV= (TextView) findViewById(R.id.tv_job_type);
        educationTV= (TextView) findViewById(R.id.tv_job_education);
        jobNumberTV= (TextView) findViewById(R.id.tv_job_number);
        jobDetailWV= (WebView) findViewById(R.id.tv_job_detail);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("职位详情");
        infobean= (RecruitInfoBean) getIntent().getSerializableExtra("RecruitInfoBean");
        fullInfo(infobean);
    }
    private void fullInfo(RecruitInfoBean recruitinfobean){
        if(recruitinfobean!=null){
            jobNameTV.setText(recruitinfobean.getJobName());
            jobAddressTV.setText(recruitinfobean.getJobAddress());
            publicDateTV.setText(recruitinfobean.getJobPubDate());
            salaryTV.setText(recruitinfobean.getJobSalary());
            experienceTV.setText(recruitinfobean.getJobExperience());
            jobTypeTV.setText(recruitinfobean.getJobType());
            educationTV.setText(recruitinfobean.getJobEducation());
            jobNumberTV.setText(recruitinfobean.getJobNumber());

            jobDetailWV.loadDataWithBaseURL(null, recruitinfobean.getJobWorkDetail(), "text/html", "utf-8", null);
        }
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recruit_detail;
    }
}
