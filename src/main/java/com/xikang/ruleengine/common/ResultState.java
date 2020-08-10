package com.xikang.ruleengine.common;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/11/8 15:18
 * @Version 1.0
 **/
public enum ResultState {

    SUCCESS(1, "成功"),
    FAIL(-1, "系统异常，请联系管理员"),

    //#1000～1999 区间表示参数错误
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数是空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    PARAM_USERNAME_PASSWORD_BLANK(1005, "用户名或密码是空"),


    //#2000～2999 区间表示用户错误
    USER_NOT_LOGIN(2001, "用户未登陆"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    INCLUDED_VALID_SERVE(2003, "请先解除该方案与服务的绑定"),
    INCLUDED_VALID_MEMBER(2004, "请先解除该服务与会员的绑定"),


    //#3000～3999 区间表示接口异常
    RECOMMEND_CHECK_ITEM_EXCEPTION(3001, "获取推荐的检查项目接口异常"),
    ;

    private int code;
    private String message;


    private ResultState(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessageByCode(int code) {
        for (ResultState state : ResultState.values()) {
            if (state.getCode() == code) {
                return state.getMessage();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
