package xyz.thelostsoul.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class BaseDataConfig {
    private final Logger LOG = LoggerFactory.getLogger(BaseDataConfig.class);
    @Bean(name="primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="datasource.primary")
    public DataSource primaryDataSource() {
        LOG.info("-------------------- primaryDataSource init ---------------------");
        return DataSourceBuilder.create().build();
    }
}
