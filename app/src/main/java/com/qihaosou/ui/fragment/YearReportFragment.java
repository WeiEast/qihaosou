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

import com.qihaosou.R;
import com.qihaosou.ui.activity.YearReportDetailsActivity;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/23
 * Description:年报
 */
public class YearReportFragment extends Fragment{
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_year_report,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.lv_year_report);
        MyAdapter adapter=new MyAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), YearReportDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        public MyAdapter(Context context){
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=inflater.inflate(R.layout.item_year_report,(ViewGroup)null);
            return convertView;
        }
    }
}
