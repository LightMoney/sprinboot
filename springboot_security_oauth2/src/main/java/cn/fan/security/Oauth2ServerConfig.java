package cn.fan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Auth Mr.fan
 * Date 2020/1/16 14:52
 * 认证授权服务器
 **/
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    TokenStore tokenStore;
    @Autowired
    private UserApprovalHandler userApprovalHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    // 获取token(默认是12小时过期，刷新token是30天)
//oauth2设置token过期时间，在配置中重写DefaultTokenServices中默认的12小时即可
//    @Bean
//    @Primary
//    public DefaultTokenServices defaultTokenServices(){
//        DefaultTokenServices services=new DefaultTokenServices();
//        services.setAccessTokenValiditySeconds(20);//设置20秒过期
//        services.setRefreshTokenValiditySeconds(666);//设置刷新token的过期时间
//        services.setTokenStore(tokenStore);
//        return services;
//    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
                .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
 .passwordEncoder(NoOpPasswordEncoder.getInstance());
//                .passwordEncoder(new BCryptPasswordEncoder());

    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * clientId：（必须的）用来标识客户的Id。
     * secret：（需要值得信任的客户端）客户端安全码，如果有的话。
     * redirectUris 返回地址,可以理解成登录后的返回地址，可以多个。应用场景有:客户端swagger调用服务端的登录页面,登录成功，返回客户端swagger页面
     * authorizedGrantTypes：此客户端可以使用的权限（基于Spring Security authorities）
     * authorization_code：授权码类型、implicit：隐式授权类型、password：资源所有者（即用户）密码类型、
     * client_credentials：客户端凭据（客户端ID以及Key）类型、refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
     * scope：用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * accessTokenValiditySeconds token有效时长
     * refreshTokenValiditySeconds refresh_token有效时长
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //直接从数据 库获取（oauth_client_details）
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));

//        clients.inMemory()
//                .withClient("root")
//                .secret(new BCryptPasswordEncoder().encode("user"))
//                .authorizedGrantTypes( "password")
//                .scopes("all")
////                .resourceIds("oauth2-resource")
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(3600);
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * 访问地址：/oauth/token
     * 属性列表:
     * authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，需要设置为这个属性注入一个 AuthenticationManager 对象。
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //设置认证管理器
//        endpoints.authenticationManager(authenticationManager);
//        //设置访问/oauth/token接口，获取token的方式
//        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
        // 这里与上一篇不同的是，增加tokenStore(用户存放token)和userApprovalHandler(用于用户登录)配置
        endpoints.tokenStore(tokenStore)
                .userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);//获取token允许的访问方式


    }
}
