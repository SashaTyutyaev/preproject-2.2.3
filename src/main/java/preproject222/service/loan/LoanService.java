package preproject222.service.loan;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface LoanService {

    Integer calculateLoan(Long id) throws JsonProcessingException;
}
