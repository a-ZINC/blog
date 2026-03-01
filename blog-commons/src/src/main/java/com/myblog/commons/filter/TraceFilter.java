package com.myblog.commons.filter;

import com.myblog.commons.context.RequestContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class TraceFilter implements Filter {
    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String USER_ID_HEADER = "X-User-Id";
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (traceId == null || traceId.isEmpty()) {
            traceId = UUID.randomUUID().toString();
        }

        String userId = request.getHeader(USER_ID_HEADER);

        RequestContext context = RequestContext.builder().traceId(traceId).userId(userId).build();
        RequestContext.set(context);
        log.debug("Request started - traceId: {}, userId: {}, method: {}, path: {}", traceId, userId, request.getMethod(), request.getRequestURI());

        try {
            filterChain.doFilter(servletRequest, servletResponse);

            response.setHeader(TRACE_ID_HEADER, traceId);
        } finally {
            RequestContext.clear();
            log.debug("Request completed - traceId: {}, userId: {}", traceId, userId);
        }

    }
}
