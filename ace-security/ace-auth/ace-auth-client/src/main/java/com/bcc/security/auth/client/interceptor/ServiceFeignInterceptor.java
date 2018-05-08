package com.bcc.security.auth.client.interceptor;

import com.bcc.security.auth.client.config.ServiceAuthConfig;
import com.bcc.security.auth.client.config.UserAuthConfig;
import com.bcc.security.auth.client.jwt.ServiceAuthUtil;
import com.bcc.security.common.context.BaseContextHandler;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ace
 * @date 2017/9/15
 */
public class ServiceFeignInterceptor implements RequestInterceptor {
    private Logger logger = LoggerFactory.getLogger(ServiceFeignInterceptor.class);
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private ServiceAuthUtil serviceAuthUtil;

    public ServiceFeignInterceptor() {
    }


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(serviceAuthConfig.getTokenHeader(), serviceAuthUtil.getClientToken());
        requestTemplate.header(userAuthConfig.getTokenHeader(), BaseContextHandler.getToken());
    }

    public void setServiceAuthConfig(ServiceAuthConfig serviceAuthConfig) {
        this.serviceAuthConfig = serviceAuthConfig;
    }

    public void setUserAuthConfig(UserAuthConfig userAuthConfig) {
        this.userAuthConfig = userAuthConfig;
    }

    public void setServiceAuthUtil(ServiceAuthUtil serviceAuthUtil) {
        this.serviceAuthUtil = serviceAuthUtil;
    }
}