package com.srp.product.providers;

import com.srp.product.pojos.ExceptionMessage;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class IllegalArugExceptionHandler implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return Response.status(400).entity(new ExceptionMessage(400L, e.getMessage(), null)).build();
    }
}
