package africa.semicolon.phoneBook.exceptions;

public class UserNotFoundException extends PhoneBookException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
