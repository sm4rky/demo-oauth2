package com.demo.microservices.common.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class CommonAuditConfig {
    @Bean
    public AuditorAware<String> headerAuditorAware() {
        return new HeaderAuditorAware();
    }
}
