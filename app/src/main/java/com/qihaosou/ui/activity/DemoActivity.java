package com.qihaosou.ui.activity;

import android.view.View;
import android.widget.ListView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.adapter.HomeListViewAdapter;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.HotCompanyBean;
import com.qihaosou.callback.HotCompanyAllCallBack;
import com.qihaosou.util.L;
import com.qihaosou.view.ProgressBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/27
 * Description:
 */
public class DemoActivity extends BaseActivity implements HomeListViewAdapter.UpdatepProgressListener{
    private ProgressBarView  view;
    ListView listView;
    private List<HotCompanyBean> list;
    HomeListViewAdapter adapter;

    @Override
    protected void init() {
        listView= (ListView) findViewById(R.id.list_view);
        view= (ProgressBarView) findViewById(R.id.demobar);
        view.setProgress(1600);
        view.setMax(2000);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("Demo");
        list=new ArrayList<HotCompanyBean>();


        gethotInfo();
    }
    private void gethotInfo() {
        OkHttpUtils.post(Constants.GET_HOT_COMPANY_URL).tag(this).execute(new HotCompanyAllCallBack() {
            @Override
            public void onResponse(List<HotCompanyBean> hotCompanyBeans) {
                list.addAll(hotCompanyBeans);
                adapter= new HomeListViewAdapter(DemoActivity.this,list);
                adapter.setUpdateprogress(DemoActivity.this);
                listView.setAdapter(adapter);
            }
        });
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_demo;
    }

    @Override
    public void updateProgress(int index, int progress) {
        updateView(index, progress);
    }
    private void updateView(int index,int progress)
    {
        int visiblestartPos = listView.getFirstVisiblePosition();
        int visibleendPos =listView.getLastVisiblePosition();
        L.e(visiblestartPos + "........." + visibleendPos);
        if(index>=visiblestartPos && index<=visibleendPos){
            int offset = index - visiblestartPos;
            View view = listView.getChildAt(offset);
            if(view.getTag() instanceof HomeListViewAdapter.ViewHolder){
                L.e(""+index);
                HomeListViewAdapter.ViewHolder holder = (HomeListViewAdapter.ViewHolder)view.getTag();
                holder.progressBarView.setProgress(progress);

            }
        }
    }
}
