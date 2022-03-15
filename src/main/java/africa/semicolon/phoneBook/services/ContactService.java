package africa.semicolon.phoneBook.services;

import africa.semicolon.phoneBook.data.models.Contact;
import africa.semicolon.phoneBook.data.repositories.ContactRepository;
import africa.semicolon.phoneBook.dtos.requests.ContactCreationForm;
import africa.semicolon.phoneBook.dtos.responses.ContactCreationResponse;
import africa.semicolon.phoneBook.dtos.responses.FindContactResponse;

public interface ContactService {
    ContactCreationResponse createAContact(ContactCreationForm creationForm);

    ContactRepository getRepository();

    FindContactResponse updateContactInfo(Contact contact);

    Contact searchForContactWithMobileNumber(String mobileNumber);

    FindContactResponse searchForAllContactsContaining(String series);

    FindContactResponse deleteContactByMobileNumber(String mobileNumber);

    FindContactResponse deleteContactByOfficeNumber(String officeNumber);

    FindContactResponse getAllContacts();
}
