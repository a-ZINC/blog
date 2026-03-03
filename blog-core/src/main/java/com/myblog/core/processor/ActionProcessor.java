package com.myblog.core.processor;

import com.google.inject.Inject;
import com.myblog.common.context.RequestContext;
import com.myblog.common.exception.BlogException;
import com.myblog.core.context.ActionContext;
import com.myblog.core.executor.Executor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class ActionProcessor {

    private final Map<ActionType, List<Executor>> executorMap;

    @Inject
    public ActionProcessor(Map<ActionType, List<Executor>> executorMap) {
        this.executorMap = executorMap;
    }

    public ActionContext process(ActionType actionType, Object inputBO) {
        RequestContext reqCtx = RequestContext.get();
        String traceId = reqCtx != null ? reqCtx.getTraceId() : "unknown-trace-id";

        log.info("process action: {}, traceId: {}", actionType, traceId);

        List<Executor> executorList = executorMap.get(actionType);
        if (executorList == null || executorList.isEmpty()) {
            log.warn("No executors found for action type: {}, traceId: {}", actionType, traceId);
            throw new IllegalStateException("No executors found for action type: " + actionType);
        }

        ActionContext actionContext = new ActionContext(actionType);
        actionContext.set("input", inputBO);

        for (Executor executor : executorList) {
            try {
                executor.execute(actionContext);
            } catch (BlogException e) {
                log.error("BlogException executing action: {}, traceId: {}, error: {}", actionType, traceId, e.getMessage(), e);
                throw new RuntimeException(e);
            } catch (Exception e) {
                log.error("Error executing action: {}, traceId: {}, error: {}", actionType, traceId, e.getMessage(), e);
                throw new RuntimeException("Error executing action: " + actionType, e);
            }
        }

        return actionContext;
    }

}
