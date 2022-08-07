package by.naumenka.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan({"by.naumenka.dao.impl", "by.naumenka.storage", "by.naumenka.service"})
@PropertySource("classpath:application.properties")
@Configuration
public class WebConfigurationTest {

}