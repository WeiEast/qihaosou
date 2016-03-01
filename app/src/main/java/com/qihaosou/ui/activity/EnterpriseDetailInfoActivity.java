package com.qihaosou.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.BeanCallBack;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoBody;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.loading.OnLoadingAndRetryListener;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LineGridView;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/1/20.
 * 企业详情
 */
public class EnterpriseDetailInfoActivity extends BaseActivity{
    private String uuid="aad4272599094555983094089e3a27bf";
    LoadingAndRetryManager mLoadingAndRetryManager;
    private LineGridView gridView;
    int[] resId = {R.mipmap.item_image_01,R.mipmap.item_image_02,R.mipmap.item_image_03,R.mipmap.item_image_04,
            R.mipmap.item_image_05,R.mipmap.item_image_06,R.mipmap.item_image_07,R.mipmap.item_image_08,R.mipmap.item_image_09,R.mipmap.item_image_10,R.mipmap.item_image_11,R.mipmap.item_image_12};
    String[] titles = {"工商信息","工商变更","年报","网站信息","商标","专利","著作权","法院诉讼","失信信息","资质","法院判决","招投标信息"};

    private TextView econ_nameTV,oper_nameTV,regist_capiTV,start_dateTV,register_statusTV,econ_kindTV;
    @Override
    protected void init() {
        setTitle("");
        gridView= (LineGridView) findViewById(R.id.line_gridview);
        gridView.setAdapter(new MyAdapter());
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, null);
        //公司名称
        econ_nameTV= (TextView) findViewById(R.id.tv_infodetial_name);
        //公司类型
        econ_kindTV= (TextView) findViewById(R.id.tv_infodetial_type);
        //公司营业状态
        register_statusTV= (TextView) findViewById(R.id.tv_compdetail_status);
        //法定代表
        oper_nameTV= (TextView) findViewById(R.id.tv_compdetail_opername);
        //注册资本
        regist_capiTV= (TextView) findViewById(R.id.tv_compdetail_registcapi);
        //成立日期
        start_dateTV= (TextView) findViewById(R.id.tv_compdetail_checkdate);
    }

    @Override
    protected void addListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 4:
                        readyGo(MarkDetailsActivity.class);
                        break;
                    case 5:
                        readyGo(CompanyPatentActivity.class);
                        break;
                    default:
                        readyGo(EnterpriseInfoDetailsActivity.class);
                }


            }
        });
    }

    @Override
    protected void addData() {
      //  loadData();
        mLoadingAndRetryManager.showLoading();
        getIcinfo(uuid);

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

                    mLoadingAndRetryManager.showContent();

            }
        }.start();


    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_enterprise_detailinfo1;
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return resId.length;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Holder holder = null;
            if(convertView == null){
                convertView = View.inflate(EnterpriseDetailInfoActivity.this, R.layout.item_enterprise_detailinfo_gridview, null);
                holder = new Holder();
                holder.tv = ((TextView) convertView.findViewById(R.id.btn_fun));
                convertView.setTag(holder);
            } else {
                holder = ((Holder) convertView.getTag());
            }
            holder.tv.setCompoundDrawablesWithIntrinsicBounds(0,resId[i],0,0);
            holder.tv.setText(titles[i]);
            return convertView;
        }
    }

    static class Holder{
        TextView tv;
    }
    public void setRetryEvent(View retryView)
    {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EnterpriseDetailInfoActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getIcinfo(String uuid){

        OkHttpUtils.post(UriHelper.getInstance().getIcInfoUrl(uuid)).tag(this).execute(new BeanCallBack<BaseBean<IcinfoBody>>() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable Exception e) {
                ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,"网络请求异常");
            }

            @Override
            public void onAfter(@Nullable BaseBean<IcinfoBody> icinfoBodyBaseBean, Request request, Response response, @Nullable Exception e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onResponse(BaseBean<IcinfoBody> icinfoBodyBaseBean) {
                if (icinfoBodyBaseBean != null) {
                    if ("0000".equals(icinfoBodyBaseBean.getCode())) {
                        fullinfo(icinfoBodyBaseBean.getBody().getIcinfo());
                    }else if("103".equals(icinfoBodyBaseBean.getCode())){
                        ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,"未登录");
                    }else{
                        ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,icinfoBodyBaseBean.getMessage());
                    }
                }
            }
        });
    }

    private void fullinfo(IcinfoBean icinfo) {
        if(icinfo!=null){
            econ_nameTV.setText(icinfo.getEconName());
            econ_kindTV.setText(icinfo.getEconKind());
            start_dateTV.setText(icinfo.getStartDate());
            oper_nameTV.setText(icinfo.getOperName());
            regist_capiTV.setText(icinfo.getRegistCapi());
            register_statusTV.setText(icinfo.getRegisterStatus());
        }
    }

}
