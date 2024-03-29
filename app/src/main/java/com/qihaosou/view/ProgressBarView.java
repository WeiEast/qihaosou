package com.qihaosou.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.qihaosou.util.L;

/**
 * Author: Created by wenjundu
 * Date:on 2016/2/27
 * Description:
 */
public class ProgressBarView extends View {
    /** * 进度条所占用的角度 */
    private static final int ARC_FULL_DEGREE = 360;
    // 进度条个数
    private static final int COUNT = 100;
    // 每个进度条所占用角度
    private static final float ARC_EACH_PROGRESS = ARC_FULL_DEGREE * 1.0f / (COUNT - 1);
    /** * 弧线细线条的长度 */
    private static final int ARC_LINE_LENGTH = 6;
    /** * 弧线细线条的宽度 */
    private static final int ARC_LINE_WIDTH = 2;
    /** * 组件的宽，高 */
    private int width, height;
    /** * 进度条最大值和当前进度值 */
    private float max=2000;
    private float progress;
    /** * 绘制弧线的画笔 */
    private Paint progressPaint;
    /** * 绘制文字的画笔 */
    private Paint textPaint;
    /** * 绘制文字背景圆形的画笔 */
    private Paint textBgPaint;
    /** * 圆弧的半径 */
    private int circleRadius;
    /** * 圆弧圆心位置 */
    private int centerX, centerY;
    private int textSize=40;
    private String text="热度";
    public ProgressBarView(Context context) {
        super(context);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textBgPaint = new Paint();
        textBgPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width == 0 || height == 0) {
            width = getWidth();
            height = getHeight();
            // 计算圆弧半径和圆心点
            circleRadius = (Math.min(width, height) - ARC_LINE_LENGTH * 2) >> 1;
            centerX = width / 2;
            centerY = height / 2;
        }
    }

    private Rect textBounds = new Rect();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // float start = (360 - ARC_FULL_DEGREE) >> 1; // 进度条起始角度 float
        float start=180;
        float sweep1 = ARC_FULL_DEGREE * (progress / max); // 进度划过的角度
        // 绘制进度条
        progressPaint.setColor(Color.parseColor(calColor(progress / max, "#ff00ff00", "#ffff0000")));
        progressPaint.setStrokeWidth(ARC_LINE_WIDTH);
        float drawDegree = 1.6f;
        while (drawDegree <= ARC_FULL_DEGREE) {
            double a = (start + drawDegree) / 180 * Math.PI;
            float lineStartX = centerX - circleRadius * (float) Math.sin(a);
            float lineStartY = centerY + circleRadius * (float) Math.cos(a);
            float lineStopX = lineStartX + ARC_LINE_LENGTH * (float) Math.sin(a);
            float lineStopY = lineStartY - ARC_LINE_LENGTH * (float) Math.cos(a);
            if (drawDegree > sweep1) { // 绘制进度条背景
                progressPaint.setColor(Color.parseColor("#88aaaaaa"));
                progressPaint.setStrokeWidth(ARC_LINE_WIDTH >> 1);
            }
            canvas.drawLine(lineStartX, lineStartY, lineStopX, lineStopY, progressPaint);
            drawDegree += ARC_EACH_PROGRESS;
        }

//        textBgPaint.setColor(Color.parseColor("#aaaaaaaa")); // 绘制文字背景圆形
//        textBgPaint.setStyle(Paint.Style.FILL);
//        // 设置填充
//        textBgPaint.setColor(Color.parseColor("#41668b"));
//        canvas.drawCircle(centerX, centerY, (circleRadius - ARC_LINE_LENGTH) * 0.8f, textBgPaint);
//        textBgPaint.setStyle(Paint.Style.STROKE);
//        // 设置空心
//        textBgPaint.setStrokeWidth(2);
//        canvas.drawCircle(centerX, centerY, (circleRadius - ARC_LINE_LENGTH) * 0.8f, textBgPaint);
        // 多少热度
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.RED);
        String hot = (int)progress+"";
        float textLen = textPaint.measureText(text);
        // 计算文字高度
        textPaint.getTextBounds(hot, 0, 1, textBounds);
        float h1 = textBounds.height();
        canvas.drawText(hot, centerX - textLen / 2, centerY - circleRadius / 10 + h1 / 4, textPaint);
        // 下一行文字
        textPaint.setTextSize(textSize);
        textLen = textPaint.measureText(text);
        canvas.drawText(text, centerX - textLen / 2, centerY + circleRadius / 2.5f, textPaint);
    }

    public void setMax(int max) {

        this.max = max;
        invalidate();
    }

    // 动画切换进度值(异步) public void
    public void setProgress(final float progress) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float oldProgress = ProgressBarView.this.progress;
                for (int i = 1; i <= 100; i++) {
                    ProgressBarView.this.progress = oldProgress + (progress - oldProgress) * (1.0f * i / 100);
                    postInvalidate();
                    SystemClock.sleep(20);
                }
            }
        }).start();
    }

    // 直接设置进度值（同步） public void
    public void setProgressSync(float progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * 计算渐变效果中间的某个颜色值。 仅支持 #aarrggbb模式,例如 #ccc9c9b2
     */
    public String calColor(float fraction, String startValue, String endValue) {
        int start_a, start_r, start_g, start_b;
        int end_a, end_r, end_g, end_b;
        // start
        start_a = getIntValue(startValue, 1, 3);
        start_r = getIntValue(startValue, 3, 5);
        start_g = getIntValue(startValue, 5, 7);
        start_b = getIntValue(startValue, 7, 9);
        // end
        end_a = getIntValue(endValue, 1, 3);
        end_r = getIntValue(endValue, 3, 5);
        end_g = getIntValue(endValue, 5, 7);
        end_b = getIntValue(endValue, 7, 9);
     String color=   "#" + getHexString((int) (start_a + fraction * (end_a - start_a)))
                + getHexString((int) (start_r + fraction * (end_r - start_r)))
                + getHexString((int) (start_g + fraction * (end_g - start_g)))
                + getHexString((int) (start_b + fraction * (end_b - start_b)));
        L.e(color);
        return color;
    }

    // 从原始#AARRGGBB颜色值中指定位置截取，并转为int.
    private int getIntValue(String hexValue, int start, int end) {
        return Integer.parseInt(hexValue.substring(start, end), 16);
    }

    private String getHexString(int value) {
        String a = Integer.toHexString(value);
        if (a.length() == 1) {
            a = "0" + a;
        }
        return a;
    }
}
