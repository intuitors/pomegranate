package org.pomegranate.demo;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.web.jaxb.dto.Contacts;
import org.pomegranate.demo.web.jaxb.dto.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author sylenthira
 */
public class JerseyClientTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyClientTest.class);

    public static void main(String[] args) {
        Client client = ClientBuilder.newBuilder().register(JacksonJsonProvider.class).build();
        WebTarget webTarget;

        LOGGER.info("Accessing TestResourceEndpoint...");
        webTarget = client.target("http://localhost:8090/demo/rest-api/test");


        String response =  webTarget.request(MediaType.TEXT_PLAIN).get(String.class);
        LOGGER.debug("Response: {}", response);

        ServiceResponse<String> serviceResponse;
        serviceResponse = webTarget.request(MediaType.APPLICATION_XML)
                .get(new GenericType<ServiceResponse<String>>() {});
        LOGGER.debug("Response: {}", serviceResponse);

        serviceResponse = webTarget.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<ServiceResponse<String>>() {});
        LOGGER.debug("Response: {}", serviceResponse);

        LOGGER.info("Accessing ContactResourceEndpoint");
        webTarget = client.target("http://localhost:8090/demo/rest-api/contacts");

        List<Contact> contactList = webTarget.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Contact>>(){});
        LOGGER.debug("Response: {}", contactList);

        Contacts contacts = webTarget.request(MediaType.APPLICATION_XML).get(Contacts.class);
        LOGGER.debug("contacts: {}", contacts);

        contacts = webTarget.queryParam("start", 0).queryParam("size", 5)
                .request(MediaType.APPLICATION_XML).get(Contacts.class);
        LOGGER.debug("contacts: {}", contacts);

        webTarget = client.target("http://localhost:8090/demo/rest-api/contacts/{id}");
        Contact contact = webTarget.resolveTemplate("id", 2).request(MediaType.APPLICATION_JSON).get(Contact.class);
        LOGGER.debug("contact: {}", contact);

/*        Contact postContact = new Contact();
        postContact.setName("Sathashivam SP");
        webTarget = client.target("http://localhost:8090/demo/rest-api/contacts");
        Response postResponse = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(postContact));
        LOGGER.debug("POST Response: {}", postResponse.getStatusInfo());*/


     /*   Phone phone = new Phone();
        phone.setNumber("0711001517");
        phone.setType(PhoneType.MOBILE);
        contact.addPhone(phone);
        Response putResponse = webTarget.resolveTemplate("id", 2).request(MediaType.APPLICATION_JSON).put(Entity.json(contact));
        LOGGER.debug("PUT Response: {}", putResponse.getStatusInfo())
         */

        client.close();


    }
}
