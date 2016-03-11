package com.qihaosou.ui.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.callback.IcinfoAllCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.ui.activity.EnterpriseDetailInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 * 我的关注
 */
public class MyCollectFragment extends Fragment{
    private ListView collectListView;
    private List<IcinfoBean> list;
    private ArrayAdapter<IcinfoBean> adapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
        addListener();
        addData();

    }

    private void addListener() {
        collectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uuid=list.get(position).getUuid();
                Bundle bundle=new Bundle();
                bundle.putString("uuid",uuid);
                Intent intent=new Intent(getActivity(), EnterpriseDetailInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void addData() {

        list=new ArrayList<IcinfoBean>();
        adapter=new ArrayAdapter<IcinfoBean>(getContext(),android.R.layout.simple_list_item_1,list);
        collectListView.setAdapter(adapter);
        getAttentList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_collect,null);
    }
    private void init(View view) {
        collectListView= (ListView) view.findViewById(R.id.my_collect_listview);

    }

    private void  getAttentList(){
        OkHttpUtils.post(UriHelper.getInstance().getAttentListUrl()).tag(this).execute(new IcinfoAllCallBack() {
            @Override
            public void onResponse(List<IcinfoBean> icinfoBeans) {
                if(icinfoBeans!=null){
                    list.addAll(icinfoBeans);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


}
