package com.myblog.commons.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", 400),
    ARTICLE_NOT_FOUND("ARTICLE_NOT_FOUND", 404),
    SERIES_NOT_FOUND("SERIES_NOT_FOUND", 404),
    DUPLICATE_SLUG("DUPLICATE_SLUG", 409),
    UNAUTHORIZED("UNAUTHORIZED", 401),
    FORBIDDEN("FORBIDDEN", 403),


    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 500),
    DOWNSTREAM_ERROR("DOWNSTREAM_ERROR", 502);

    private final String code;
    private final int httpStatus;
}
