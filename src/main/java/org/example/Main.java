package org.example;

import org.example.property.SecurityProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(value = {SecurityProp.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}