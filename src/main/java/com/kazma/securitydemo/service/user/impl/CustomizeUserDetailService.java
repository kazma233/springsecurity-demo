package com.kazma.securitydemo.service.user.impl;

import com.kazma.securitydemo.dao.UserDao;
import com.kazma.securitydemo.entity.user.Permissions;
import com.kazma.securitydemo.entity.user.User;
import com.kazma.securitydemo.entity.user.UserPermissionsDTO;
import com.kazma.securitydemo.entity.user.vo.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 进行认证的时候需要一个 UserDetailsService 来获取用户的信息 UserDetails
 * 如果需要改变认证的用户信息来源，我们可以实现 UserDetailsService
 */
@Service
public class CustomizeUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.queryByUsername(username);

        List<String> permissionsList = userDao.findPermissionsList(user.getRoleId()).stream().
                map(Permissions::getName).
                collect(Collectors.toList());

        UserPermissionsDTO userPermissionsDTO = new UserPermissionsDTO();
        BeanUtils.copyProperties(user, userPermissionsDTO);
        userPermissionsDTO.setPermissionsList(permissionsList);

        return UserPrincipal.create(userPermissionsDTO);
    }
}
