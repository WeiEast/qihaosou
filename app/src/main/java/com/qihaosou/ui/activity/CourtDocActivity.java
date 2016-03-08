package com.qihaosou.ui.activity;

import android.webkit.WebView;

import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.bean.CourtBean;
import com.qihaosou.callback.CourtCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.L;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/8
 * Description:法院文书
 */
public class CourtDocActivity extends BaseActivity {
    private WebView webView;
    private String uuid;
    private String docId;
    @Override
    protected void init() {
        webView= (WebView) findViewById(R.id.web_view);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("法律文书");
        uuid=getIntent().getStringExtra("uuid");
        docId=getIntent().getStringExtra("docId");
        getCourt(docId,uuid);
    }

    private void getCourt(String docId,String uuid){
        OkHttpUtils.post(UriHelper.getInstance().getCourtUrl(docId, uuid)).tag(this).execute(new CourtCallBack() {
            @Override
            public void onResponse(CourtBean courtBean) {
                loadWebInfo(courtBean.getCaseInfo());
                L.e("......."+courtBean.getCaseInfo());
            }
        });
    }
    private void loadWebInfo(String caseInfo){
        webView.loadDataWithBaseURL(null, caseInfo, "text/html", "utf-8", null);
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_court_doc;
    }
}
