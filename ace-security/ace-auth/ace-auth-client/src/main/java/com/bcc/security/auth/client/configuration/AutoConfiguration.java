package com.bcc.security.auth.client.configuration;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bcc.security.auth.client.config.ServiceAuthConfig;
import com.bcc.security.auth.client.config.UserAuthConfig;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@ComponentScan({"com.github.wxiaoqi.security.auth.client","com.github.wxiaoqi.security.auth.common.event"})
@RemoteApplicationEventScan(basePackages = "com.github.wxiaoqi.security.auth.common.event")
public class AutoConfiguration {
    @Bean
    ServiceAuthConfig getServiceAuthConfig(){
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig(){
        return new UserAuthConfig();
    }
}
