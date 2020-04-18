package com.zrar.easyweb.bpmjob.tdd;

import com.zrar.easyweb.bpmjob.tdd.domain.Car;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
//可以通过classes指定具体的@EnableCaching的类，避免在全局的spring boot applicaion上面加@EnableCaching（尤其应用很大的情况）
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
classes = CachingConfig.class)
@AutoConfigureTestDatabase
@AutoConfigureCache
public class CachingTest {
    @Autowired
    private CarService service;

    @MockBean
    private CarRepository carRepository;

    @Test
    public void caching() throws Exception {
        given(carRepository.findByName(anyString())).willReturn(new Car("prius", "hybrid"));
        service.getCarDetails("prius");
        service.getCarDetails("prius");
        verify(carRepository, times(1)).findByName("prius");
    }
}
