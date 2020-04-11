package com.kazma.securitydemo.controller;

import com.kazma.securitydemo.entity.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/books")
@RolesAllowed({"ROLE_BOOK"})
public class BookController {

    @GetMapping
    public Result test() {
        return Result.success();
    }

}
