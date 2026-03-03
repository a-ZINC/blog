package com.myblog.service.resource;

import jakarta.ws.rs.Produces;
import lombok.extern.slf4j.Slf4j;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.myblog.common.context.RequestContext;


@Slf4j
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Response sayHello() {
        RequestContext ctx = RequestContext.get();
        String traceId = (ctx != null && ctx.getTraceId() != null) ? ctx.getTraceId() : "unknown";
        log.info("Received request with ID: {}", traceId);
        return Response.ok("Hello, World! Your request ID is: " + traceId).build();
    }

}