package com.bcc.security.auth.client.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.bcc.security.auth.client.config.ServiceAuthConfig;
import com.bcc.security.auth.client.config.UserAuthConfig;
import com.bcc.security.auth.client.feign.ServiceAuthFeign;
import com.bcc.security.common.msg.ObjectRestResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听完成时触发
 *
 * @author ace
 * @create 2017/11/29.
 */
@Configuration
@Slf4j
public class AuthClientRunner implements CommandLineRunner {

    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private UserAuthConfig userAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化加载用户pubKey");
        ObjectRestResponse<byte[]> resp = serviceAuthFeign.getUserPublicKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            this.userAuthConfig.setPubKeyByte(resp.getData());
        }
        log.info("初始化加载客户pubKey");
        resp = serviceAuthFeign.getServicePublicKey(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            this.serviceAuthConfig.setPubKeyByte(resp.getData());
        }
    }
}