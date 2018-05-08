package com.bcc.security.auth.util.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.bcc.security.auth.common.util.jwt.IJWTInfo;
import com.bcc.security.auth.common.util.jwt.JWTHelper;
import com.bcc.security.auth.configuration.KeyConfiguration;

/**
 * Created by ace on 2017/9/10.
 */
@Configuration
public class ClientTokenUtil {

    @Value("${client.expire}")
    private int expire;
    @Autowired
    private KeyConfiguration keyConfiguration;

    public String generateToken(IJWTInfo jwtInfo) throws Exception {
        return JWTHelper.generateToken(jwtInfo, keyConfiguration.getServicePriKey(), expire);
    }

    public IJWTInfo getInfoFromToken(String token) throws Exception {
        return JWTHelper.getInfoFromToken(token, keyConfiguration.getServicePubKey());
    }

}
