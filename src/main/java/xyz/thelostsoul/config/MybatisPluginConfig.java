package xyz.thelostsoul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.thelostsoul.base.TableRouteInterceptor;

@Configuration
public class MybatisPluginConfig {
    @Bean(name = "tableSplitInterceptor")
    public TableRouteInterceptor tableSplitInterceptor() {
        return new TableRouteInterceptor();
    }
}
