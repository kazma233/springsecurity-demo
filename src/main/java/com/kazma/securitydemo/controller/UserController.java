package com.kazma.securitydemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kazma.securitydemo.entity.common.Result;
import com.kazma.securitydemo.entity.user.vo.UserLogin;
import com.kazma.securitydemo.entity.user.vo.UserRegister;
import com.kazma.securitydemo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RequestMapping("/users")
@RestController
@PermitAll
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/signup")
    public Result signup(@RequestBody @Validated UserRegister userRegister) {
        userService.signupAndGetToken(userRegister);

        return Result.success();
    }

    @PostMapping("/signin")
    public Result signin(@RequestBody @Validated UserLogin userLogin) throws JsonProcessingException {
        String token = userService.signinAndGetToken(userLogin);

        return Result.success(token);
    }

}
