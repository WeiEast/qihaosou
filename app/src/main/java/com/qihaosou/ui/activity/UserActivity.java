package com.qihaosou.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.https.TaskException;
import com.lzy.okhttputils.request.BaseRequest;
import com.qihaosou.R;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.net.UriHelper;
import com.qihaosou.util.BitmapUtil;
import com.qihaosou.util.L;
import com.qihaosou.util.MaterialDialogUtil;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.CircleImageView;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/15
 * Description:个人中心
 */
public class UserActivity extends BaseActivity implements OnClickListener{
    private RelativeLayout btnUserSet ,btnCheckUpdate;
    private TextView userNameTV;
    private TextView btnOut;
    private CircleImageView userHeaderIV;

    @Override
    protected void init() {
        btnUserSet= (RelativeLayout)findViewById(R.id.layout_user_set);
        btnOut= (TextView) findViewById(R.id.btn_exit_login);
        userNameTV= (TextView)findViewById(R.id.tv_user_name);
        userHeaderIV= (CircleImageView)findViewById(R.id.iv_user_header);
        btnCheckUpdate= (RelativeLayout) findViewById(R.id.layout_check_update);
    }

    @Override
    protected void addListener() {
        btnUserSet.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        userHeaderIV.setOnClickListener(this);
        btnCheckUpdate.setOnClickListener(this);
    }

    @Override
    protected void addData() {

        setTitle("个人中心");
        userNameTV.setText("醉心客");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user;
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.layout_user_set://用户设置
                intent=new Intent(this, SetActivity.class);
                break;
            case R.id.iv_user_header:
                UCrop.Options options = new UCrop.Options();
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                new  PickConfig.Builder(this)
                        .isneedcrop(true)//是否裁剪
//                       .actionBarcolor(Color.parseColor("#FE9221"))
//                        .statusBarcolor(Color.parseColor("#FE9221"))
                        .isneedcamera(true)//是否需要相机
                        .isSqureCrop(true)//是否正方形裁剪
                        .setUropOptions(options)
                        .maxPickSize(1)//最多选1张
                        .spanCount(3)//一行显示3张
                        .pickMode(PickConfig.MODE_SINGLE_PICK).build();
                break;
            case R.id.layout_check_update:

                break;
            case R.id.btn_exit_login://退出登录
                loginOut();
                break;
        }
        if(intent!=null)
            startActivity(intent);
    }

    private void loginOut(){
        OkHttpUtils.getInstance().clearCookie();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            //在data中返回 选择的图片列表
            ArrayList<String> paths=data.getStringArrayListExtra("data");
            L.e(paths.get(0));
            uploadImage(paths.get(0));
        }
    }

    private void uploadImage(String path){

      String base64= BitmapUtil.toBase64(path);
      L.e("base64:" + base64);
        final MaterialDialog materialDialog= MaterialDialogUtil.getNormalProgressDialog(this,getString(R.string.uploading));

      OkHttpUtils.post(Constants.UPLOAD_AVATAR_URL).tag(this).params("base64String",base64).params("imageType","jpg").execute(new QihaosouBeanCallBack() {
          @Override
          public void onBefore(BaseRequest request) {
              materialDialog.show();
          }

          @Override
          public void onAfter(@Nullable QihaosouBean qihaosouBean, Request request, Response response, @Nullable TaskException e) {
              materialDialog.dismiss();
          }

          @Override
          public void onError(Request request, @Nullable Response response, @Nullable TaskException e) {
              ToastUtil.TextToast(UserActivity.this, e.getMessage());
          }
          @Override
          public void onResponse(QihaosouBean qihaosouBean) {
              ToastUtil.TextToast(UserActivity.this, qihaosouBean.getMessage());
          }
      });
    }

}