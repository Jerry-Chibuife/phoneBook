package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.User;

import java.util.List;

public interface UserRepository {

    void saveUser(User user);

    int count();

    User getUserWithLastName(String lastName);

    User getUserWithMiddleName(String middleName);

    User getUserWithFirstName(String firstName);

    User getUserUsingMobileNumber(String mobileNumber);

    User getUserUsingOfficeNumber(String officeNumber);

    void deleteByMobileNumber(String mobileNumber);

    void deleteByFirstname(String firstName);

    List<User> retrieveAllUsers();
}
