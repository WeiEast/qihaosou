package com.qihaosou.net;
import com.qihaosou.app.Constants;
import com.qihaosou.bean.AnnualBean;
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
    public String rePasswordUrl(String phone,String vcode,String password){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.REPASSWORD_URL);
        sb.append("&mobile=");
        sb.append(phone);
        sb.append("&vcode=");
        sb.append(vcode);
        sb.append("&password=");
        sb.append(password);
        return  sb.toString();
    }

    public String getHomePageUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_HOME_PAGE_URL);
        sb.append("&uuid=");
        sb.append(uuid);
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
    //关注url
    public String getAttentUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.ATTENTION_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //关注列表Url
    public  String getAttentListUrl(){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ATTENTION_LIST_URL);
        return sb.toString();
    }
    //取消关注
    public String cancelAttentUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.CANCEL_ATTENTION_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //变更信息
    public String getChangeInfoUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ICIONFO_CHANGE_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //年报list
    public String getAnnualListUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ICIONFO_ANNUAL_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //年报详情
    public String getAnnualBasicUrl(AnnualBean annualBean){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ICIONFO_ANNUAL_BASIC_URL);
        sb.append("&submittedYear=");
        sb.append(annualBean.getSubmittedYear());
        sb.append("&uuid=");
        sb.append(annualBean.getUuid());
        return sb.toString();
    }
    //投资人信息
    public String getPartnerInfoUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_ICINFO_PARTNER_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //网站备案信息
    public String getRecordUrl(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_RECORD_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //商标列表
    public String getMarkListUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_COMPANY_LOGO_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //专利
    public String getPatentlistUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_PATENT_OUTLINE_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //作品著作权
    public String getWorkCopyRightUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_WORK_COPYRIGHT_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //软件著作权
    public String getSoftWareCopyRightUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_COMPUTER_COPYRIGHT_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //招聘信息
    public String getRecruitInfoListUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_RECRUITINFO_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //招投标信息
    public String getTenderListUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_TENDERS_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //诉讼URL
    public String getCourtListUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_COURTLIST_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //法律文书
    public String getCourtUrl(String docId,String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_COURT_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        sb.append("&docId=");
        sb.append(docId);
        return sb.toString();
    }
    //评论列表
    public String getComments(String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_COMMENT_URL);
        sb.append("&uuid=");
        sb.append(uuid);
        return sb.toString();
    }
    //模糊搜索
    public String vagueQueryUrl(String keyWord){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.VAGUE_QUERY_URL);
        sb.append("&keyWord=");
        sb.append(keyWord);
        return sb.toString();
    }
    //失信列表
    public String getDishonestyListUrl(String econName){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_DISHONESTYLIST_URL);
        sb.append("&econName=");
        sb.append(econName);
        return sb.toString();
    }
    //失信详情
    public String getDishonestyDetailUrl(String AutoID,String uuid){
        StringBuffer sb=new StringBuffer();
        sb.append(Constants.GET_DISHONESTY_URL);
        sb.append("&AutoID=");
        sb.append(AutoID);
        sb.append("&uuid=");
        sb.append(uuid);
        return  sb.toString();
    }

}
