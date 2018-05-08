package com.bcc.security.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bcc.security.auth.interceptor.ClientTokenInterceptor;

/**
 * Created by ace on 2017/9/12.
 */
@Configuration
public class FeignConfiguration {
    @Bean
    ClientTokenInterceptor getClientTokenInterceptor(){
        return new ClientTokenInterceptor();
    }
}
