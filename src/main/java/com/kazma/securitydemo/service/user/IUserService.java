package com.kazma.securitydemo.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kazma.securitydemo.entity.user.vo.UserLogin;
import com.kazma.securitydemo.entity.user.vo.UserRegister;

public interface IUserService {

    String signinAndGetToken(UserLogin userLogin) throws JsonProcessingException;

    void signupAndGetToken(UserRegister userRegister);
}
