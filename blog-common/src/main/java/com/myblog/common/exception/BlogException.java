package com.myblog.common.exception;

import lombok.Getter;

@Getter
public class BlogException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String details;

    public BlogException(ErrorCode errorCode, String details) {
        super(details);
        this.errorCode = errorCode;
        this.details = details;
    }

    public BlogException(ErrorCode errorCode, String details, Throwable cause) {
        super(details, cause);
        this.errorCode = errorCode;
        this.details = details;
    }

}
