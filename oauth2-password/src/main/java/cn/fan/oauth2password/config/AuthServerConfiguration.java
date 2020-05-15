package cn.fan.oauth2password.config;

import cn.fan.oauth2password.handler.JwtTokenEnhancer;
import cn.fan.oauth2password.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

//    //读取密钥的配置
//    @Bean("keyProp")
//    public KeyProperties keyProperties(){
//        return new KeyProperties();
//    }
//
//    @Resource(name = "keyProp")
//    private KeyProperties keyProperties;

    //    //客户端配置
//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() // 使用in-memory存储
//                .withClient("clientId")
//                .secret(new BCryptPasswordEncoder().encode("clientSecret"))
//                .authorizedGrantTypes("authorization_code","password","refresh_token")
//                .scopes("all")
//                .redirectUris("http://www.baidu.com");
//数据库里的secret中的数据要加密，securityconfig中加了密
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //需要已验证的
                .allowFormAuthenticationForClients();
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                //该字段设置设置refresh token是否重复使用,true:reuse;false:no reuse.
                .reuseRefreshTokens(false)
                //刷新令牌授权将包含对用户详细信息的检查，以确保该帐户仍然活动,因此需要配置userDetailsService(不刷新也可不配)
                .userDetailsService(new MyUserDetailsService());


        // jwt增强器
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(jwtTokenEnhancer());
        enhancers.add(jwtAccessTokenConverter());
        enhancerChain.setTokenEnhancers(enhancers);

        endpoints
                .tokenEnhancer(enhancerChain)
                //配置令牌生成
                .accessTokenConverter(jwtAccessTokenConverter());
    }


    /**
     * 将token存储到redis中
     *
     * @return
     */
//    @Bean
//    public TokenStore tokenStore() {
//        return new RedisTokenStore(redisConnectionFactory());
//    }
//
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
//        这里采用的是对称加密，也可以使用非对称
        accessTokenConverter.setSigningKey("myJwtKey");
        return accessTokenConverter;
    }

    /**
     * token增强器
     *
     * @return
     */
    @Bean
    public TokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }


}
