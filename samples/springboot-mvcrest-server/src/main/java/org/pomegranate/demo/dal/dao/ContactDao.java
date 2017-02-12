package org.pomegranate.demo.dal.dao;

import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;

import java.util.List;

/**
 * @author sylenthira
 */
public interface ContactDao {
    List<Contact> getContacts();
    List<Contact> getContacts(Integer startIndex, Integer size);
    Contact getContactById(Integer id);
    Contact getContactByPhoneNo(String phoneNo);
    Contact addContact(Contact contact);
    void updateContact(Integer id, Contact contact);
    void deleteContact(Contact contact);

    List<Phone> getPhonesByContactId(Integer contactId);
    Phone getPhoneByContactIdPhoneId(Integer contactId, Integer phoneId);
    Phone addPhone(Integer contactId, Phone phone);
    void updatePhone(Integer contactId, Integer phoneId, Phone phone);
    void deletePhone(Integer contactId, Phone phone);
}
