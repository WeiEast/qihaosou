package com.qihaosou.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.qihaosou.R;
import com.qihaosou.bean.UMShareImageBean;
import com.qihaosou.bean.UMShareVideoBean;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

/**
 * Author: wenjundu
 * Email:179451678@qq.com
 * Date:2016/1/12
 * Description:分享窗口
 */
public class ShareDialog extends Dialog implements View.OnClickListener{
	private Activity context;
	private TextView shareQQ,shareWX,sharePYQ,shareKJ;
	private ShareAction shareAction;
	public ShareDialog(Activity context,UMShareImageBean umShareImageBean) {
		this(context, umShareImageBean, R.style.MyDialog);
	}
	public ShareDialog(Activity context,UMShareVideoBean umShareVideoBean){
		this(context, umShareVideoBean, R.style.MyDialog);
	}
	public ShareDialog(Activity context,UMShareImageBean umShareImageBean,int theme){
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
		shareAction=new ShareAction(context);
		shareAction.withMedia(umShareImageBean.getUmImage()).withTitle(umShareImageBean.getShareTitle()).withText(umShareImageBean.getShareText());
	}
	public ShareDialog(Activity context,UMShareVideoBean umShareVideoBean,int theme){
		super(context, theme);
		shareAction=new ShareAction(context);
		shareAction.withMedia(umShareVideoBean.getUmVideo()).withTitle(umShareVideoBean.getShareTitle()).withText(umShareVideoBean.getShareText());
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_share_modul);
		getWindow().setGravity(Gravity.BOTTOM);
		setCancelable(true);
		getWindow().getAttributes().width =getWindow().getWindowManager().getDefaultDisplay().getWidth();
		init();
		addListener();
	}
	private void addListener() {
		// TODO Auto-generated method stub
		shareKJ.setOnClickListener(this);
		sharePYQ.setOnClickListener(this);
		shareQQ.setOnClickListener(this);
		shareWX.setOnClickListener(this);
	}
	private void init() {
		// TODO Auto-generated method stub
		shareQQ=(TextView) findViewById(R.id.share_qq);
		shareKJ=(TextView) findViewById(R.id.share_kj);
		sharePYQ=(TextView) findViewById(R.id.share_pyq);
		shareWX=(TextView) findViewById(R.id.share_wx);
	}
	//设置友盟分享状态回调
	public void setUmShareListener(UMShareListener listener){if(listener!=null)shareAction.setCallback(listener); }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.share_kj://分享到空间
			shareAction.setPlatform(SHARE_MEDIA.QZONE).share();
			break;
		case R.id.share_pyq://分享到朋友圈
			shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
			break;
		case R.id.share_qq://分享到QQ
			shareAction.setPlatform(SHARE_MEDIA.QQ).share();
			break;
		case R.id.share_wx://分享到微信
			shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();
			break;
		}
		dismiss();
	}

}
