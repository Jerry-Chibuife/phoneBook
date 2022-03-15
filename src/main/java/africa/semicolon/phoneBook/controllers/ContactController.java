package africa.semicolon.phoneBook.controllers;


import africa.semicolon.phoneBook.dtos.requests.ContactCreationForm;
import africa.semicolon.phoneBook.dtos.responses.ContactCreationResponse;
import africa.semicolon.phoneBook.dtos.responses.FindContactResponse;
import africa.semicolon.phoneBook.services.ContactService;
import africa.semicolon.phoneBook.services.ContactServiceImplementation;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/contact")
public class ContactController {
    ContactService contactService = new ContactServiceImplementation();

    @PostMapping("/createContact")
    public ContactCreationResponse response(@RequestBody ContactCreationForm creationForm){
        return contactService.createAContact(creationForm);
    }

    @DeleteMapping("/{deleteParam}")
    public FindContactResponse deleteResponse(@PathVariable String deleteParam){
        if(Objects.equals(contactService.deleteContactByMobileNumber(deleteParam).getResponseInfo(), "Delete Successful!"))
            return contactService.deleteContactByMobileNumber(deleteParam);
        else return contactService.deleteContactByOfficeNumber(deleteParam);
    }

    @GetMapping("/{searchByParam}")
    public FindContactResponse searchResponse(@PathVariable String searchByParam){
        return contactService.searchForAllContactsContaining(searchByParam);
    }

    @GetMapping("/retrieveAllContacts")
    public FindContactResponse getAllContacts(){
        return contactService.getAllContacts();
    }
}
