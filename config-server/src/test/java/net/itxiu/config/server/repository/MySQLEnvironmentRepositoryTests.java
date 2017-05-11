package net.itxiu.config.server.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.ConfigServerApplication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        value = {"server.port:8080","spring.config.name:configserver"}
        ,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
        ,classes = {ConfigServerApplication.class}
)
@ActiveProfiles("mysql")
public class MySQLEnvironmentRepositoryTests {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Value("${server.port}")
    private int port;

    @Test
    public void testMySQL() {

        Environment environment = restTemplate.getForObject("http://localhost:"+port+"/testapp-dev.yml", Environment.class);
        System.out.println();
    }



    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerApplication.class).profiles("mysql").properties(
                "spring.config.name=configserver","server.port=8080").run(args);
    }

}