package pl.polsl.users.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long string) {
        super(String.valueOf(string));
    }

    public UserNotFoundException(String string) {
        super(string);
    }
}
