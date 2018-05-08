
package com.bcc.security.auth.client.jwt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bcc.security.auth.client.config.ServiceAuthConfig;
import com.bcc.security.auth.client.exception.JwtIllegalArgumentException;
import com.bcc.security.auth.client.exception.JwtSignatureException;
import com.bcc.security.auth.client.exception.JwtTokenExpiredException;
import com.bcc.security.auth.client.feign.ServiceAuthFeign;
import com.bcc.security.auth.common.util.jwt.IJWTInfo;
import com.bcc.security.auth.common.util.jwt.JWTHelper;
import com.bcc.security.common.msg.ObjectRestResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by ace on 2017/9/15.
 */
@Configuration
@Slf4j
@EnableScheduling
public class ServiceAuthUtil {
    @Autowired
    private ServiceAuthConfig serviceAuthConfig;
    @Autowired
    private ServiceAuthFeign serviceAuthFeign;
    private List<String> allowedClient;
    private String clientToken;


    public IJWTInfo getInfoFromToken(String token) throws Exception {
        try {
            return JWTHelper.getInfoFromToken(token, serviceAuthConfig.getPubKeyByte());
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenExpiredException("Client token expired!");
        } catch (SignatureException ex) {
            throw new JwtSignatureException("Client token signature error!");
        } catch (IllegalArgumentException ex) {
            throw new JwtIllegalArgumentException("Client token is null or empty!");
        }
    }

    public void refreshAllowedClient() {
        log.info("refresh allowedClient.....");
        ObjectRestResponse<List<String>> resp = serviceAuthFeign.getAllowedClient(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            this.allowedClient = resp.getData();
        }
    }


    @Scheduled(cron = "0 0/30 * * * ?")
    public void refreshClientToken() {
        log.info("refresh client token.....");
        ObjectRestResponse<String> resp = serviceAuthFeign.getAccessToken(serviceAuthConfig.getClientId(), serviceAuthConfig.getClientSecret());
        if (resp.getStatus() == 200) {
            this.clientToken = resp.getData();
        }
    }


    public String getClientToken() {
        if (this.clientToken == null) {
            this.refreshClientToken();
        }
        return clientToken;
    }

    public List<String> getAllowedClient() {
        if (this.allowedClient == null) {
            this.refreshAllowedClient();
        }
        return allowedClient;
    }
}

