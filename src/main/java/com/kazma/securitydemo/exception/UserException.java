package com.kazma.securitydemo.exception;

import com.kazma.securitydemo.enums.Status;

public class UserException extends CommonException {

    public UserException(Status status) {
        super(status);
    }
}
