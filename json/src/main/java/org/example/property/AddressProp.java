package org.example.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(value = "address")
@Data
public class AddressProp {
    private String url;
    private String apiKey;
}
