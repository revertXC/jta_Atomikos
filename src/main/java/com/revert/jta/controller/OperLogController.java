package com.revert.jta.controller;

import com.revert.jta.model.OperLog;
import com.revert.jta.service.OperLogService;
import com.revert.platform.common.base.model.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xiecong
 * @Date 2019/6/18 15:24
 * @Description TODO
 */
@RestController
@RequestMapping("api/v1/operLog")
public class OperLogController {

    @Autowired
    private OperLogService operLogService;

    @RequestMapping(method = RequestMethod.GET)
    public WebResult findByPage(OperLog operLog){
        return new WebResult().data(operLogService.selectByProperties(operLog));
    }

    @RequestMapping(method = RequestMethod.GET, value = "testTran")
    public WebResult testTran(OperLog operLog){
        operLogService.testTranSerivce(operLog);
        return new WebResult();
    }

    @RequestMapping(method = RequestMethod.GET, value = "moreDb")
    public WebResult moreDb(OperLog operLog){
        return new WebResult().data(operLogService.selectMoerDbByProperties(operLog));
    }
}
