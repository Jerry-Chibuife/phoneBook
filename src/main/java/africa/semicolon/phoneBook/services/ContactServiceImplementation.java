package africa.semicolon.phoneBook.services;

import africa.semicolon.phoneBook.data.models.Contact;
import africa.semicolon.phoneBook.data.repositories.ContactRepository;
import africa.semicolon.phoneBook.data.repositories.ContactRepositoryImplementation;
import africa.semicolon.phoneBook.dtos.requests.ContactCreationForm;
import africa.semicolon.phoneBook.dtos.responses.ContactCreationResponse;
import africa.semicolon.phoneBook.dtos.responses.FindContactResponse;

import java.util.List;

public class ContactServiceImplementation implements ContactService{

    private ContactRepository contactRepository = new ContactRepositoryImplementation();

    @Override
    public ContactCreationResponse createAContact(ContactCreationForm creationForm) {
        Contact contact = new Contact();
        contact.setFirstName(creationForm.getFirstName());
        contact.setLastName(creationForm.getLastName());
        contact.setMiddleName(creationForm.getMiddleName());
        contact.setMobile(creationForm.getMobile());
        contact.setOffice(creationForm.getOffice());
        contactRepository.saveContact(contact);

        ContactCreationResponse response = new ContactCreationResponse();
        response.setName(contact.getFirstName() + " " + contact.getMiddleName() + " " + contact.getLastName());
        response.setMobile(contact.getMobile());
        response.setOffice(contact.getOffice());
        return response;
    }

    @Override
    public ContactRepository getRepository() {
        return contactRepository;
    }

    @Override
    public FindContactResponse updateContactInfo(Contact contact) {
        for(Contact repoContact : contactRepository.getAllContacts()){
            if(repoContact.getMobile().contains(contact.getMobile().get(0))
                    || repoContact.getOffice().contains(contact.getOffice().get(0))){
                Contact updatedContact = new Contact();
                if(contact.getLastName() != null)
                    updatedContact.setLastName(contact.getLastName());
                if(contact.getFirstName() != null)
                    updatedContact.setFirstName(contact.getFirstName());
                if(contact.getMiddleName() != null)
                    updatedContact.setMiddleName(contact.getMiddleName());
                if(!(repoContact.getMobile().contains(contact.getMobile().get(0)))) {
                    for(String number : repoContact.getMobile()){
                        contact.setMobile(number);
                    }
//                    contact.getMobile().add(repoContact.getMobile().get(0));
                }
                if(!(repoContact.getOffice().contains(contact.getOffice().get(0)))) {
                    for(String number : repoContact.getOffice()){
                        contact.setMobile(number);
                    }
//                    repoContact.getOffice().add(contact.getOffice().get(0));
                }
                for(String number : contact.getMobile()){
                    updatedContact.setMobile(number);
                }
                for(String number : contact.getOffice()){
                    updatedContact.setOffice(number);
                }
                contactRepository.deleteContactByObject(repoContact);
                contactRepository.saveContact(updatedContact);
            }
            break;
        }
        FindContactResponse response = new FindContactResponse();
        response.setResponseInfo("Successfully Updated!");
        return response;
    }

    @Override
    public Contact searchForContactWithMobileNumber(String mobileNumber) {
        for(Contact contact : contactRepository.getAllContacts()){
            if(contact.getMobile().contains(mobileNumber))
                return contact;
        }
        return null;
    }

    @Override
    public FindContactResponse searchForAllContactsContaining(String series) {
        List<Contact> contactList = contactRepository.getListOfContactsRelatingTo(series);
        StringBuilder builder = new StringBuilder();
        for(Contact contact : contactList){
            builder.append(contact.toString());
        }
        FindContactResponse response = new FindContactResponse();
        response.setResponseInfo("These are all the contacts relating to "+series+":\n"+ builder);
        return response;
    }

    @Override
    public FindContactResponse deleteContactByMobileNumber(String mobileNumber) {
        for(Contact contact : contactRepository.getAllContacts()){
            if(contact.getMobile().contains(mobileNumber))
                contactRepository.getAllContacts().remove(contact);
            break;
        }
        FindContactResponse response = new FindContactResponse();
        response.setResponseInfo("Delete Successful!");
        return response;
    }

    @Override
    public FindContactResponse deleteContactByOfficeNumber(String officeNumber) {
        for(Contact contact : contactRepository.getAllContacts()){
            if(contact.getOffice().contains(officeNumber))
                contactRepository.getAllContacts().remove(contact);
            break;
        }
        FindContactResponse response = new FindContactResponse();
        response.setResponseInfo("Delete Successful!");
        return response;
    }

    @Override
    public FindContactResponse getAllContacts() {
        StringBuilder builder = new StringBuilder();
        for(Contact contact : contactRepository.getAllContacts()){
            builder.append(contact.toString());
        }
        FindContactResponse response = new FindContactResponse();
        response.setResponseInfo("These are all the contacts in your PhoneBook:\n"+ builder);
        return response;
    }
}
