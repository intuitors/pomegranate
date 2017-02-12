package org.pomegranate.demo.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;
import org.pomegranate.demo.service.ContactEntryService;
import org.pomegranate.demo.web.jaxb.dto.Contacts;
import org.pomegranate.demo.web.jaxb.dto.JaxbContact;
import org.pomegranate.demo.web.jaxb.dto.JaxbPhone;
import org.pomegranate.demo.web.jaxb.dto.Phones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sylenthira on 2/12/2017.
 */
@RestController
@RequestMapping("/rest-api/contacts")
public class ContactRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactRestController.class);

    @Autowired
    private HttpServletRequest httpRequest;

    @Autowired
    private ContactEntryService contactEntryService;


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getContactsInJSON(@RequestParam(value = "start", required = false) Integer start,
                                            @RequestParam(value = "size", required = false) Integer size) {
        LOGGER.info("Inside getContactsInJSON");

        return ResponseEntity.ok(getContacts(start, size));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity getContactsInXML(@RequestParam(value = "start", required = false) Integer start,
                                           @RequestParam(value = "size", required = false) Integer size) {
        LOGGER.info("Inside getContactsInXML");

        List<Contact> contactList = getContacts(start, size);
        Contacts contacts = null;
        if (contactList != null && contactList.size() > 0) {
            contacts = new Contacts();
            List<JaxbContact> jaxbContacts = new ArrayList<>();
            contactList.forEach(contact -> {
                jaxbContacts.add(createJaxbContact(contact));
            });
            contacts.setContactList(jaxbContacts);
        }
        return ResponseEntity.ok(contacts);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getContactById(@PathVariable("id") Integer id) {
        LOGGER.info("Inside getContactById");

        Contact contact = contactEntryService.getContactById(id);
        String accept = httpRequest.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isNotBlank(accept) && accept.equals(MediaType.APPLICATION_XML_VALUE)){
            return ResponseEntity.ok(createJaxbContact(contact));
        }
        return ResponseEntity.ok(contact);
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getContactByPhone(@RequestParam("phoneNo") String phoneNo) {
        LOGGER.info("Inside getContactByPhone");

        Contact contact = contactEntryService.getContactByPhoneNo(phoneNo);
        String accept = httpRequest.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isNotBlank(accept) && accept.equals(MediaType.APPLICATION_XML_VALUE)){
            return ResponseEntity.ok(createJaxbContact(contact));
        }
        return ResponseEntity.ok(contact);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addContact(@RequestBody Contact contact) {
        LOGGER.info("Inside addContact");

        contactEntryService.addContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateContact(@PathVariable("id") Integer id, @RequestBody Contact contact) {
        LOGGER.info("Inside updateContact");

        contactEntryService.updateContact(id, contact);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteContact(@PathVariable("id") Integer id) {
        LOGGER.info("Inside deleteContact");

        contactEntryService.deleteContact(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{contactId}/phones", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPhonesByContactId(@PathVariable("contactId") Integer contactId) {
        LOGGER.info("Inside getPhonesByContactId");

        return ResponseEntity.ok(contactEntryService.getPhones(contactId));
    }

    @RequestMapping(value = "/{contactId}/phones/{phoneId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPhoneByContactIdPhoneId(@PathVariable("contactId") Integer contactId,
                                                     @PathVariable("phoneId") Integer phoneId){
        LOGGER.info("Inside getPhoneByContactIdPhoneId");

        return ResponseEntity.ok(contactEntryService.getPhoneByContactIdPhoneId(contactId, phoneId));
    }

    @RequestMapping(value = "/{contactId}/phones", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPhone(@PathVariable("contactId") Integer contactId, @RequestBody Phone phone){
        LOGGER.info("Inside addPhone");

        contactEntryService.addPhone(contactId, phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/{contactId}/phones/{phoneId}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePhone(@PathVariable("contactId") Integer contactId,
                                      @PathVariable("phoneId") Integer phoneId, @RequestBody Phone phone) {
        LOGGER.info("Inside updatePhone");

        contactEntryService.updatePhone(contactId, phoneId, phone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/{contactId}/phones/{phoneId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePhone(@PathVariable("contactId") Integer contactId,
                                      @PathVariable("phoneId") Integer phoneId) {
        LOGGER.info("Inside deletePhone");

        contactEntryService.deletePhone(contactId, phoneId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    private JaxbContact createJaxbContact(Contact contact) {
        JaxbContact jaxbContact = new JaxbContact();
        jaxbContact.setId(contact.getId());
        jaxbContact.setName(contact.getName());
        if (contact.getPhones() != null && contact.getPhones().size() > 0) {
            Phones phones = new Phones();
            phones.setPhoneList(new ArrayList<>());
            contact.getPhones().forEach(p-> {
                JaxbPhone jaxbPhone = new JaxbPhone();
                jaxbPhone.setId(p.getId());
                jaxbPhone.setNumber(p.getNumber());
                jaxbPhone.setType(p.getType());
                phones.getPhoneList().add(jaxbPhone);
            });
            jaxbContact.setPhones(phones);
        }
        return jaxbContact;
    }

    private List<Contact> getContacts(Integer start, Integer size) {
        List<Contact> contactList;
        if (start != null && size != null){
            contactList = contactEntryService.getContacts(start, size);
        } else {
            contactList =  contactEntryService.getContacts();
        }
        return contactList;
    }
}
