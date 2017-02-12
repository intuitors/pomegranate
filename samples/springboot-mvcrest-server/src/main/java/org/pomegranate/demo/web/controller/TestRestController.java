package org.pomegranate.demo.web.controller;

import org.pomegranate.demo.web.jaxb.dto.ServiceResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sylenthira on 2/12/2017.
 */
@RestController
@RequestMapping("/rest-api/test")
public class TestRestController {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity greetingsInText() {
        return ResponseEntity.ok("Greetings from Spring Boot!");
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity greetingsInJSON() {
        ServiceResponse<String> responseEntity = new ServiceResponse<>();
        responseEntity.setStatus(ServiceResponse.STATUS_SUCCESS);
        responseEntity.setMessage("Greetings from Spring Boot Jersey Server!");
        return ResponseEntity.ok(responseEntity);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity greetingsInXml() {
        ServiceResponse<String> responseEntity = new ServiceResponse<>();
        responseEntity.setStatus(ServiceResponse.STATUS_SUCCESS);
        responseEntity.setMessage("Greetings from Spring Boot Jersey Server!");
        return ResponseEntity.ok(responseEntity);
    }

}
