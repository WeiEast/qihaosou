package com.qihaosou.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.qihaosou.R;
import com.qihaosou.util.L;

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
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        L.e("................");
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

}
