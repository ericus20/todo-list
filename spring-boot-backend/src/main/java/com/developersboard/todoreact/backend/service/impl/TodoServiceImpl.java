package com.developersboard.todoreact.backend.service.impl;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.persistence.repository.TodoRepository;
import com.developersboard.todoreact.backend.service.TodoService;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Autowired
  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public Todo saveOrUpdate(Todo todo) {
    return todoRepository.save(todo);
  }

  @Override
  public Iterable<Todo> getAllTodoItems() {
    return todoRepository.findAll();
  }

  @Override
  public Todo getTodoById(Long id) {
    // TODO: 7/30/2019 Create custom exception for Todo Object
    return todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Override
  public void deleteTodoById(Long id) {
    todoRepository.deleteById(id);
  }
}
