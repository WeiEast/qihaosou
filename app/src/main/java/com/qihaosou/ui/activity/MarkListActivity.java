package com.qihaosou.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.CompanyLogoBean;
import com.qihaosou.callback.CompanyLogoAllCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/5
 * Description:商标列表
 */
public class MarkListActivity extends BaseActivity{
    private MarkAdapter adapter;
    private ListView listView;
    private List<CompanyLogoBean> companyLogoBeans;
    private String uuid;
    private String econName;
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.mark_listview);
    }

    @Override
    protected void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readyGo(MarkDetailsActivity.class);
            }
        });
    }

    @Override
    protected void addData() {
        setTitle("商标信息");
        uuid=getIntent().getExtras().getString("uuid");
        econName=getIntent().getExtras().getString("name");
        companyLogoBeans=new ArrayList<CompanyLogoBean>();
        adapter=new MarkAdapter(this,companyLogoBeans);
        listView.setAdapter(adapter);
        getMarkList(econName);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mark_list;
    }

    private void getMarkList(String econName){
        OkHttpUtils.post(UriHelper.getInstance().getMarkListUrl(econName)).tag(this).execute(new CompanyLogoAllCallBack() {
            @Override
            public void onResponse(List<CompanyLogoBean> companyLogoBeans) {
               MarkListActivity.this.companyLogoBeans.clear();
                MarkListActivity.this.companyLogoBeans.addAll(companyLogoBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }
    class MarkAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<CompanyLogoBean> companyLogoBeans;

        public MarkAdapter(Context context,List<CompanyLogoBean> companyLogoBeans){
            inflater=LayoutInflater.from(context);
            this.companyLogoBeans=companyLogoBeans;
        }
        @Override
        public int getCount() {
            return companyLogoBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return companyLogoBeans.get(position);
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
                convertView=inflater.inflate(R.layout.item_mark_listview,null);
                viewHolder.markIconIV= (ImageView) convertView.findViewById(R.id.mark_icon_iv);
                viewHolder.markNameTV= (TextView) convertView.findViewById(R.id.tv_mark_name);
                viewHolder.markCateTV= (TextView) convertView.findViewById(R.id.tv_mark_category);
                convertView.setTag(viewHolder);

            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            CompanyLogoBean companyLogoBean=companyLogoBeans.get(position);
            viewHolder.markNameTV.setText(companyLogoBean.getRegName());
            viewHolder.markCateTV.setText(companyLogoBean.getSbName());
            return convertView;
        }
    }
    static class ViewHolder{
        ImageView markIconIV;
        TextView markNameTV,markCateTV;
    }
}
