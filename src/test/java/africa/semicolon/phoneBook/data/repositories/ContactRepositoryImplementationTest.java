package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.Contact;
import africa.semicolon.phoneBook.exceptions.ContactNotInExistenceException;
import africa.semicolon.phoneBook.exceptions.ExactContactExistenceException;
import africa.semicolon.phoneBook.exceptions.NullFieldsException;
import africa.semicolon.phoneBook.exceptions.PhoneBookException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactRepositoryImplementationTest {

    ContactRepository contactRepository = new ContactRepositoryImplementation();

    private void saveAContact() {
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
    }

    @Test
    void contactCanBeSaved_RepositoryRegistersOneContactTest(){
        saveAContact();
        assertEquals(1, contactRepository.count());
    }

    @Test
    void contactCanBeSaved_WillReturnSavedContactTest(){
        saveAContact();
        Contact savedContact = contactRepository.getContactByMobile("08142194364");
        assertEquals("Uzuegbunam", savedContact.getLastName());
    }

    @Test
    void saveDuplicateContact_ThrowException(){
        saveAContact();
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");

        assertThrows(PhoneBookException.class, ()-> contactRepository.saveContact(contact));
        assertThrows(ExactContactExistenceException.class, ()-> contactRepository.saveContact(contact));
    }

    @Test
    void saveContactWithEmptyMobileAndOfficeFields_ThrowException(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");

        assertThrows(NullFieldsException.class, ()-> contactRepository.saveContact(contact));
    }

    @Test
    void saveContactWithEmptyFirstNameLastNameMiddleNameFields_ThrowException(){
        Contact contact = new Contact();
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");

        assertThrows(NullFieldsException.class, ()-> contactRepository.saveContact(contact));
    }

    @Test
    void throwException_GettingContactWithInvalidMobileNumber(){
        saveAContact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByMobile("0987686546"));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByMobile("0987686546"));
    }

    @Test
    void canRetrieveContactByLastName(){
        saveAContact();
        Contact savedContact = contactRepository.getContactByLastName("Uzuegbunam");
        List<String> mobiles = savedContact.getMobile();
        assertEquals("08142194364", mobiles.get(0));
//        System.out.println(savedContact.getID());
    }

    @Test
    void retrieveContactThatDoesNotExistByLastName_ThrowsContactNotInExistenceException(){
        saveAContact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByLastName("Joshua"));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByLastName("Joshua"));
    }

    @Test
    void canRetrieveContactByFirstName(){
        saveAContact();
        Contact savedContact = contactRepository.getContactByFirstName("Chibuife");
        assertEquals("Uzuegbunam", savedContact.getLastName());
    }

    @Test
    void retrieveContactThatDoesNotExistByFirstName_ThrowsContactNotInExistenceException(){
        saveAContact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByFirstName("Joshua"));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByFirstName("Joshua"));
    }

    @Test
    void canRetrieveContactByMiddleName(){
        saveAContact();
        Contact savedContact = contactRepository.getContactByMiddleName("Jerry");
        assertEquals("Uzuegbunam", savedContact.getLastName());
    }

    @Test
    void retrieveContactThatDoesNotExistByMiddleName_ThrowsContactNotInExistenceException(){
        saveAContact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByMiddleName("Joshua"));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByMiddleName("Joshua"));
    }

    @Test
    void canRetrieveContactByOfficeNumber(){
        saveAContact();
        Contact savedContact = contactRepository.getContactByOfficeNumber("09121143477");
        assertEquals("Uzuegbunam", savedContact.getLastName());
    }

    @Test
    void retrieveContactThatDoesNotExistByOfficeNumber_ThrowsContactNotInExistenceException(){
        saveAContact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByOfficeNumber("0898790646"));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByOfficeNumber("0898790646"));
    }

    @Test
    void canRetrieveContactByContactObject(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        Contact contact1 = contactRepository.getContactByContactObject(contact);
        assertEquals("Uzuegbunam", contact1.getLastName());
    }

    @Test
    void retrieveContactThatDoesNotExistByContactObject_ThrowsContactNotInExistenceException(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        Contact newContact = new Contact();
        assertThrows(PhoneBookException.class, ()-> contactRepository.getContactByContactObject(newContact));
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.getContactByContactObject(newContact));
    }

    @Test
    void canRetrieveAllContactsThatContainACertainSeriesOfNumbersInTheirMobile(){
        saveAContact();

        Contact contact2 = new Contact();
        contact2.setMobile("0842197689");
        contact2.setLastName("Okon");
        contactRepository.saveContact(contact2);

        Contact contact3 = new Contact();
        contact3.setMobile("0889064219");
        contact3.setLastName("James");
        contactRepository.saveContact(contact3);

        List<Contact> contacts = contactRepository.getListOfContactsRelatingTo("4219");

        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact2));
        assertTrue(contacts.contains(contact3));
    }

    @Test
    void canRetrieveAllContactsThatContainACertainSeriesOfNumbersInTheirOffice(){
        saveAContact();

        Contact contact2 = new Contact();
        contact2.setOffice("0842197689");
        contact2.setLastName("Okon");
        contactRepository.saveContact(contact2);

        Contact contact3 = new Contact();
        contact3.setOffice("0889064219");
        contact3.setLastName("James");
        contactRepository.saveContact(contact3);

        List<Contact> contacts = contactRepository.getListOfContactsRelatingTo("4219");

        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact2));
        assertTrue(contacts.contains(contact3));
    }

    @Test
    void canRetrieveAllContactsThatContainACertainNumberSeriesInTheirOfficeOrMobile(){
        saveAContact();

        Contact contact2 = new Contact();
        contact2.setMobile("0842197689");
        contact2.setLastName("Okon");
        contactRepository.saveContact(contact2);

        Contact contact3 = new Contact();
        contact3.setOffice("0889064219");
        contact3.setLastName("James");
        contactRepository.saveContact(contact3);

        List<Contact> contacts = contactRepository.getListOfContactsRelatingTo("4219");

        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact2));
        assertTrue(contacts.contains(contact3));
    }

    @Test
    void canRetrieveAllContactsThatContainACertainCharacterSeriesInTheirFirst_Last_OrMiddleName(){
        saveAContact();

        Contact contact2 = new Contact();
        contact2.setMobile("0842197689");
        contact2.setMiddleName("Uzundu");
        contactRepository.saveContact(contact2);

        Contact contact3 = new Contact();
        contact3.setOffice("0889064219");
        contact3.setFirstName("Uzuboz");
        contactRepository.saveContact(contact3);

        List<Contact> contacts = contactRepository.getListOfContactsRelatingTo("Uzu");

        assertEquals(3, contacts.size());
        assertTrue(contacts.contains(contact2));
        assertTrue(contacts.contains(contact3));
    }

    @Test
    void canUpdateValuesOfAContact(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);

        contact.setLastName("Garfield");
        contact.setOffice("09087651234");

        assertEquals(1, contactRepository.count());
        assertEquals("Garfield", contact.getLastName());
    }

    @Test
    void canDeleteAContactByMobile(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        assertEquals(1, contactRepository.count());
        contactRepository.deleteContactByMobileNumber("08142194364");
        assertEquals(0, contactRepository.count());
    }

    @Test
    void canNotDeleteWithInvalidMobileNumber(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        assertEquals(1, contactRepository.count());
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.deleteContactByMobileNumber("0876543284"));
        assertEquals(1, contactRepository.count());
    }

    @Test
    void canDeleteContactByOffice(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        assertEquals(1, contactRepository.count());
        contactRepository.deleteContactByOfficeNumber("09121143477");
        assertEquals(0, contactRepository.count());
    }

    @Test
    void canNotDeleteWithInvalidOfficeNumber(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);
        assertEquals(1, contactRepository.count());
        assertThrows(ContactNotInExistenceException.class, ()-> contactRepository.deleteContactByOfficeNumber("0876543284"));
        assertEquals(1, contactRepository.count());
    }

    @Test
    void canRetrieveAllContacts(){
        Contact contact = new Contact();
        contact.setFirstName("Chibuife");
        contact.setLastName("Uzuegbunam");
        contact.setMiddleName("Jerry");
        contact.setMobile("08142194364");
        contact.setOffice("09121143477");
        contactRepository.saveContact(contact);

        Contact anotherContact = new Contact();
        anotherContact.setFirstName("Alan");
        anotherContact.setMiddleName("Don");
        anotherContact.setLastName("Vaughn");
        anotherContact.setMobile("00999865");
        anotherContact.setOffice("765765689");
        contactRepository.saveContact(anotherContact);

        List<Contact> contactList = contactRepository.getAllContacts();

        assertEquals(2, contactList.size());
    }
}