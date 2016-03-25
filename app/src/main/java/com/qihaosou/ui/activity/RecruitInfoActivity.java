package com.qihaosou.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.RecruitInfoBean;
import com.qihaosou.callback.RecruitInfoCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:招聘信息
 */
public class RecruitInfoActivity extends BaseActivity{
    private ListView listView;
    private List<RecruitInfoBean> list;
    private RecruitInfoAdapter adapter;
    private String uuid;
    private String econName;
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.recruit_listview);
    }

    @Override
    protected void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RecruitInfoBean recruitInfoBean=list.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("RecruitInfoBean",recruitInfoBean);
                readyGo(RecruitDetailActivity.class,bundle);

            }
        });
    }

    @Override
    protected void addData() {
        setTitle("招聘信息");
        uuid=getIntent().getExtras().getString("uuid");
        econName=getIntent().getExtras().getString("name");
        list=new ArrayList<RecruitInfoBean>();
        adapter=new RecruitInfoAdapter(this,list);
        listView.setAdapter(adapter);
        getInfoList(econName);
    }

    private void getInfoList(String econName) {
        OkHttpUtils.post(UriHelper.getInstance().getRecruitInfoListUrl(econName)).tag(this).execute(new RecruitInfoCallBack() {
            @Override
            public void onResponse(List<RecruitInfoBean> recruitInfoBeans) {
                list.addAll(recruitInfoBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recruit_info;
    }
    class RecruitInfoAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<RecruitInfoBean> list;
        public RecruitInfoAdapter(Context context ,List<RecruitInfoBean> list){
            inflater=LayoutInflater.from(context);
            this.list=list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.item_recruit_info,null);
                viewHolder.jobNameTV= (TextView) convertView.findViewById(R.id.tv_recruit_coname);
                viewHolder.publicDateTV= (TextView) convertView.findViewById(R.id.tv_recruit_public_date);
                viewHolder.salaryTV= (TextView) convertView.findViewById(R.id.tv_recruit_salary);
                viewHolder.addressTV= (TextView) convertView.findViewById(R.id.tv_recruit_address);
                viewHolder.experienceTV= (TextView) convertView.findViewById(R.id.tv_recruit_experience);
                viewHolder.educationTV= (TextView) convertView.findViewById(R.id.tv_recruit_education);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            RecruitInfoBean recruitInfoBean=list.get(position);
            viewHolder.jobNameTV.setText(recruitInfoBean.getJobName());
            viewHolder.publicDateTV.setText(recruitInfoBean.getJobPubDate());
            viewHolder.salaryTV.setText(String.format(getString(R.string.job_salary), recruitInfoBean.getJobSalary()));
            viewHolder.addressTV.setText(String.format(getString(R.string.job_address), recruitInfoBean.getJobAddress()));
            viewHolder.experienceTV.setText(String.format(getString(R.string.job_experience), recruitInfoBean.getJobExperience()));
            viewHolder.educationTV.setText(String.format(getString(R.string.job_education),recruitInfoBean.getJobEducation()));

            return convertView;
        }
    }
    static class ViewHolder{
        TextView jobNameTV,publicDateTV,salaryTV,addressTV,experienceTV,educationTV;
    }
}
