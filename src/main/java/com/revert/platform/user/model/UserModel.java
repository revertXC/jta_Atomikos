package com.revert.platform.user.model;

import com.revert.platform.common.base.model.BaseEntity;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserModel extends BaseEntity {

    private String name;

    private String account;

    private String password;


}