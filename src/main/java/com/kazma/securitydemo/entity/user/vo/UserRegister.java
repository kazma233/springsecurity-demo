package com.kazma.securitydemo.entity.user.vo;

import javax.validation.constraints.NotBlank;

public class UserRegister {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private String roleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
