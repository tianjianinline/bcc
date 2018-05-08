package com.bcc.security.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bcc.security.auth.common.util.jwt.IJWTInfo;
import com.bcc.security.auth.configuration.ClientConfiguration;
import com.bcc.security.auth.service.AuthClientService;
import com.bcc.security.auth.util.client.ClientTokenUtil;
import com.bcc.security.common.exception.auth.ClientForbiddenException;

/**
 * Created by ace on 2017/9/12.
 */
public class ServiceAuthRestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ClientTokenUtil clientTokenUtil;
    @Autowired
    private AuthClientService authClientService;
    @Autowired
    private ClientConfiguration clientConfiguration;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(clientConfiguration.getClientTokenHeader());
        IJWTInfo infoFromToken = clientTokenUtil.getInfoFromToken(token);
        String uniqueName = infoFromToken.getUniqueName();
        for(String client: authClientService.getAllowedClient(clientConfiguration.getClientId())){
            if(client.equals(uniqueName)){
                return super.preHandle(request, response, handler);
            }
        }
        throw new ClientForbiddenException("Client is Forbidden!");
    }
}
