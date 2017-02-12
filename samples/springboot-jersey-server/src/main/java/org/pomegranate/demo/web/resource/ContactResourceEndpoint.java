package org.pomegranate.demo.web.resource;

import org.apache.commons.lang3.StringUtils;
import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;
import org.pomegranate.demo.service.ContactEntryService;
import org.pomegranate.demo.web.jaxb.dto.*;
import org.pomegranate.demo.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sylenthira on 2/12/2017.
 */
@Component
@Path("/contacts")
public class ContactResourceEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactResourceEndpoint.class);

    @Autowired
    private ContactEntryService contactEntryService;

    @Context
    private HttpServletRequest httpRequest;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactsInJSON(@QueryParam("start") Integer start, @QueryParam("size") Integer size) {
        LOGGER.info("Inside getContactsInJSON");

        return ResponseUtil.getOkSuccess(getContacts(start, size));
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getContactsInXML(@QueryParam("start") Integer start, @QueryParam("size") Integer size) {
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
        return ResponseUtil.getOkSuccess(contacts);
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getContactById(@PathParam("id") Integer id) {
        LOGGER.info("Inside getContactById");

        Contact contact = contactEntryService.getContactById(id);
        String accept = httpRequest.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isNotBlank(accept) && accept.equals(MediaType.APPLICATION_XML)){
            return ResponseUtil.getOkSuccess(createJaxbContact(contact));
        }
        return ResponseUtil.getOkSuccess(contact);
    }

    @GET
    @Path("/contact")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getContactByPhone(@QueryParam("phoneNo") String phoneNo) {
        LOGGER.info("Inside getContactByPhone");

        Contact contact = contactEntryService.getContactByPhoneNo(phoneNo);
        String accept = httpRequest.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isNotBlank(accept) && accept.equals(MediaType.APPLICATION_XML)){
            return ResponseUtil.getOkSuccess(createJaxbContact(contact));
        }
        return ResponseUtil.getOkSuccess(contact);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addContact(Contact contact) {
        LOGGER.info("Inside addContact");

        Contact rec = contactEntryService.addContact(contact);
        return ResponseUtil.getCreated(uriInfo.getBaseUri() + "contacts/" + rec.getId());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(@PathParam("id") Integer id, Contact contact) {
        LOGGER.info("Inside updateContact");

        contactEntryService.updateContact(id, contact);
        return ResponseUtil.getCreated(uriInfo.getBaseUri() + "contacts/" + id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") Integer id) {
        LOGGER.info("Inside deleteContact");

        contactEntryService.deleteContact(id);
        return ResponseUtil.getOkSuccess();
    }

    @GET
    @Path("/{contactId}/phones")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhonesByContactId(@PathParam("contactId") Integer contactId) {
        LOGGER.info("Inside getPhonesByContactId");

        return ResponseUtil.getOkSuccess(contactEntryService.getPhones(contactId));
    }

    @GET
    @Path("/{contactId}/phones/{phoneId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoneByContactIdPhoneId(@PathParam("contactId") Integer contactId,
                                               @PathParam("phoneId") Integer phoneId){
        LOGGER.info("Inside getPhoneByContactIdPhoneId");

        return ResponseUtil.getOkSuccess(contactEntryService.getPhoneByContactIdPhoneId(contactId, phoneId));
    }

    @POST
    @Path("/{contactId}/phones")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPhone(@PathParam("contactId") Integer contactId, Phone phone){
        LOGGER.info("Inside addPhone");

        Phone rec = contactEntryService.addPhone(contactId, phone);
        return ResponseUtil.getCreated(uriInfo.getBaseUri() + "contacts/" + contactId + "phones/" + rec.getId());
    }

    @PUT
    @Path("/{contactId}/phones/{phoneId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePhone(@PathParam("contactId") Integer contactId, @PathParam("phoneId") Integer phoneId,
                                Phone phone) {
        LOGGER.info("Inside updatePhone");

        contactEntryService.updatePhone(contactId, phoneId, phone);
        return ResponseUtil.getCreated(uriInfo.getBaseUri() + "contacts/" + contactId + "phones/" + phoneId);
    }

    @DELETE
    @Path("/{contactId}/phones/{phoneId}")
    public Response deletePhone(@PathParam("contactId") Integer contactId, @PathParam("phoneId") Integer phoneId) {
        LOGGER.info("Inside deletePhone");

        contactEntryService.deletePhone(contactId, phoneId);
        return ResponseUtil.getOkSuccess();
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
