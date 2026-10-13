package com.marketplace.config;

import com.marketplace.repository.impl.GenericRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.marketplace.repository",
    repositoryBaseClass = GenericRepositoryImpl.class
)
public class RepositoryConfig {
    // Configuration pour activer les repositories génériques
}
