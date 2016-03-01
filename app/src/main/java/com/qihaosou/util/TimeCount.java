package com.qihaosou.util;


import android.os.CountDownTimer;

import com.qihaosou.listener.TimeCountListener;

/**倒计时
 * @author wenjundu
 *
 */
public class TimeCount extends CountDownTimer {
	private TimeCountListener timeCountListener;
	public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
	}
	public void setOnTimeCountListener(TimeCountListener timeCountListener){
		this.timeCountListener=timeCountListener;
	}
	@Override
	public void onFinish() {// 计时完毕时触发
		if(timeCountListener!=null){
			timeCountListener.onFinish();
		}
	}

	@Override
	public void onTick(long millisUntilFinished) {// 计时过程显示
		if(timeCountListener!=null){
			timeCountListener.onTick(millisUntilFinished);
		}
	}
}
