package com.nopain.livetv.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@EnableConfigurationProperties(JwtProperties.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {
    private String secret;
    private Long expiration;
}
