package com.developersboard.todoreact.controller;

import com.developersboard.todoreact.backend.persistence.domain.Todo;
import com.google.gson.Gson;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class TodoResourceTest {

  private static final String BASE_URL = "/api/v1/todos";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getAllTodos() throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(BASE_URL))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void getTodoById() throws Exception {
    fetchTodoById(MockMvcResultMatchers.jsonPath("$.name").exists());
  }

  @Test
  void getTodoByIdOnItemNotExists() throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + -1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void createTodo(TestInfo testInfo) throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders
                    .post(BASE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getTodoAsJson(testInfo.getDisplayName(), null)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().string("location",
                    Matchers.containsString(BASE_URL)));
  }

  @Test
  void createTodoOnEmptyName() {
    Assertions.assertThrows(Exception.class, () -> this.mockMvc.perform(MockMvcRequestBuilders
            .post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getTodoAsJson("", null))));
  }

  @Test
  void updateTodo(TestInfo testInfo) throws Exception {
    fetchTodoById(MockMvcResultMatchers.jsonPath("$.name").value("Buy Milk"));
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getTodoAsJson(testInfo.getDisplayName(), 1L));
    this.mockMvc
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
    fetchTodoById(MockMvcResultMatchers.jsonPath("$.name").value(testInfo.getDisplayName()));
  }

  @Test
  void updateTodoOnEmptyTodo() {
    Assertions.assertThrows(Exception.class, () -> this.mockMvc.perform(MockMvcRequestBuilders
            .put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getTodoAsJson("", null))));
  }

  @Test
  void deleteTodo() throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.delete(BASE_URL + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + 1))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  private void fetchTodoById(ResultMatcher exists) throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(BASE_URL + "/1"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(exists)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  private String getTodoAsJson(String name, Long id) {
    Todo todo = new Todo(name);
    todo.setId(id);
    return  new Gson().toJson(todo);
  }
}
