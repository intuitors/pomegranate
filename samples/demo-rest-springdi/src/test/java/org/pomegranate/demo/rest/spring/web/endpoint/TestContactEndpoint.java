package org.pomegranate.demo.rest.spring.web.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.pomegranate.demo.rest.spring.web.json.ContactDto;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;


@Test
public class TestContactEndpoint extends JerseyTestNg.ContainerPerClassTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestContactEndpoint.class);

    @Override
    protected Application configure() {
        //specifying the endpoint class under test
        return new ResourceConfig(ContactEndpoint.class)
                //setting spring application context config location
                .property("contextConfigLocation", "classpath:META-INF/app-context.xml");
    }

    @Test
    public void test_getContacts() {
        final Response response = target("contacts").request().get();
        Assert.assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        List<ContactDto> contactDtoList = response.readEntity(new GenericType<List<ContactDto>>() {});
        Assert.assertNotNull(contactDtoList);
        Assert.assertTrue(contactDtoList.size() > 0);

        try {
            LOGGER.debug("ContactDtoList: {}", new ObjectMapper().writeValueAsString(contactDtoList));
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while converting to JSON string ", e);
        }

    }

}
