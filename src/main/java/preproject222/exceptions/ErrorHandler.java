package preproject222.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.error("User not found");
        return new ErrorResponse("User not found");
    }

    @ExceptionHandler(NotApprovedLoanException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotApprovedLoanException(NotApprovedLoanException e) {
        log.error("Loan is not approved");
        return new ErrorResponse("Loan is not approved");
    }
}
