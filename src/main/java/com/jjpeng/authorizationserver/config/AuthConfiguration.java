package com.jjpeng.authorizationserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author JJPeng
 * @date 2022/7/23 20:58
 */
@Configuration
//指示springboot启动OAuth2的授权服务器配置
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {
}
