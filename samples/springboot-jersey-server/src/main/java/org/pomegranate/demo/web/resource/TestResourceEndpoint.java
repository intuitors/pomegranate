package org.pomegranate.demo.web.resource;

import org.pomegranate.demo.web.jaxb.dto.ResponseBody;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sylenthira on 2/12/2017.
 */
@Component
@Path("/test")
public class TestResourceEndpoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response greetingsInText(){
        return Response.ok().entity("Greetings from Spring Boot Jersey Server!").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response greetingsInJSON() {
        ResponseBody<String> responseEntity = new ResponseBody<>();
        responseEntity.setStatus(ResponseBody.STATUS_SUCCESS);
        responseEntity.setMessage("Greetings from Spring Boot Jersey Server!");
        return Response.ok().entity(responseEntity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response greetingsInXml() {
        ResponseBody<String> responseEntity = new ResponseBody<>();
        responseEntity.setStatus(ResponseBody.STATUS_SUCCESS);
        responseEntity.setMessage("Greetings from Spring Boot Jersey Server!");
        return Response.ok().entity(responseEntity).build();
    }
}
