package com.qihaosou.net;
import com.qihaosou.app.Constants;
import com.qihaosou.util.L;

public class UriHelper {

    private static volatile UriHelper instance = null;

    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;

    public static final String URL_MUSICS_LIST_CHANNEL_ID = "0";

    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance){
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }

    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    public String getLoginUrl(String phone, String password,String clientType) {
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.LOGIN_URL);
        sb.append("&mobile=");
        sb.append(phone);
        sb.append("&password=");
        sb.append(password);
        sb.append("&clientType=");
        sb.append(clientType);
        return sb.toString();
    }
    public String getVcodeUrl(String phone){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_V_CODE_URL);
        sb.append("&mobile=");
        sb.append(phone);

        return sb.toString();
    }

    public String getRegisterUrl(String phone,String password,String vcode,String nickname){
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.REGISTER_URL);
        sb.append("&mobile=");
        sb.append(phone);
        sb.append("&password=");
        sb.append(password);
        sb.append("&vcode=");
        sb.append(vcode);
        sb.append("&nickname=");
        sb.append(nickname);
        L.e(sb.toString());
        return sb.toString();
    }
    public String getIcInfoUrl(String uuid){
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.GET_ICINFO);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    public String getIcInfoEmpUrl(String uuid){
        StringBuffer sb = new StringBuffer();
        sb.append(Constants.GET_ICINFO_EMP);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    public String getIcinfo(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ICINFO);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
}
