package org.pomegranate.demo.web.jaxb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sylenthira on 09/02/17.
 */
@XmlRootElement(name = "contact")
@XmlAccessorType(XmlAccessType.FIELD)
public class JaxbContact {
    @XmlAttribute(name = "id")
    private Integer id;
    private String name;
    private Phones phones;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phones getPhones() {
        return phones;
    }

    public void setPhones(Phones phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "JaxbContact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                '}';
    }
}
