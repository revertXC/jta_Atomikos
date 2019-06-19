package com.revert.platform.common.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类父类：
 *
 * ----------------------
 * jackson 注解含义：
 * @JsonIgnore 序列化时忽略此字段
 * @JsonInclude(JsonInclude.Include.NON_EMPTY) 仅在属性不为空时序列化此字段，对于字符串，即null或空字符串
 * @JsonFormat 指定Date类字段序列化时的格式
 */
@Data
public class BaseEntity implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -1879869989975645868L;

    /** 序号 */
    private Long id;

    /** 当前页*/
    @JsonIgnore
    private Integer pageNum = 1;

    /** 每页数量*/
    @JsonIgnore
    private Integer pageSize = 20;

    /** 排序方式*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String orderBy;

    /** sql 追加*/
    @JsonIgnore
    private String addSql;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer deleted;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date createDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date updateDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long updateBy;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer display;

    public void preSave(){
        try {
            this.createDate = new Date();
            this.updateDate = new Date();
        }catch (Exception e){}
    }

}
