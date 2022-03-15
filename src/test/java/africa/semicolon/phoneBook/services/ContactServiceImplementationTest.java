package africa.semicolon.phoneBook.services;

import africa.semicolon.phoneBook.data.models.Contact;
import africa.semicolon.phoneBook.dtos.requests.ContactCreationForm;
import africa.semicolon.phoneBook.dtos.responses.ContactCreationResponse;
import africa.semicolon.phoneBook.dtos.responses.FindContactResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceImplementationTest {

    private ContactService contactService = new ContactServiceImplementation();

    @Test
    void addANewContactToContactRepoTest(){
        ContactCreationForm creationForm = new ContactCreationForm();
        creationForm.setFirstName("Benjamin");
        creationForm.setLastName("Artemis");
        creationForm.setMiddleName("Isaac");
        creationForm.setMobile("09087651234");
        creationForm.setOffice("08123456790");
        contactService.createAContact(creationForm);

        assertEquals(1, contactService.getRepository().count());
    }

    @Test
    void addContact_GetNotifiedOfContactCreation(){
        ContactCreationForm creationForm = new ContactCreationForm();
        creationForm.setFirstName("Benjamin");
        creationForm.setLastName("Artemis");
        creationForm.setMiddleName("Isaac");
        creationForm.setMobile("09087651234");
        creationForm.setOffice("08123456790");
        ContactCreationResponse response = contactService.createAContact(creationForm);
        assertEquals("Benjamin Isaac Artemis", response.getName());
        assertEquals("09087651234\n", response.getMobile());
    }

    @Test
    void updateSavedContactInfo(){
        ContactCreationForm creationForm = new ContactCreationForm();
        creationForm.setLastName("Dennis");
        creationForm.setMiddleName("Asha");
        creationForm.setOffice("099886554");
        contactService.createAContact(creationForm);
        Contact contact = contactService.getRepository().getContactByLastName("Dennis");
        contact.setMobile("123534776");
        contact.setFirstName("Sam");
        FindContactResponse response = contactService.updateContactInfo(contact);
        Contact updatedContact = contactService.getRepository().getContactByLastName("Dennis");
        assertEquals(1, contactService.getRepository().count());
        assertEquals("Sam", updatedContact.getFirstName());
        assertEquals("Successfully Updated!", response.getResponseInfo());

        ContactCreationForm creationForm1 = new ContactCreationForm();
        creationForm1.setLastName("Blue");
        creationForm1.setFirstName("Ivy");
        creationForm1.setMiddleName("Coin");
        creationForm1.setMobile("09012345678");
        creationForm1.setOffice("09087654321");
        contactService.createAContact(creationForm1);
        Contact contact1 = contactService.getRepository().getContactByMobile("09012345678");
        contact1.setMobile("123534776");
        contact1.setFirstName("Sam");
        FindContactResponse response1 = contactService.updateContactInfo(contact1);
        Contact updatedContact1 = contactService.getRepository().getContactByLastName("Blue");
        assertEquals(2, contactService.getRepository().count());
        assertEquals("Sam", updatedContact1.getFirstName());
        assertEquals("123534776", updatedContact1.getMobile().get(1));
        assertEquals("Successfully Updated!", response1.getResponseInfo());
    }

    @Test
    void canSearchThroughContactList(){
        ContactCreationForm creationForm1 = new ContactCreationForm();
        creationForm1.setLastName("Blue");
        creationForm1.setFirstName("Ivy");
        creationForm1.setMiddleName("Coin");
        creationForm1.setMobile("09012345678");
        creationForm1.setOffice("09087654321");
        contactService.createAContact(creationForm1);

        ContactCreationForm creationForm2 = new ContactCreationForm();
        creationForm2.setLastName("Asa");
        creationForm2.setFirstName("Benedict");
        creationForm2.setMiddleName("Ino");
        creationForm2.setMobile("74839948392");
        creationForm2.setOffice("82483983954");
        contactService.createAContact(creationForm2);

        ContactCreationForm creationForm3 = new ContactCreationForm();
        creationForm3.setLastName("Will");
        creationForm3.setFirstName("Byers");
        creationForm3.setMiddleName("Wheeler");
        creationForm3.setMobile("988493948");
        creationForm3.setOffice("2132884899");
        contactService.createAContact(creationForm3);

        ContactCreationForm creationForm4 = new ContactCreationForm();
        creationForm4.setLastName("Cass");
        creationForm4.setFirstName("Adams");
        creationForm4.setMiddleName("Ezekiel");
        creationForm4.setMobile("489493020");
        creationForm4.setOffice("102939203");
        contactService.createAContact(creationForm4);

        ContactCreationForm creationForm5 = new ContactCreationForm();
        creationForm5.setLastName("Asta");
        creationForm5.setFirstName("Yuno");
        creationForm5.setMiddleName("Gauche");
        creationForm5.setMobile("74484748823");
        creationForm5.setOffice("109938293849");
        contactService.createAContact(creationForm5);

        assertEquals(5, contactService.getRepository().count());
        Contact contact = contactService.searchForContactWithMobileNumber("489493020");
        assertEquals("Adams", contact.getFirstName());
        FindContactResponse response = contactService.searchForAllContactsContaining("99");
    }

    @Test
    void deleteContactAndGetResponseOfDeletion(){
        ContactCreationForm creationForm1 = new ContactCreationForm();
        creationForm1.setLastName("Blue");
        creationForm1.setFirstName("Ivy");
        creationForm1.setMiddleName("Coin");
        creationForm1.setMobile("09012345678");
        creationForm1.setOffice("09087654321");
        contactService.createAContact(creationForm1);

        FindContactResponse response = contactService.deleteContactByMobileNumber("09012345678");
        assertEquals(0, contactService.getRepository().count());
        assertEquals("Delete Successful!", response.getResponseInfo());

        ContactCreationForm creationForm5 = new ContactCreationForm();
        creationForm5.setLastName("Asta");
        creationForm5.setFirstName("Yuno");
        creationForm5.setMiddleName("Gauche");
        creationForm5.setMobile("74484748823");
        creationForm5.setOffice("109938293849");
        contactService.createAContact(creationForm5);

        FindContactResponse response1 = contactService.deleteContactByOfficeNumber("109938293849");
        assertEquals(0, contactService.getRepository().count());
        assertEquals("Delete Successful!", response1.getResponseInfo());
    }

    @Test
    public void canGetAllContacts(){
        ContactCreationForm creationForm1 = new ContactCreationForm();
        creationForm1.setLastName("Blue");
        creationForm1.setFirstName("Ivy");
        creationForm1.setMiddleName("Coin");
        creationForm1.setMobile("09012345678");
        creationForm1.setOffice("09087654321");
        contactService.createAContact(creationForm1);

        ContactCreationForm creationForm2 = new ContactCreationForm();
        creationForm2.setLastName("Asa");
        creationForm2.setFirstName("Benedict");
        creationForm2.setMiddleName("Ino");
        creationForm2.setMobile("74839948392");
        creationForm2.setOffice("82483983954");
        contactService.createAContact(creationForm2);

        ContactCreationForm creationForm3 = new ContactCreationForm();
        creationForm3.setLastName("Will");
        creationForm3.setFirstName("Byers");
        creationForm3.setMiddleName("Wheeler");
        creationForm3.setMobile("988493948");
        creationForm3.setOffice("2132884899");
        contactService.createAContact(creationForm3);

        FindContactResponse response = contactService.getAllContacts();

    }
}