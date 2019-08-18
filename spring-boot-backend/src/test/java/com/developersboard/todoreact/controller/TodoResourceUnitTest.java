package com.developersboard.todoreact.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.developersboard.todoreact.backend.service.TodoService;
import com.developersboard.todoreact.backend.service.ValidationService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {TodoResource.class})
class TodoResourceUnitTest {

  private static final String BASE_URL = "/api/v1/todos";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TodoService todoService;

  @MockBean
  private ValidationService validationService;

  @Test
  void assertMocksNotNull() {
    Assertions.assertAll("Assert that mocks are initialized appropriately", () -> {
      Assertions.assertNotNull(todoService);
      Assertions.assertNotNull(validationService);
    });
  }

  @Test
  void getAllTodos(TestInfo testInfo) throws Exception {
    List<Todo> todos = Collections.singletonList(
            new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2))
    );
    Mockito.when(todoService.getAllTodoItems()).thenReturn(todos);
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].name").value(testInfo.getDisplayName()));
    Mockito.verify(todoService, Mockito.atMost(1)).getAllTodoItems();
  }

  @Test
  void getTodoById(TestInfo testInfo) throws Exception {
    Todo todo = new Todo(testInfo.getDisplayName(), LocalDate.now().plusDays(2));
    todo.setId(1L);
    Mockito.doReturn(todo).when(todoService).getTodoById(Mockito.eq(1L));
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.name").value(todo.getName()))
            .andExpect(jsonPath("$.id").value(1L));
    Mockito.verify(todoService, Mockito.atMost(1)).getTodoById(1L);
  }
}