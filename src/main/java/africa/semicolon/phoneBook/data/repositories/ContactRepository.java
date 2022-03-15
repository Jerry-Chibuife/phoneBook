package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.Contact;

import java.util.List;

public interface ContactRepository {
    void saveContact(Contact contact);

    int count();

    Contact getContactByMobile(String mobileNumber);

    Contact getContactByLastName(String lastName);

    Contact getContactByFirstName(String firstName);

    Contact getContactByMiddleName(String middleName);

    Contact getContactByOfficeNumber(String officeNumber);

    List<Contact> getListOfContactsRelatingTo(String series);

    Contact getContactByContactObject(Contact contact);

    void deleteContactByMobileNumber(String mobileNumber);

    void deleteContactByOfficeNumber(String officeNumber);

    List<Contact> getAllContacts();

    void deleteContactByObject(Contact repoContact);
}
