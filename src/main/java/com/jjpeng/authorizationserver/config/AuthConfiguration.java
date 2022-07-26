package com.jjpeng.authorizationserver.config;

import com.jjpeng.authorizationserver.enhancer.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author JJPeng
 * @date 2022/7/23 20:58
 */
@Configuration
//指示springboot启动OAuth2的授权服务器配置
@EnableAuthorizationServer
public class AuthConfiguration extends AuthorizationServerConfigurerAdapter {

    @Value("${password}")
    private String password;

    @Value("${privateKey}")
    private String privateKey;

    @Value("${alias}")
    private String alias;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        List<TokenEnhancer> tokenEnhancers = Arrays.asList(new CustomTokenEnhancer(), jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
//                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(
                jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(privateKey),
                password.toCharArray()
        );
        converter.setKeyPair(
                keyStoreKeyFactory.getKeyPair(alias));
        return converter;
    }

    //简短版本的配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read");
    }

}