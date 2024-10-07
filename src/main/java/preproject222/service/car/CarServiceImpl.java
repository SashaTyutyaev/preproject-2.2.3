package preproject222.service.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import preproject222.model.Car;
import preproject222.repository.CarRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    @Transactional
    public void addCar(Car car) {
        Car savedCar = carRepository.save(car);
        log.info("Successfully saved car with id {}", savedCar.getId());
    }
}
