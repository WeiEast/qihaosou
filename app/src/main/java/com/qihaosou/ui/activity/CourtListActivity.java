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
import com.qihaosou.bean.CourtBean;
import com.qihaosou.callback.CourtAllCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:法院列表
 */
public class CourtListActivity extends BaseActivity{
    private ListView listView;
    private List<CourtBean> list;
    private CourtAdapter adapter;
    private String uuid="efbc0d4d76cc4d59981363f0cc385924";
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.court_listview);
    }

    @Override
    protected void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CourtBean courtBean=list.get(position);
                Bundle bundle=new Bundle();
                bundle.putString("docId",courtBean.getDocId());
                bundle.putString("uuid",uuid);
                readyGo(CourtDocActivity.class,bundle);
            }
        });
    }

    @Override
    protected void addData() {
        setTitle("法院诉讼列表");
        list=new ArrayList<CourtBean>();
        adapter=new CourtAdapter(this,list);
        listView.setAdapter(adapter);
        getCourtList(uuid);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_courtlist;
    }
    private void getCourtList(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getCourtListUrl(uuid)).tag(this).execute(new CourtAllCallBack() {
            @Override
            public void onResponse(List<CourtBean> courtBeans) {
                list.addAll(courtBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class CourtAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<CourtBean> list;
        public CourtAdapter(Context context,List<CourtBean> list){
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
                convertView=inflater.inflate(R.layout.item_court,null);
                viewHolder.caseNameTV= (TextView) convertView.findViewById(R.id.tv_case_name);
                viewHolder.caseNumberTV= (TextView) convertView.findViewById(R.id.tv_case_number);
                viewHolder.caseDateTV= (TextView) convertView.findViewById(R.id.tv_case_update_date);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            CourtBean courtBean=list.get(position);
            viewHolder.caseNameTV.setText(courtBean.getCaseName());
            viewHolder.caseNumberTV.setText(courtBean.getCaseNumber());
            viewHolder.caseDateTV.setText(courtBean.getUpdateDate().split(" ")[0]);
            return convertView;
        }
    }
    static class ViewHolder{
        TextView caseNameTV,caseNumberTV,caseDateTV;
    }
}
