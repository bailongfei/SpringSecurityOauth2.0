package com.itheima.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Administrator
 * @version 1.0
 * 授权服务配置
 **/
@Configuration
//可以用 @EnableAuthorizationServer 注解并继承AuthorizationServerConfigurerAdapter来配置OAuth2.0 授权服务器
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    //主要用于 "authorization_code" 授权码类型模式
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    //authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置
    //这个属性注入一个 AuthenticationManager 对象
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    //将客户端信息存储到数据库
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    //客户端详情服务,用来配置客户端详情服务（ClientDetailsService），客户端详情信息在
    //这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.withClientDetails(clientDetailsService);
       /* clients.inMemory()// 先测试使用in‐memory内存存储
                .withClient("c1")// client_id（必须的）用来标识客户的Id
                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端密钥，（需要值得信任的客户端）客户端安全码，如果有的话
                .resourceIds("res1")//可以访问的资源列表
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")
                .scopes("all")// all就是一个标识，允许的授权范围如：user-server标识，用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围
                .autoApprove(false)//false跳转到授权页面
                //加上验证回调地址
                .redirectUris("http://www.baidu.com")*/
                ;

       /* 一,测试授权码模式获取令牌：常用模式
          1./oauth/authorize：获取授权码端点。
          例：http://localhost:53020/uaa/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=http://www.baidu.com
            参数列表如下：
            client_id：客户端准入标识。
            response_type：授权码模式固定为code。
            scope：客户端权限。
            redirect_uri：跳转uri，当授权码申请成功后会跳转到此地址，并在后边带上code参数（授权码）。例：https://www.baidu.com/?code=4y6ZNP

          2./oauth/token：获取令牌端点。
          例：http://localhost:53020/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=authorization_code&code=5PgfcD&redirect_uri=http://www.baidu.com
            client_id：客户端准入标识。
            client_secret：客户端秘钥。
            grant_type：授权类型，填写authorization_code，表示授权码模式
            code：授权码，就是刚刚获取的授权码，注意：授权码只使用一次就无效了，需要重新申请。
            redirect_uri：申请授权码时的跳转url，一定和申请授权码时用的redirect_uri一致。
          二，简化模式,简化模式用于没有服务器端的第三方单页面应用
           例：http://localhost:53020/uaa/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_uri=http://www.baidu.com
            参数描述同授权码模式 ，注意response_type=token，说明是简化模式
            返回：token
            https://www.baidu.com/#access_token=d8c9d9fe-d5f3-4502-8c2f-71a3ae75f67f&token_type=bearer&expires_in=5723
          三，密码模式，密码泄露，一般用于自己开发的第一应用
             例：http://localhost:53020/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=password&username=shangsan&password=123
          四，客户端模式最不安全的模式。式一般用来提供给我们完全信任的服务器端服务。比如，合作方系统对接，拉取一组用户信息。
          例：http://localhost:53020/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=client_credentials
          */
    }


    //配置令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);//客户端详情服务
        service.setSupportRefreshToken(true);//支持刷新令牌
        service.setTokenStore(tokenStore);//令牌存储策略
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    //设置授权码模式的授权码如何存取，暂时采用内存方式
/*    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }*/

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    }

    //配置令牌访问端点

    /**
     * AuthorizationServerEndpointsConfigurer 这个配置对象有一个叫做 pathMapping() 的方法用来配置端点URL链
     * 接，它有两个参数：
     * 第一个参数：String 类型的，这个端点URL的默认链接。
     * 第二个参数：String 类型的，你要进行替代的URL链接。
     * 以上的参数都将以 "/" 字符为开始的字符串，框架的默认URL链接如下列表，可以作为这个 pathMapping() 方法的
     * 第一个参数：
     * /oauth/authorize：获取授权码端点。
     * /oauth/token：获取令牌端点。
     * /oauth/confirm_access：用户确认授权提交端点。
     * /oauth/error：授权服务错误信息端点。
     * /oauth/check_token：用于资源服务访问的令牌解析端点。
     * /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。
     * 需要注意的是授权端点这个URL应该被Spring Security保护起来只供授权用户访问.
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //密码模式需要 , 需要配置authenticationManager：认证管理器，和userDetailsService用户服务，认证管理器
                .authenticationManager(authenticationManager)
                //授权码模式需要，需要配置AuthorizationCodeServices授权码如何存取, 授权码服务
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenService())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST); //允许断点post提交
    }

    /**
     * 令牌端点的安全约束
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
         //tokenkey这个endpoint当使用JwtToken且使用非对称加密时，资源服务用于获取公钥而开放的，这里指这个endpoint完全公开
                .tokenKeyAccess("permitAll()")                    //oauth/token_key是公开
                .checkTokenAccess("permitAll()")                  //oauth/check_token公开 检测令牌
                .allowFormAuthenticationForClients()				//表单认证（申请令牌）
        ;
    }

}
