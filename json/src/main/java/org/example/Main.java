package org.example;

import org.example.property.AddressProp;
import org.example.property.GrapphonerProp;
import org.example.property.SecurityProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
@EnableConfigurationProperties(value = {SecurityProp.class, GrapphonerProp.class, AddressProp.class})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}