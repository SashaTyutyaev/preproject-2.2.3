package preproject222.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import preproject222.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
