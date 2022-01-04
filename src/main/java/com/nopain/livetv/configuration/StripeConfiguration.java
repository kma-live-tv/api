package com.nopain.livetv.configuration;

import com.nopain.livetv.properties.StripeProperties;
import com.stripe.Stripe;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StripeConfiguration {
    private final StripeProperties properties;

    @Bean
    public void configApiKey() {
        Stripe.apiKey = properties.getSecret();
    }
}
