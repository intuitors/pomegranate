package org.pomegranate.demo.rest.endpoint;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Test
public class TestGreetingsEndpoint extends JerseyTestNg.ContainerPerClassTest  {

    @Override
    protected Application configure() {
        //specifying the endpoint class under test
        return new ResourceConfig(GreetingsEndpoint.class);
    }


    @Test
    public void testHello() {
        final Response response = target("greetings/hello").request().get();

        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        Assert.assertEquals(response.readEntity(String.class), "Hello, people!");
    }

}
