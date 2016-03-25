package com.qihaosou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.HotCompanyBean;
import com.qihaosou.view.ProgressBarView;

import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/25
 * Description:
 */

    public class HomeListViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<HotCompanyBean> list;
        private UpdatepProgressListener updatepProgressListener;
        public HomeListViewAdapter(Context context,List<HotCompanyBean> list){
            this.list=list;
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }
        public void setUpdateprogress(UpdatepProgressListener updatepProgressListener){
            this.updatepProgressListener=updatepProgressListener;
        }
        @Override
        public Object getItem(int position) {
            return position;
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
                convertView=inflater.inflate(R.layout.item_ecently_browse,null);
                viewHolder.companyNameTV= (TextView) convertView.findViewById(R.id.tv_company_name);
                viewHolder.lagerPersonTV= (TextView) convertView.findViewById(R.id.tv_legal_person);
                viewHolder.progressBarView= (ProgressBarView) convertView.findViewById(R.id.progressbar);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            HotCompanyBean companyBean=list.get(position);
            viewHolder.companyNameTV.setText(companyBean.getEconName());
            viewHolder.lagerPersonTV.setText(companyBean.getOperName());
            int prorgess=Integer.parseInt(companyBean.getHot());
            viewHolder.progressBarView.setProgress(1442);
          //  updatepProgressListener.updateProgress(position,prorgess);
            return convertView;
        }
        public static class ViewHolder{
          public  TextView companyNameTV,lagerPersonTV;
         public   ProgressBarView progressBarView;
        }
    public interface UpdatepProgressListener{
        void updateProgress(int index,int progress);
    }
    }

