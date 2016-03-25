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
import com.qihaosou.bean.TenderBean;
import com.qihaosou.callback.TenderAllCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:招投标
 */
public class TenderActivity extends BaseActivity {
    private ListView listView;
    private List<TenderBean> list;
    private TenderAdapter adapter;
    private String uuid;
    private String econName;
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.tender_listview);
    }

    @Override
    protected void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TenderBean tenderBean=list.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("TenderBean",tenderBean);
                readyGo(TenderDetailActivity.class,bundle);
            }
        });
    }

    @Override
    protected void addData() {
        setTitle("招投标信息");
        uuid=getIntent().getExtras().getString("uuid");
        econName=getIntent().getExtras().getString("name");
        list=new ArrayList<TenderBean>();
        adapter=new TenderAdapter(this,list);
        listView.setAdapter(adapter);
        getTenderList(econName);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tender;
    }
    private void getTenderList(String econName){
        OkHttpUtils.post(UriHelper.getInstance().getTenderListUrl(econName)).tag(this).execute(new TenderAllCallBack() {
            @Override
            public void onResponse(List<TenderBean> tenderBeans) {
                list.addAll(tenderBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class TenderAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<TenderBean> list;
        public TenderAdapter(Context context ,List<TenderBean> list){
            this.list=list;
            inflater=LayoutInflater.from(context);
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
                convertView=inflater.inflate(R.layout.item_tender,null);
                viewHolder.tenderNameTV= (TextView) convertView.findViewById(R.id.tv_tender_name);
                viewHolder.tenderRegionTV= (TextView) convertView.findViewById(R.id.tv_tender_region);
                viewHolder.openTimeTV= (TextView) convertView.findViewById(R.id.tv_tender_opentime);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            TenderBean tenderBean=list.get(position);
            viewHolder.tenderNameTV.setText(tenderBean.getName());
            viewHolder.tenderRegionTV.setText(tenderBean.getRegion());
            viewHolder.openTimeTV.setText(tenderBean.getOpenTime());
            return convertView;
        }
    }
    static class ViewHolder{
        TextView tenderNameTV,tenderRegionTV,openTimeTV;
    }
}
