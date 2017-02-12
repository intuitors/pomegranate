package org.pomegranate.demo;

import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.web.jaxb.dto.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author sylenthira
 */
public class RestTemplateClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateClient.class);

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
        HttpHeaders httpHeaders;
        HttpEntity httpEntity;
        ServiceResponse serviceResponse;

        String response = restTemplate.getForObject("http://localhost:8090/demo/rest-api/test", String.class);
        LOGGER.debug("Response: {}", response);

        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE);
        httpEntity = new HttpEntity<>(httpHeaders);
        serviceResponse = restTemplate.exchange("http://localhost:8090/demo/rest-api/test", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<ServiceResponse<String>>() {}).getBody();

        LOGGER.debug("Response: {}", serviceResponse);

        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpEntity = new HttpEntity<>(httpHeaders);
        serviceResponse = restTemplate.exchange("http://localhost:8090/demo/rest-api/test", HttpMethod.GET, httpEntity,
                        new ParameterizedTypeReference<ServiceResponse<String>>() {}).getBody();

        LOGGER.debug("Response: {}", serviceResponse);

        httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpEntity = new HttpEntity<>(httpHeaders);
        List<Contact> contactList = restTemplate.exchange("http://localhost:8090/demo/rest-api/contacts", HttpMethod.GET,
                httpEntity, new ParameterizedTypeReference<List<Contact>>() {}).getBody();
        LOGGER.debug("Response: {}", contactList);

       /* Contact postContact = new Contact();
        postContact.setName("Kamalambal");
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpEntity = new HttpEntity<>(postContact, httpHeaders);
        ResponseEntity responseEntity = restTemplate.exchange("http://localhost:8090/demo/rest-api/contacts",
                HttpMethod.POST, httpEntity, Object.class);


        LOGGER.debug("Post Response: {}", responseEntity.getStatusCode());*/
    }
}

