package com.srp.users.providers;

import com.srp.users.exceptions.GeneralException;
import com.srp.users.pojos.ExceptionMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionHandler implements ExceptionMapper<GeneralException> {
    @Override
    public Response toResponse(GeneralException e) {
        return Response.serverError().entity(new ExceptionMessage(500L, e.getMessage(), null)).build();
    }
}
