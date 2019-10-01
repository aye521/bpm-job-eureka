package com.zrar.easyweb.bpmjob.base;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {
	@Bean
    UndertowServletWebServerFactory undertowServletWebServerFactory() {
		UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
		return factory;
	}
}
