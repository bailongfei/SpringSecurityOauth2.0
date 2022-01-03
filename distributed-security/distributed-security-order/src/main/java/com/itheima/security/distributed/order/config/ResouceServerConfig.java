package com.itheima.security.distributed.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {


    public static final String RESOURCE_ID = "res1"; //和uaad 的resourceIds("res1")//访问资源列表配置一致，令牌可以访问的节点

    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID)//资源 id
                .tokenStore(tokenStore) //jwt自行验证
//                .tokenServices(tokenService())//token验证令牌的服务
                .stateless(true);
    }

    /*请求资源安全验证配置*/
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                //所有请求必须包含scopes("ROLE_ADMIN")，客户端允许的授权范围
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')") //jtw之前的aouth2.0配置，和颁发令牌scopes("all")标识一致
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//使用令牌，session不用生成
    }

    /**
     * 令牌解析方法： 使用 DefaultTokenServices 在资源服务器本地配置令牌存储、解码、解析方式 使用
     * RemoteTokenServices 资源服务器通过 HTTP 请求来解码令牌，每次都请求授权服务器端点 /oauth/check_token
     * @return
     */
    //资源服务令牌解析服务
 /*   @Bean
    public ResourceServerTokenServices tokenService() {
        //使用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
        RemoteTokenServices service=new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token"); //token验证地址
        service.setClientId("c1"); //客户端id
        service.setClientSecret("secret"); //客户端密码
        return service;
    }*/

}
