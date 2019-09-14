package com.developersboard.backend.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface ValidationService {

  /**
   * Takes a binding result object and check if there exists any error.
   *
   * @param result binding result
   * @return response entity
   */
  ResponseEntity<?> validateTodoObject(BindingResult result);

  /**
   * Validates inputs passed to controller.
   *
   * @param inputs input objects
   */
  void validateInputs(Class<?> clazz, Object... inputs);
}
