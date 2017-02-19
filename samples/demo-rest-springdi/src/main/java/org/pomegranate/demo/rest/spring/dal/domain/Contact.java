package org.pomegranate.demo.rest.spring.dal.domain;

import java.util.HashSet;
import java.util.Set;


public class Contact {
    private int id;
    private String name;
    private Set<Phone> phones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public void addPhone(Phone phone) {
        if(this.phones == null) {
            this.phones = new HashSet<>();
        }
        phones.add(phone);
    }
}
