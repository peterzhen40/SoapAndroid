/**
 * Project Name:Demolish
 * File Name:ResultState.java
 * Package Name:com.sxzb.demolish.common
 * Date:2015年8月13日 下午4:52:12
 * Copyright (c) 2014, 中爆安全网科技有限公司 All Rights Reserved.
 */

package com.zyy.soap.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于判断是否有错误
 */
public class ResultState {

    public static Map<String, String> mErrorMap = new HashMap<String, String>();

    static {
        mErrorMap.put("err-9999", "连接服务器失败,不会出现在返回信息内,暂未使用");
        mErrorMap.put("err-0000", "服务器错误,一般有跟具体说明");
        mErrorMap.put("err-1111", "未找到相关信息");
        mErrorMap.put("err-1112", "单位id不能为空");
        mErrorMap.put("err-1113", "记录不存在");

        mErrorMap.put("err-1001", "用户名不能为空");
        mErrorMap.put("err-1002", "密码不能为空");
        mErrorMap.put("err-1003", "用户不存在");
        mErrorMap.put("err-1004", "密码不正确");
        mErrorMap.put("err-1005", "用户被锁定,禁止登录");
        mErrorMap.put("err-1006", "用户过期");
        mErrorMap.put("err-1007", "用户未对应任何单位");
        mErrorMap.put("err-1008", "用户所属单位信息不存在");
        mErrorMap.put("err-1009", "用户所属单位被锁定");

        mErrorMap.put("err-1100", "SN已失效,请注销之后重新登录");
        mErrorMap.put("err-1101", "设备代码错误");
        mErrorMap.put("err-1102", "APP被禁止使用");

        mErrorMap.put("err-1500", "要审核的项目类型不能为空");
        mErrorMap.put("err-1501", "节点名不能为空");
        mErrorMap.put("err-1502", "记录id不能为空");
        /************** 作业监理日志或施工日志 *****************/
        mErrorMap.put("err-1200", "施工单位id或者名称不能为空");
        mErrorMap.put("err-1201", "填报日期不能为空");
        mErrorMap.put("err-1202", "爆破项目id或者项目名称不能为空");
        mErrorMap.put("err-1203", "天气情况不能为空");
        mErrorMap.put("err-1204", "工程部位不能为空");
        mErrorMap.put("err-1205", "项目管辖机关code不能为空");
        mErrorMap.put("err-1206", "现场监理人员不能为空"); // 施工单位规划负责人不能为空(监理日志) 或
        // 施工规划负责人不能为空(施工日志)
        mErrorMap.put("err-1207", "工程技术人员不能为空"); // 监理单位负责监督的监理人不能为空(监理日志) 或
        // 现场监理人员不能为空(施工日志)
        mErrorMap.put("err-1208", "爆破员不能为空"); // 监理单位项目技术负责人不能为空(监理日志) 或
        // 爆破现场警戒人员不能为空(施工日志)
        mErrorMap.put("err-1209", "安全员不能为空");
        mErrorMap.put("err-1210", "业主单位负责人不能为空");
        mErrorMap.put("err-1211", "使工程进度不能为空");
        mErrorMap.put("err-1212", "作业单位人员动态不能为空");
        mErrorMap.put("err-1213", "爆破作业人员是否持证上岗不能为空(0 是,1 否)");
        mErrorMap.put("err-1214", "是否按照爆破设计方案进行不能为空(0 是,1 否)");
        mErrorMap.put("err-1215", "基本情况不能为空");
        mErrorMap.put("err-1216", "清退民爆物品不能为空(当是否清退民爆物品值为 0 时)");
        mErrorMap.put("err-1217", "流失民爆物品不能为空(当是否流失民爆物品值为 0 时)");
        mErrorMap.put("err-1218", "盲炮处理方式不能为空(当是否有盲炮值为 0 时)");
        mErrorMap.put("err-1219", "安全事故处理方式不能为空(当是否发生安全事故值为 0 时)");
        mErrorMap.put("err-1220", "监理单位id或者名称不能为空");
        mErrorMap.put("err-1221", "项目地址不能为空");
        mErrorMap.put("err-1222", "项目类别不能为空");
        mErrorMap.put("err-1223", "项目类型不能为空(1控制爆破,0非控制爆破)");
        mErrorMap.put("err-1224", "施工规划负责人不能为空");
        mErrorMap.put("err-1225", "爆破现场警戒不能为空");
        mErrorMap.put("err-1226", "使用炸药数量不能为空");
        mErrorMap.put("err-1227", "使用雷管数量不能为空");
        mErrorMap.put("err-1228", "使用索类数量不能为空");
        mErrorMap.put("err-1229", "爆破现场警戒负责人不能为空");
        mErrorMap.put("err-1230", "作业安全警戒距离不能为空");
        mErrorMap.put("err-1231", "作业时间范围不能为空");
        mErrorMap.put("err-1232", "放炮时间不能为空");

        /************** 现场安全检查 *****************/
        mErrorMap.put("err-1300", "公安账户不存在或密码错误");
        mErrorMap.put("err-1301", "公安机关用户名不能为空");
        mErrorMap.put("err-1302", "公安机关用户密码不能为空");
        mErrorMap.put("err-1303", "输入的用户名密码错误");
        mErrorMap.put("err-1304", "检查机关id与名称不能为空");
        mErrorMap.put("err-1305", "爆破项目id与名称不能为空");
        mErrorMap.put("err-1306", "检查时间不能为空");
        mErrorMap.put("err-1307", "检查类型不能为空");
        mErrorMap.put("err-1308", "检查内容项不能为空");
        mErrorMap.put("err-1309", "项目和单位信息不存在");

        /************** 库房安全检查 *****************/
        mErrorMap.put("err-1400", "检查机关id与名称不能为空");
        mErrorMap.put("err-1401", "储存仓库id与名称不能为空");
        mErrorMap.put("err-1402", "检查时间不能为空");
        mErrorMap.put("err-1403", "检查类型不能为空");
        mErrorMap.put("err-1404", "检查内容项不能为空");
        /************** 赣州民爆 *****************/
        mErrorMap.put("err-360701", "使用数量大于配送数量");
        mErrorMap.put("err-360702", "退库数量大于配送数量");
        mErrorMap.put("err-360703", "配送数量大于计划数量");
        mErrorMap.put("err-360704", "获取信息失败");
        mErrorMap.put("err-360705", "只有本单位可以删除");
        mErrorMap.put("err-360706", "只有新增的爆破计划可以删除");
        mErrorMap.put("err-360707", "获取信息失败");
        mErrorMap.put("err-360711", "上传爆破录像失败");
        mErrorMap.put("err-360712", "只有公安用户可以检查");
        mErrorMap.put("err-360713", "原密码不正确");
        /************** 广西民爆 *****************/
        mErrorMap.put("err-450001", "获取查看实时视频用户失败");
        mErrorMap.put("err-450002", "该库房中的炸药数量小于要配送的炸药数量");
        mErrorMap.put("err-450003", "该库房中的雷管数量小于要配送的雷管数量");
    }

    /**
     * 根据error code 获取 error 状态
     *
     * @param errorCode error code
     * @return error 状态
     */
    public static String getErrorState(String errorCode) {
        if (mErrorMap.containsKey(errorCode)) {
            return mErrorMap.get(errorCode);
        }
        return "未知错误：" + errorCode;
    }
}
