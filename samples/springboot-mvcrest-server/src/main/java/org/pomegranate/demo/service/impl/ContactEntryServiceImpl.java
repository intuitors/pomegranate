package org.pomegranate.demo.service.impl;


import org.pomegranate.demo.dal.dao.ContactDao;
import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;
import org.pomegranate.demo.service.ContactEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sylenthira
 */
@Service
public class ContactEntryServiceImpl implements ContactEntryService {

    @Autowired
    private ContactDao contactDao;


    @Override
    public List<Contact> getContacts() {
        return contactDao.getContacts();
    }

    @Override
    public List<Contact> getContacts(int start, int size) {
        return contactDao.getContacts(start, size);
    }

    @Override
    public Contact getContactById(int id) {
        return contactDao.getContactById(id);
    }

    @Override
    public Contact getContactByPhoneNo(String phoneNo) {
        return contactDao.getContactByPhoneNo(phoneNo);
    }

    @Override
    public Contact addContact(Contact contact) {
        return contactDao.addContact(contact);
    }

    @Override
    public void updateContact(int id, Contact contact) {
        contactDao.updateContact(id, contact);
    }

    @Override
    public void deleteContact(int id) {
        contactDao.deleteContact(getContactById(id));
    }

    @Override
    public List<Phone> getPhones(int contactId) {
        return contactDao.getPhonesByContactId(contactId);
    }

    @Override
    public Phone getPhoneByContactIdPhoneId(int contactId, int phoneId) {
        return contactDao.getPhoneByContactIdPhoneId(contactId, phoneId);
    }

    @Override
    public Phone addPhone(int contactId, Phone phone) {
        return contactDao.addPhone(contactId, phone);
    }

    @Override
    public void updatePhone(int contactId, int phoneId, Phone phone) {
        contactDao.updatePhone(contactId, phoneId, phone);
    }

    @Override
    public void deletePhone(int contactId, int phoneId) {
        contactDao.deletePhone(contactId, getPhoneByContactIdPhoneId(contactId, phoneId));
    }
}
