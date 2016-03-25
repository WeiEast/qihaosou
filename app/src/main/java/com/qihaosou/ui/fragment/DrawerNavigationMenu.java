package com.qihaosou.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qihaosou.R;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.UserBean;
import com.qihaosou.ui.activity.LoginActivity;
import com.qihaosou.ui.activity.UserActivity;
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
    @InjectView(R.id.iv_avatar)
    SimpleDraweeView headerIcon;
    @InjectView(R.id.tv_name)
    TextView userName;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(LoginActivity.LOGIN_SUCCESSED_ACTION);
        intentFilter.addAction(UserActivity.UPLOAD_IMAGE_SUCCESSED_ACTION);
        intentFilter.addAction(UserActivity.LOGIN_OUT_ACTION);
        getContext().registerReceiver(receiver,intentFilter);
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
                if(!getApplication().login)
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

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(LoginActivity.LOGIN_SUCCESSED_ACTION.equals(intent.getAction())){
                getApplication().login=true;
                UserBean userBean=(UserBean)intent.getExtras().get("userinfo");
                Uri imageUri = Uri.parse(Constants.BASE_IMAGE_URL+userBean.getAvatar());
                headerIcon.setImageURI(imageUri);
                userName.setText(userBean.getNickname());
            }
            if(UserActivity.UPLOAD_IMAGE_SUCCESSED_ACTION.equals(intent.getAction())){
                String avatarPath=intent.getExtras().getString("avatarPath");
                Uri imageUri = Uri.parse(Constants.BASE_IMAGE_URL + avatarPath);
                headerIcon.setImageURI(imageUri);
            }
            if(UserActivity.LOGIN_OUT_ACTION.equals(intent.getAction())){
                headerIcon.setImageURI(null);
                userName.setText("未登录");
            }
        }
    };

    public enum MenuType{
        MENU_SEARCH,MENU_SHARE,MENU_COM,MENU_USER
}
    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(MenuType menuType);
    }

    @Override
    public void onDestroy() {
        getContext().unregisterReceiver(receiver);
        super.onDestroy();
    }
}
