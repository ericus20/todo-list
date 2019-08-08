package com.developersboard.todoreact.backend.service;

import com.developersboard.todoreact.backend.persistence.domain.Todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TodoServiceTest {

  @Autowired
  private TodoService todoService;

  @Test
  void saveTodo(TestInfo testInfo) {
    createAndVerifyTodoItem(testInfo);
  }

  @Test
  void updateTodo(TestInfo testInfo) {
    Todo todo = createAndVerifyTodoItem(testInfo);
    todo.setName(todo.getName().concat("Updated"));
    Todo updatedTodo = todoService.saveOrUpdate(todo);
    Assertions.assertAll("Update should not create new todo", () -> {
      Assertions.assertNotNull(updatedTodo);
      Assertions.assertEquals(updatedTodo.getName(), testInfo.getDisplayName().concat("Updated"));
      Assertions.assertEquals(updatedTodo.getId(), todo.getId());
    });
  }

  @Test
  void saveOrUpdateAll(TestInfo testInfo) {
    Todo[] todoItems = {
            new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2)),
            new Todo(testInfo.getDisplayName().concat("1"), LocalDate.now().plusDays(2), true),
    };
    Iterable<Todo> todos = Arrays.asList(todoItems);
    Iterable<Todo> todoIterable = todoService.saveOrUpdateAll(todos);
    ArrayList<Todo> todoList = new ArrayList<>();
    todoIterable.forEach(todoList::add);

    for (Todo todoItem : todoItems) {
      Assertions.assertTrue(todoList.contains(todoItem));
    }
  }

  @Test
  void getAllTodoItems(TestInfo testInfo) {
    Todo todo = createAndVerifyTodoItem(testInfo);
    Iterable<Todo> allTodoItems = todoService.getAllTodoItems();
    ArrayList<Todo> list = new ArrayList<>();
    allTodoItems.forEach(list::add);

    Assertions.assertTrue(list.contains(todo));
  }

  @Test
  void getTodoById(TestInfo testInfo) {
    Todo todo = createAndVerifyTodoItem(testInfo);
    Todo todoById = todoService.getTodoById(todo.getId());
    Assertions.assertAll("Returned object must match as expected", () -> {
      Assertions.assertNotNull(todoById);
      Assertions.assertEquals(todoById, todo, "Equals does not include id and should be true");
    });
  }

  @Test
  void deleteTodoById(TestInfo testInfo) {
    Todo todo = createAndVerifyTodoItem(testInfo);
    Assertions.assertDoesNotThrow(() -> todoService.deleteTodoById(todo.getId()));
  }

  private Todo createAndVerifyTodoItem(TestInfo testInfo) {
    Todo todo = new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2));
    Todo savedTodo = todoService.saveOrUpdate(todo);
    Assertions.assertAll("Todo creation should succeed and return valid object", () -> {
      Assertions.assertNotNull(savedTodo);
      Assertions.assertNotNull(savedTodo.getId());
    });
    return todo;
  }
}