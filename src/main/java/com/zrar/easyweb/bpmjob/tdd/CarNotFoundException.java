package com.zrar.easyweb.bpmjob.tdd;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 可以在controller里面用ExeptionHandler
 * 也可以在异常类上添加注解 @ResponseStatus(HttpStatus.NOT_FOUND)
 */

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarNotFoundException extends RuntimeException {

}
