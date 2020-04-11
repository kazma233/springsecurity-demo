package com.kazma.securitydemo.exception;

import com.kazma.securitydemo.enums.Status;

public class CommonException extends RuntimeException {

    private String code;

    public CommonException(Status status) {
        super(status.getMsg());
        this.code = status.getCode();
    }

}
