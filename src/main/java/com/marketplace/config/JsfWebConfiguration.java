package com.marketplace.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Enregistre les filtres pour les URLs Facelets.
 */
@Configuration
public class JsfWebConfiguration {

    @Bean
    public FilterRegistrationBean<FlashAttributesFilter> flashAttributesFilterRegistration() {
        FilterRegistrationBean<FlashAttributesFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new FlashAttributesFilter());
        reg.addUrlPatterns("*.xhtml");
        reg.setOrder(1);
        return reg;
    }

    @Bean
    public FilterRegistrationBean<JsfLocaleFilter> jsfLocaleFilterRegistration(LocaleResolver localeResolver) {
        FilterRegistrationBean<JsfLocaleFilter> reg = new FilterRegistrationBean<>();
        reg.setFilter(new JsfLocaleFilter(localeResolver));
        reg.addUrlPatterns("*.xhtml");
        reg.setOrder(0);
        return reg;
    }
}
