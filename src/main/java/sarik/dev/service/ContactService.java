package sarik.dev.service;

import sarik.dev.model.Contact;
import sarik.dev.repository.ContactRepository;

import java.util.List;

public class ContactService {

    private static ContactRepository contactRepository = new ContactRepository();

    public boolean addContact(String name, String surname, String phone) {
        Contact exists = contactRepository.getByPhone(phone);
        if (exists != null) {
            return false; // Telefon raqam bo'yicha kontakt mavjud
        }

        Contact contact = new Contact(null, name, surname, phone);
        return contactRepository.saveContact(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.getAllContacts();
    }

    public Contact getContactByPhone(String phone) {
        return contactRepository.getByPhone(phone);
    }

    public boolean updateContact(Contact contact) {
        return contactRepository.updateContact(contact);
    }

    public boolean deleteContact(String phone) {
        return contactRepository.deleteContact(phone);
    }

    public List<Contact> searchContacts(String searchText) {
        return contactRepository.searchContacts(searchText);
    }
}
