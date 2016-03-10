package com.qihaosou.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.DishonestyBean;
import com.qihaosou.callback.DishonestyListCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/9
 * Description:失信列表
 */
public class DishonestyActivity extends BaseActivity {

    private DishonestyAdapter adapter;
    private ListView listView;
    private List<DishonestyBean> list;
    private String uuid="b2b285a5ffd645cfb84457089f3572f1";
    LoadingAndRetryManager mLoadingAndRetryManager;
    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.dishonesty_listview);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(listView, null);
    }

    @Override
    protected void addListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String autoID=list.get(position).getAutoID();
                Bundle bundle=new Bundle();
                bundle.putString("uuid",uuid);
                bundle.putString("autoID",autoID);
                readyGo(DishonestyDetailActivity.class,bundle);
            }
        });
    }

    @Override
    protected void addData() {
        setTitle("失信列表");
        mLoadingAndRetryManager.showLoading();
        list=new ArrayList<DishonestyBean>();
        adapter=new DishonestyAdapter(this,list);
        listView.setAdapter(adapter);
        getDishonestyList(uuid);
    }
    private void getDishonestyList(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getDishonestyListUrl(uuid)).tag(this).execute(new DishonestyListCallBack() {

            @Override
            public void onAfter(@Nullable List<DishonestyBean> dishonestyBeans, Request request, Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onResponse(List<DishonestyBean> dishonestyBeans) {
                list.addAll(dishonestyBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_dishonesty;
    }
    class DishonestyAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<DishonestyBean> list;
        public DishonestyAdapter(Context context,List<DishonestyBean> list){
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
                convertView=inflater.inflate(R.layout.item_dishonesty,null);
                viewHolder.iNameTV= (TextView) convertView.findViewById(R.id.tv_iname);
                viewHolder.caseCodeTV= (TextView) convertView.findViewById(R.id.tv_casecode);
                viewHolder.regDateTV= (TextView) convertView.findViewById(R.id.tv_regdate);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            DishonestyBean dishonestyBean=list.get(position);
            viewHolder.iNameTV.setText(dishonestyBean.getIname());
            viewHolder.caseCodeTV.setText(dishonestyBean.getCaseCode());
            viewHolder.regDateTV.setText(dishonestyBean.getRegDate().split(" ")[0]);
            return convertView;
        }
    }
    static class ViewHolder{
        TextView iNameTV,caseCodeTV,regDateTV;
    }
}
