package org.pomegranate.demo.dal.dao.impl;

import org.pomegranate.demo.dal.dao.ContactDao;
import org.pomegranate.demo.dal.entity.Contact;
import org.pomegranate.demo.dal.entity.Phone;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.pomegranate.demo.data.ContactDatabase.*;

/**
 * @author sylenthira
 */
@Repository
public class ContactDaoImpl implements ContactDao {

    @Override
    public List<Contact> getContacts() {
        return CONTACT_RECORDS.values().stream().collect(Collectors.toList());
    }

    public List<Contact> getContacts(Integer startIndex, Integer size) {
        return IntStream.range(startIndex, size)
                .filter(i -> i < getContacts().stream().count())
                .mapToObj(i -> getContacts().get(i))
                .collect(Collectors.toList());
    }

    @Override
    public Contact getContactById(Integer id) {
        return CONTACT_RECORDS.get(id);
    }

    @Override
    public Contact getContactByPhoneNo(String phoneNo) {
        Optional<Contact> contactEntry = getContacts().stream()
                .filter(contact -> contact.getPhones() != null)
                .filter(contact -> contact.getPhones().stream()
                        .anyMatch(phone -> phone.getNumber().equals(phoneNo)))
                .findFirst();
        return contactEntry.orElse(null);
    }

    @Override
    public Contact addContact(Contact contact) {
        if (contact != null) {
            contact.setId(CONTACT_ID.incrementAndGet());
            final Set<Phone> phones = contact.getPhones();
            if (phones != null && phones.size() > 0) {
                phones.forEach(phone -> {
                    if (phone.getId() == null) {
                        phone.setId(PHONE_ID.incrementAndGet());
                    }
                });
            }
            CONTACT_RECORDS.put(CONTACT_ID.get(), contact);
        }
        return contact;
    }

    @Override
    public void updateContact(Integer id, Contact contact) {
        if (contact != null) {
            Contact rec = CONTACT_RECORDS.get(id);
            if(rec!=null) {
                rec.setName(contact.getName());
                final Set<Phone> phones = contact.getPhones();
                if (phones !=null && phones.size()>0){
                    phones.forEach(phone -> {
                        if (phone.getId() == null) {
                            phone.setId(PHONE_ID.incrementAndGet());
                        }
                    });
                }
                rec.setPhones(phones);
                CONTACT_RECORDS.put(rec.getId(), rec);
            }

        }
    }

    @Override
    public void deleteContact(Contact contact) {
        if (contact != null) {
            CONTACT_RECORDS.remove(contact.getId());
        }
    }

    @Override
    public List<Phone> getPhonesByContactId(Integer contactId) {
        Contact contact = CONTACT_RECORDS.get(contactId);
        final Set<Phone> phones = contact.getPhones();
        return phones != null && phones.size() > 0 ? new ArrayList<>(phones) : null;

    }

    @Override
    public Phone getPhoneByContactIdPhoneId(Integer contactId, Integer phoneId) {
        Contact contact = CONTACT_RECORDS.get(contactId);
        if (contact != null) {
            final Set<Phone> phones = contact.getPhones();
            if (phones != null) {
                Optional<Phone> phoneEntry = phones.stream()
                        .filter(phone -> phone.getId().equals(phoneId))
                        .findFirst();
                if (phoneEntry.isPresent()) {
                    return phoneEntry.get();
                }
            }
        }
        return null;
    }

    @Override
    public Phone addPhone(Integer contactId, Phone phone) {
        if (phone != null) {
            Contact contact = CONTACT_RECORDS.get(contactId);
            if (contact != null) {
                phone.setId(PHONE_ID.incrementAndGet());
                contact.addPhone(phone);
            }
        }
        return phone;
    }

    @Override
    public void updatePhone(Integer contactId, Integer phoneId, Phone phone) {
        if (phone != null) {
            Contact contact = CONTACT_RECORDS.get(contactId);
            final Set<Phone> phones = contact.getPhones();
            if (phones != null) {
                phones.removeIf(p -> phoneId.equals(p.getId()));
                phones.add(phone);
            }
            CONTACT_RECORDS.put(contact.getId(), contact);
        }
    }

    @Override
    public void deletePhone(Integer contactId, Phone phone) {
        if (phone != null) {
            Contact contact = CONTACT_RECORDS.get(contactId);
            if (contact.getPhones() != null) {
                contact.getPhones().remove(phone);
            }
        }
    }

}
