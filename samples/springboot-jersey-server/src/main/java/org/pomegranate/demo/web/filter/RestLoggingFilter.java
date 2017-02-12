package org.pomegranate.demo.web.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.pomegranate.demo.web.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author sylenthira on 8/6/15.
 */
@Provider
public class RestLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String SEPARATOR = ": ";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestLoggingFilter.class);

    @Context
    HttpServletRequest httpServletRequest;
    @Context
    HttpServletResponse httpServletResponse;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        String clientIP = httpServletRequest.getHeader(Constants.HEADER_X_FORWARDED_FOR);
        if (clientIP == null) {
            clientIP = httpServletRequest.getRemoteAddr();
        }
        request.getHeaders().add(Constants.HEADER_CLIENT_IP, clientIP);
        if (LOGGER.isTraceEnabled()) {
            StringBuilder output = getLoggingMessageBuilder();

            output.append(request.getMethod()).append(" ")
                    .append(request.getUriInfo().getRequestUri()).append(" ")
                    .append(httpServletRequest.getProtocol()).append("\n");

            for (String s : request.getHeaders().keySet()) {
                output.append(s).append(SEPARATOR).append(request.getHeaderString(s)).append("\n");
            }

            output.append("\n");

            String content = null;
            try {
                content = IOUtils.toString(request.getEntityStream(), Constants.CHARSET_UTF_8);
                if (StringUtils.isNotBlank(content)) {
                    output.append(content).append("\n");
                }
            } catch (IOException e) {
                LOGGER.error("Error ", e);
            }

            if (content != null) {
                request.setEntityStream(IOUtils.toInputStream(content, Constants.CHARSET_UTF_8));
            }

            LOGGER.trace("Server in-bound: \n{}", output.toString());
        }
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws
            IOException {
        if (LOGGER.isTraceEnabled()) {

            StringBuilder output = getLoggingMessageBuilder();
            for (String key : response.getHeaders().keySet()) {
                output.append(key).append(SEPARATOR).append(response.getHeaders().getFirst(key))
                        .append("\n");
            }

            output.append("\n");

            if (response.getEntity() != null) {
                try {
                    if (isJson(response)) {

                        String content = new ObjectMapper().writerWithDefaultPrettyPrinter()
                                .writeValueAsString(response.getEntity());
                        output.append(content);


                    } else if (isXml(response)) {
                        final Marshaller marshaller =
                                JAXBContext.newInstance(response.getEntity().getClass()).createMarshaller();
                        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        final StringWriter stringWriter = new StringWriter();
                        marshaller.marshal(response.getEntity(), stringWriter);
                        output.append(stringWriter.toString());

                    } else {
                        if (response.getEntity() != null) {
                            output.append(response.getEntity());
                        }
                    }


                } catch (Exception e) {
                    LOGGER.error("Error while converting response entity to String ", e);
                }
            }


            LOGGER.trace("Server Out-bound: \n{}\n", output.toString());
        }
    }


    boolean isJson(ContainerResponseContext response) {
        return MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType());
    }

    boolean isXml(ContainerResponseContext response) {
        return MediaType.APPLICATION_XML_TYPE.equals(response.getMediaType());
    }

    private StringBuilder getLoggingMessageBuilder() {
        return new StringBuilder();
    }


}
