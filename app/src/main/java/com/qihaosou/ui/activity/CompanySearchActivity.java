package com.qihaosou.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.qihaosou.R;
import com.qihaosou.ui.fragment.CompanyQueryBeginFragment;
import com.qihaosou.view.EditTextWithDelete;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/19
 * Description:查企业
 */
public class CompanySearchActivity extends BaseActivity {
    private EditTextWithDelete searchET;
    private FragmentManager fragmentManager;
    private Fragment companyQueryBeginFragment;

    @Override
    protected void init() {
        searchET= (EditTextWithDelete) findViewById(R.id.edt_search);
        fragmentManager=getSupportFragmentManager();
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        companyQueryBeginFragment=new CompanyQueryBeginFragment();
        fragmentManager.beginTransaction().add(R.id.search_content_layout,companyQueryBeginFragment).commit();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_company_search;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            showToast("搜索");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
