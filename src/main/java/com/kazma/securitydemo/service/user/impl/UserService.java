package com.kazma.securitydemo.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kazma.securitydemo.dao.UserDao;
import com.kazma.securitydemo.entity.user.User;
import com.kazma.securitydemo.entity.user.vo.UserLogin;
import com.kazma.securitydemo.entity.user.vo.UserRegister;
import com.kazma.securitydemo.entity.user.vo.UserToken;
import com.kazma.securitydemo.enums.Status;
import com.kazma.securitydemo.exception.UserException;
import com.kazma.securitydemo.service.user.IUserService;
import com.kazma.securitydemo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    private static final String ROLE_ADMIN = "1";
    private static final String ROLE_BOOK = "2";
    private static final String ROLE_PHONE = "3";

    private static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Override
    public String signinAndGetToken(UserLogin userLogin) throws JsonProcessingException {
        User user = Optional.ofNullable(userDao.queryByUsername(userLogin.getUsername())).
                orElseThrow(() -> new UserException(Status.USER_UN_EXITS));

        if (!B_CRYPT_PASSWORD_ENCODER.matches(userLogin.getPassword(), user.getPassword())) {
            throw new UserException(Status.AUTH_FAILD);
        }

        UserToken userToken = new UserToken();
        userToken.setId(user.getId());
        userToken.setUsername(user.getUsername());

        return JwtUtils.getLoginToken(userToken);
    }

    @Override
    public void signupAndGetToken(UserRegister userRegister) {
        userRegister.setPassword(B_CRYPT_PASSWORD_ENCODER.encode(userRegister.getPassword()));

        switch (userRegister.getUsername()) {
            case "admin": {
                userRegister.setRoleId(ROLE_ADMIN);
                break;
            }
            case "book": {
                userRegister.setRoleId(ROLE_BOOK);
                break;
            }
            case "phone": {
                userRegister.setRoleId(ROLE_PHONE);
            }
            default: {
                break;
            }
        }

        userDao.addUser(userRegister);
    }

}
