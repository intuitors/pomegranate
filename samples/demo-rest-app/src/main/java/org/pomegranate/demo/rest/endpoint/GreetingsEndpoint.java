package org.pomegranate.demo.rest.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("greetings")
public class GreetingsEndpoint {

    @GET
    @Path("/hello")
    public String hello() {
        return "Hello, people!";
    }
}
