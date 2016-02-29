package com.qihaosou.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.EmpBean;
import com.qihaosou.bean.EmployeeBean;
import com.qihaosou.bean.UserBean;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/22
 * Description:主要人员
 * */
public class KeyPersonFragment extends Fragment{
    private GridView gridView;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private GridViewAdapter adapter;
    private List<EmployeeBean> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_key_person,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());

    }

    private void init(View view) {
        gridView= (GridView) view.findViewById(R.id.gv_key_person);
        mLoadingAndRetryManager=LoadingAndRetryManager.generate(gridView,null);
        list=new ArrayList<EmployeeBean>();
        adapter=new GridViewAdapter(getContext(),list);
        gridView.setAdapter(adapter);
        mLoadingAndRetryManager.showLoading();
      //  loadData();
    }
    class GridViewAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<EmployeeBean> list;
        public GridViewAdapter(Context context,List<EmployeeBean> list){
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
                convertView=inflater.inflate(R.layout.item_key_person,(ViewGroup)null);
                viewHolder.empNameTV= (TextView) convertView.findViewById(R.id.tv_person_name);
                viewHolder.empJbTV= (TextView) convertView.findViewById(R.id.tv_person_post);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.empNameTV.setText(list.get(position).getEmpName());
            viewHolder.empJbTV.setText(list.get(position).getEmpJob());

            return convertView;
        }
        class ViewHolder{
            TextView empNameTV,empJbTV;
        }
    }
//    private void loadData(){
//        String uuid="1f236e6b71484e06abf96130b6fe64aa";
//        if(NetUtils.isNetworkConnected(getContext())){
//            GsonRequest<EmpBean> gsonRequest=new GsonRequest<EmpBean>(Request.Method.POST, UriHelper.getInstance().getIcInfoEmpUrl(uuid),
//                    new Response.Listener<EmpBean>() {
//                        @Override
//                        public void onResponse(EmpBean response) {
//
//                            if(response==null || response.getBody().getEmployeeAll().size()==0){
//
//                                mLoadingAndRetryManager.showEmpty();}
//                            else{
//                                mLoadingAndRetryManager.showContent();
//                              fullData(response.getBody().getEmployeeAll());
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });
//
//
//            VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
//        }
//    }

    private void fullData(List<EmployeeBean> list) {
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
