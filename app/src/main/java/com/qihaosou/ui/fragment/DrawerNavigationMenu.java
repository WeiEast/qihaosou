package com.qihaosou.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qihaosou.R;
import com.qihaosou.util.UIHelper;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/21
 * Description:
 */
public class DrawerNavigationMenu extends BaseFragment {
    @InjectView(R.id.rl_header)
    View mMenu_rl_header;
    @InjectView(R.id.menu_item_search)
    View mMenu_rl_search;
    @InjectView(R.id.menu_item_share)
    View mMenu_rl_share;
    @InjectView(R.id.menu_item_com)
    View mMenu_rl_com;
    @InjectView(R.id.menu_item_user)
    View mMenu_rl_user;

    private NavigationDrawerCallbacks mCallbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    "Activity must implement NavigationDrawerCallbacks.");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_main_drawer_menu;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
    @OnClick({R.id.rl_header, R.id.menu_item_search, R.id.menu_item_share, R.id.menu_item_com, R.id.menu_item_user})
    public void OnViewClick(View view){
        switch (view.getId()){
            case R.id.rl_header:
                UIHelper.showLoginActivity(getContext());
                break;
            case R.id.menu_item_search:
                if(mCallbacks!=null){
                    mCallbacks.onNavigationDrawerItemSelected(MenuType.MENU_SEARCH);
                }
                break;
            case R.id.menu_item_share:
                if(mCallbacks!=null){
                    mCallbacks.onNavigationDrawerItemSelected(MenuType.MENU_SHARE);
                }
                break;
            case R.id.menu_item_com:
                if(mCallbacks!=null){
                    mCallbacks.onNavigationDrawerItemSelected(MenuType.MENU_COM);
                }
                break;
            case R.id.menu_item_user:
                if(mCallbacks!=null){
                    mCallbacks.onNavigationDrawerItemSelected(MenuType.MENU_USER);
                }
                break;
        }
    }
    public enum MenuType{
        MENU_SEARCH,MENU_SHARE,MENU_COM,MENU_USER
}
    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(MenuType menuType);
    }
}
