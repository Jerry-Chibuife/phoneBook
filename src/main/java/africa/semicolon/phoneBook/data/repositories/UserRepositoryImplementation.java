package africa.semicolon.phoneBook.data.repositories;

import africa.semicolon.phoneBook.data.models.User;
import africa.semicolon.phoneBook.exceptions.ExactUserExistenceException;
import africa.semicolon.phoneBook.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepositoryImplementation implements UserRepository{

    private List<User> storage = new ArrayList<>();

    @Override
    public void saveUser(User user) {
        checkAgainstDuplicateUser(user);
        boolean check = checkForSimilarUserWithNewNumber(user);
        if (!check)
            storage.add(user);
        else{
            for(User aUser : storage){
                if(aUser.getLastName().equals(user.getLastName()) && aUser.getFirstName().equals(user.getFirstName())
                        && aUser.getMiddleName().equals(user.getMiddleName())){
                    if(!(user.getMobile().isEmpty()))
                        aUser.getMobile().add(user.getMobile().get(0));
                    if(!(user.getOffice().isEmpty()))
                        aUser.getOffice().add(user.getOffice().get(0));
                    break;
                }
            }
        }
    }

    private boolean checkForSimilarUserWithNewNumber(User user) {
        for(User aUser : storage){
            if(aUser.getLastName().equals(user.getLastName()) && aUser.getFirstName().equals(user.getFirstName())
                    && aUser.getMiddleName().equals(user.getMiddleName())){
                return true;
            }
        }
        return false;
    }

    private void checkAgainstDuplicateUser(User user) {
        for (User aUser : storage){
            if(Objects.equals(aUser.getFirstName(), user.getFirstName()))
                if(Objects.equals(aUser.getMiddleName(), user.getMiddleName()))
                    if(Objects.equals(aUser.getLastName(), user.getLastName()))
                        if(Objects.equals(aUser.getMobile(), user.getMobile()))
                            if(Objects.equals(aUser.getOffice(), user.getOffice()))
                                throw new ExactUserExistenceException("This user already exists");
        }
    }

    @Override
    public int count() {
        return storage.size();
    }

    @Override
    public User getUserWithLastName(String lastName) {
        for(User user : storage){
            if(user.getLastName().equals(lastName))
                return user;
        }
        throw new UserNotFoundException("User with this last name does not exist");
    }

    @Override
    public User getUserWithMiddleName(String middleName) {
        for(User user : storage){
            if(user.getMiddleName().equals(middleName))
                return user;
        }
        throw new UserNotFoundException("User with this middle name does not exist");
    }

    @Override
    public User getUserWithFirstName(String firstName) {
        for(User user : storage){
            if(user.getFirstName().equals(firstName))
                return user;
        }
        throw new UserNotFoundException("User with this first name does not exist");
    }

    @Override
    public User getUserUsingMobileNumber(String mobileNumber) {
        for(User user : storage){
            if(user.getMobile().contains(mobileNumber))
                return user;
        }
        throw new UserNotFoundException("User with this mobile number does not exist");
    }

    @Override
    public User getUserUsingOfficeNumber(String officeNumber) {
        for(User user : storage){
            if(user.getOffice().contains(officeNumber))
                return user;
        }
        throw new UserNotFoundException("User with this office number does not exist");
    }

    @Override
    public void deleteByMobileNumber(String mobileNumber) {
        for(User user : storage){
            if(user.getMobile().contains(mobileNumber)) {
                storage.remove(user);
                break;
            }
            else throw new UserNotFoundException("Can't delete, user with this mobile number does not exist");
        }
    }

    @Override
    public void deleteByFirstname(String firstName) {
        for(User user : storage){
            if(user.getFirstName().equals(firstName)) {
                storage.remove(user);
                break;
            }
            else throw new UserNotFoundException("Can't delete, user with this first name does not exist");
        }
    }

    @Override
    public List<User> retrieveAllUsers() {
        return storage;
    }
}
