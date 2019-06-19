package com.revert.platform.common.constant;

import lombok.Data;

import java.io.Serializable;

public class ResultInfo implements Serializable {

    /**
     * 异常code
     *  200:正常
     *  500:异常
     */
    public final static String CODE_SUCCESS = "200";
    public final static String CODE_ERROR = "500";
    public final static String CODE_AUTHORITY = "503";

    /**
     * 消息message
     *
     */
    public final static String MSG_SUCCESS = "ok";
    public final static String MSG_ERROR = "系统异常";
    public final static String MSG_AUTHORITY = "无权限";



}
