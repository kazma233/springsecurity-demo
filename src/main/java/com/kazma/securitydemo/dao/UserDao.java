package com.kazma.securitydemo.dao;

import com.kazma.securitydemo.entity.user.Permissions;
import com.kazma.securitydemo.entity.user.User;
import com.kazma.securitydemo.entity.user.vo.UserRegister;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {

    @Select("select * from t_user where username = #{username}")
    User queryByUsername(String username);

    @Select("select tp.* from t_permissions tp " +
            "join t_role_permissions trp on tp.id = trp.permissions_id " +
            "where trp.role_id = #{roleId}")
    List<Permissions> findPermissionsList(String roleId);

    @Insert("insert into t_user (username, password, role_id) values (#{username}, #{password}, #{roleId})")
    void addUser(UserRegister userRegister);

}
