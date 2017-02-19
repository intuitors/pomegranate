package org.pomegranate.demo.rest.spring.web.endpoint;

import org.pomegranate.demo.rest.spring.web.json.ContactDto;
import org.pomegranate.demo.rest.spring.dal.domain.Phone;
import org.pomegranate.demo.rest.spring.web.json.PhoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.pomegranate.demo.rest.spring.dal.domain.Contact;
import org.pomegranate.demo.rest.spring.service.ContactService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2017 MILLENNIUM IT SOFTWARE (PRIVATE) LIMITED
 * All rights reserved.
 * <p/>
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF
 * MILLENNIUM IT SOFTWARE (PRIVATE) LIMITED.
 * <p/>
 * This copy of the Source Code is intended for MILLENNIUM IT SOFTWARE (PRIVATE) LIMITED 's internal use only and is
 * intended for view by persons duly authorized by the management of MILLENNIUM IT SOFTWARE (PRIVATE) LIMITED. No
 * part of this file may be reproduced or distributed in any form or by any
 * means without the written approval of the Management of MILLENNIUM IT SOFTWARE (PRIVATE) LIMITED.
 * <p/>
 * Created by Sylenthira Sathashivam on 2/15/2017.
 */
@Component
@Path(ContactEndpoint.PATH)
public class ContactEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactEndpoint.class);
    public static final String PATH = "contacts";

    @Autowired
    private ContactService contactService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContacts() {
        List<Contact> contactList = contactService.getContacts();

        List<ContactDto> contactDtoList = null;
        if (contactList != null && contactList.size() > 0) {
            contactDtoList = new ArrayList<>();
            for (Contact contact : contactList) {
                ContactDto contactDto = new ContactDto();
                contactDto.setId(contact.getId());
                contactDto.setName(contact.getName());

                if (contact.getPhones() != null && contact.getPhones().size() > 0) {
                    for (Phone phone : contact.getPhones()) {
                        PhoneDto phoneDto = new PhoneDto();
                        phoneDto.setId(phone.getId());
                        phoneDto.setNumber(phone.getPhoneNo());
                        phoneDto.setType(phone.getPhoneType().getValue());
                    }
                }
                contactDtoList.add(contactDto);
            }
        }

        //return Response with HATEOAS links
        return Response.ok(contactDtoList)
                .link("self", uriInfo.getBaseUri() + PATH)
                .link("pageable", uriInfo.getBaseUri() + PATH  + "?start={start}&size={size}")
                .build();
    }
}
