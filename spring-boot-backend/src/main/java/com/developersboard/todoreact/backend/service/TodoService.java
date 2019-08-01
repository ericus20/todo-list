package com.developersboard.todoreact.backend.service;

import com.developersboard.todoreact.backend.persistence.domain.Todo;

import org.springframework.stereotype.Service;

@Service
public interface TodoService {

  Todo saveOrUpdate(Todo todo);

  Iterable<Todo> saveOrUpdateAll(Iterable<Todo> todos);

  Iterable<Todo> getAllTodoItems();

  Todo getTodoById(Long id);

  void deleteTodoById(Long id);
}
