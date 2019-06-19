package com.revert.platform.user.controller;

import com.revert.platform.common.base.model.WebResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.revert.platform.user.service.UserService;
import com.revert.platform.user.model.UserModel;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public WebResult findByPage(UserModel vo){
        WebResult webResult = new WebResult();
        webResult.data(userService.selectByPage(vo));
        return webResult;
    }

}