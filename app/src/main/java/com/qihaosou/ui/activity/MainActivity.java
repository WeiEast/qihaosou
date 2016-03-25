package com.qihaosou.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.qihaosou.R;
import com.qihaosou.app.Constants;
import com.qihaosou.app.MyApplication;
import com.qihaosou.bean.UMShareImageBean;
import com.qihaosou.listener.UMShareCallBack;
import com.qihaosou.ui.fragment.DrawerNavigationMenu;
import com.qihaosou.ui.fragment.HomeFragment;
import com.qihaosou.update.UpdateChecker;
import com.qihaosou.util.UIHelper;
import com.qihaosou.view.CircleImageView;
import com.qihaosou.view.ShareDialog;
import com.umeng.socialize.media.UMImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements DrawerNavigationMenu.NavigationDrawerCallbacks{

    private static long DOUBLE_CLICK_TIME = 0L;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerNavigationMenu mNavigationDrawerFragment;
    private Fragment currentSupportFragment;
    private UMShareImageBean umShareImageBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDrawer();

            changeFragment(R.id.main_content, new HomeFragment());
            //检测新版本
            UpdateChecker.checkForDialog(MainActivity.this, Constants.GET_NEW_VERSION_URL);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        umShareImageBean=new UMShareImageBean();
        umShareImageBean.setShareText("hahahahah");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.search_bg_02);
        UMImage image = new UMImage(MainActivity.this, bitmap);
        umShareImageBean.setUmImage(image);
        umShareImageBean.setShareTitle("快快分享吧");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                    showToast(getString(R.string.double_click_exit));
                    DOUBLE_CLICK_TIME = System.currentTimeMillis();
                } else {
                    getMyApplication().exitApp();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void configureDrawer() {
        // Configure drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar,
                R.string.open,
                R.string.close) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    public void changeFragment(int resView, Fragment targetFragment) {
        if (targetFragment.equals(currentSupportFragment)) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
//            transaction.replace(resView, targetFragment);
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
        }
        if (currentSupportFragment != null && currentSupportFragment.isVisible()) {
            transaction.hide(currentSupportFragment);
        }
        currentSupportFragment = targetFragment;
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onNavigationDrawerItemSelected(DrawerNavigationMenu.MenuType menuType) {
        switch (menuType){
            case MENU_SEARCH://企业查询
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case MENU_SHARE://APP分享
                ShareDialog shareDialog=new ShareDialog(MainActivity.this,umShareImageBean );
                shareDialog.setUmShareListener(new UMShareCallBack(MainActivity.this));
                shareDialog.show();
                break;
            case MENU_COM://评价
                break;
            case MENU_USER://个人中心
                if(((MyApplication)getApplication()).login)
                    UIHelper.showUserActivity(this);
                else
                    UIHelper.showLoginActivity(this);
                break;
        }
    }
}
