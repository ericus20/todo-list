package com.developersboard.todoreact.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration targeting the JPA and transactions of database.
 *
 * @author Eric Opoku on 7/29/2019
 * @version 1.0
 * @since 1.0
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.developersboard.todoreact.backend.persistence.domain")
@EnableJpaRepositories(basePackages = "com.developersboard.todoreact.backend.persistence.repository")
public class JpaConfig {
}
