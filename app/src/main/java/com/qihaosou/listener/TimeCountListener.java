package com.qihaosou.listener;

/**倒计时回调接口
 * @author wenjundu
 *
 */
public interface TimeCountListener {
	public void onFinish();
	public void onTick(long millisUntilFinished);
}
