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

import com.qihaosou.R;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.util.L;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/23
 * Description:变更信息
 */
public class ChangeInfoFragment extends Fragment{
    private ListView listView;
    LoadingAndRetryManager mLoadingAndRetryManager;
    SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_changinfo,(ViewGroup)null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init(getView());
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(swipeRefreshLayout, null);
        loadData();
    }
    private void loadData()
    {
        mLoadingAndRetryManager.showLoading();

        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                mLoadingAndRetryManager.showEmpty();

            }
        }.start();


    }
    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.lv_changeinfo);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        MyAdapter adapter=new MyAdapter(getContext());
        listView.setAdapter(adapter);
    }
    class MyAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        public MyAdapter(Context context){
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return 2;
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
            convertView=inflater.inflate(R.layout.item_changeinfo,(ViewGroup)null);
            return convertView;
        }
    }
}
