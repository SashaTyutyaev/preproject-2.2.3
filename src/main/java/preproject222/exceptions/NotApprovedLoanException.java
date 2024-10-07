package preproject222.exceptions;

public class NotApprovedLoanException extends RuntimeException {

    private String message;

    public NotApprovedLoanException(String message) {
        super(message);
    }
}
