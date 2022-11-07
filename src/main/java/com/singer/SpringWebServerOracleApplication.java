package com.singer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SpringWebServerOracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServerOracleApplication.class, args);
    }

}
