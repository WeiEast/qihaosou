package com.qihaosou.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.ChangeInfoBean;
import com.qihaosou.callback.ChangeAllCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/23
 * Description:变更信息
 */
public class ChangeInfoFragment extends Fragment{
    private ListView listView;
    LoadingAndRetryManager mLoadingAndRetryManager;
    private List<ChangeInfoBean> changeInfoBeanList;
    private ChangeInfoAdapter adapter;
    private String uuid="2ee37bb8b9ab4fe5ad6a8b9fdce26401";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changinfo,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init(getView());
        loadData();
    }
    private void loadData()
    {
      mLoadingAndRetryManager.showLoading();
        getChangeAll(uuid);
    }
    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.lv_changeinfo);
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(listView, null);
        changeInfoBeanList=new ArrayList<ChangeInfoBean>();
       adapter=new ChangeInfoAdapter(getContext(),changeInfoBeanList);
        listView.setAdapter(adapter);
    }

    private void getChangeAll(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getChangeInfoUrl(uuid)).tag(this).execute(new ChangeAllCallBack() {
            @Override
            public void onAfter(@Nullable List<ChangeInfoBean> changeInfoBeans, Request request, Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getActivity().getApplicationContext(),e.getMessage());
            }

            @Override
            public void onResponse(List<ChangeInfoBean> changeInfoBeans) {

                changeInfoBeanList.addAll(changeInfoBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class ChangeInfoAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<ChangeInfoBean> list;
        public ChangeInfoAdapter(Context context,List<ChangeInfoBean> list){
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
           ViewHolder viewHolder;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.item_changeinfo,(ViewGroup)null);
                viewHolder.afterContentTV= (TextView) convertView.findViewById(R.id.tv_after_content);
                viewHolder.beforeContentTV= (TextView) convertView.findViewById(R.id.tv_before_content);
                viewHolder.changeDateTV= (TextView) convertView.findViewById(R.id.tv_change_date);
                viewHolder.projectNameTV= (TextView) convertView.findViewById(R.id.tv_project_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            ChangeInfoBean changeInfoBean=list.get(position);

            viewHolder.projectNameTV.setText(changeInfoBean.getProjectName());
            viewHolder.changeDateTV.setText(changeInfoBean.getChangeDate());
            viewHolder.beforeContentTV.setText(changeInfoBean.getBeforeContent());
            viewHolder.afterContentTV.setText(changeInfoBean.getAfterContent());

            return convertView;
        }
    }
    static class ViewHolder{
        TextView afterContentTV,beforeContentTV,changeDateTV,projectNameTV;
    }
}
