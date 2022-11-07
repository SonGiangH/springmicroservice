package com.shopme.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DataSource dataSource;

    // Register client to server (in memory only)
    // https://oauthdebugger.com/debug dong vai tro la client gui request
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // fix cung trong memory
//        clients.inMemory().withClient("giangSon").secret(passwordEncoder.encode("123"))
//                .authorizedGrantTypes("Authorization Code", "password", "implicit", "client_credentials", "authorization_code","refresh_token")
//                .redirectUris("http://localhost:3000/login").scopes("read", "write")
//            	.accessTokenValiditySeconds(3600) // 1 hour
//		        .refreshTokenValiditySeconds(2592000); // 30 days
        // connect with datasource database of account service
        clients.jdbc(dataSource);
    }

    // Bai nay su dung in memory de save token (legacy)
//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }

    // Using JWT library to save token
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenStoreConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenStoreConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey("123"); //symmetric key - asymmetric key is more secure
            return converter;
    }

    // config endpoint with token luu trong tokenstore
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(accessTokenStoreConverter());
    }

    // encode password
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }
}
