package com.revert.platform.db.controller;

import com.revert.platform.common.base.model.UsefulPage;
import com.revert.platform.common.base.model.WebResult;
import com.revert.platform.db.model.DBTable;
import com.revert.platform.db.service.DBTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/p/dbTable")
public class DBTableController {

    @Autowired
    private DBTableService dbTableService;

    @RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
    public WebResult findByPage(DBTable dbTable){
        UsefulPage<DBTable> usefulPage = dbTableService.selectByPage(dbTable);
        WebResult webResult = new WebResult().data(usefulPage);
        return webResult;
    }

    @RequestMapping("/generate")
    public WebResult generateCode(DBTable dbTable){
        WebResult webResult = new WebResult();
        List<DBTable> listDBTable = dbTableService.selectByProperties(dbTable);

        listDBTable.forEach( (item) ->{
            item.setPackageVal(dbTable.getPackageVal());
            dbTableService.generateCode(item);
        });

        return webResult;
    }

}
