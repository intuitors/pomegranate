# Pomegranate

## Samples
* **samples/demo-rest-app** - A very simple JAX-RS Jersey project, including test class driven by Jersey Test Framework and TestNg.
* **samples/demo-rest-springdi** - Simple JAX-RS Jersey project with Spring Integration, including test class driven by Jersey Test Framework and TestNg.
* **samples/springboot-jersey-server** - Sample project to demonstrate Jax-RS based REST service implementation using Jersey powered by Spring Boot.
 * Host - localhost
 * Port - 8090
 * Context - http://localhost:8090/demo/ 
 * WADL - http://localhost:8090/demo/rest-api/application.wadl
 * Test - http://localhost:8090/demo/rest-api/test
 * Contacts - http://localhost:8090/demo/rest-api/contacts
* **samples/springboot-mvcrest-server** - Sample project to demonstrate Spring MVC based REST service implementation powered by Spring Boot. 
 * Host - localhost
 * Port - 8092
 * Context - http://localhost:8092/demo/ 
 * Test - http://localhost:8092/demo/rest-api/test
 * Contacts - http://localhost:8092/demo/rest-api/contacts

### Maven Build and Run
* mvn clean install
* mvn spring-boot:run

## References
* **references/REST-Concepts-and-Practice.pdf** - Presentation in PDF that outlines the basic concepts and usage of RESTful Web Services.
* **references/Maven_the_reference_guide.pdf** - Maven: The Complete Reference in PDF
