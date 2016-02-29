package com.qihaosou.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.ui.activity.DemoActivity;
import com.qihaosou.view.ProgressBarView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeListViewFragment extends StickHeaderListFragment {

    public static HomeListViewFragment newInstance() {
        HomeListViewFragment fragment = new HomeListViewFragment();
        return fragment;
    }

    public static HomeListViewFragment newInstance(String title) {
        HomeListViewFragment fragment = new HomeListViewFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void bindData() {
        setAdapter();
    }

    public void setAdapter() {
        if (getActivity() == null) return;

//        int size = 100;
//        String[] stringArray = new String[size];
//        for (int i = 0; i < size; ++i) {
//            stringArray[i] = ""+i;
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringArray);

        HomeListViewAdapter adapter=new HomeListViewAdapter(getActivity(),null);
        getScrollView().setAdapter(adapter);
        getScrollView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), DemoActivity.class);
                startActivity(intent);
            }
        });
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
    public class HomeListViewAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<CompanyBean> list;
        public HomeListViewAdapter(Context context,List<CompanyBean> list){
            this.list=list;
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return 20;
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
            ViewHolder viewHolder=null;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView=inflater.inflate(R.layout.item_ecently_browse,(ViewGroup)null);
                viewHolder.companyNameTV= (TextView) convertView.findViewById(R.id.tv_company_name);
                viewHolder.lagerPersonTV= (TextView) convertView.findViewById(R.id.tv_legal_person);
                viewHolder.progressBarView= (ProgressBarView) convertView.findViewById(R.id.progressbar);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.progressBarView.setProgressSync(1800);
            return convertView;
        }
        class ViewHolder{
            TextView companyNameTV,lagerPersonTV;
            ProgressBarView progressBarView;
        }
    }
}
