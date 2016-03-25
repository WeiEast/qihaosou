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
import com.qihaosou.bean.WorkCopyrightBean;
import com.qihaosou.callback.WorkCopyrightListCallBack;
import com.qihaosou.net.UriHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/7
 * Description:作品著作权
 */
public class WorkCopyRightFragment extends Fragment {
    private ListView listView;
    private List<WorkCopyrightBean> list;
    private WorkCopyRightAdapter adapter;
    private String uuid;
    private String econName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_copy_right,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
        addDate();
    }

    private void addDate() {
        uuid=getArguments().getString("uuid");
        econName=getArguments().getString("name");
        list=new ArrayList<WorkCopyrightBean>();
        adapter=new WorkCopyRightAdapter(getContext(),list);
        listView.setAdapter(adapter);
        getworkInfo(econName);
    }

    private void getworkInfo(String econName) {
        OkHttpUtils.post(UriHelper.getInstance().getWorkCopyRightUrl(econName)).tag(this).execute(new WorkCopyrightListCallBack() {
            @Override
            public void onResponse(List<WorkCopyrightBean> workCopyrightBeans) {
                list.addAll(workCopyrightBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.copy_right_listview);
    }

    class WorkCopyRightAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<WorkCopyrightBean> list;
        public WorkCopyRightAdapter(Context context ,List<WorkCopyrightBean> list){
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
                convertView=inflater.inflate(R.layout.item_work_copyright,null);
                viewHolder.nameTV= (TextView) convertView.findViewById(R.id.tv_copy_right_name);
                viewHolder.categoryTV= (TextView) convertView.findViewById(R.id.tv_copy_right_category);
                viewHolder.regNoTV= (TextView) convertView.findViewById(R.id.tv_copy_right_reg_no);
                viewHolder.regDateTV= (TextView) convertView.findViewById(R.id.tv_copy_right_reg_date);
                viewHolder.publicDateTV= (TextView) convertView.findViewById(R.id.tv_copy_right_public_date);
                viewHolder.completeDateTV= (TextView) convertView.findViewById(R.id.tv_copy_right_complete_date);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            WorkCopyrightBean workCopyrightBean=list.get(position);
            viewHolder.nameTV.setText(workCopyrightBean.getWorkName());
            viewHolder.categoryTV.setText(String.format(getString(R.string.patent_type), workCopyrightBean.getWorktype()));
            viewHolder.regNoTV.setText(String.format(getString(R.string.copyright_reg_no),workCopyrightBean.getRegNo()));
            viewHolder.regDateTV.setText(String.format(getString(R.string.copyright_reg_date),workCopyrightBean.getPublicDate()));
            viewHolder.publicDateTV.setText(String.format(getString(R.string.copyright_public_date),workCopyrightBean.getPublicDate()));
            viewHolder.completeDateTV.setText(String.format(getString(R.string.copyright_complete_date),workCopyrightBean.getCompleteDate()));
            return convertView;
        }
    }
    static class ViewHolder{
        TextView nameTV,categoryTV,regNoTV,regDateTV,publicDateTV,completeDateTV;
    }
}
