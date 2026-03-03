package com.myblog.core.processor;

public enum ActionType {

    CREATE_ARTICLE,
    UPDATE_ARTICLE,
    DELETE_ARTICLE,
    GET_ARTICLE,

    CREATE_SERIES,
    UPDATE_SERIES,
    TOGGLE_SERIES_PRIVACY,

    CREATE_USER,
    DELETE_USER,

    LIKE_ARTICLE,
    BOOKMARK_ARTICLE
}
