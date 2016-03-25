package com.qihaosou.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.qihaosou.R;
import com.qihaosou.util.L;
import com.qihaosou.util.SystemBarTintManager;

/**
 * Author: Created by wenjundu
 * Date:on 2016/1/26
 * Description: 启动页
 */
public class SplashActivity extends BaseActivity {
    private ImageView splashIV;
    @Override
    protected void init() {
        splashIV= (ImageView) findViewById(R.id.splash_image);
        View root = findViewById(R.id.app_start_view);
        if (root != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);
        }
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {

        Animation animation =  AnimationUtils.loadAnimation(this, R.anim.splash);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigateToHomePage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashIV.startAnimation(animation);
    }

    private void navigateToHomePage() {
        readyGoThenKill(MainActivity.class);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }
    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
