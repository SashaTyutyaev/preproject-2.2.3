package preproject222.service.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import preproject222.exceptions.NotApprovedLoanException;
import preproject222.exceptions.UserNotFoundException;
import preproject222.model.User;
import preproject222.repository.UserRepository;
import starterProject.incomeClient_starter.client.IncomeClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {


    @Value("${loan.url}")
    private String url;
    @Value("${loan.minimalIncome}")
    private Integer minimalIncome;
    @Value("${loan.minimalCarPrice}")
    private Integer minimalCarPrice;

    private final UserRepository userRepository;
    private final IncomeClient incomeClient;
    private final ObjectMapper objectMapper;

    @PostConstruct
    private void setIncomeToUsers() throws JsonProcessingException {
        String incomeData = incomeClient.getDataAsString(url);
        List<User> users = objectMapper.readValue(incomeData, new TypeReference<>() {
        });

        List<Long> ids = users.stream().map(User::getId).toList();

        userRepository.findAllById(ids);

        users.forEach(userRepository::saveAndFlush);
    }

    @Override
    public Integer calculateLoan(Long id) throws JsonProcessingException {
        int loanApprovedAmountByIncome = 0;
        int loanApprovedAmountByCarPrice = 0;
        User user = getUserById(id);
        Boolean isApprovedIncome = checkUsersIncome(user);
        Boolean isApprovedCarPrice = checkUsersCarPrice(user);

        if (!isApprovedIncome && !isApprovedCarPrice) {
            throw new NotApprovedLoanException("Loan is not approved for user with id " + id);
        }

        if (user.getCar() != null) {
            loanApprovedAmountByCarPrice = (int) (user.getCar().getPrice() * 0.3);
        }

        if (user.getIncome() != null) {
            loanApprovedAmountByIncome = user.getIncome() / 2;
        }

        if (isApprovedCarPrice && isApprovedIncome) {
            log.info("Accepted loan amount will be maximum of : 30% of car price or half of users income");
            return Math.max(loanApprovedAmountByCarPrice, loanApprovedAmountByIncome);
        } else if (isApprovedCarPrice) {
            log.info("Accepted loan amount is : 30% of car price");
            return loanApprovedAmountByCarPrice;
        } else {
            log.info("Accepted loan amount is : half of users income");
            return loanApprovedAmountByIncome;
        }
    }

    private Boolean checkUsersIncome(User user) {
        return user.getIncome() != null && user.getIncome() >= minimalIncome;
    }

    private Boolean checkUsersCarPrice(User user) {
        return user.getCar() != null && user.getCar().getPrice() >= minimalCarPrice;
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
