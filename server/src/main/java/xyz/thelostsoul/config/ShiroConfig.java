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

    // ɢ���㷨
    private static final String HASH_ALGORITHM = "md5";
    // ɢ�д���
    private static final Integer HASH_TIMES = 2;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/");

        //���ò���¼���Է��ʵ���Դ��anon ��ʾ��Դ��������������
        //���ü�ס�һ���֤ͨ�����Է��ʵĵ�ַ
        filterChainDefinitionMap.put("/api/login", "anon");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        //logout��shiro�ṩ�Ĺ�����
        filterChainDefinitionMap.put("/api/logout", "logout");
        //user��ʾ���ü�ס�һ���֤ͨ�����Է��ʵĵ�ַ
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
