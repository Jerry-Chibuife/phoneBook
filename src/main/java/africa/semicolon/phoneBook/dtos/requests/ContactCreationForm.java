package africa.semicolon.phoneBook.dtos.requests;

import lombok.Data;

@Data
public class ContactCreationForm {
    private String firstName;
    private String lastName;
    private String middleName;
    private String mobile;
    private String office;
}
