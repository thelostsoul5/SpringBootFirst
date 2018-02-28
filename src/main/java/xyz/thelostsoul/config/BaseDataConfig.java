package xyz.thelostsoul.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import xyz.thelostsoul.base.Database;
import xyz.thelostsoul.base.MultipleDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Profile("dev")
public class BaseDataConfig {

    private final Logger LOG = LoggerFactory.getLogger(BaseDataConfig.class);

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource primaryDataSource() {
        LOG.info("-------------------- primaryDataSource init ---------------------");
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "datasource.second")
    public DataSource secondDataSource() {
        LOG.info("-------------------- secondDataSource init ---------------------");
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("b")
                .addScript("classpath:/H2_TYPE.sql")
                .addScript("classpath:/INIT_TABLE.sql")
                .addScript("classpath:/INIT_DATA2.sql")
                .build();
    }

    @Bean(name = "multiDataSource")
    public DataSource multiDataSource(@Qualifier("primaryDataSource") DataSource primaryDataSource,
                                      @Qualifier("secondDataSource") DataSource secondDataSource) {
        Map targetDataSources = new HashMap();
        targetDataSources.put(Database.primary, primaryDataSource);
        targetDataSources.put(Database.second, secondDataSource);
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setTargetDataSources(targetDataSources);
        multipleDataSource.setDefaultTargetDataSource(primaryDataSource);
        return multipleDataSource;
    }
}
