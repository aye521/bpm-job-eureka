package com.zrar.easyweb.bpmjob.tdd;

import com.zrar.easyweb.bpmjob.tdd.domain.Car;
import org.springframework.cache.annotation.Cacheable;

public class CarService {

    private CarRepository carRepository;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Cacheable("cars")
    public Car getCarDetails(String name) {
        final Car car = carRepository.findByName(name);
        if (car == null) {
            throw new CarNotFoundException();
        }
        return car;
    }
}
