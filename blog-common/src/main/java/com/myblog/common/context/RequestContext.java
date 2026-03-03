package com.myblog.common.context;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestContext {

    private final String traceId;
    private final String userId;

    private static final ThreadLocal<RequestContext> contextHolder = new ThreadLocal<>();

    public static void set(RequestContext context) {
        contextHolder.set(context);
    }

    public static RequestContext get() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
