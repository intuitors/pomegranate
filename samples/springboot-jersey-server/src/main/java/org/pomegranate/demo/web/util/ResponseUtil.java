package org.pomegranate.demo.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author sylenthira
 */
public class ResponseUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    public static Response getOkSuccess() {
        return Response.ok().build();
    }


    public static Response getOkSuccess(Object entity) {
        return Response.status(Status.OK).entity(entity).build();
    }

    public static Response getCreated() {
        return Response.status(Status.CREATED).build();
    }

    public static Response getCreated(String path) {
        try {
            return Response.created(new URI(path)).build();
        } catch (URISyntaxException e) {
            return getCreated();
        }
    }

    public static Response getCreated(Object entity) {
        return Response.status(Status.CREATED).entity(entity).build();
    }

    public static Response getCreated(Object entity, String path) {
        try {
            return Response.status(Status.CREATED).entity(entity).contentLocation(new URI(path)).build();
        } catch (URISyntaxException e) {
            return getCreated(entity);
        }
    }

    public static Response getNoContent() {
        return Response.status(Status.NO_CONTENT).build();
    }

}
