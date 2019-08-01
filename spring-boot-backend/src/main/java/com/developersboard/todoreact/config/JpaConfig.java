package com.developersboard.todoreact.config;

import com.developersboard.todoreact.backend.persistence.domain.base.TodoAuditorAware;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EntityScan(basePackages = "com.developersboard.todoreact.backend.persistence.domain")
@EnableJpaRepositories("com.developersboard.todoreact.backend.persistence.repository")
public class JpaConfig {

  /**
   * A bean to be served for teh AuditorAware interface.
   *
   * @return auditorAware instance.
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return new TodoAuditorAware();
  }
}
