package org.example.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(value = "grapphoner")
@Data
public class GrapphonerProp {
    String url;
    String key;
    BigDecimal minLatitude;
    BigDecimal maxLatitude;
    BigDecimal minLongitude;
    BigDecimal maxLongitude;
}
