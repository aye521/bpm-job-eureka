package com.zrar.easyweb.bpmjob.base;

import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.bouncycastle.jcajce.provider.digest.SM3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sound.midi.Soundbank;
import java.util.Objects;

/**
 * 
 * @author liyg
 * @Date 2018-08-14
 */
@Configuration
public class FeignConfig {

	@Value("${configData.account.auth:feignCallEncry}")
	private String encryKey;

	@Bean
	public Contract feignContract(){
		return new SpringMvcContract();
	}

	/**
	 * 从请求中获取 Authorization 设置到feign 请求中
	 * @author liyanggui
	 *
	 */
	@Bean
	public RequestInterceptor requestTokenBearerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				String token ="";
				try {
					token = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("Authorization");
				} catch (Exception e) {
				}
				if(StringUtil.isNotEmpty(token)){
					requestTemplate.header("Authorization", token);
				}else {
					try {
					    //使用加密key的方式，两边key和加密算法必须一致
                        requestTemplate.header("Authorization","Basic "+ Base64.getBase64("admin:"+ EncryptUtil.encrypt(encryKey)));
                        //使用用户名和密码组合的方式，目前框架那边先使用了EncryptUtil.decrypt(pwd)的方式，会导致直接异常，目前只能采用上面的方式
                        //密码：encode(用户名+密码)
//                        final String pwd = SM3Util.encodeSM3("admin" + "1");
//                        requestTemplate.header("Authorization","Basic "+ Base64.getBase64("admin:"+ pwd));
					} catch (Exception e) {
					    e.printStackTrace();
					}
				}
			}
		};
	}

}
