package com.zrar.easyweb.bpmjob.tdd;

import com.netflix.discovery.converters.Auto;
import com.zrar.easyweb.bpmjob.tdd.domain.Car;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Log4j2
public class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    //using entityManger to take first level cache and flush it to database,more like a real application
    //repo.save and find , will just save it to cache and return it
    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCar_returnsCarDetails() {
        //save it to db , then fetch it from db  and reconstruct  Car
        final Car savedCar = entityManager.persistFlushFind(new Car("prius", "hybrid"));
        final Car car = repository.findByName("prius");
        log.info("savedCar == car : {}", car == savedCar);
        assertThat(car.getName()).isEqualTo(savedCar.getName());


    }
}