package com.qihaosou.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lzy.okhttputils.OkHttpUtils;
import com.qihaosou.R;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.BaseBean;
import com.qihaosou.bean.QihaosouBean;
import com.qihaosou.callback.QihaosouBeanCallBack;
import com.qihaosou.net.GsonRequest;
import com.qihaosou.net.UriHelper;
import com.qihaosou.net.VolleyHelper;
import com.qihaosou.ui.activity.SetActivity;
import com.qihaosou.util.L;
import com.qihaosou.util.NetUtils;
import com.qihaosou.util.ToastUtil;
import com.qihaosou.view.CircleImageView;

/**
 * Author:wenjundu
 * Email: 179451678@qq.com
 * Date:  2016/1/15
 * Description:个人中心
 */
public class UserFragment extends Fragment implements OnClickListener{
    private RelativeLayout btnUserSet;
    private TextView userNameTV;
    private TextView btnOut;
    private CircleImageView userHeaderIV;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }

    private void init(View view) {
        btnUserSet= (RelativeLayout) view.findViewById(R.id.layout_user_set);
        btnOut= (TextView) view.findViewById(R.id.btn_exit_login);
        userNameTV= (TextView) view.findViewById(R.id.tv_user_name);
        userNameTV.setText("醉心客");
        btnUserSet.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        userHeaderIV= (CircleImageView) view.findViewById(R.id.iv_user_header);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user, (ViewGroup)null);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.layout_user_set://用户设置
                intent=new Intent(getActivity(), SetActivity.class);
                break;
            case R.id.btn_exit_login://退出登录
                loginOut();
                break;
        }
        if(intent!=null)
            startActivity(intent);
    }
    private void loginOut(){
        OkHttpUtils.post(Constants.LOGINOUT_URL).tag(this).execute(new QihaosouBeanCallBack() {
            @Override
            public void onResponse(QihaosouBean qihaosouBean) {

            }
        });
    }
}
