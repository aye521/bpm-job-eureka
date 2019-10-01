package com.zrar.easyweb.bpmjob;

import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.service.ApplicationContextProvider;
import com.zrar.easyweb.bpmjob.service.DataOPServiceDB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.zrar.easyweb.*"})
@MapperScan(basePackages={"com.zrar.easyweb.bpmjob.dao"})
@EnableFeignClients(basePackages = {"com.zrar.easyweb.bpmjob.*"})
public class BpmJobApplication {

   	public static void main(String[] args) {
		SpringApplication.run(BpmJobApplication.class, args);
    }
}
