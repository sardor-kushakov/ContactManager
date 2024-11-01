package sarik.dev.controller;

import sarik.dev.model.Contact;
import sarik.dev.service.ContactService;
import sarik.dev.util.ValidationUtil;

import java.util.List;

import static sarik.dev.util.ScannerUtils.scanStr;

public class ContactController {

    private static ContactService contactService = new ContactService();

    public static void addContact() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanStr.nextLine();
            if (!ValidationUtil.validateName(name)) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (!ValidationUtil.validateName(name));

        System.out.print("Enter surname: ");
        String surname = scanStr.nextLine();
        if (surname.trim().isEmpty()) {
            surname = null; // Familiya kiritilmagan bo‘lsa, null qilamiz
        }

        String phone;
        do {
            System.out.print("Enter phone: ");
            phone = scanStr.nextLine();
            if (!ValidationUtil.validatePhoneNumber(phone)) {
                System.out.println("Invalid phone number. Please enter a valid Uzbekistan phone number in the format +998XXXXXXXXX.");
            }
        } while (!ValidationUtil.validatePhoneNumber(phone));

        boolean result = contactService.addContact(name, surname, phone);
        if (result) {
            System.out.println("Contact added successfully");
        } else {
            System.out.println("Contact already exists");
        }
    }

    public static void showContacts() {
        List<Contact> contacts = contactService.getAllContacts();

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact.getName() + " " + contact.getSurname() + " - " + contact.getPhone());
            }
        }
    }

    public static void editContact() {
        System.out.print("Enter the phone number of the contact to edit: ");
        String phone = scanStr.nextLine();

        Contact contact = contactService.getContactByPhone(phone);

        if (contact == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanStr.nextLine();
        if (!name.isEmpty()) {
            contact.setName(name);
        }

        System.out.print("Enter new surname (or press Enter to keep current): ");
        String surname = scanStr.nextLine();
        if (!surname.isEmpty()) {
            contact.setSurname(surname);
        }

        System.out.print("Enter new phone (or press Enter to keep current): ");
        String newPhone = scanStr.nextLine();
        if (!newPhone.isEmpty() && !newPhone.equals(contact.getPhone())) {
            Contact exists = contactService.getContactByPhone(newPhone);
            if (exists != null) {
                System.out.println("Another contact with this phone number already exists.");
                return;
            }
            contact.setPhone(newPhone);
        }

        boolean result = contactService.updateContact(contact);
        if (result) {
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Something went wrong while updating contact.");
        }
    }

    public static void deleteContact() {
        System.out.print("Enter the phone number of the contact to delete: ");
        String phone = scanStr.nextLine();

        boolean result = contactService.deleteContact(phone);
        if (result) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact not found or something went wrong while deleting contact.");
        }
    }

    public static void searchContacts() {
        System.out.print("Enter search text (name, surname or phone part): ");
        String searchText = scanStr.nextLine();

        List<Contact> contacts = contactService.searchContacts(searchText);

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact.getName() + " " + contact.getSurname() + " - " + contact.getPhone());
            }
        }
    }
}
