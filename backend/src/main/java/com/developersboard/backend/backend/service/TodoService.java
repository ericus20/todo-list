package com.developersboard.backend.backend.service;

import org.springframework.stereotype.Service;

import com.developersboard.backend.backend.persistence.domain.Todo;

@Service
public interface TodoService {

  Todo saveOrUpdate(Todo todo);

  Iterable<Todo> saveOrUpdateAll(Iterable<Todo> todos);

  Iterable<Todo> getAllTodoItems();

  Todo getTodoById(Long id);

  void deleteTodoById(Long id);

  void deleteAllTodos();
}
