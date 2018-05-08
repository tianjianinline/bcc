package com.bcc.security.auth.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.bcc.security.auth.client.configuration.AutoConfiguration;

import java.lang.annotation.*;

/**
 * Created by ace on 2017/9/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
@Documented
@Inherited
public @interface EnableAceAuthClient {
}
