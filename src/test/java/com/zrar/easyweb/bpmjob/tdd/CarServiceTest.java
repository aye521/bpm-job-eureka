package com.zrar.easyweb.bpmjob.tdd;

import com.zrar.easyweb.bpmjob.tdd.domain.Car;
import javafx.scene.text.FontPosture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    private CarService carService;

    @Before
    public void setup() throws Exception {
        carService = new CarService(carRepository);
    }
    
    @Test
    public void getCarDetail_returnCarInfo() throws Exception {
        given(carRepository.findByName("prius")).willReturn(new Car("prius", "hybrid"));
        final Car car = carService.getCarDetails("prius");
        assertThat(car.getName()).isEqualTo("prius");
        assertThat(car.getType()).isEqualTo("hybrid");
    }

    @Test(expected = CarNotFoundException.class)
    public void getCarDetail_whenCarNotFound() throws Exception {
        given(carRepository.findByName("prius")).willReturn(null);

        carService.getCarDetails("prius");
    }

}