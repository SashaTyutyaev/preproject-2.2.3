package preproject222.exceptions;

public class UserNotFoundException extends RuntimeException {

    private String message;

    public UserNotFoundException(String message) {
        super(message);
    }
}
