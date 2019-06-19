package com.revert.platform.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revert.platform.common.base.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 当前连接数据库表
 */
public class DBTable extends BaseEntity {

    private static final long serialVersionUID = -6354567681645730215L;

    @Getter
    @Setter
    private String tableName;

    @Getter
    @Setter
    private String tableRows;

    @Getter
    @Setter
    private String tableComment;

    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /** 包值  例：com.revert.demo.user*/
    @JsonIgnore
    @Getter
    @Setter
    private String packageVal;

    @JsonIgnore
    @Getter
    @Setter
    private List<Columns> listColumns;

}
