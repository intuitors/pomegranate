package org.pomegranate.demo.web.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/rest-api")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		packages("org.pomegranate");
	}

}