package com.qihaosou.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.RecordBean;
import com.qihaosou.callback.RecordAllCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:网站备案信息
 */
public class WebInfoActivity extends BaseActivity{

    private ListView listView;
    private WebInfoAdapter adapter;
    private List<RecordBean> recordBeans;
    private String uuid="415419221aab4c60b7e217a37d46e685";
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.web_info_listview);

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("域名信息");
        recordBeans=new ArrayList<RecordBean>();
        adapter=new WebInfoAdapter(this,recordBeans);
        listView.setAdapter(adapter);
        getRecord(uuid);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_web_info;
    }

    //获取网站备案信息
    private void getRecord(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getRecordUrl(uuid)).tag(this).execute(new RecordAllCallBack() {
            @Override
            public void onResponse(List<RecordBean> recordBeans) {
                WebInfoActivity.this.recordBeans.clear();
               WebInfoActivity.this.recordBeans.addAll(recordBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class WebInfoAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<RecordBean> list;
        public WebInfoAdapter(Context context,List<RecordBean> list){
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
                convertView=inflater.inflate(R.layout.item_web_info,null);
                viewHolder.domainTV= (TextView) convertView.findViewById(R.id.tv_domain);
                viewHolder.acceptNoTV= (TextView) convertView.findViewById(R.id.tv_check_no);
                viewHolder.acceptTimeTV= (TextView) convertView.findViewById(R.id.tv_check_date);
                viewHolder.websitNameTV= (TextView) convertView.findViewById(R.id.tv_website_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            RecordBean recordBean=list.get(position);
            viewHolder.domainTV.setText(recordBean.getWebsiteDetail());
            viewHolder.acceptNoTV.setText(recordBean.getAcceptNo());
            viewHolder.websitNameTV.setText(String.format(getResources().getString(R.string.website_name),recordBean.getWebsitName()));
            viewHolder.acceptTimeTV.setText(String.format(getResources().getString(R.string.website_check_date),recordBean.getAcceptTime()));
            return convertView;
        }
    }
    static class ViewHolder{
        TextView domainTV,acceptNoTV,acceptTimeTV,websitNameTV;
    }
}
