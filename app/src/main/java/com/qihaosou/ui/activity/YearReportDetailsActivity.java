package com.qihaosou.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.qihaosou.R;
import com.qihaosou.util.L;
import com.qihaosou.view.ExpandableLayout;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/25
 * Description:年报详情
 */
public class YearReportDetailsActivity extends BaseActivity implements ExpandableLayout.ExpandableListener{

    private ExpandableLayout assetdataEL,partnersET;//资产信息
    private ImageView assetdataIVtips,partnersIVtips;
    @Override
    protected void init() {
        assetdataEL= (ExpandableLayout) findViewById(R.id.el_reportdetial_assetdata);
        assetdataEL.setIsExpand(true);
        assetdataIVtips= (ImageView) assetdataEL.getHeaderLayout().findViewById(R.id.iv_expend_tip);

        partnersET= (ExpandableLayout) findViewById(R.id.el_reportdetial_partners);
        partnersET.setIsExpand(true);
        partnersIVtips= (ImageView) partnersET.getHeaderLayout().findViewById(R.id.iv_expend_tip);
    }

    @Override
    protected void addListener() {
        assetdataEL.setExpandableListener(this);
    }

    @Override
    protected void addData() {
        setTitle("年报详情");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_yearreportdetails;
    }


    @Override
    public void expandable(View view, Boolean isExpand) {
       switch (view.getId()){
           case R.id.el_reportdetial_assetdata:
               if(isExpand){
                   assetdataIVtips.setImageResource(R.mipmap.arrow_up_expand);
               }
               else{
                   assetdataIVtips.setImageResource(R.mipmap.arrow_down_close);
               }
               break;
       }
    }
}
