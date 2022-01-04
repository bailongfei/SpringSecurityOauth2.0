package com.itheima.security.distributed.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author Administrator
 * @version 1.0
 * 在ResouceServerConfig中定义资源服务配置，主要配置的内容就是定义一些匹配规则，描述某个接入客户端需要
 * 什么样的权限才能访问某个微服务
 **/
@Configuration
public class ResouceServerConfig  {

    public static final String RESOURCE_ID = "res1";

  //网关当做资源服务配置，一个资源微服务配置一个config

    //uaa资源服务配置
    @Configuration
    @EnableResourceServer
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore)//jwt自行验证
                    .resourceId(RESOURCE_ID)//uaa访问资源列表配置一致，令牌可以访问的节点
                    .stateless(true);
        }

        /*请求资源安全验证配置*/
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                 .antMatchers("/uaa/**").permitAll(); //UAAServerConfig指定了若请求匹配/uaa/**网关不进行拦截。
        }
    }


    //order资源
    @Configuration
    @EnableResourceServer
    public class OrderServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources){
            resources.tokenStore(tokenStore)
                     .resourceId(RESOURCE_ID)
                    .stateless(true);
        }

     //OrderServerConfig指定了若请求匹配/order/**，也就是访问统一用户服务，
     // 接入客户端需要有scope中包含read，并且authorities(权限)中需要包含ROLE_USER。由于res1这个接入客户端，read包括ROLE_ADMIN,ROLE_USER,ROLE_API三个权限。
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    //校验用户权限，所有请求必须包含scopes("ROLE_ADMIN")，客户端允许的授权范围
                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }


    //配置其它的资源服务..


}
