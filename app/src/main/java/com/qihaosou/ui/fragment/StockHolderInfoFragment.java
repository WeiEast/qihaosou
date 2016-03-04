package com.qihaosou.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.PartnerBean;
import com.qihaosou.callback.PartnerAllCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/23
 * Description:股东信息
 */
public class StockHolderInfoFragment extends Fragment {

    private String uuid="2ee37bb8b9ab4fe5ad6a8b9fdce26401";
    private ListView listView;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private List<PartnerBean> list;
    private PartnerAdapter  adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stockholderinfo,(ViewGroup)null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
        addDate();
    }

    private void addDate() {
        list=new ArrayList<PartnerBean>();
        adapter=new PartnerAdapter(getContext(),list);
        listView.setAdapter(adapter);
        mLoadingAndRetryManager= LoadingAndRetryManager.generate(listView,null);
        mLoadingAndRetryManager.showLoading();
        getPartnerInfo(uuid);
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.partner_listview);
    }

    //返回投资人信息
    private void getPartnerInfo(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getPartnerInfoUrl(uuid)).tag(this).execute(new PartnerAllCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getActivity().getApplication(),e.getMessage());
            }

            @Override
            public void onAfter(@Nullable List<PartnerBean> partnerBeans, Request request, Response response, @Nullable TaskException e) {
               mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onResponse(List<PartnerBean> partnerBeans) {
                list.clear();
                list.addAll(partnerBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class PartnerAdapter extends BaseAdapter{
        private List<PartnerBean> list;
        private LayoutInflater inflater;
        public PartnerAdapter(Context context,List<PartnerBean> list){
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
                convertView=inflater.inflate(R.layout.item_stockholderinfo,null);
                viewHolder.identifyNoTV= (TextView) convertView.findViewById(R.id.tv_cert_num);
                viewHolder.orgTV= (TextView) convertView.findViewById(R.id.tv_partner_type);
                viewHolder.stockNameTV= (TextView) convertView.findViewById(R.id.tv_partner);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            PartnerBean partnerBean=list.get(position);
            viewHolder.stockNameTV.setText(partnerBean.getStockName());
            viewHolder.orgTV.setText(partnerBean.getOrg());
            viewHolder.identifyNoTV.setText(partnerBean.getIdentifyNo());
            return convertView;
        }

    }
    static class ViewHolder{
        TextView stockNameTV,orgTV,identifyNoTV;
    }
}
