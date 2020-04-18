package com.zrar.easyweb.bpmjob;

import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.service.ApplicationContextProvider;
import com.zrar.easyweb.bpmjob.service.DataOPServiceDB;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
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

//如果用tdd.*结尾，会扫描不到tdd包下的class，tdd/Some.class 和 模式 tdd/*/**/*.class不匹配，**/*.class 是需要扫描的默认资源模式
//tdd.* 扫描 tdd/*/**/*.class tdd后面多了一层目录，所以是扫描tdd下面所有子包里面的class，不包含tdd下面的class
//匹配时，先把"."转换成"/",然后取最后一个”/“之前的作为基础包
@SpringBootApplication(scanBasePackages = {"com.zrar.easyweb.bpmjob.tdd"})
//@MapperScan(basePackages={"com.zrar.easyweb.bpmjob.dao"})
//@EnableFeignClients(basePackages = {"com.zrar.easyweb.bpmjob.*"})
@EnableCaching
public class BpmJobApplication  {

   	public static void main(String[] args) {
		SpringApplication.run(BpmJobApplication.class, args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(BpmJobApplication.class);
//    }
}
