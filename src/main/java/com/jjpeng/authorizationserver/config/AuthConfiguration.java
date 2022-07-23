package com.jjpeng.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author JJPeng
 * @date 2022/7/23 20:58
 */
@Configuration
//指示springboot启动OAuth2的授权服务器配置
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }


    //详细版本的配置，当使用数据库存储client details是应该使用详细的配置
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        InMemoryClientDetailsService inMemoryClientDetailsService = new InMemoryClientDetailsService();
//
//        BaseClientDetails clientDetails = new BaseClientDetails();
//        clientDetails.setClientId("client");
//        clientDetails.setClientSecret("secret");
//        clientDetails.setScope(Arrays.asList("read"));
//        clientDetails.setAuthorizedGrantTypes(Arrays.asList("password"));
//
//        HashMap<String, ClientDetails> map = new HashMap<>();
//        //key:value -> clientId:clientDetails
//        map.put("client", clientDetails);
//        inMemoryClientDetailsService.setClientDetailsStore(map);
//
//        clients.withClientDetails(inMemoryClientDetailsService);
//    }


    //简短版本的配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .scopes("read")
                .redirectUris("http://localhost:9090/home");
    }

}
