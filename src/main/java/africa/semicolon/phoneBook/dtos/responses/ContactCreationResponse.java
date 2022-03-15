package africa.semicolon.phoneBook.dtos.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationResponse {
    private String name;
    private List<String> mobile = new ArrayList<>();
    private List<String> office = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        StringBuilder builder = new StringBuilder();
        for(String number : mobile){
            builder.append(number).append("\n");
        }
        return String.valueOf(builder);
    }

    public void setMobile(List<String> mobile) {
        this.mobile = mobile;
    }

    public String getOffice() {
        StringBuilder builder = new StringBuilder();
        for(String number : office){
            builder.append(number).append("\n");
        }
        return String.valueOf(builder);
    }

    public void setOffice(List<String> office) {
        this.office = office;
    }
}
