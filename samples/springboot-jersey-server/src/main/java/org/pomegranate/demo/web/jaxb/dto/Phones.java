package org.pomegranate.demo.web.jaxb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author sylenthira
 */
@XmlRootElement(name = "phones")
@XmlAccessorType(XmlAccessType.FIELD)
public class Phones {

    @XmlElement(name = "phone")
    private List<JaxbPhone> jaxbPhoneList;

    public List<JaxbPhone> getPhoneList() {
        return jaxbPhoneList;
    }

    public void setPhoneList(List<JaxbPhone> phoneList) {
        this.jaxbPhoneList = phoneList;
    }
}
