package net.file.server.api.error;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.USER)
public class GlobalExceptionHandler implements ExceptionMapper<IOException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(IOException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorInfo(uriInfo.getRequestUri().toString(), exception.getMessage()))
                .build();
    }
}
