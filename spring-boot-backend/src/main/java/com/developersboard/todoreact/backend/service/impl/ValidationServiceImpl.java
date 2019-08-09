package com.developersboard.todoreact.backend.service.impl;

import com.developersboard.todoreact.backend.service.ValidationService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class ValidationServiceImpl implements ValidationService {
  /**
   * Takes a binding result object and check if there exists any error.
   *
   * @param result binding result
   * @return response entity
   */
  @Override
  public ResponseEntity<?> validateTodoObject(BindingResult result) {
    if (result.hasErrors()) {
      Map<String, String> errorMap = new HashMap<>();

      for (FieldError error : result.getFieldErrors()) {
        errorMap.put(error.getField(), error.getDefaultMessage());
      }
      return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
    return null;
  }

  /**
   * Validates inputs passed to controller.
   *
   * @param inputs input objects
   */
  @Override
  public void validateInputs(Class<?> clazz, Object... inputs) {
    for (Object object : inputs) {
      if (object == null) {
        throw new IllegalArgumentException("Null values not allowed in " + clazz.getName());
      }
    }
  }
}
