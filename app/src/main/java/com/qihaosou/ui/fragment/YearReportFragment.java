package com.qihaosou.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.qihaosou.bean.AnnualBean;
import com.qihaosou.callback.AnnualAllCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.ui.activity.YearReportDetailsActivity;
import com.qihaosou.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/23
 * Description:年报
 */
public class YearReportFragment extends Fragment{
    private ListView listView;
    private YearReportAdapter adapter;
    private List<AnnualBean> list;
    private String uuid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_year_report,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uuid=getArguments().getString("uuid");
        init(getView());
        addData();
    }

    private void addData() {
        list=new ArrayList<AnnualBean>();
        adapter=new YearReportAdapter(getContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), YearReportDetailsActivity.class);
                intent.putExtra("AnnualBean" ,list.get(position));
                startActivity(intent);
            }
        });
        getYearReoprtList(uuid);
    }

    private void getYearReoprtList(String uuid) {
        OkHttpUtils.post(UriHelper.getInstance().getAnnualListUrl(uuid)).tag(this).execute(new AnnualAllCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getActivity().getApplication(),e.getMessage());
            }

            @Override
            public void onResponse(List<AnnualBean> annualBeans) {
                list.clear();
                list.addAll(annualBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.lv_year_report);

    }



    class YearReportAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<AnnualBean> list;
        public YearReportAdapter(Context context,List<AnnualBean> list){
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
                convertView=inflater.inflate(R.layout.item_year_report,(ViewGroup)null);
                viewHolder.yearTV= (TextView) convertView.findViewById(R.id.tv_year_report);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            AnnualBean annualBean=list.get(position);
            viewHolder.yearTV.setText(annualBean.getSubmittedYear());
            return convertView;
        }
    }
    static class ViewHolder{
        TextView yearTV;
    }

}
