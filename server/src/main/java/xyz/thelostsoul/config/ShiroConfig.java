package xyz.thelostsoul.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import xyz.thelostsoul.base.FirstShiroRealm;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 散列算法
    private static final String HASH_ALGORITHM = "md5";
    // 散列次数
    private static final Integer HASH_TIMES = 2;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/");

        //配置不登录可以访问的资源，anon 表示资源都可以匿名访问
        //配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/api/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/api/logout", "logout");
        //user表示配置记住我或认证通过可以访问的地址
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public Realm shiroRealm() {
        FirstShiroRealm shiroRealm = new FirstShiroRealm();
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM);
//        hashedCredentialsMatcher.setHashIterations(HASH_TIMES);

        SimpleCredentialsMatcher matcher = new SimpleCredentialsMatcher();
        shiroRealm.setCredentialsMatcher(matcher);
        return shiroRealm;
    }
}
