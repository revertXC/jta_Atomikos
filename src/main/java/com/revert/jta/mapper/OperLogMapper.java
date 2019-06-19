package com.revert.jta.mapper;

import com.revert.jta.model.OperLog;
import com.revert.platform.common.annotation.T2MySqlDao;
import com.revert.platform.common.base.mapper.BaseMapper;

import java.util.List;

@T2MySqlDao
public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 跨库联表查询
     * @param operLog
     * @return
     */
    List<OperLog> selectMoerDbByProperties(OperLog operLog);


}