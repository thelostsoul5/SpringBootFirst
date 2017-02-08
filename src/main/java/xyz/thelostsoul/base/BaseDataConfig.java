package xyz.thelostsoul.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author zhouzm5
 * @version v1.0.0
 * @Copyright Copyright (c) 2017 AsiaInfo
 * @ClassName
 * @Description
 * @date 2017/2/8 <br>
 * Modification History:<br>
 * Date Author Version Description<br>
 * ---------------------------------------------------------*<br>
 * 2017/2/8 zhouzm5 v1.0.0 <br>
 */

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
