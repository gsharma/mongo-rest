package com.mulesoft.mongo.util;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class BaseExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
    private static Logger logger = LoggerFactory.getLogger(BaseExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        logger.error("Service exception handled by BaseExceptionMapper", exception);
        return Response.status(HttpStatusMapper.ClientError.BAD_REQUEST.code()).type(MediaType.APPLICATION_JSON)
                .entity(HttpStatusMapper.ClientError.BAD_REQUEST.message()).build();
    }
}