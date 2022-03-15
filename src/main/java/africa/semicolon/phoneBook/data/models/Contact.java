package africa.semicolon.phoneBook.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class Contact {
    @Getter@Setter
    private String firstName;
    @Getter@Setter
    private String lastName;
    @Getter@Setter
    private String middleName;
    private final List<String> mobile = new ArrayList<>();
    private final List<String> office = new ArrayList<>();

    public List<String> getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.add(mobile);
    }

    public List<String> getOffice() {
        return this.office;
    }

    public void setOffice(String office) {
        this.office.add(office);
    }

    @Override
    public String toString() {
        return getFirstName()+" "+getMiddleName()+" "+getLastName()+"\n"
                +"Mobile number(s): "+ getMobile().toString()+"\n"
                +"Office number(s): "+ getOffice().toString()+"\n\n";
    }
}
