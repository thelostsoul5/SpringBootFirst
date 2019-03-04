package xyz.thelostsoul.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Created by jamie on 17-2-21.
 */
@Configuration
@Profile("dev")
@AutoConfigureAfter(H2Config.class)
public class TestDataConfig {
    @Bean(name="primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix="datasource.primary")
    public DataSource primaryDataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:/H2_TYPE.sql")
                .addScript("classpath:/INIT_TABLE.sql")
                .addScript("classpath:/INIT_DATA.sql")
                .build();
    }
}
