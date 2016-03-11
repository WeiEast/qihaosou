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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.bean.SearchHistoryBean;
import com.qihaosou.ui.activity.CompanySearchActivity;
import com.qihaosou.ui.activity.EnterpriseDetailInfoActivity;
import com.qihaosou.util.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/20.
 */
public class HistorySearchFragment extends Fragment {
    private ListView historyListView;
    private List<SearchHistoryBean> list;
    private EditText editText;
    private TextView btnClean;
   private  ArrayAdapter<SearchHistoryBean> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_search,null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof CompanySearchActivity){
            editText= (EditText) getActivity().findViewById(R.id.edt_search);

        }
        init(getView());
        addData();
    }

    private void addData() {
        list=DBUtils.getSearchHistory();
       adapter=new ArrayAdapter<SearchHistoryBean>(getContext(),android.R.layout.simple_list_item_1,list);
        historyListView.setAdapter(adapter);
    }

    private void init(View view) {
        historyListView= (ListView) view.findViewById(R.id.history_listview);
        btnClean= (TextView) view.findViewById(R.id.tv_beginclear_history);
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(list.get(position).getKey());
            }
        });
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.clearSearchHistory();
                list.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
