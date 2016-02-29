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
import com.qihaosou.bean.UMShareImageBean;
import com.qihaosou.listener.UMShareCallBack;
import com.qihaosou.ui.fragment.HomeFragment;
import com.qihaosou.ui.fragment.UserFragment;
import com.qihaosou.view.CircleImageView;
import com.qihaosou.view.ShareDialog;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity{
    private DrawerLayout drawerLayout;
    private ListView leftMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private CircleImageView headIcon;
    private static final int TAB_HOME = 0;
    private static final int TAB_USER= 1;
    private FragmentManager fragmentManager;
    private String[] navName = {"企业查询","APP分享","给个好评,亲","个人中心"};
    private int[] navDrawable={R.mipmap.menu_search,R.mipmap.menu_share,R.mipmap.menu_comment,R.mipmap.menu_userinfo};
    private SimpleAdapter simpleAdapter;
    private Fragment homeFragment,userFragment ;
    private static long DOUBLE_CLICK_TIME = 0L;
    private UMShareImageBean umShareImageBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
              //  setTitle(getString(R.string.app_name));

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        drawerLayout= (DrawerLayout) findViewById(R.id.dl_layout);
        leftMenu= (ListView) findViewById(R.id.lv_left_menu);
        headIcon= (CircleImageView) findViewById(R.id.head_icon);
        fragmentManager=getSupportFragmentManager();
    }

    @Override
    protected void addListener() {
        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(LoginActivity.class);
            }
        });
        leftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        selectItem(TAB_HOME);

                        break;
                    case 1:
                        ShareDialog shareDialog=new ShareDialog(MainActivity.this,umShareImageBean );
                        shareDialog.setUmShareListener(new UMShareCallBack(MainActivity.this));
                        shareDialog.show();
                        break;
                    case 3:
                        selectItem(TAB_USER);

                        break;
                }
            }
        });
    }

    @Override
    protected void addData() {
        //设置菜单列表
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < navName.length; i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("head", navDrawable[i]);
            listem.put("name", navName[i]);
            listems.add(listem);
        }
        simpleAdapter = new SimpleAdapter(this, listems,R.layout.menu_item, new String[]{"head","name"},new int[]{R.id.head,R.id.name});
        leftMenu.setAdapter(simpleAdapter);
        selectItem(TAB_HOME);
        leftMenu.setLayoutAnimation(getListAnim());
        umShareImageBean=new UMShareImageBean();
        umShareImageBean.setShareText("hahahahah");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.search_bg_02);
        UMImage image = new UMImage(MainActivity.this, bitmap);
        umShareImageBean.setUmImage(image);
        umShareImageBean.setShareTitle("快快分享吧");
    }
    private void selectItem(int position) {
        // update the main content by replacing fragments
        // 开启一个Fragment事务

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (position){
            case TAB_HOME:
                setTitle(getString(R.string.home_title));
                if(homeFragment == null)
                {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.home_container, homeFragment);
                }
                else
                {
                    transaction.show(homeFragment);
                }
                break;
            case TAB_USER:
                setTitle(getString(R.string.user_center));
                if(userFragment == null)
                {
                    userFragment = new UserFragment();
                    transaction.add(R.id.home_container, userFragment);
                }
                else
                {
                    transaction.show(userFragment);
                }
                break;
        }
        transaction.addToBackStack(null);
        transaction.commit();
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if(null != homeFragment)
        {
            transaction.hide(homeFragment);
        }
        if(null != userFragment)
        {
            transaction.hide(userFragment);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
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
    private LayoutAnimationController getListAnim() {
        AnimationSet set = new AnimationSet(true);
        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(300);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.5f);
        return controller;
    }

}
