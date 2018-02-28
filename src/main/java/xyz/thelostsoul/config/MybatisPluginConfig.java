package xyz.thelostsoul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.thelostsoul.base.TableSplitInterceptor;

@Configuration
public class MybatisPluginConfig {
    @Bean(name = "tableSplitInterceptor")
    public TableSplitInterceptor tableSplitInterceptor() {
        return new TableSplitInterceptor();
    }
}
