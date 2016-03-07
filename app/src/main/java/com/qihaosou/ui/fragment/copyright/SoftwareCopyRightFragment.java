package com.qihaosou.ui.fragment.copyright;

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
import com.qihaosou.R;
import com.qihaosou.bean.ComputerCopyrightBean;
import com.qihaosou.callback.ComputerCopyrightListCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:软件著作权
 */
public class SoftwareCopyRightFragment extends Fragment {
    private ListView listView;
    private SoftWareCopyRightAdapter adapter;
    private List<ComputerCopyrightBean> list;
    private String uuid="0a43123cd81a4bd69f9068d205a4f771";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_copy_right,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
        addDate();
    }

    private void addDate() {
        list=new ArrayList<ComputerCopyrightBean>();
        adapter=new SoftWareCopyRightAdapter(getContext(),list);
        listView.setAdapter(adapter);
        getSoftwareInfo(uuid);
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.copy_right_listview);
    }
    private void getSoftwareInfo(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getSoftWareCopyRightUrl(uuid)).tag(this).execute(new ComputerCopyrightListCallBack() {
            @Override
            public void onResponse(List<ComputerCopyrightBean> computerCopyrightBeans) {
                list.addAll(computerCopyrightBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }
    class SoftWareCopyRightAdapter extends BaseAdapter {
        private List<ComputerCopyrightBean> list;
        private LayoutInflater inflater;
        public SoftWareCopyRightAdapter(Context context,List<ComputerCopyrightBean> list){
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
                convertView=inflater.inflate(R.layout.item_software_copyright,null);
                viewHolder.nameTV= (TextView) convertView.findViewById(R.id.tv_software_name);
                viewHolder.typeTV= (TextView) convertView.findViewById(R.id.tv_software_type);
                viewHolder.regNoTV= (TextView) convertView.findViewById(R.id.tv_reg_no);
                viewHolder.classNoTV= (TextView) convertView.findViewById(R.id.tv_class_no);
                viewHolder.regDateTV= (TextView) convertView.findViewById(R.id.tv_reg_date);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            ComputerCopyrightBean bean=list.get(position);
            String[] names= bean.getSoftwareName().split("\\[");
            viewHolder.nameTV.setText(bean.getSoftwareName().split("\\[")[0]);
            if(names.length>1)
                viewHolder.typeTV.setText(bean.getSoftwareName().split("\\[")[1]);
            else
                viewHolder.typeTV.setVisibility(View.GONE);
            viewHolder.regNoTV.setText(String.format(getString(R.string.copyright_reg_no), bean.getRegNo()));
            viewHolder.classNoTV.setText(String.format(getString(R.string.copyright_class_no), bean.getClassNo()));
            viewHolder.regDateTV.setText(String.format(getString(R.string.copyright_reg_date),bean.getRegDate()));
            return convertView;
        }
    }
    static class ViewHolder{
        TextView nameTV,typeTV,regNoTV,classNoTV,regDateTV;
    }
}
