package com.qihaosou.ui.activity;

import android.content.res.Resources;
import android.webkit.WebView;

import com.qihaosou.R;
import com.qihaosou.util.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/30
 * Description:服务协议
 */
public class AgreementActivity extends BaseActivity {
    private WebView agreementWV;
    @Override
    protected void init() {
        agreementWV= (WebView) findViewById(R.id.tv_agreement);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void addData() {
        setTitle("服务协议");
        agreementWV.loadDataWithBaseURL(null, readFile(), "text/html", "utf-8", null);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_agreement;
    }
    private String readFile() {
        Resources res = this.getResources();
        InputStream in = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        sb.append("");
        try {
            in = res.openRawResource(R.raw.agreement);
            String str;
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            while ((str = br.readLine()) != null) {
                sb.append(str);
                sb.append("\n");
            }
        } catch (Resources.NotFoundException e) {
            L.e( "文本文件不存在");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            L.e("文本编码出现异常");
            e.printStackTrace();
        } catch (IOException e) {
            L.e("文件读取错误");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
