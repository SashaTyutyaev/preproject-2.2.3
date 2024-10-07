package preproject222.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import preproject222.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
