package com.revert.jta.service;

import com.revert.jta.mapper.OperLogMapper;
import com.revert.jta.model.OperLog;
import com.revert.platform.common.base.model.BaseEntity;
import com.revert.platform.common.base.service.BaseService;
import com.revert.platform.user.model.UserModel;
import com.revert.platform.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class OperLogService extends BaseService<OperLogMapper, OperLog> {

    @Autowired
    private OperLogService operLogService;

    @Autowired
    private UserService userService;

    @Transactional(transactionManager = "jtaTransactionManager")
    public void testTranSerivce(OperLog operLog){
        if(StringUtils.isEmpty(operLog.getRemark())){
            operLog.setRemark("默认备注参数");
        }
        if(operLog.getUserId() == null){
            operLog.setUserId(1L);
        }

        operLogService.saveNotNull(operLog);

        UserModel userModel = new UserModel();
        userModel.setName("xxx");
        userModel.setName("xxxx");
        userModel.setPassword("xxxxx");
        userService.saveNotNull(userModel);
        if(operLog.getUserId().longValue() == -1){
            int i = 1/0;
        }

    }

    public List<OperLog> selectMoerDbByProperties(OperLog operLog){
        return this.mapper.selectMoerDbByProperties(operLog);
    }

}