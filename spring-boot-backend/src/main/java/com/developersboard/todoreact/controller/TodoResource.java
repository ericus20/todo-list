package com.developersboard.todoreact.controller;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.service.TodoService;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  public TodoResource(TodoService todoService) {
    this.todoService = todoService;
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
    Todo todoById = todoService.getTodoById(id);
    if (todoById == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(todoById);
  }

  /**
   * Create and persist a new todo item to the database.
   *
   * @param todo todo
   * @return persisted todo
   */
  @PostMapping
  public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
    if (todo.getDueDate() == null) {
      todo.setDueDate(LocalDate.now().plusDays(2));
    }
    Todo savedTodo = todoService.saveOrUpdate(todo);
    if (savedTodo == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // build a location to the newly created resource by expanding the existing path.
    // request from /api/v1/todos will build to include id of new object as /api/v1/todos/3
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedTodo.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  /**
   * Updates a todo object in the database.
   *
   * @param todo todo
   * @return updated todo
   */
  @PutMapping
  public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo) {
    Todo updatedTodo = todoService.saveOrUpdate(todo);
    if (updatedTodo == null) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
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
    todoService.deleteTodoById(id);
    return ResponseEntity.ok().build();
  }
}
