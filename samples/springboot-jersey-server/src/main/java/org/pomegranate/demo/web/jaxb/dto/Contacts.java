package org.pomegranate.demo.web.jaxb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author sylenthira
 */
@XmlRootElement(name = "contacts")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contacts {

    @XmlElement(name = "contact")
    private List<JaxbContact> contactList;


    public List<JaxbContact> getContactList() {
        return contactList;
    }

    public void setContactList(List<JaxbContact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contactList=" + contactList +
                '}';
    }
}
