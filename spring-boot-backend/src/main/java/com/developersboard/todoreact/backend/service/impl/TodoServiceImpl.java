package com.developersboard.todoreact.backend.service.impl;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.persistence.repository.TodoRepository;
import com.developersboard.todoreact.backend.service.TodoService;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Autowired
  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  @Transactional
  public Todo saveOrUpdate(Todo todo) {
    if (todo.getDueDate() == null) {
      todo.setDueDate(LocalDate.now().plusDays(2));
    }
    return todoRepository.save(todo);
  }

  @Override
  @Transactional
  public Iterable<Todo> saveOrUpdateAll(Iterable<Todo> todos) {
    return todoRepository.saveAll(todos);
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
  @Transactional
  public void deleteTodoById(Long id) {
    todoRepository.deleteById(id);
  }
}
