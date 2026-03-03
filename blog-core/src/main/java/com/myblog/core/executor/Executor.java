package com.myblog.core.executor;

import com.myblog.common.exception.BlogException;
import com.myblog.core.context.ActionContext;

public interface Executor {

    void execute(ActionContext context) throws BlogException;
}
