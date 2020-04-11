package com.kazma.securitydemo.controller;

import com.kazma.securitydemo.entity.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/phones")
@RolesAllowed({"ROLE_PHONE"})
public class PhoneController {

    @GetMapping
    public Result phones() {
        return Result.success();
    }

}
