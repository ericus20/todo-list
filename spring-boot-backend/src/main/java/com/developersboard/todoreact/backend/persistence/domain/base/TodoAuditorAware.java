package com.developersboard.todoreact.backend.persistence.domain.base;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

/**
 * This class gets the application's current auditor.
 *
 * @author Eric Opoku on 7/29/2019
 * @version 1.0
 * @since 1.0
 */
public class TodoAuditorAware implements AuditorAware<String> {

  /**
   * Returns the current auditor of the application.
   *
   * @return the current auditor
   */
  @Override
  public Optional<String> getCurrentAuditor() {
    // the system will be used as the current auditor.
    return Optional.of("system");

  }
}
