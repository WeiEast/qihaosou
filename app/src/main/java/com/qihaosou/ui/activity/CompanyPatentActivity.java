package com.qihaosou.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.PartnerBean;
import com.qihaosou.bean.PatentOutlineBean;
import com.qihaosou.callback.PatentOutlineAllCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.view.SelectPatentCategoryPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/18
 * Description:专利列表
 */
public class CompanyPatentActivity extends BaseActivity implements View.OnClickListener{
    private TextView btnSelectCategroy;
    private SelectPatentCategoryPopup patentCategoryPopup;
    private ListView patentlistView;
    private List<PatentOutlineBean> list;
    private PatentAapter patentAapter;
    private String uuid;
    private String econName;
    @Override
    protected void init() {
        setTitle("");
        btnSelectCategroy= (TextView) findViewById(R.id.btn_select_patent_category);
        patentlistView= (ListView) findViewById(R.id.lv_compatent_list);
    }

    @Override
    protected void addListener() {
        btnSelectCategroy.setOnClickListener(this);
        patentlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatentOutlineBean patentOutlineBean=list.get(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("patentOutlineBean", patentOutlineBean);
                readyGo(PatentDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void addData() {
        list=new ArrayList<PatentOutlineBean>();
        uuid=getIntent().getExtras().getString("uuid");
        econName=getIntent().getExtras().getString("name");
        patentAapter=new PatentAapter(this,list);
        patentlistView.setAdapter(patentAapter);
        getPatentOutline(econName);

        patentCategoryPopup=new SelectPatentCategoryPopup(this);
        patentCategoryPopup.setSelectPatentCategoryListener(new SelectPatentCategoryPopup.SelectPatentCategoryListener() {
            @Override
            public void selected( String categoryName) {

                btnSelectCategroy.setText(categoryName);
            }
        });
        backgroundAlpha(1f);
        // 添加pop窗口关闭事件
        patentCategoryPopup.setOnDismissListener(new poponDismissListener());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_company_patent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select_patent_category:
                patentCategoryPopup.showAsDropDown(mToolbar, 0, 0);
                backgroundAlpha(0.7f);
                break;
        }
    }

    private void getPatentOutline(String econName){
        OkHttpUtils.post(UriHelper.getInstance().getPatentlistUrl(econName)).tag(this).execute(new PatentOutlineAllCallBack() {
            @Override
            public void onResponse(List<PatentOutlineBean> patentOutlineBeans) {
                list.addAll(patentOutlineBeans);
                patentAapter.notifyDataSetChanged();
            }
        });
    }
    class PatentAapter extends BaseAdapter{
        private Context context;
        private List<PatentOutlineBean> list;
        private LayoutInflater inflater;
        public PatentAapter(Context context ,List<PatentOutlineBean> list){
            this.context=context;
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
                convertView=inflater.inflate(R.layout.item_patent_outline,null);
                viewHolder.patentNameTV= (TextView) convertView.findViewById(R.id.tv_patent_name);
                viewHolder.patentTypeTV= (TextView) convertView.findViewById(R.id.tv_patent_type);
                viewHolder.publicDateTV= (TextView) convertView.findViewById(R.id.tv_public_date);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            PatentOutlineBean patentOutlineBean=list.get(position);
            viewHolder.patentNameTV.setText(patentOutlineBean.getName());
            viewHolder.patentTypeTV.setText(String.format(getString(R.string.patent_type), patentOutlineBean.getType()));
            viewHolder.publicDateTV.setText(String.format(getString(R.string.patent_public_date),patentOutlineBean.getPublicDate()));
            return convertView;
        }
    }
    static class ViewHolder{
        TextView patentNameTV,patentTypeTV,publicDateTV;
    }
}
