package africa.semicolon.phoneBook.data.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class User {
    @Getter @Setter
    private String firstName;
    @Getter @Setter
    private String lastName;
    @Getter @Setter
    private String middleName;
    private final List<String> office = new ArrayList<>();
    private final List<String> mobile = new ArrayList<>();
    private List<Contact> contactList = new ArrayList<>();

    public List<Contact> getContactList() {
        return contactList;
    }

    public List<String> getOffice() {
        return this.office;
    }

    public void setOffice(String office) {
        this.office.add(office);
    }

    public List<String> getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.add(mobile);
    }
}
