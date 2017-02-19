package org.pomegranate.demo.rest.spring.service.impl;

import org.pomegranate.demo.rest.spring.service.ContactService;
import org.springframework.stereotype.Service;
import org.pomegranate.demo.rest.spring.dal.domain.Contact;

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
@Service
public class ContactServiceImpl implements ContactService {
    @Override
    public List<Contact> getContacts() {
        List<Contact> contactList = new ArrayList<>();
        Contact contact;
        contact = new Contact();
        contact.setId(1);
        contact.setName("Anu");
        contactList.add(contact);

        contact = new Contact();
        contact.setId(2);
        contact.setName("Pooja");
        contactList.add(contact);
        return contactList;
    }
}
