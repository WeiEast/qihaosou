package com.qihaosou.ui.fragment.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoVagueQuery;
import com.qihaosou.bean.SearchHistoryBean;
import com.qihaosou.callback.IcinfoBeanCallBack;
import com.qihaosou.callback.IcinfoVagueQueryAllCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.ui.activity.CompanySearchActivity;
import com.qihaosou.ui.activity.EnterpriseDetailInfoActivity;
import com.qihaosou.util.DBUtils;
import com.qihaosou.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/9
 * Description:显示搜素结果
 */
public class SearchResultFragment extends Fragment{
    private ListView listView;
    private SearchResultAdapter adapter;
    private EditText editText;
    private List<IcinfoVagueQuery> resultlist;
    private String keyword;
    @Override
    public void onAttach(Context context) {


        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof CompanySearchActivity){
            editText= (EditText) getActivity().findViewById(R.id.edt_search);
            keyword=editText.getText().toString();
        }
        init(getView());
        addListener();
        addDate();
    }

    private void addDate() {
        resultlist=new ArrayList<IcinfoVagueQuery>();
        adapter=new SearchResultAdapter(getContext(),resultlist);
        listView.setAdapter(adapter);
        queryDate(keyword);
    }

    private void addListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().toString().length()>1){
                    keyword=editText.getText().toString();
                    queryDate(keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uuid=resultlist.get(position).getUuid();
                Bundle bundle=new Bundle();
                bundle.putString("uuid",uuid);
                Intent intent=new Intent(getActivity(), EnterpriseDetailInfoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void init(View view) {
        listView= (ListView) view.findViewById(R.id.search_result_listview);

    }
    private void queryDate(String keyWord){
        OkHttpUtils.post(UriHelper.getInstance().vagueQueryUrl(keyWord)).tag(this).execute(new IcinfoVagueQueryAllCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                if(e.getCode().equals("113")){
                    resultlist.clear();
                    adapter.notifyDataSetChanged();
                }
                ToastUtil.TextToast(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(List<IcinfoVagueQuery> icinfoVagueQueries) {

                DBUtils.saveSearch(new SearchHistoryBean(keyword));
                resultlist.clear();
                resultlist.addAll(icinfoVagueQueries);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_result,null);
    }

    class SearchResultAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<IcinfoVagueQuery> list;
        public SearchResultAdapter(Context context,List<IcinfoVagueQuery> list){
            inflater=LayoutInflater.from(context);
            this.list=list;
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
                convertView=inflater.inflate(R.layout.item_search_result,null);
                viewHolder.econNameTV= (TextView) convertView.findViewById(R.id.tv_econ_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            IcinfoVagueQuery bean=list.get(position);
            viewHolder.econNameTV.setText(bean.getEconName());
            return convertView;
        }
    }
    static class ViewHolder{
        TextView econNameTV;
    }
}
