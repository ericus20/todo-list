package com.developersboard.todoreact.backend.service;

import static org.mockito.Mockito.inOrder;
import static org.mockito.internal.verification.VerificationModeFactory.calls;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.persistence.repository.TodoRepository;
import com.developersboard.todoreact.backend.service.impl.TodoServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceUnitTest {

  @Mock
  private TodoRepository todoRepository;

  @InjectMocks
  private TodoServiceImpl todoService;

  @Test
  void saveOrUpdate(TestInfo testInfo) {
    Todo todo = new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2));
    todo.setId(1L);
    Mockito.when(todoRepository.save(Mockito.any(Todo.class))).thenReturn(todo);

    Todo saveOrUpdate = todoService.saveOrUpdate(new Todo(testInfo.getDisplayName()));
    Assertions.assertEquals(saveOrUpdate, todo);
  }

  @Test
  void saveOrUpdateAll(TestInfo testInfo) {
    Iterable<Todo> todos = getMockedTodos(testInfo, todoRepository.saveAll(Mockito.anyIterable()));
    Iterable<Todo> actual = todoService.saveOrUpdateAll(Arrays.asList(new Todo(), new Todo()));
    inOrder(todoRepository).verify(todoRepository, calls(1)).saveAll(Mockito.anyIterable());
    Assertions.assertEquals(todos, actual);
  }

  @Test
  void getAllTodoItems(TestInfo testInfo) {
    Iterable<Todo> todos = getMockedTodos(testInfo, todoRepository.findAll());
    Iterable<Todo> actual = todoService.getAllTodoItems();
    Assertions.assertEquals(todos, actual);
  }

  @Test
  void getTodoById(TestInfo testInfo) {
    Todo todo = new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2));
    Mockito.when(todoRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(todo));
    Todo todoById = todoService.getTodoById(1L);
    Mockito.verify(todoRepository, Mockito.atMost(1)).findById(Mockito.anyLong());
    Assertions.assertEquals(todo, todoById);
  }

  private Iterable<Todo> getMockedTodos(TestInfo testInfo, Iterable<Todo> todoIterable) {
    Iterable<Todo> todos = Arrays.asList(
            new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2)),
            new Todo(testInfo.getDisplayName().concat("2"), LocalDate.now().plusDays(2))
    );
    Mockito.when(todoIterable).thenReturn(todos);
    return todos;
  }
}