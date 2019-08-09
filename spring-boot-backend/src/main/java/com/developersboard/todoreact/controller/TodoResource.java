package com.developersboard.todoreact.controller;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.service.TodoService;
import com.developersboard.todoreact.backend.service.ValidationService;
import com.developersboard.todoreact.exception.TodoResourceUnavailableException;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/v1/todos")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class TodoResource {

  private final TodoService todoService;
  private final ValidationService validationService;

  @Autowired
  public TodoResource(TodoService todoService, ValidationService validationService) {
    this.todoService = todoService;
    this.validationService = validationService;
  }

  @GetMapping
  public Iterable<Todo> getAllTodos() {
    return todoService.getAllTodoItems();
  }

  /**
   * Retrieve todo item from database that matches the given id.
   *
   * @param id id
   * @return todo
   */
  @GetMapping(path = {"/{id}"})
  public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
    try {
      validationService.validateInputs(getClass(), id);
      return ResponseEntity.ok(todoService.getTodoById(id));
    } catch (TodoResourceUnavailableException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Create and persist a new todo item to the database.
   *
   * @param todo todo
   * @return persisted todo
   */
  @PostMapping
  public ResponseEntity<?> createTodo(@Valid @RequestBody Todo todo, BindingResult bindingResult) {
    ResponseEntity<?> errorResponse = validationService.validateTodoObject(bindingResult);
    if (errorResponse != null) {
      return errorResponse;
    }
    URI location = getUriFromCreatedTodo(todo);
    return ResponseEntity.created(location).build();
  }

  /**
   * Updates a todo object in the database.
   *
   * @param todo todo
   * @return updated todo
   */
  @PutMapping
  public ResponseEntity<?> updateTodo(@Valid @RequestBody Todo todo, BindingResult bindingResult) {
    ResponseEntity<?> errorResponse = validationService.validateTodoObject(bindingResult);
    if (errorResponse != null) {
      return errorResponse;
    }
    Todo updatedTodo = todoService.saveOrUpdate(todo);
    return ResponseEntity.ok(updatedTodo);
  }

  /**
   * Delete todo item that matches the given id from the database.
   *
   * @param id id
   * @return ok status
   */
  @DeleteMapping(path = {"/{id}"})
  public ResponseEntity<Todo> deleteTodo(@PathVariable Long id) {
    validationService.validateInputs(getClass(), id);
    todoService.deleteTodoById(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Todo> deleteAllTodos() {
    todoService.deleteAllTodos();
    return ResponseEntity.ok().build();
  }

  /**
   * Builds a location to the newly created resource by expanding the existing path
   * request from /api/v1/todos will build to include id of new object as /api/v1/todos/3.
   *
   * @param todo todo
   * @return uri of todo created
   * @throws RuntimeException error while saving todo
   */
  private URI getUriFromCreatedTodo(Todo todo) throws RuntimeException {
    Todo savedTodo = todoService.saveOrUpdate(todo);
    return ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedTodo.getId())
      .toUri();
  }
}
