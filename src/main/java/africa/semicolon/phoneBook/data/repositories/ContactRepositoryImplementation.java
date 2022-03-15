package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.Contact;
import africa.semicolon.phoneBook.exceptions.ContactNotInExistenceException;
import africa.semicolon.phoneBook.exceptions.ExactContactExistenceException;
import africa.semicolon.phoneBook.exceptions.NullFieldsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContactRepositoryImplementation implements ContactRepository{

    private List<Contact> storage = new ArrayList<>();

    @Override
    public void saveContact(Contact contact) {
        checkAgainstDuplicateContact(contact);
        if(contact.getMobile().isEmpty() && contact.getOffice().isEmpty())
            throw new NullFieldsException("Your mobile and office number fields cannot both be empty");
        if(contact.getLastName() == null && contact.getFirstName() == null && contact.getMiddleName() == null)
            throw new NullFieldsException("At least one name should be tied to this new contact");
        storage.add(contact);
    }

    private void checkAgainstDuplicateContact(Contact contact) {
        for(Contact savedContact : storage){
            if(Objects.equals(savedContact.getMobile(), contact.getMobile()))
                if(Objects.equals(savedContact.getLastName(), contact.getLastName()))
                    if(Objects.equals(savedContact.getFirstName(), contact.getFirstName()))
                        if(Objects.equals(savedContact.getMiddleName(), contact.getMiddleName()))
                            if(Objects.equals(savedContact.getOffice(), contact.getOffice()))
                                throw new ExactContactExistenceException("This exact contact exists already");
        }
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public Contact getContactByMobile(String mobileNumber) {
        for (Contact contact : storage)
            if(contact.getMobile().contains(mobileNumber))
                return contact;
        throw new ContactNotInExistenceException("Contact not found, mobile number does not exist");
    }

    @Override
    public Contact getContactByLastName(String lastName) {
        for (Contact contact : storage)
            if(contact.getLastName().equals(lastName))
                return contact;
        throw new ContactNotInExistenceException("Contact not found, last name does not exist");
    }

    @Override
    public Contact getContactByFirstName(String firstName) {
        for (Contact contact : storage)
            if(contact.getFirstName().equals(firstName))
                return contact;
        throw new ContactNotInExistenceException("Contact not found, first name does not exist");
    }

    @Override
    public Contact getContactByMiddleName(String middleName) {
        for (Contact contact : storage)
            if(contact.getMiddleName().equals(middleName))
                return contact;
        throw new ContactNotInExistenceException("Contact not found, middle name does not exist");
    }

    @Override
    public Contact getContactByOfficeNumber(String officeNumber) {
        for (Contact contact : storage)
            if(contact.getOffice().contains(officeNumber))
                return contact;
        throw new ContactNotInExistenceException("Contact not found, office number does not exist");
    }

    @Override
    public Contact getContactByContactObject(Contact contact) {
        for (Contact contacts : storage)
            if(contacts.equals(contact))
                return contacts;
        throw new ContactNotInExistenceException("Contact not found, contact does not exist");
    }

    @Override
    public void deleteContactByMobileNumber(String mobileNumber) {
        for (Contact contact:storage){
            if(contact.getMobile() != null && contact.getMobile().contains(mobileNumber)) {
                storage.remove(contact);
                break;
            }
            else throw new ContactNotInExistenceException("Contact doesn't exist, no contact is linked to this mobile number");
        }
    }

    @Override
    public void deleteContactByOfficeNumber(String officeNumber) {
        for (Contact contact:storage){
            if(contact.getOffice() != null && contact.getOffice().contains(officeNumber)) {
                storage.remove(contact);
                break;
            }
            else throw new ContactNotInExistenceException("Contact doesn't exist, no contact is linked to this office number");
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        return storage;
    }

    @Override
    public void deleteContactByObject(Contact repoContact) {
        for(Contact contact : storage){
            if(contact.equals(repoContact))
                storage.remove(contact);
            break;
        }
    }

    @Override
    public List<Contact> getListOfContactsRelatingTo(String series) {
        List<Contact> contactList = new ArrayList<>();
        for(Contact contact : storage){
            if(contact.getMobile() != null) {
                for (String mobile : contact.getMobile()) {
                    if (mobile.contains(series))
                        contactList.add(contact);
                }
            }
            if(contact.getOffice() != null) {
                for (String office : contact.getOffice()) {
                    if (office.contains(series))
                        contactList.add(contact);
                }
            }
            if(contact.getFirstName() != null && contact.getFirstName().contains(series))
                contactList.add(contact);
            else if(contact.getMiddleName() != null && contact.getMiddleName().contains(series))
                contactList.add(contact);
            else if(contact.getLastName() != null && contact.getLastName().contains(series))
                contactList.add(contact);
        }
        return contactList;
    }
}
