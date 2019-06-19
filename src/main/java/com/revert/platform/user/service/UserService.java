package com.revert.platform.user.service;

import com.revert.platform.common.base.service.BaseService;
import com.revert.platform.user.mapper.UserMapper;
import com.revert.platform.user.model.UserModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService extends BaseService<UserMapper,UserModel> {
}