package preproject222.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import preproject222.model.User;
import preproject222.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void addUser(User user) {
        User savedUser = userRepository.save(user);
        log.info("Successfully saved user with id {}", savedUser.getId());
    }

}
