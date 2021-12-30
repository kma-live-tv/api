package com.nopain.livetv.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "srs")
@Getter
@Setter
public class SrsProperties {
    private String apiUrl;
}
