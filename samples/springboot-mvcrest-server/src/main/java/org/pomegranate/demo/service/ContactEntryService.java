package org.pomegranate.demo.service;

import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;

import java.util.List;

/**
 * @author sylenthira
 */
public interface ContactEntryService {
    List<Contact> getContacts();
    List<Contact> getContacts(int start, int size);
    Contact getContactById(int id);
    Contact getContactByPhoneNo(String phoneNo);
    Contact addContact(Contact contact);
    void updateContact(int id, Contact contact);
    void deleteContact(int id);

    List<Phone> getPhones(int contactId);
    Phone getPhoneByContactIdPhoneId(int contactId, int phoneId);
    Phone addPhone(int contactId, Phone phone);
    void updatePhone(int contactId, int phoneId, Phone phone);
    void deletePhone(int contactId, int phoneId);

}
