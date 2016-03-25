package com.qihaosou.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.adapter.HomeListViewAdapter;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.HotCompanyBean;
import com.qihaosou.callback.HotCompanyAllCallBack;
import com.qihaosou.ui.activity.DemoActivity;
import com.qihaosou.ui.activity.EnterpriseDetailInfoActivity;
import com.qihaosou.util.L;
import com.qihaosou.util.UIHelper;
import com.qihaosou.view.ProgressBarView;

import java.util.ArrayList;
import java.util.List;


/**
 *热门企业
 */
public class HotCompanyFragment extends StickHeaderListFragment implements HomeListViewAdapter.UpdatepProgressListener {
    private List<HotCompanyBean> list;
    HomeListViewAdapter adapter;
    public static HotCompanyFragment newInstance() {
        HotCompanyFragment fragment = new HotCompanyFragment();
        return fragment;
    }

    public static HotCompanyFragment newInstance(String title) {
        HotCompanyFragment fragment = new HotCompanyFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void bindData() {
        setAdapter();
        gethotInfo();
    }

    private void gethotInfo() {
        OkHttpUtils.post(Constants.GET_HOT_COMPANY_URL).tag(this).execute(new HotCompanyAllCallBack() {
            @Override
            public void onResponse(List<HotCompanyBean> hotCompanyBeans) {
                list.addAll(hotCompanyBeans);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setAdapter() {
        if (getActivity() == null) return;
        list=new ArrayList<HotCompanyBean>();
       adapter= new HomeListViewAdapter(getActivity(),list);
        getScrollView().setAdapter(adapter);
        getScrollView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString("uuid",list.get(position).getUuid());
                UIHelper.showEnterpriseDetailInfoActivity(getActivity(),bundle);
            }
        });
        adapter.setUpdateprogress(this);
    }

    @Override
    public void updateProgress(int index,int progress) {
        updateView(index, progress);
    }
    private void updateView(int index,int progress)
    {
        int visiblestartPos = getScrollView().getFirstVisiblePosition();
        int visibleendPos =getScrollView().getLastVisiblePosition();
        L.e(visiblestartPos + "........."+visibleendPos);
        if(index>=visiblestartPos && index<=visibleendPos){
            int offset = index - visiblestartPos;
            View view = getScrollView().getChildAt(offset);
            if(view.getTag() instanceof HomeListViewAdapter.ViewHolder){
                L.e(""+index);
                HomeListViewAdapter.ViewHolder holder = (HomeListViewAdapter.ViewHolder)view.getTag();
                holder.progressBarView.setProgressSync(progress);

            }
        }
    }


    public class CompanyBean{

        private String companyName;
        private String legalPerson;
        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }


    }


}
