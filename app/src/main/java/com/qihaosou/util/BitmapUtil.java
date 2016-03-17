package com.qihaosou.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Created by wenjundu
 * Date:on 2016/3/4
 * Description:
 */
public class BitmapUtil {
    public static String toBase64(String imgpath){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgpath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串

    }
}
