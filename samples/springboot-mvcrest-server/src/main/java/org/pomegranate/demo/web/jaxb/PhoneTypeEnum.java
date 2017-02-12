package org.pomegranate.demo.web.jaxb;


import org.pomegranate.demo.dal.entity.enums.PhoneType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Sylenthira
 */
public class PhoneTypeEnum extends XmlAdapter<Integer, PhoneType> {
    @Override
    public PhoneType unmarshal(Integer type) throws Exception {
        return PhoneType.valueOf(type);
    }

    @Override
    public Integer marshal(PhoneType phoneType) throws Exception {
        return phoneType.getType();
    }
}
