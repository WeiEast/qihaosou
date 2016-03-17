package com.qihaosou.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import com.qihaosou.R;
import com.qihaosou.ui.fragment.search.CompanyQueryBeginFragment;
import com.qihaosou.ui.fragment.search.SearchResultFragment;
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
    private static final int TAB_BEGIN = 0;
    private static final int TAB_RESULT= 1;
    private Fragment companyQueryBeginFragment,searchResultFragment ;
    @Override
    protected void init() {
        searchET= (EditTextWithDelete) findViewById(R.id.edt_search);
        fragmentManager=getSupportFragmentManager();
    }

    @Override
    protected void addListener() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchET.getText().toString().length()>1){
                    selectItem(TAB_RESULT);
                }else if(searchET.getText().toString().length()==0){
                    selectItem(TAB_BEGIN);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void addData() {
        companyQueryBeginFragment=new CompanyQueryBeginFragment();
        fragmentManager.beginTransaction().add(R.id.search_content_layout,companyQueryBeginFragment).commit();
        selectItem(TAB_BEGIN);
    }



    private void selectItem(int position) {
        // update the main content by replacing fragments
        // 开启一个Fragment事务

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (position){
            case TAB_BEGIN:
                if(companyQueryBeginFragment == null)
                {
                    companyQueryBeginFragment = new CompanyQueryBeginFragment();
                    transaction.add(R.id.search_content_layout, companyQueryBeginFragment);
                }
                else
                {
                    transaction.show(companyQueryBeginFragment);
                }
                break;
            case TAB_RESULT:
                if(searchResultFragment == null)
                {
                    searchResultFragment = new SearchResultFragment();
                    transaction.add(R.id.search_content_layout, searchResultFragment);
                }
                else
                {
                    transaction.show(searchResultFragment);
                }
                break;
        }
//        transaction.addToBackStack(null);
        transaction.commit();
    }
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if(null != companyQueryBeginFragment)
        {
            transaction.hide(companyQueryBeginFragment);
        }
        if(null != searchResultFragment)
        {
            transaction.hide(searchResultFragment);
        }
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
