package com.qihaosou.ui.fragment.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.qihaosou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 * 我的关注
 */
public class MyCollectFragment extends Fragment{
    private ListView collectListView;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_collect,null);
    }
    private void init(View view) {
        collectListView= (ListView) view.findViewById(R.id.my_collect_listview);
        List<String> list=new ArrayList<String>();
        for(int i=0;i<10;i++)
            list.add("google ....");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        collectListView.setAdapter(adapter);
    }
}
