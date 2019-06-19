package com.revert.platform.db.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Columns implements Serializable {
    private static final long serialVersionUID = 5177952835647190553L;

    @Getter
    @Setter
    private String columnName;

    @Getter
    @Setter
    private String dataType;

    @Getter
    @Setter
    private String javaType;

    @Getter
    @Setter
    private String aliasName;

}
