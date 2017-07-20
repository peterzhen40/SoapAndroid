/**
 * Project Name:Demolish
 * File Name:LoginInfo.java
 * Package Name:com.sxzb.demolish.webservice.vo
 * Date:2015年8月13日下午5:27:11
 * Copyright (c) 2015, 中爆安全网科技有限公司 All Rights Reserved.
 */

package zhaoyang.soap;

/**
 * ClassName:LoginInfo <br/>
 * Function: 登录成功后返回的信息. <br/>
 * Date: 2015年8月13日 下午5:27:11 <br/>
 *
 * @author ReiChin_
 * @see
 * @since JDK 1.6
 */
public class UserInfo {
    /**
     * 销售平台
     **/
    public static final int APP_XIAO_SHOU = 0;
    /**
     * 使用平台
     **/
    public static final int APP_SHI_YONG = 1;
    /**
     * 公安平台
     **/
    public static final int APP_GA = 2;


    private String sn;
    private String compNo; /* 唯一SN */   //此值弃用，注意，由于WEB端接口修改，sn用sn  来代表，
    // 取值依然用getCompNo或getSn;
    private String compName; /* 单位名称 */
    private String compId; /* 单位Id */
    private String compAddress; /* 单位地址 */
    private String compBm; /* 单位区域编码 */
    private String userRealName; /* 用户实名 */
    private String userId; /* 用户ID */
    /**
     * 单位类型(公安: PS(派出所)、FJ(分局)、SJ(市局) 企业:SY(营业单位)、XS(销售单位))
     **/
    private String compNature; /* 单位类型 */// SY 使用单位 GJ: ST：省厅; SJ: 市局; FJ: 分局;
    // PS：派出所; KF：系统管理; PX：培训; CC:
    private String userType; // "userType":"用户类型,1系统管理员2单位管理员3普通用户4爆破项目人员"
    private String userSex;// 用户的性别男女
    private String userMobile;
    private String userReg;
    private String loginName;
    private String touxiangId;
    private String touxiangName;

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getSn() {
        return sn;
    }

    public UserInfo() {

    }
    //SC 表示生产单位、XS表示销售单位、YS表示运输单位、SY表示非营业单位、CC表示营业单位即爆破单位
    //SY也是爆破单位  PS是派出所、FJ是分局、SJ市局


    public UserInfo(String compNo, String compName, String compId, String compAddress,
                    String compBm, String userRealName, String userId, String compNature,
                    String userType, String userSex, String userMobile, String userReg,
                    String loginName, String touxiangId, String touxiangName) {
        this.sn = compNo;
        this.compNo = compNo;
        this.compName = compName;
        this.compId = compId;
        this.compAddress = compAddress;
        this.compBm = compBm;
        this.userRealName = userRealName;
        this.userId = userId;
        this.compNature = compNature;
        this.userType = userType;
        this.userSex = userSex;
        this.userMobile = userMobile;
        this.userReg = userReg;
        this.loginName = loginName;
        this.touxiangId = touxiangId;
        this.touxiangName = touxiangName;
    }

    public UserInfo(String compNo, String compName, String compId, String compAddress, String compBm,
                    String userRealName, String userId, String compNature, String userType) {
        this.sn = compNo;
        this.compName = compName;
        this.compId = compId;
        this.compAddress = compAddress;
        this.compBm = compBm;
        this.userRealName = userRealName;
        this.userId = userId;
        this.compNature = compNature;
        this.userType = userType;
    }

    public String getUserReg() {
        return userReg;
    }

    public void setUserReg(String userReg) {
        this.userReg = userReg;
    }

    /**
     * 是否为公安
     *
     * @return
     */
    public boolean isGA() {
        if (null == compNature || compNature.length() == 0) {
            return false;
        }
        return "PS".equals(compNature) || "SJ".equals(compNature) || "FJ".equals(compNature) || "KF".equals(compNature);
    }

    /**
     * 获取平台类型<br/>
     * PS(派出所)、FJ(分局)、SJ(市局) 企业:SY(营业单位)、XS(销售单位)
     *
     * @return
     */
    public int getAppType() {
        if ("PS".equals(compNature) || "SJ".equals(compNature) || "FJ".equals(compNature)) {
            return APP_GA;
        } else if ("SY".equals(compNature)) {
            return APP_SHI_YONG;
        } else if ("XS".equals(compNature)) {
            return APP_XIAO_SHOU;
        }
        return APP_SHI_YONG;
    }

    public String getCompNo() {
        return sn;
    }

    public void setCompNo(String sn) {
        this.sn = sn;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getCompId() {
        return compId;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompBm() {
        return compBm;
    }

    public void setCompBm(String compBm) {
        this.compBm = compBm;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompNature() {
        return compNature;
    }

    public void setCompNature(String compNature) {
        this.compNature = compNature;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * getUserTypeName:(这里用一句话描述这个方法的作用). <br/>
     */
    public String getUserTypeName() {
        // "userType":"用户类型,1系统管理员2单位管理员3普通用户4爆破项目人员"
        if (userType.equals("1")) {
            return "系统管理员";
        } else if (userType.equals("2")) {
            return "单位管理员";
        } else if (userType.equals("3")) {
            return "普通用户";
        } else if (userType.equals("4")) {
            return "爆破项目人员";
        }
        return "其他用户";
    }


    public String getTouxiangId() {
        return touxiangId;
    }

    public void setTouxiangId(String touxiangId) {
        this.touxiangId = touxiangId;
    }

    public String getTouxiangName() {
        return touxiangName;
    }

    public void setTouxiangName(String touxiangName) {
        this.touxiangName = touxiangName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "sn='" + sn + '\'' +
                ", compNo='" + compNo + '\'' +
                ", compName='" + compName + '\'' +
                ", compId='" + compId + '\'' +
                ", compAddress='" + compAddress + '\'' +
                ", compBm='" + compBm + '\'' +
                ", userRealName='" + userRealName + '\'' +
                ", userId='" + userId + '\'' +
                ", compNature='" + compNature + '\'' +
                ", userType='" + userType + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userReg='" + userReg + '\'' +
                ", loginName='" + loginName + '\'' +
                ", touxiangId='" + touxiangId + '\'' +
                ", touxiangName='" + touxiangName + '\'' +
                '}';
    }
}
