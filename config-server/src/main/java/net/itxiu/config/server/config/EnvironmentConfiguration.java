package net.itxiu.config.server.config;

import net.itxiu.config.server.repository.MySQLEnvironmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
@Profile("jdbc")
public class EnvironmentConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public EnvironmentRepository environmentRepository(){
        return new MySQLEnvironmentRepository(jdbcTemplate);
    }

}
