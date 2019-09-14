package com.developersboard.backend.backend.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.developersboard.backend.backend.persistence.domain.Todo;
import com.developersboard.backend.backend.persistence.repository.TodoRepository;
import com.developersboard.backend.backend.service.TodoService;
import com.developersboard.backend.exception.TodoResourceUnavailableException;

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
  public Iterable<Todo> getAllTodoItems() throws TodoResourceUnavailableException {
    Iterable<Todo> todos = todoRepository.findAll();
    if (todos.spliterator().getExactSizeIfKnown() == 0) {
      throw new TodoResourceUnavailableException("There are no todos available");
    }
    return todos;
  }

  @Override
  public Todo getTodoById(Long id) {
    return todoRepository.findById(id).orElseThrow(TodoResourceUnavailableException::new);
  }

  @Override
  @Transactional
  public void deleteTodoById(Long id) {
    todoRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteAllTodos() {
    todoRepository.deleteAll();
  }
}
