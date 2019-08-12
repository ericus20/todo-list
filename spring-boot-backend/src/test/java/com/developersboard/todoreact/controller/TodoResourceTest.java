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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ActiveProfiles(value = {"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoResourceTest {

  private static final String BASE_URL = "/api/v1/todos";

  @Autowired
  private MockMvc mockMvc;

  @Test
  void getAllTodos(TestInfo testInfo) throws Exception {
    createTodoItem(testInfo);
    fetchTodo(BASE_URL, MockMvcResultMatchers.status().isOk());
  }

  @Test
  void getTodoById(TestInfo testInfo) throws Exception {
    MvcResult result = createTodoItem(testInfo);
    fetchTodoById(MockMvcResultMatchers.jsonPath("$.name").exists(), result);
  }

  @Test
  void getTodoByIdOnItemNotExists() throws Exception {
    fetchTodo(BASE_URL + "/" + -1, MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void createTodo(TestInfo testInfo) throws Exception {
    createTodoItem(testInfo);
  }

  @Test
  void createTodoOnEmptyName() throws Exception {
    requestOnEmptyName(MockMvcRequestBuilders.post(BASE_URL));
  }

  @Test
  void updateTodo(TestInfo testInfo) throws Exception {
    MvcResult result = createTodoItem(testInfo);
    fetchTodoById(MockMvcResultMatchers.jsonPath("$.name").exists(), result);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(getTodoAsJson(testInfo.getDisplayName(), 1L));
    this.mockMvc
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
    fetchTodoById(
          MockMvcResultMatchers.jsonPath("$.name").value(testInfo.getDisplayName()), result);
  }

  @Test
  void updateTodoOnEmptyTodo() throws Exception {
    requestOnEmptyName(MockMvcRequestBuilders.put(BASE_URL));
  }

  @Test
  void deleteTodoById(TestInfo testInfo) throws Exception {
    deleteAllTodos();
    MvcResult result = createTodoItem(testInfo);
    this.mockMvc
            .perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + getIdFromMvcResult(result)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
    fetchTodo(BASE_URL + "/1", MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void deleteAllTodos() throws Exception {
    deleteTodo(MockMvcRequestBuilders.delete(BASE_URL), MockMvcResultMatchers.status().isOk());
    fetchTodo(BASE_URL, MockMvcResultMatchers.status().isNotFound());
  }

  private void requestOnEmptyName(MockHttpServletRequestBuilder post) throws Exception {
    this.mockMvc
            .perform(post
                .contentType(MediaType.APPLICATION_JSON)
                .content(getTodoAsJson("", null)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
            .value("Todo item name is required"));
  }

  private void deleteTodo(MockHttpServletRequestBuilder del, ResultMatcher ok) throws Exception {
    this.mockMvc
            .perform(del)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(ok);
  }

  private void fetchTodo(String baseUrl, ResultMatcher ok) throws Exception {
    deleteTodo(MockMvcRequestBuilders.get(baseUrl), ok);
  }

  private void fetchTodoById(ResultMatcher exists, MvcResult result) throws Exception {
    this.mockMvc
            .perform(MockMvcRequestBuilders.get(BASE_URL + "/" + getIdFromMvcResult(result)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(exists)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }

  private MvcResult createTodoItem(TestInfo testInfo) throws Exception {
    return this.mockMvc
      .perform(MockMvcRequestBuilders
        .post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(getTodoAsJson(testInfo.getDisplayName(), null)))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.header().string("location",
        Matchers.containsString(BASE_URL)))
      .andReturn();
  }

  private String getIdFromMvcResult(MvcResult result) {
    String header = result.getResponse().getHeader("location");
    Assertions.assertNotNull(header);
    String[] split = header.split("/");
    return split[split.length - 1];
  }

  private String getTodoAsJson(String name, Long id) {
    Todo todo = new Todo(name);
    todo.setId(id);
    return new Gson().toJson(todo);
  }
}
