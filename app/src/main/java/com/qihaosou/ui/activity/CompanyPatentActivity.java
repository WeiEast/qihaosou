package com.qihaosou.ui.activity;

import android.view.View;
import android.widget.TextView;
import com.qihaosou.R;
import com.qihaosou.view.SelectPatentCategoryPopup;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/18
 * Description:专利列表
 */
public class CompanyPatentActivity extends BaseActivity implements View.OnClickListener{
    private TextView btnSelectCategroy;
    private SelectPatentCategoryPopup patentCategoryPopup;

    @Override
    protected void init() {
        setTitle("");
        btnSelectCategroy= (TextView) findViewById(R.id.btn_select_patent_category);
    }

    @Override
    protected void addListener() {
        btnSelectCategroy.setOnClickListener(this);
    }

    @Override
    protected void addData() {
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
}
