package com.demo.microservices.common.config.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class HeaderAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
