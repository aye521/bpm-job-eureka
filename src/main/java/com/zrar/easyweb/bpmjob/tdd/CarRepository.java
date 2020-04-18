package com.zrar.easyweb.bpmjob.tdd;

import com.zrar.easyweb.bpmjob.tdd.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
     Car findByName(String name);
}
