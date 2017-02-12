package org.pomegranate.demo.web.jaxb.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sylenthira on 2/12/2017.
 */
@XmlRootElement(name = "response", namespace = "https://github.com/intuitors/pomegranate")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseBody<T> {

    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_ERROR = -1;

    private int status;
    private T message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
