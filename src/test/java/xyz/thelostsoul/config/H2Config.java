package xyz.thelostsoul.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

/**
 * Created by jamie on 17-2-22.
 */
@Configuration
@Profile("dev")
public class H2Config {

    @Bean(name="startH2",initMethod = "start",destroyMethod = "stop")
    public Server startH2() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort","8043","-tcpPassword","sa");
    }
}
