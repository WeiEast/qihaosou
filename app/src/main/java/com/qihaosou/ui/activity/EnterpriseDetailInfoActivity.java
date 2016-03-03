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
import com.lzy.okhttputils.https.TaskException;
import com.qihaosou.R;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.HomepageBean;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoBody;
import com.qihaosou.bean.IcinfoListBody;
import com.qihaosou.callback.HomePageBeanCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.loading.OnLoadingAndRetryListener;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LineGridView;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/1/20.
 * 企业详情
 */
public class EnterpriseDetailInfoActivity extends BaseActivity implements View.OnClickListener{
    private String uuid="2ee37bb8b9ab4fe5ad6a8b9fdce26401";
    LoadingAndRetryManager mLoadingAndRetryManager;
    private LineGridView gridView;
    int[] resId = {R.mipmap.item_image_01,R.mipmap.item_image_02,R.mipmap.item_image_03,R.mipmap.item_image_04,
            R.mipmap.item_image_05,R.mipmap.item_image_06,R.mipmap.item_image_07,R.mipmap.item_image_08,R.mipmap.item_image_09,R.mipmap.item_image_10,R.mipmap.item_image_11,R.mipmap.item_image_12};
    String[] titles = {"工商信息","工商变更","年报","网站信息","商标","专利","著作权","法院诉讼","失信信息","资质","法院判决","招投标信息"};
    private TextView btnAttent;
    private TextView econ_nameTV,oper_nameTV,regist_capiTV,start_dateTV,register_statusTV,econ_kindTV,refreshTV;
    private List<IcinfoBean> list;
    private Boolean isAttent=false;//是否关注
    //浏览量
    private int readCount;
    //工商更改数量
    private int changerecordsCount;
    //年报数量
    private int annualCount;
    //网站数量
    private int webCount;
    //
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
        //更新时间
        refreshTV= (TextView) findViewById(R.id.tv_infodetial_refresh);
        //关注
        btnAttent= (TextView) findViewById(R.id.tv_infordetial_attention);
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
        btnAttent.setOnClickListener(this);
    }

    @Override
    protected void addData() {
      //  loadData();
        mLoadingAndRetryManager.showLoading();
        getHomePage(uuid);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_infordetial_attention://关注,取消关注
                if(isAttent)
                    cancelAttent(uuid);
                else
                    attent(uuid);
                break;
        }
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

    private void getHomePage(String uuid){

        OkHttpUtils.post(UriHelper.getInstance().getHomePageUrl(uuid)).tag(this).execute(new HomePageBeanCallBack() {

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onAfter(@Nullable HomepageBean homepageBean, Request request, Response response, @Nullable TaskException e) {
                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onResponse(HomepageBean homepageBean) {
                if (homepageBean != null)
                    fullinfo(homepageBean);
            }
        });
    }

    private void fullinfo(HomepageBean homepageBean) {

        econ_nameTV.setText(homepageBean.getEconName());
        econ_kindTV.setText(homepageBean.getEconKind());
        start_dateTV.setText(homepageBean.getStartDate());
        oper_nameTV.setText(homepageBean.getOperName());
        regist_capiTV.setText(homepageBean.getRegistCapi());
        register_statusTV.setText(homepageBean.getRegisterStatus());
        refreshTV.setText(homepageBean.getUpdateTime());
    }
    //关注
    private void attent(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getAttentUrl(uuid)).tag(this).execute(new BeanCallBack<BaseBean>() {

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,getString(R.string.network_exception));
            }

            @Override
            public void onResponse(BaseBean baseBean) {
                if(baseBean!=null){
                    if("0000".equals(baseBean.getCode()))
                        isAttent=true;
                    ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,baseBean.getMessage());

                }

            }
        });
    }
    //取消关注
    private void cancelAttent(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().cancelAttentUrl(uuid)).tag(this).execute(new BeanCallBack<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                if(baseBean!=null){
                    if("0000".equals(baseBean.getCode())){
                        isAttent=false;
                    }
                    ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,baseBean.getMessage());
                }
            }

            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(EnterpriseDetailInfoActivity.this,getString(R.string.network_exception));
            }
        });
    }

}
