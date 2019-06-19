package com.revert.platform.common.base.model;

import com.revert.platform.common.constant.ResultInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class WebResult implements Serializable {

    private static final long serialVersionUID = -4454196913984770561L;

    @Getter
    @Setter
    private String code = ResultInfo.CODE_SUCCESS;

    @Getter
    @Setter
    private String message = ResultInfo.MSG_SUCCESS;

    @Getter
    @Setter
    private Object data;

    public WebResult code(String code){
        this.code = code;
        return this;
    }
    public WebResult message(String message){
        this.message = message;
        return this;
    }
    public WebResult data(Object data){
        this.data = data;
        return this;
    }
}
