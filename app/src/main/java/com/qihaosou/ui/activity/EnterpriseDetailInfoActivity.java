package com.qihaosou.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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
import com.qihaosou.bean.HomePageGridViewBean;
import com.qihaosou.bean.HomepageBean;
import com.qihaosou.bean.IcinfoBean;
import com.qihaosou.bean.IcinfoBody;
import com.qihaosou.bean.IcinfoListBody;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.callback.HomePageBeanCallBack;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.loading.LoadingAndRetryManager;
import com.qihaosou.loading.OnLoadingAndRetryListener;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.LineGridView;

import java.util.ArrayList;
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
    String[] titles = {"工商信息","工商变更","年报","网站信息","商标","专利","著作权","法院诉讼","失信信息","资质","招聘","招投标信息"};
    private TextView btnAttent;
    private TextView btnComment;
    private TextView econ_nameTV,oper_nameTV,regist_capiTV,start_dateTV,register_statusTV,econ_kindTV,refreshTV;
    private TextView readCountTV;
    private List<IcinfoBean> list;
    private List<HomePageGridViewBean> gridViewlist;
    private HomePageGridViewAdapter gridViewAdapter;
    //浏览量
    private int readCount;
    //工商更改数量
    private int changerecordsCount;
    //年报数量
    private int annualCount;
    //网站数量
    private int webCount;
    //商标数量
    private int logoCount;
    //专利数量
    private int patentCount;
    //著作权数量
    private int copyrightCount;
    //法院诉讼数量
    private int courtCount;
    //失信信息数量
    private int dishonestyCount;
    //资质数量
    private int qualificationCount;
    //招聘信息数量
    private int recruitCount;
    //招标数量
    private int tendersCount;
    //关注状态
    private Boolean attentionStatus=false;
    //企业头像
    private String companyAvatar;
    //创建时间
    private String createDate;
    @Override
    protected void init() {

        gridView= (LineGridView) findViewById(R.id.line_gridview);

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
        //阅读量
        readCountTV= (TextView) findViewById(R.id.tv_detailinfo_attention);
        //评论
        btnComment= (TextView) findViewById(R.id.tv_infordetial_comment);
    }

    @Override
    protected void addListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                    case 1:
                    case 2:
                        readyGo(EnterpriseInfoDetailsActivity.class);
                        break;
                    case 3:
                        readyGo(WebInfoActivity.class);
                        break;
                    case 4:
                        readyGo(MarkListActivity.class);
                        break;
                    case 5:
                        readyGo(CompanyPatentActivity.class);
                        break;
                    case 6:
                        readyGo(CopyRightActivity.class);
                        break;
                    case 7:
                        readyGo(CourtListActivity.class);
                        break;
                    case 10:
                        readyGo(RecruitInfoActivity.class);
                        break;
                    case 11:
                        readyGo(TenderActivity.class);
                        break;
                }


            }
        });
        btnAttent.setOnClickListener(this);
        btnComment.setOnClickListener(this);
    }

    @Override
    protected void addData() {
      //  loadData();
        setTitle("企业详情");
        mLoadingAndRetryManager.showLoading();
        gridViewlist=new ArrayList<HomePageGridViewBean>();
        initGridViewData();
        gridViewAdapter=new HomePageGridViewAdapter(this,gridViewlist);
        gridView.setAdapter(gridViewAdapter);
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
                if(attentionStatus)
                    cancelAttent(uuid);
                else
                    attent(uuid);
                break;
            case  R.id.tv_infordetial_comment://评论
                readyGo(CommentListActivity.class);
        }
    }

    class HomePageGridViewAdapter extends BaseAdapter {
        private Context context;
        private List<HomePageGridViewBean> list;
        private LayoutInflater inflater;
        public HomePageGridViewAdapter(Context context,List<HomePageGridViewBean> list){
            this.context=context;
            this.list=list;
            inflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            Holder holder = null;
            if(convertView == null){
                holder = new Holder();
                convertView = inflater.inflate( R.layout.item_enterprise_detailinfo_gridview, null);
                holder.tv = ((TextView) convertView.findViewById(R.id.btn_fun));
                holder.numTV= (TextView) convertView.findViewById(R.id.tv_num);
                convertView.setTag(holder);
            } else {
                holder = ((Holder) convertView.getTag());
            }
            holder.tv.setCompoundDrawablesWithIntrinsicBounds(0,list.get(i).getImgId(),0,0);
            holder.tv.setText(list.get(i).getName());
            if(list.get(i).getNum()!=0)
                holder.numTV.setText(""+list.get(i).getNum());
            return convertView;
        }
    }

    static class Holder{
        TextView tv;
        TextView numTV;
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
    //初始化GridView数据
    private void  initGridViewData(){
        gridViewlist.clear();
        int[] resIds = {R.mipmap.item_image_01,R.mipmap.item_image_02,R.mipmap.item_image_03,R.mipmap.item_image_04,
                R.mipmap.item_image_05,R.mipmap.item_image_06,R.mipmap.item_image_07,R.mipmap.item_image_08,R.mipmap.item_image_09,R.mipmap.item_image_10,R.mipmap.item_image_11,R.mipmap.item_image_12};
        String[] titles = {"工商信息","工商变更","年报","网站信息","商标","专利","著作权","法院诉讼","失信信息","资质","招聘","招投标信息"};
        int[] nums={0,changerecordsCount,annualCount,webCount,logoCount,patentCount,copyrightCount,courtCount,dishonestyCount,qualificationCount,0,tendersCount};
        for(int i=0;i<resId.length;i++){
            HomePageGridViewBean homepageBean=new HomePageGridViewBean(resIds[i],titles[i],nums[i]);
            gridViewlist.add(homepageBean);
        }
    }
    private void fullinfo(HomepageBean homepageBean) {

        econ_nameTV.setText(homepageBean.getEconName());
        econ_kindTV.setText(homepageBean.getEconKind());
        start_dateTV.setText(homepageBean.getStartDate());
        oper_nameTV.setText(homepageBean.getOperName());
        regist_capiTV.setText(homepageBean.getRegistCapi());
        register_statusTV.setText(homepageBean.getRegisterStatus());
        refreshTV.setText(homepageBean.getUpdateTime());
        changerecordsCount=Integer.valueOf(homepageBean.getChangerecordsCount());
        annualCount=Integer.valueOf(homepageBean.getAnnualCount());
        webCount=Integer.valueOf(homepageBean.getWebCount());
        logoCount=Integer.valueOf(homepageBean.getLogoCount());
        patentCount=Integer.valueOf(homepageBean.getPatentCount());
        copyrightCount=Integer.valueOf(homepageBean.getCopyrightCount());
        courtCount=Integer.valueOf(homepageBean.getCourtCount());
        dishonestyCount=Integer.valueOf(homepageBean.getDishonestyCount());
        qualificationCount=Integer.valueOf(homepageBean.getQualificationCount());
        tendersCount=Integer.valueOf(homepageBean.getTendersCount());
        gridViewAdapter.notifyDataSetChanged();
        readCountTV.setText("" + homepageBean.getReadCount());
        attentionStatus=homepageBean.getAttentionStatus().equals("0")?true:false;
        if(attentionStatus){
            Drawable add=getResources().getDrawable(R.mipmap.add_attention, null);
            add.setBounds(0,0,add.getMinimumWidth(),add.getMinimumHeight());
            btnAttent.setCompoundDrawables(null, add, null, null);
        }else{
            Drawable cancel=getResources().getDrawable(R.mipmap.cancel_attention, null);
            cancel.setBounds(0,0,cancel.getMinimumWidth(),cancel.getMinimumHeight());
            btnAttent.setCompoundDrawables(null, cancel, null, null);
        }
        initGridViewData();
        gridViewAdapter.notifyDataSetChanged();

    }
    //关注
    private void attent(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getAttentUrl(uuid)).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
                ToastUtil.TextToast(getApplicationContext(),e.getMessage());
            }
            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                ToastUtil.TextToast(getApplicationContext(), "关注成功");
                Drawable add=getResources().getDrawable(R.mipmap.add_attention, null);
                add.setBounds(0, 0, add.getMinimumWidth(), add.getMinimumHeight());
                btnAttent.setCompoundDrawables(null, add, null, null);
                attentionStatus=true;
            }
        });
    }
    //取消关注
    private void cancelAttent(String uuid){
        OkHttpUtils.post(UriHelper.getInstance().cancelAttentUrl(uuid)).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
               ToastUtil.TextToast(getApplicationContext(),e.getMessage());
            }

            @Override
            public void onResponse(QihaosouBean qihaosouBean) {
                ToastUtil.TextToast(getApplicationContext(),"取消关注");
                Drawable cancel=getResources().getDrawable(R.mipmap.cancel_attention, null);
                cancel.setBounds(0, 0, cancel.getMinimumWidth(), cancel.getMinimumHeight());
                btnAttent.setCompoundDrawables(null, cancel, null, null);
                attentionStatus=false;
            }
        });
    }


}
