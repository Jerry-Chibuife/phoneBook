package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.User;
import africa.semicolon.phoneBook.exceptions.ExactUserExistenceException;
import africa.semicolon.phoneBook.exceptions.PhoneBookException;
import africa.semicolon.phoneBook.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplementationTest {

    private UserRepository userRepository = new UserRepositoryImplementation();

    private void saveAUser() {
        User user = new User();
        user.setLastName("Affleck");
        user.setFirstName("Benedict");
        user.setMiddleName("Timothy");
        user.setMobile("09087654321");
        user.setOffice("08012345678");
        userRepository.saveUser(user);
        assertEquals(1, userRepository.count());
    }

    @Test
    void canSaveAUserToRepository_RepoCountIs1Test(){
        saveAUser();
    }

    @Test
    void checkAgainstExactUserAddition(){
        saveAUser();
        User user = new User();
        user.setLastName("Affleck");
        user.setFirstName("Benedict");
        user.setMiddleName("Timothy");
        user.setMobile("09087654321");
        user.setOffice("08012345678");
        assertThrows(PhoneBookException.class, ()-> userRepository.saveUser(user));
        assertThrows(ExactUserExistenceException.class, ()-> userRepository.saveUser(user));
        assertEquals(1, userRepository.count());
    }

    @Test
    void sameUserWithDifferentMobileAndOrOfficeNumber_SaveNumbersToAlreadyExistingUser(){
        User user = new User();
        user.setLastName("Affleck");
        user.setFirstName("Benedict");
        user.setMiddleName("Timothy");
        user.setMobile("09087654321");
        user.setOffice("08012345678");
        userRepository.saveUser(user);

        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);
        assertEquals(1, userRepository.count());
        assertEquals(2, user.getMobile().size());
        assertEquals(2, user.getOffice().size());
    }

    @Test
    void mobileAndOfficeNullForSimilarUser_DoNotSave(){
        User user = new User();
        user.setLastName("Affleck");
        user.setFirstName("Benedict");
        user.setMiddleName("Timothy");
        user.setMobile("09087654321");
        user.setOffice("08012345678");
        userRepository.saveUser(user);

        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        userRepository.saveUser(user1);

        assertEquals(1, userRepository.count());
        assertEquals(1, user.getMobile().size());
        assertEquals(1, user.getOffice().size());
    }

    @Test
    void multipleUsersCanBeAdded(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user2 = new User();
        user2.setLastName("Constantine");
        user2.setFirstName("John");
        user2.setMiddleName("Alaric");
        user2.setMobile("09098998777");
        user2.setOffice("9099667545");
        userRepository.saveUser(user2);

        assertEquals(2, userRepository.count());
    }

    @Test
    void userSavedCanBeRetrieved_UsingUserLastName(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user = userRepository.getUserWithLastName("Affleck");
        assertEquals("Timothy", user.getMiddleName());
    }

    @Test
    void userWithLastNameNotFound_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertThrows(UserNotFoundException.class, ()->userRepository.getUserWithLastName("Anderson"));
    }

    @Test
    void userSavedCanBeRetrieved_UsingMiddleName(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user = userRepository.getUserWithMiddleName("Timothy");
        assertEquals("Affleck", user.getLastName());
    }

    @Test
    void userWithMiddleNameNotFound_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertThrows(UserNotFoundException.class, ()->userRepository.getUserWithMiddleName("Anderson"));
    }

    @Test
    void userSavedCanBeRetrieved_UsingFirstName(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user = userRepository.getUserWithFirstName("Benedict");
        assertEquals("Affleck", user.getLastName());
    }

    @Test
    void userWithFirstNameNotFound_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertThrows(UserNotFoundException.class, ()->userRepository.getUserWithMiddleName("Anderson"));
    }

    @Test
    void userSavedCanBeRetrieved_UsingMobileNumber(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user = userRepository.getUserUsingMobileNumber("0897343948");
        assertEquals("Affleck", user.getLastName());
    }

    @Test
    void userWithMobileNumberNotFound_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertThrows(UserNotFoundException.class, ()->userRepository.getUserUsingMobileNumber("0987564325"));
    }

    @Test
    void userSavedCanBeRetrieved_UsingOfficeNumber(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user = userRepository.getUserUsingOfficeNumber("07123424947");
        assertEquals("Affleck", user.getLastName());
    }

    @Test
    void userWithOfficeNumberNotFound_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertThrows(UserNotFoundException.class, ()->userRepository.getUserUsingOfficeNumber("0987564325"));
    }

    @Test
    void retrieveAllUsers(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        User user2 = new User();
        user2.setMiddleName("Ryn");
        user2.setFirstName("Kon");
        user2.setLastName("Ayd");
        user2.setOffice("000999877");
        user2.setMobile("12343432343");
        userRepository.saveUser(user2);

        List<User> users = userRepository.retrieveAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void userAttributesCanBeUpdated(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        user1.setMiddleName("Hassan");
        User savedUser = userRepository.getUserWithMiddleName("Hassan");
        assertEquals("Hassan", savedUser.getMiddleName());
    }

    @Test
    void userCanBeDeleted_UsingMobileNumber(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertEquals(1, userRepository.count());

        userRepository.deleteByMobileNumber("0897343948");

        assertEquals(0, userRepository.count());
    }

    @Test
    void deleteWithNonExistentMobileNumber_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertEquals(1, userRepository.count());

        assertThrows(UserNotFoundException.class, ()-> userRepository.deleteByMobileNumber("989384839"));
    }

    @Test
    void userCanBeDeleted_UsingFirstName(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertEquals(1, userRepository.count());

        userRepository.deleteByFirstname("Benedict");

        assertEquals(0, userRepository.count());
    }

    @Test
    void deleteWithNonExistentFirstName_ThrowException(){
        User user1 = new User();
        user1.setLastName("Affleck");
        user1.setFirstName("Benedict");
        user1.setMiddleName("Timothy");
        user1.setMobile("0897343948");
        user1.setOffice("07123424947");
        userRepository.saveUser(user1);

        assertEquals(1, userRepository.count());

        assertThrows(UserNotFoundException.class, ()-> userRepository.deleteByFirstname("Adeleke"));
    }
}