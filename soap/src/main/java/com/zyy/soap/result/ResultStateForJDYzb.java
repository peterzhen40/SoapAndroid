package com.zyy.soap.result;

import java.util.HashMap;

/**
 * 判断返回结果（剧毒易制爆）
 */
public class ResultStateForJDYzb {

    public static HashMap<String, String> errMap = new HashMap<String, String>();

    static {

        errMap.put("err-9999", "链接服务器超时");
        errMap.put("err-0000", "服务器错误");

        errMap.put("err-1001", "用户名不能为空");
        errMap.put("err-1002", "密码名不能为空");
        errMap.put("err-1003", "用户名不存在");
        errMap.put("err-1004", "密码不正确");
        errMap.put("err-1005", "用户被锁定,禁止登陆");
        errMap.put("err-1006", "用户过期");
        errMap.put("err-1007", "用户未对应任何单位");
        errMap.put("err-1008", "用户所属单位信息不存在");
        errMap.put("err-1009", "用户所属单位被锁定");
        errMap.put("err-1100", "SN已失效，请重新登录");

        errMap.put("err-1101", "设备代码错误");
        errMap.put("err-1102", "APP被禁止使用");
        errMap.put("err-1103", "设备唯一标识码为空");

        errMap.put("err-1200", "购买人不能为空");
        errMap.put("err-1201", "购买人证件号不能为空");
        errMap.put("err-1205", "购买人证件照片不能为空");
        errMap.put("err-1206", "现场照片不能为空");
        errMap.put("err-1210", "销售人不能为空");
        errMap.put("err-1211", "销售人证件号不能为空");
        errMap.put("err-1220", "销售物品不能为空");
        errMap.put("err-1230", "物品id不能为空");
        errMap.put("err-1231", "物品不存在");
        errMap.put("err-1232", "厂家不能为空");
        errMap.put("err-1233", "批号不能为空");
        errMap.put("err-1234", "规格不能为空");
        errMap.put("err-1235", "物品对应的厂家,批号,规格重复");
        errMap.put("err-1236", "记录id不能为空");
        errMap.put("err-1237", "记录不存在");
        errMap.put("err-1250", "实名销售权限不够");
        errMap.put("err-1251", "实名销售权限不能为空");
        //仅硝基复混肥实名销售才会出现
        errMap.put("err-1268", "标签参数为空");
        errMap.put("err-1269", "只能是硝基复混肥实名销售才可使用");
        errMap.put("err-1270", "标签不存在");
        errMap.put("err-1271", "标签对应的物品不存在");
        errMap.put("err-1300", "销售物品的库存不足");

        //新疆 实名销售
        errMap.put("err-1260", "业务类型不能为空");
        errMap.put("err-1261", "单位id为空或单位名为空");
        errMap.put("err-1262", "单位未库存初始化");
        errMap.put("err-1263", "销售单位不能是本单位");
        errMap.put("err-1265", "销售单位无对应物品");
        errMap.put("err-1266", "未选择出入库物品");
        errMap.put("err-1267", "销售单位无物品");
        errMap.put("err-1300", "销售物品的库存不足");
    }

    /**
     * 根据error code 获取 error 状态
     *
     * @param errorCode error code
     * @return error 状态
     */
    public static String getErrorState(String errorCode) {
        if (errMap.containsKey(errorCode)) {
            return errMap.get(errorCode);
        }
        return "未知错误：" + errorCode;
    }
}