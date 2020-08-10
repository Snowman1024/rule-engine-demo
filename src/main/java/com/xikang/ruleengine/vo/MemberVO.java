package com.xikang.ruleengine.vo;

import lombok.Data;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2020/3/12 16:02
 * @Version 1.0
 **/
@Data
public class MemberVO {

    private static final long serialVersionUID = 5814267095227730073L;
    /**
     * 会员编码
     */
    private String memberCode;
    /**
     * 名称
     */
    private String name;
    /**
     * 性别 1男，2女，0未知
     */
    private int gender = 0;
    /**
     * 年龄
     */
    private String age = "";
    /**
     * 是否高血压；0否，1是
     */
    private String hypertensionFlag = "";
    /**
     * 是否糖尿病；0否，1是
     */
    private String diabetesFlag = "";
    /**
     * 是否冠心病；0否，1是
     */
    private String coronaryFlag = "";
    /**
     * 是否慢性阻塞性肺疾病；0否，1是
     */
    private String copdFlag = "";
    /**
     * 是否恶性肿瘤；0否，1是
     */
    private String tumourFlag = "";
    /**
     * 恶性肿瘤名
     */
    private String tumourName = "";
    /**
     * 是否脑卒中；0否，1是
     */
    private String strokeFlag = "";
    /**
     * 是否严重精神障碍；0否，1是
     */
    private String mentalFlag = "";
    /**
     * 是否结核病；0否，1是
     */
    private String tuberculosisFlag = "";
    /**
     * 是否肝炎；0否，1是
     */
    private String hepatitisFlag = "";
    /**
     * 是否其他法定传染病；0否，1是
     */
    private String infectiousFlag = "";
    /**
     * 是否职业病；0否，1是
     */
    private String occupationalFlag = "";
    /**
     * 职业病名
     */
    private String occupationalName = "";
    /**
     * 饮食习惯：1荤素均衡，2荤食为主，3素食为主，4嗜盐，5嗜油，6嗜糖（多个逗号分隔）
     */
    private String eatingHabits = "";
    /**
     * 生活习惯：1吸烟，2饮酒（多个逗号分隔）
     */
    private String livingHabits = "";

}
