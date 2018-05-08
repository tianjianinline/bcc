package com.bcc.security.auth.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcc.security.auth.configuration.ClientConfiguration;
import com.bcc.security.auth.service.AuthClientService;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by ace on 2017/9/12.
 */
public class ClientTokenInterceptor implements RequestInterceptor {
    @Autowired
    private ClientConfiguration clientConfiguration;
    @Autowired
    private AuthClientService authClientService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try {
            requestTemplate.header(clientConfiguration.getClientTokenHeader(), authClientService.apply(clientConfiguration.getClientId(), clientConfiguration.getClientSecret()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
