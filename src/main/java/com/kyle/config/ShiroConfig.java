package com.kyle.config;


import com.kyle.shiro.CustomizedModularRealmAuthenticator;
import com.kyle.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ShiroConfig {

    /*
     * 设置securityManager运行环境
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactroyBean(@Qualifier("defaultWebSecurityManager")DefaultWebSecurityManager defaultWebSecurityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

       /* //设置登录页面
        shiroFilterFactoryBean.setLoginUrl("/login");*/
        //无权限得情况下跳转得方法
        // shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");

/*        Map filtes = new HashMap<>();

        //设置权限
        filtes.put("/insert","perms[user_editsjh]");
        filtes.put("/delete","perms[user_editsss]");
        filtes.put("/update","perms[user_forbiddens]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filtes);*/

        //设置securityManager运行环境
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        return shiroFilterFactoryBean;
    }


    /*
     * 创建securityManager实例
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myShiroRealm")MyShiroRealm myShiroRealm,@Qualifier("customizedModularRealmAuthenticator") CustomizedModularRealmAuthenticator customizedModularRealmAuthenticator ){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(customizedModularRealmAuthenticator);
//        defaultWebSecurityManager.setRealm(myShiroRealm);
        List<Realm> list =new ArrayList<>();
        list.add(myShiroRealm);
        defaultWebSecurityManager.setRealms(list);

        return defaultWebSecurityManager;
    }

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        //credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    @Bean("myShiroRealm")
    public MyShiroRealm loginRealm(@Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher hashedCredentialsMatcher){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setAuthorizationCachingEnabled(false);
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myShiroRealm;
    }

    /**
     * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    /**
     * 开启aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     * 配置使用自定义认证器，可以实现多Realm认证，并且可以指定特定Realm处理特定类型的验证
     * 配置认证策略，只要有一个Realm认证成功即可，并且返回所有认证成功信息
     */
  @Bean
    public CustomizedModularRealmAuthenticator customizedModularRealmAuthenticator(){
       CustomizedModularRealmAuthenticator customizedModularRealmAuthenticator = new CustomizedModularRealmAuthenticator();
      AtLeastOneSuccessfulStrategy atLeastOneSuccessfulStrategy = new AtLeastOneSuccessfulStrategy();
       customizedModularRealmAuthenticator.setAuthenticationStrategy(atLeastOneSuccessfulStrategy);
       return customizedModularRealmAuthenticator;
  }
}