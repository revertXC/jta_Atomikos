package com.revert.jta.model;

import com.revert.platform.common.base.model.BaseEntity;

import java.util.Date;

public class OperLog extends BaseEntity {

    private Long userId;

    private String remark;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}