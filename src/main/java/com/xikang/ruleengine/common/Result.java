package com.xikang.ruleengine.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman1024
 * @Date 2019/11/8 9:46
 * @Version 1.0
 **/
@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 5055063536192997292L;

    private int code;

    private String message;

    private Object data;


    /**
     * 成功
     *
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(ResultState.SUCCESS.getCode());
        return result;
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(ResultState.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 失败
     *
     * @return
     */
    public static Result failure() {
        Result result = new Result();
        result.setCode(ResultState.FAIL.getCode());
        result.setMessage(ResultState.FAIL.getMessage());
        return result;
    }

    /**
     * 失败
     *
     * @param message
     * @return
     */
    public static Result failure(String message) {
        Result result = new Result();
        result.setCode(ResultState.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static Result failure(int code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 失败
     *
     * @param resultState
     * @return
     */
    public static Result failure(ResultState resultState) {
        Result result = new Result();
        result.setCode(resultState.getCode());
        result.setMessage(resultState.getMessage());
        return result;
    }
}
