package com.marketplace.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Désactive le ErrorPageFilter de Spring Boot pour éviter les boucles circulaires
 * avec les pages d'erreur JSF.
 */
@Configuration
public class ErrorPageFilterConfig {

    @Bean
    public FilterRegistrationBean<org.springframework.boot.web.servlet.support.ErrorPageFilter> disableErrorPageFilter() {
        FilterRegistrationBean<org.springframework.boot.web.servlet.support.ErrorPageFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new org.springframework.boot.web.servlet.support.ErrorPageFilter());
        registration.setEnabled(false);
        registration.setOrder(-100);
        return registration;
    }
}
